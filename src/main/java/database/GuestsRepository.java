package database;

import model.Guests;
import org.tinylog.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GuestsRepository {

    /**
     * This find rows by name in the Guests table.
     * @param selectedName
     * @return a list of Guests
     */

    public List<Guests> findByName(String selectedName) {
        EntityManager em = EmfGetter.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Guests> cq = cb.createQuery(Guests.class);
        Root<Guests> from = cq.from(Guests.class);

        cq.select(from).where(cb.like(from.get("name"), selectedName));
        try {
            Query q = em.createQuery(cq);
            Logger.info("Select completed successful");
            return q.getResultList();
        } catch (Exception e) {
            Logger.error("Select failed");
        } finally {
            em.close();
        }
        return new ArrayList<>();
    }

    /**
     * Insert new guest in the table.
     * @param newGuest
     */
    public void insertGuest(Guests newGuest){
        EntityManager em = EmfGetter.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newGuest);
            em.getTransaction().commit();
            Logger.info("Inserting new guest into the database successfully");
        }catch (Exception e){
            Logger.error("Inserting new guest into the database failed");
        }finally {
            em.close();
        }
    }

    /**
     * Allows you to change the data in the Guests table cells.
     * @param change
     */
    public void  commitChange(Guests change){
        EntityManager em = EmfGetter.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(change);
            em.getTransaction().commit();
            Logger.info("Commit success");
        }catch (Exception e){
            Logger.error("Commit failed");
        }finally {
            em.close();
        }
    }

    /**
     * Remove Guests from the table.
     * @param entity
     */
}
