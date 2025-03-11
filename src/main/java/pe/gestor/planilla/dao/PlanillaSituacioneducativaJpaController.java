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
import pe.gestor.planilla.dto.PlanillaSituacioneducativa;

/**
 *
 * @author Adria
 */
public class PlanillaSituacioneducativaJpaController implements Serializable {

    public PlanillaSituacioneducativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaSituacioneducativa planillaSituacioneducativa)
            throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaSituacioneducativa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaSituacioneducativa(planillaSituacioneducativa.getCodiSituEduc()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaSituacioneducativa " + planillaSituacioneducativa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaSituacioneducativa planillaSituacioneducativa)
            throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaSituacioneducativa = em.merge(planillaSituacioneducativa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaSituacioneducativa.getCodiSituEduc();
                if (findPlanillaSituacioneducativa(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaSituacioneducativa with id " + id + " no longer exists.");
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
            PlanillaSituacioneducativa planillaSituacioneducativa;
            try {
                planillaSituacioneducativa = em.getReference(PlanillaSituacioneducativa.class, id);
                planillaSituacioneducativa.getCodiSituEduc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The planillaSituacioneducativa with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaSituacioneducativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaSituacioneducativa> findPlanillaSituacioneducativaEntities() {
        return findPlanillaSituacioneducativaEntities(true, -1, -1);
    }

    public List<PlanillaSituacioneducativa> findPlanillaSituacioneducativaEntities(int maxResults, int firstResult) {
        return findPlanillaSituacioneducativaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaSituacioneducativa> findPlanillaSituacioneducativaEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaSituacioneducativa.class));
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

    public PlanillaSituacioneducativa findPlanillaSituacioneducativa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaSituacioneducativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaSituacioneducativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaSituacioneducativa> rt = cq.from(PlanillaSituacioneducativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
