package facades;

import dtos.RaceDTO;
import entities.Race;

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
        Race race = new Race(raceDTO.getDummy());

        try {
            em.getTransaction().begin();
            em.persist(race);
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
            race.setId(id);
            race.setDummy(raceDTO.getDummy());

            em.getTransaction().begin();
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
