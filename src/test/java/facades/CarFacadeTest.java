package facades;

import dtos.CarDTO;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;

    private static Driver driver1, driver2, driver3;
    private static User user1, user2, user3;
    private static Car car1, car2, car3;

    public CarFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CarFacade.getCarFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("JB", "test123");
        user2 = new User("AnneW", "test123");
        user3 = new User("test", "test123");

        car1 = new Car("Lynet", "Mercedes", "Series 3", "2018", "Rolex", "Silver", new ArrayList<>(), new ArrayList<>());
        car2 = new Car("Bravo", "BMW", "MX3", "2020", "DC", "Black", new ArrayList<>(), new ArrayList<>());

        driver1 = new Driver("James Brown", "1997", "amateur", "male", user1, car1);
        driver2 = new Driver("Anna West", "2001", "professional", "female", user2, car2);
        driver3 = new Driver("test", "test", "test", "tset", user3, null);


        car1.addDriver(driver1);
        car2.addDriver(driver2);

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
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals(2, carDTOS.size());
    }

    @Test
    void getDrivers() {
        List<DriverDTO> driverDTOS = facade.getDrivers(car1.getId());

        assertEquals(1, driverDTOS.size());
        assertEquals(driver1.getName(), driverDTOS.get(0).getName());
    }


    @Test
    void getCarById() {
        CarDTO carDTO = facade.getCarById(car2.getId());

        assertEquals("Bravo", carDTO.getName());
        assertEquals(driver2.getId(), carDTO.getDriversIds().get(0));
    }

    @Test
    void createCar() {
        car3 = new Car("test", "test", "test", "test", "test", "test", new ArrayList<>(), new ArrayList<>());

        CarDTO carDTO = facade.createCar(new CarDTO(car3));
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals("test", carDTO.getName());
        assertEquals(0, carDTO.getDriversIds().size());
        assertEquals(3, carDTOS.size());
    }

    @Test
    void updateCar() {
        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(driver3);

        car1.setName("updated");
        car1.setDrivers(drivers);
        CarDTO carDTO = facade.updateCar(car1.getId(), new CarDTO(car1));
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals("updated", carDTO.getName());
        assertEquals(driver3.getId(), carDTO.getDriversIds().get(0));
        assertEquals(2, carDTOS.size());
    }

    @Test
    void deleteCar() {
        CarDTO carDTO = facade.deleteCar(car1.getId());
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals("Lynet", carDTO.getName());
        assertEquals(1, carDTOS.size());
    }

}
