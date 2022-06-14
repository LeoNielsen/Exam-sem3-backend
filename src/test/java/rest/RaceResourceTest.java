package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RaceDTO;
import entities.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class RaceResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Driver driver1, driver2, driver3;
    private static User user1, user2, user3;
    private static Car car1, car2, car3;
    private static Race race1, race2, race3;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("JB","test123");
        user2 = new User("AnneW","test123");
        user3 = new User("test","test123");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        user3.addRole(adminRole);
        user3.addRole(userRole);

        car1 = new Car("Lynet","Mercedes","Series 3","2018","Rolex","Silver",new ArrayList<>(), new ArrayList<>());
        car2 = new Car("Bravo","BMW","MX3","2020","DC","Black",new ArrayList<>(), new ArrayList<>());
        car3 = new Car("test","test","test","test","test","test",new ArrayList<>(), new ArrayList<>());

        driver1 = new Driver("James Brown","1997","amateur","male", user1, car1);
        driver2 = new Driver("Anna West", "2001", "professional", "female",user2, car2);
        driver3 = new Driver("test", "test", "test", "tset",user3, car3);

        race1 = new Race("Nas", "Miami", "27-06-22", "210", new ArrayList<>());
        race2 = new Race("Le Mans", "Nice", "17-08-22", "210", new ArrayList<>());

        car1.addDriver(driver1);
        car2.addDriver(driver2);
        car3.addDriver(driver3);

        race1.addCar(car1);
        race1.addCar(car2);
        race2.addCar(car1);

        car1.addRace(race1);
        car1.addRace(race2);
        car2.addRace(race1);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("user.deleteAllRows").executeUpdate();
            em.createNamedQuery("race.deleteAllRows").executeUpdate();
            em.createNamedQuery("car.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();

            em.persist(adminRole);
            em.persist(userRole);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(driver1);
            em.persist(driver2);
            em.persist(driver3);
            em.persist(car1);
            em.persist(car2);
            em.persist(car3);
            em.persist(race1);
            em.persist(race2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/race").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void hello() {
        given()
                .contentType("application/json")
                .get("/race/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello from race"));
    }


    @Test
    void getAll() {
        given()
                .contentType("application/json")
                .get("/race/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems(race1.getName(), race2.getName()));
    }

    @Test
    void getCars() {
        given()
                .contentType("application/json")
                .get("/race/getcars/" + race1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems(car1.getName(), car2.getName()));
    }

    @Test
    void getRaceById() {
        login("test", "test123");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/race/"+race2.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(race2.getName()));
    }

    @Test
    void createRace() {
        login("test", "test123");

        race3 = new Race("test","test","test","test",new ArrayList<>());
        race3.addCar(car3);
        race3.addCar(car2);
        race3.addCar(car1);
        RaceDTO raceDTO = new RaceDTO(race3);
        String data = GSON.toJson(raceDTO);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(data)
                .post("/race/create").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(raceDTO.getName()))
                .body("carsId", hasItems(Integer.valueOf(String.valueOf(car2.getId())),Integer.valueOf(String.valueOf(car3.getId()))));
    }

    @Test
    void updateRace() {
        login("test", "test123");

        race1.setName("updated");
        RaceDTO raceDTO = new RaceDTO(race1);
        String data = GSON.toJson(raceDTO);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(data)
                .put("/race/update/"+race1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(race1.getName()));
    }

    @Test
    void deleteRace() {
        login("test", "test123");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .delete("/race/delete/"+race1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(race1.getName()));
    }


}
