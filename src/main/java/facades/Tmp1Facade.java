package facades;

import dtos.Tmp1DTO;
import entities.Tmp1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import error handling.RenameMeNotFoundException;


/**
 * Rename Class to a relevant name Add relevant facade methods
 */
public class Tmp1Facade {

    private static Tmp1Facade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private Tmp1Facade() {
    }

    public static Tmp1Facade getTmp1Facade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Tmp1Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Tmp1DTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Tmp1> query = em.createQuery("SELECT tmp1 FROM Tmp1 tmp1", Tmp1.class);
            List<Tmp1> tmp1s = query.getResultList();
            return Tmp1DTO.getDTOs(tmp1s);
        } finally {
            em.close();
        }
    }

    public Tmp1DTO getTmp1ById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Tmp1 tmp1 = em.find(Tmp1.class, id);
            return new Tmp1DTO(tmp1);
        } finally {
            em.close();
        }
    }

    public Tmp1DTO createTmp1(Tmp1DTO tmp1DTO) {
        EntityManager em = emf.createEntityManager();
        Tmp1 tmp1 = new Tmp1(tmp1DTO.getDummy());

        try {
            em.getTransaction().begin();
            em.persist(tmp1);
            em.getTransaction().commit();
            return new Tmp1DTO(tmp1);
        } finally {
            em.close();
        }
    }

    public Tmp1DTO updateTmp1(long id, Tmp1DTO tmp1DTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Tmp1 tmp1 = em.find(Tmp1.class, id);
            tmp1.setId(id);
            tmp1.setDummy(tmp1DTO.getDummy());

            em.getTransaction().begin();
            em.merge(tmp1);
            em.getTransaction().commit();
            return new Tmp1DTO(tmp1);
        } finally {
            em.close();
        }
    }

    public Tmp1DTO deleteTmp1(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Tmp1 tmp1 = em.find(Tmp1.class, id);

            em.getTransaction().begin();
            em.remove(tmp1);
            em.getTransaction().commit();
            return new Tmp1DTO(tmp1);
        } finally {
            em.close();
        }
    }
}
