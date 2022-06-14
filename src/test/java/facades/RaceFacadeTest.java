package facades;

import dtos.RaceDTO;
import entities.Race;
import utils.EMF_Creator;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class RaceFacadeTest {

    private static EntityManagerFactory emf;
    private static RaceFacade facade;

    private static Race race1, race2;

    public RaceFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = RaceFacade.getRaceFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        race1 = new Race("1");
        race2 = new Race("2");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("race.deleteAllRows").executeUpdate();
            em.persist(race1);
            em.persist(race2);

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
       List<RaceDTO> raceDTOS = facade.getAll();

       assertEquals(2, raceDTOS.size());
    }

    @Test
    void getRaceById() {
        RaceDTO raceDTO = facade.getRaceById(race2.getId());

        assertEquals("2", raceDTO.getDummy());
    }

    @Test
    void createRace() {
        Race race = new Race("test");
        RaceDTO raceDTO = facade.createRace(new RaceDTO(race));
        List<RaceDTO> raceDTOS = facade.getAll();

        assertEquals("test", raceDTO.getDummy());
        assertEquals(3, raceDTOS.size());
    }

    @Test
    void updateRace() {
        race1.setDummy("updated");
        RaceDTO raceDTO = facade.updateRace(race1.getId(),new RaceDTO(race1));
        List<RaceDTO> raceDTOS = facade.getAll();

        assertEquals("updated", raceDTO.getDummy());
        assertEquals(2, raceDTOS.size());
    }

    @Test
    void deleteRace() {
        RaceDTO raceDTO = facade.deleteRace(race1.getId());
        List<RaceDTO> raceDTOS = facade.getAll();

        assertEquals("1", raceDTO.getDummy());
        assertEquals(1, raceDTOS.size());
    }
}
