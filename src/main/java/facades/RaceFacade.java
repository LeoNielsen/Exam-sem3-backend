package facades;

import dtos.CarDTO;
import dtos.RaceDTO;
import entities.Car;
import entities.Race;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import error handling.RenameMeNotFoundException;


/**
 * Rename Class to a relevant name Add relevant facade methods
 */
public class RaceFacade {

    private static RaceFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private RaceFacade() {
    }

    public static RaceFacade getRaceFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RaceFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<RaceDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Race> query = em.createQuery("SELECT race FROM Race race", Race.class);
            List<Race> races = query.getResultList();
            return RaceDTO.getDTOs(races);
        } finally {
            em.close();
        }
    }

    public List<CarDTO> getCars(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Race race = em.find(Race.class, id);
            List<Car> carList = new ArrayList<>(race.getCars());
            return CarDTO.getDTOs(carList);
        } finally {
            em.close();
        }
    }

    public RaceDTO getRaceById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Race race = em.find(Race.class, id);
            return new RaceDTO(race);
        } finally {
            em.close();
        }
    }

    public RaceDTO createRace(RaceDTO raceDTO) {
        EntityManager em = emf.createEntityManager();

        Race race = new Race(raceDTO.getName(), raceDTO.getLocation(), raceDTO.getStartDate(), raceDTO.getDuration(), new ArrayList<>());

        if (raceDTO.getCarsId() != null) {
            for (long carId : raceDTO.getCarsId()) {
                Car car = em.find(Car.class, carId);
                car.addRace(race);
                race.addCar(car);
            }
        }

        try {
            em.getTransaction().begin();
            em.persist(race);

            for (Car car : race.getCars()) {
                em.merge(car);
            }

            em.getTransaction().commit();
            return new RaceDTO(race);
        } finally {
            em.close();
        }
    }

    public RaceDTO updateRace(long id, RaceDTO raceDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Race race = em.find(Race.class, id);
            em.getTransaction().begin();

            for (Car car : race.getCars()) {
                car.removeRace(race);
                em.merge(car);
            }

            race.setId(id);
            race.setName(raceDTO.getName());
            race.setLocation(raceDTO.getLocation());
            race.setStartDate(raceDTO.getStartDate());
            race.setDuration(raceDTO.getDuration());
            race.setName(raceDTO.getName());



            race.setCars(new ArrayList<>());

            for (Long carId : raceDTO.getCarsId()) {
                Car car = em.find(Car.class, carId);
                car.addRace(race);
                race.addCar(car);
                em.merge(car);
            }

            em.merge(race);
            em.getTransaction().commit();
            return new RaceDTO(race);
        } finally {
            em.close();
        }
    }

    public RaceDTO deleteRace(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Race race = em.find(Race.class, id);

            em.getTransaction().begin();
            em.remove(race);
            em.getTransaction().commit();
            return new RaceDTO(race);
        } finally {
            em.close();
        }
    }
}
