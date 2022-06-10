package facades;

import dtos.Tmp3DTO;
import entities.Tmp3;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class Tmp3FacadeTest {

    private static EntityManagerFactory emf;
    private static Tmp3Facade facade;

    private static Tmp3 tmp31, tmp32;

    public Tmp3FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = Tmp3Facade.getTmp3Facade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        tmp31 = new Tmp3("1");
        tmp32 = new Tmp3("2");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Tmp3.deleteAllRows").executeUpdate();
            em.persist(tmp31);
            em.persist(tmp32);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }


    @Test
    void getAll() {
       List<Tmp3DTO> tmp3DTOs = facade.getAll();

       assertEquals(2, tmp3DTOs.size());
    }

    @Test
    void getTmp3ById() {
        Tmp3DTO tmp3DTO = facade.getTmp3ById(tmp32.getId());

        assertEquals("2", tmp3DTO.getDummy());
    }
}
