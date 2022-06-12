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

    @Test
    void createTmp1() {
        Tmp1 tmp1 = new Tmp1("test");
        Tmp1DTO tmp1DTO = facade.createTmp1(new Tmp1DTO(tmp1));
        List<Tmp1DTO> tmp1DTOs = facade.getAll();

        assertEquals("test", tmp1DTO.getDummy());
        assertEquals(3, tmp1DTOs.size());
    }

    @Test
    void updateTmp1() {
        tmp11.setDummy("updated");
        Tmp1DTO tmp1DTO = facade.updateTmp1(tmp11.getId(),new Tmp1DTO(tmp11));
        List<Tmp1DTO> tmp1DTOs = facade.getAll();

        assertEquals("updated", tmp1DTO.getDummy());
        assertEquals(2, tmp1DTOs.size());
    }

    @Test
    void deleteTmp1() {
        Tmp1DTO tmp1DTO = facade.deleteTmp1(tmp11.getId());
        List<Tmp1DTO> tmp1DTOs = facade.getAll();

        assertEquals("1", tmp1DTO.getDummy());
        assertEquals(1, tmp1DTOs.size());
    }
}
