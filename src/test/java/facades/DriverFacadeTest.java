package facades;

import dtos.DriverDTO;
import entities.Car;
import entities.Driver;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class DriverFacadeTest {

    private static EntityManagerFactory emf;
    private static DriverFacade facade;

    private static Driver driver1, driver2;
    private static User user1, user2, user3;
    private static Car car1, car2, car3;


    public DriverFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = DriverFacade.getDriverFacade(emf);
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

        car1 = new Car("Lynet","Merceds","Serie 3","2018","Rolex","Silver",new ArrayList<>());
        car2 = new Car("Bravo","BMW","MX3","2020","DC","Black",new ArrayList<>());
        car3 = new Car("test","test","test","test","test","test",new ArrayList<>());


        driver1 = new Driver("James Brown","1997","amateur","male", user1, null);
        driver2 = new Driver("Anna West", "2001", "professional", "female",user2, null);

        car1.addDriver(driver1);
        car2.addDriver(driver2);
        driver1.setCar(car1);
        driver2.setCar(car2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("driver.deleteAllRows").executeUpdate();
            em.createNamedQuery("user.deleteAllRows").executeUpdate();
            em.createNamedQuery("car.deleteAllRows").executeUpdate();
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(driver1);
            em.persist(driver2);
            em.persist(car1);
            em.persist(car2);
            em.persist(car3);
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
       List<DriverDTO> driverDTOS = facade.getAll();

       assertEquals(2, driverDTOS.size());
    }

    @Test
    void getDriverById() {
        DriverDTO driverDTO = facade.getDriverById(driver2.getId());

        assertEquals("Anna West", driverDTO.getName());
    }

    @Test
    void createDriver() {
        Driver driver = new Driver("test","test","test","test",user3, car3);
        DriverDTO driverDTO = facade.createDriver(new DriverDTO(driver));
        List<DriverDTO> driverDTOS = facade.getAll();

        assertEquals("test", driverDTO.getName());
        assertEquals(3, driverDTOS.size());
        assertEquals(car3.getId(), driverDTO.getCarId());
    }

    @Test
    void updateDriver() {
        driver1.setName("updated");
        DriverDTO driverDTO = facade.updateDriver(driver1.getId(),new DriverDTO(driver1));
        List<DriverDTO> driverDTOS = facade.getAll();

        assertEquals("updated", driverDTO.getName());
        assertEquals(2, driverDTOS.size());
    }

    @Test
    void deleteDriver() {
        DriverDTO driverDTO = facade.deleteDriver(driver1.getId());
        List<DriverDTO> driverDTOS = facade.getAll();

        assertEquals("James Brown", driverDTO.getName());
        assertEquals(1, driverDTOS.size());
    }
}
