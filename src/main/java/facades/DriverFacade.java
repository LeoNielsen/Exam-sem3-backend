package facades;

import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Driver;
import entities.Race;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

//import error handling.RenameMeNotFoundException;


/**
 * Rename Class to a relevant name Add relevant facade methods
 */
public class DriverFacade {

    private static DriverFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DriverFacade() {
    }

    public static DriverFacade getDriverFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DriverFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<DriverDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("SELECT driver FROM Driver driver", Driver.class);
            List<Driver> drivers = query.getResultList();
            return DriverDTO.getDTOs(drivers);
        } finally {
            em.close();
        }
    }

    public DriverDTO getDriverById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Driver driver = em.find(Driver.class, id);
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }

    public DriverDTO createDriver(DriverDTO driverDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Car car = null;

            User user = em.find(User.class, driverDTO.getUser());

            if (driverDTO.getCarId() != null)
                car = em.find(Car.class, driverDTO.getCarId());

            Driver driver = new Driver(driverDTO.getName(), driverDTO.getBirthYear(), driverDTO.getExperience(), driverDTO.getGender(), user, car);

            em.getTransaction().begin();
            em.persist(driver);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }

    }

    public DriverDTO updateDriver(long id, DriverDTO driverDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Driver driver = em.find(Driver.class, id);

            Car oldCar = driver.getCar();
            oldCar.removeDriver(driver);

            driver.setId(id);
            driver.setName(driverDTO.getName());
            driver.setBirthYear(driverDTO.getBirthYear());
            driver.setExperience(driverDTO.getExperience());
            driver.setGender(driverDTO.getGender());


            Car car = em.find(Car.class,driverDTO.getCarId());

            driver.setCar(car);
            car.addDriver(driver);

            em.getTransaction().begin();
            em.merge(driver);
            em.merge(car);
            em.merge(oldCar);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }

    public DriverDTO deleteDriver(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Driver driver = em.find(Driver.class, id);

            em.getTransaction().begin();
            em.remove(driver);
            em.getTransaction().commit();
            return new DriverDTO(driver);
        } finally {
            em.close();
        }
    }

    public List<RaceDTO> getRacesByDriver(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d WHERE d.user.userName = :id", Driver.class);
            query.setParameter("id", id);
            Driver driver = query.getSingleResult();
            List<Race> races = new ArrayList<>(driver.getCar().getRaces());
            return RaceDTO.getDTOs(races);
        } finally {
            em.close();
        }
    }
}
