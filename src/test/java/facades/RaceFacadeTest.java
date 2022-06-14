package facades;

import dtos.CarDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import entities.User;
import utils.EMF_Creator;

import java.util.ArrayList;
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

    private static Driver driver1, driver2, driver3;
    private static User user1, user2, user3;
    private static Car car1, car2, car3;
    private static Race race1, race2, race3;

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

        user1 = new User("JB","test123");
        user2 = new User("AnneW","test123");
        user3 = new User("test","test123");

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
    void getCars() {
        List<CarDTO> carDTOS = facade.getCars(race1.getId());

        assertEquals(2, carDTOS.size());
    }

    @Test
    void getRaceById() {
        RaceDTO raceDTO = facade.getRaceById(race2.getId());

        assertEquals("Le Mans", raceDTO.getName());
    }

    @Test
    void createRace() {
        race3 = new Race("test","test","test","test",new ArrayList<>());
        race3.addCar(car3);
        RaceDTO raceDTO = facade.createRace(new RaceDTO(race3));
        List<RaceDTO> raceDTOS = facade.getAll();

        assertEquals("test", raceDTO.getName());
        assertEquals(3, raceDTOS.size());
        assertEquals(car3.getId(), raceDTO.getCarsId().get(0));
    }

    @Test
    void updateRace() {
        race1.setName("updated");
        RaceDTO raceDTO = facade.updateRace(race1.getId(),new RaceDTO(race1));
        List<RaceDTO> raceDTOS = facade.getAll();

        assertEquals("updated", raceDTO.getName());
        assertEquals(2, raceDTOS.size());
    }

    @Test
    void deleteRace() {
        RaceDTO raceDTO = facade.deleteRace(race1.getId());
        List<RaceDTO> raceDTOS = facade.getAll();

        assertEquals("Nas", raceDTO.getName());
        assertEquals(1, raceDTOS.size());
    }
}
