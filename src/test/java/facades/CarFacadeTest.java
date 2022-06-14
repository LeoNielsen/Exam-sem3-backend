package facades;

import dtos.CarDTO;
import entities.Car;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;

    private static Car car1, car2;

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

        car1 = new Car("1");
        car2 = new Car("2");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("car.deleteAllRows").executeUpdate();
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
    void getCarById() {
        CarDTO carDTO = facade.getCarById(car2.getId());

        assertEquals("2", carDTO.getDummy());
    }

    @Test
    void createCar() {
        Car car = new Car("test");
        CarDTO carDTO = facade.createCar(new CarDTO(car));
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals("test", carDTO.getDummy());
        assertEquals(3, carDTOS.size());
    }

    @Test
    void updateCar() {
        car1.setDummy("updated");
        CarDTO carDTO = facade.updateCar(car1.getId(),new CarDTO(car1));
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals("updated", carDTO.getDummy());
        assertEquals(2, carDTOS.size());
    }

    @Test
    void deleteCar() {
        CarDTO carDTO = facade.deleteCar(car1.getId());
        List<CarDTO> carDTOS = facade.getAll();

        assertEquals("1", carDTO.getDummy());
        assertEquals(1, carDTOS.size());
    }
}
