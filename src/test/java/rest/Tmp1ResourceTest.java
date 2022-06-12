package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Tmp1DTO;
import entities.Tmp1;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class Tmp1ResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Tmp1 tmp11, tmp12;

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
        tmp11 = new Tmp1("1");
        tmp12 = new Tmp1("2");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Tmp1.deleteAllRows").executeUpdate();
            em.persist(tmp11);
            em.persist(tmp12);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/tmp1").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void hello() {
        given()
                .contentType("application/json")
                .get("/tmp1/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello from tmp1"));
    }


    @Test
    void getAll() {
        given()
                .contentType("application/json")
                .get("/tmp1/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dummy", hasItems("1","2"));
    }

    @Test
    void getTmp1ById() {
        given()
                .contentType("application/json")
                .get("/tmp1/"+tmp12.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dummy", equalTo(tmp12.getDummy()));
    }

    @Test
    void createTmp1() {
        Tmp1DTO tmp1DTO = new Tmp1DTO(new Tmp1("test"));
        String data = GSON.toJson(tmp1DTO);

        given()
                .contentType("application/json")
                .body(data)
                .post("/tmp1/create").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dummy", equalTo(tmp1DTO.getDummy()));
    }

    @Test
    void updateTmp1() {
        tmp11.setDummy("updated");
        Tmp1DTO tmp1DTO = new Tmp1DTO(tmp11);
        String data = GSON.toJson(tmp1DTO);

        given()
                .contentType("application/json")
                .body(data)
                .put("/tmp1/update/"+tmp11.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dummy", equalTo(tmp11.getDummy()));
    }

    @Test
    void deleteTmp1() {
        given()
                .contentType("application/json")
                .delete("/tmp1/delete/"+tmp11.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dummy", equalTo(tmp11.getDummy()));
    }
}
