package facades;

import dtos.Tmp2DTO;
import entities.Tmp2;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class Tmp2FacadeTest {

    private static EntityManagerFactory emf;
    private static Tmp2Facade facade;

    private static Tmp2 tmp21, tmp22;

    public Tmp2FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = Tmp2Facade.getTmp2Facade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        tmp21 = new Tmp2("1");
        tmp22 = new Tmp2("2");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Tmp2.deleteAllRows").executeUpdate();
            em.persist(tmp21);
            em.persist(tmp22);

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
       List<Tmp2DTO> tmp2DTOs = facade.getAll();

       assertEquals(2, tmp2DTOs.size());
    }

    @Test
    void getTmp2ById() {
        Tmp2DTO tmp2DTO = facade.getTmp2ById(tmp22.getId());

        assertEquals("2", tmp2DTO.getDummy());
    }
}
