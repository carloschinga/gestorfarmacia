package pe.gestor.planilla.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.planilla.dao.exceptions.NonexistentEntityException;
import pe.gestor.planilla.dao.exceptions.PreexistingEntityException;
import pe.gestor.planilla.dto.PlanillaSituacionespecial;

/**
 *
 * @author Adria
 */
public class PlanillaSituacionespecialJpaController implements Serializable {

    public PlanillaSituacionespecialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaSituacionespecial planillaSituacionespecial)
            throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaSituacionespecial);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaSituacionespecial(planillaSituacionespecial.getCodiSituEspe()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaSituacionespecial " + planillaSituacionespecial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaSituacionespecial planillaSituacionespecial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaSituacionespecial = em.merge(planillaSituacionespecial);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaSituacionespecial.getCodiSituEspe();
                if (findPlanillaSituacionespecial(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaSituacionespecial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanillaSituacionespecial planillaSituacionespecial;
            try {
                planillaSituacionespecial = em.getReference(PlanillaSituacionespecial.class, id);
                planillaSituacionespecial.getCodiSituEspe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The planillaSituacionespecial with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaSituacionespecial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaSituacionespecial> findPlanillaSituacionespecialEntities() {
        return findPlanillaSituacionespecialEntities(true, -1, -1);
    }

    public List<PlanillaSituacionespecial> findPlanillaSituacionespecialEntities(int maxResults, int firstResult) {
        return findPlanillaSituacionespecialEntities(false, maxResults, firstResult);
    }

    private List<PlanillaSituacionespecial> findPlanillaSituacionespecialEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaSituacionespecial.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PlanillaSituacionespecial findPlanillaSituacionespecial(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaSituacionespecial.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaSituacionespecialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaSituacionespecial> rt = cq.from(PlanillaSituacionespecial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
