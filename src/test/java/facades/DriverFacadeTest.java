package facades;

import dtos.DriverDTO;
import entities.Driver;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class DriverFacadeTest {

    private static EntityManagerFactory emf;
    private static DriverFacade facade;

    private static Driver driver1, driver2;
    private static User user1, user2, user3;


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

        driver1 = new Driver("James Brown","1997","amateur","male", user1);
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
        Driver driver = new Driver("test","test","test","test",user3);
        DriverDTO driverDTO = facade.createDriver(new DriverDTO(driver));
        List<DriverDTO> driverDTOS = facade.getAll();

        assertEquals("test", driverDTO.getName());
        assertEquals(3, driverDTOS.size());
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
