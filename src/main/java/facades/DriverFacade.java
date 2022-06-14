package facades;

import dtos.DriverDTO;
import entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

//import error handling.RenameMeNotFoundException;


/**
 *
 * Rename Class to a relevant name Add relevant facade methods
 */
public class DriverFacade {

    private static DriverFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private DriverFacade() {}
    
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
    
    public List<DriverDTO> getAll(){
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
        Driver driver = new Driver(driverDTO.getDummy());

        try {
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
            driver.setId(id);
            driver.setDummy(driverDTO.getDummy());

            em.getTransaction().begin();
            em.merge(driver);
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
}