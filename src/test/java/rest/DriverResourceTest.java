package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DriverDTO;
import entities.Driver;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class DriverResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Driver driver1, driver2;
    private static User user1, user2, user3;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
        user1 = new User("JB", "test123");
        user2 = new User("AnneW", "test123");
        user3 = new User("test", "test123");

        driver1 = new Driver("James Brown", "1997", "amateur", "male", user1);
        driver2 = new Driver("Anna West", "2001", "professional", "female", user2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("user.deleteAllRows").executeUpdate();
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(driver1);
            em.persist(driver2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/driver").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void hello() {
        given()
                .contentType("application/json")
                .get("/driver/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello from driver"));
    }


    @Test
    void getAll() {
        given()
                .contentType("application/json")
                .get("/driver/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems(driver1.getName(), driver2.getName()));
    }

    @Test
    void getDriverById() {
        given()
                .contentType("application/json")
                .get("/driver/" + driver2.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(driver2.getName()));
    }

    @Test
    void createDriver() {
        DriverDTO driverDTO = new DriverDTO(new Driver("test","test","test","test",user3));
        String data = GSON.toJson(driverDTO);

        given()
                .contentType("application/json")
                .body(data)
                .post("/driver/create").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(driverDTO.getName()));
    }

    @Test
    void updateDriver() {
        driver1.setName("updated");
        DriverDTO driverDTO = new DriverDTO(driver1);
        String data = GSON.toJson(driverDTO);

        given()
                .contentType("application/json")
                .body(data)
                .put("/driver/update/" + driver1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(driver1.getName()));
    }

    @Test
    void deleteDriver() {
        given()
                .contentType("application/json")
                .delete("/driver/delete/" + driver1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo(driver1.getName()));
    }
}
