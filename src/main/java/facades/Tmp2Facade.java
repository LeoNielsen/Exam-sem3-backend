package facades;


import dtos.Tmp2DTO;
import entities.Tmp2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

//import error handling.RenameMeNotFoundException;


/**
 *
 * Rename Class to a relevant name Add relevant facade methods
 */
public class Tmp2Facade {

    private static Tmp2Facade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private Tmp2Facade() {}

    public static Tmp2Facade getTmp2Facade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Tmp2Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<Tmp2DTO> getAll(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Tmp2> query = em.createQuery("SELECT tmp2 FROM Tmp2 tmp2", Tmp2.class);
            List<Tmp2> tmp2s = query.getResultList();
            return Tmp2DTO.getDTOs(tmp2s);
        } finally {
            em.close();
        }
    }

    public Tmp2DTO getTmp2ById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Tmp2 tmp2 = em.find(Tmp2.class, id);
            return new Tmp2DTO(tmp2);
        } finally {
            em.close();
        }
    }
}
