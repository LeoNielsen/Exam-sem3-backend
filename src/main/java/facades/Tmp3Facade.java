package facades;

import dtos.Tmp3DTO;
import entities.Tmp3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

//import error handling.RenameMeNotFoundException;


/**
 *
 * Rename Class to a relevant name Add relevant facade methods
 */
public class Tmp3Facade {

    private static Tmp3Facade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private Tmp3Facade() {}
    
    public static Tmp3Facade getTmp3Facade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Tmp3Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<Tmp3DTO> getAll(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Tmp3> query = em.createQuery("SELECT tmp3 FROM Tmp3 tmp3", Tmp3.class);
            List<Tmp3> tmp3s = query.getResultList();
            return Tmp3DTO.getDTOs(tmp3s);
        } finally {
            em.close();
        }
    }

    public Tmp3DTO getTmp3ById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Tmp3 tmp3 = em.find(Tmp3.class, id);
            return new Tmp3DTO(tmp3);
        } finally {
            em.close();
        }
    }
}
