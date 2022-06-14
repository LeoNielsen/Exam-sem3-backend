package facades;


import dtos.CarDTO;
import entities.Car;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

//import error handling.RenameMeNotFoundException;


/**
 *
 * Rename Class to a relevant name Add relevant facade methods
 */
public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private CarFacade() {}

    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<CarDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Car> query = em.createQuery("SELECT car FROM Car car", Car.class);
            List<Car> cars = query.getResultList();
            return CarDTO.getDTOs(cars);
        } finally {
            em.close();
        }
    }

    public CarDTO getCarById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Car car = em.find(Car.class, id);
            return new CarDTO(car);
        } finally {
            em.close();
        }
    }

    public CarDTO createCar(CarDTO carDTO) {
        EntityManager em = emf.createEntityManager();
        Car car = new Car(carDTO.getDummy());

        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return new CarDTO(car);
        } finally {
            em.close();
        }
    }

    public CarDTO updateCar(long id, CarDTO carDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Car car = em.find(Car.class, id);
            car.setId(id);
            car.setDummy(carDTO.getDummy());

            em.getTransaction().begin();
            em.merge(car);
            em.getTransaction().commit();
            return new CarDTO(car);
        } finally {
            em.close();
        }
    }

    public CarDTO deleteCar(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Car car = em.find(Car.class, id);

            em.getTransaction().begin();
            em.remove(car);
            em.getTransaction().commit();
            return new CarDTO(car);
        } finally {
            em.close();
        }
    }
}
