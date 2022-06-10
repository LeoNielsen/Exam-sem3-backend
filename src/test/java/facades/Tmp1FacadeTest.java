package facades;

import dtos.Tmp1DTO;
import utils.EMF_Creator;
import entities.Tmp1;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class Tmp1FacadeTest {

    private static EntityManagerFactory emf;
    private static Tmp1Facade facade;

    private static Tmp1 tmp11, tmp12;

    public Tmp1FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = Tmp1Facade.getTmp1Facade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }


    @Test
    void getAll() {
       List<Tmp1DTO> tmp1DTOs = facade.getAll();

       assertEquals(2, tmp1DTOs.size());
    }

    @Test
    void getTmp1ById() {
        Tmp1DTO tmp1DTO = facade.getTmp1ById(tmp12.getId());

        assertEquals("2", tmp1DTO.getDummy());
    }
}
