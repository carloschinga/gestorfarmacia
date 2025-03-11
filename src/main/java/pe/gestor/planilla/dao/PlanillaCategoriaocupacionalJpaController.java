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
import pe.gestor.planilla.dto.PlanillaCategoriaocupacional;

/**
 *
 * @author Adria
 */
public class PlanillaCategoriaocupacionalJpaController implements Serializable {

    public PlanillaCategoriaocupacionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaCategoriaocupacional planillaCategoriaocupacional)
            throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaCategoriaocupacional);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaCategoriaocupacional(planillaCategoriaocupacional.getCodiCateOcup()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaCategoriaocupacional " + planillaCategoriaocupacional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaCategoriaocupacional planillaCategoriaocupacional)
            throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaCategoriaocupacional = em.merge(planillaCategoriaocupacional);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaCategoriaocupacional.getCodiCateOcup();
                if (findPlanillaCategoriaocupacional(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaCategoriaocupacional with id " + id + " no longer exists.");
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
            PlanillaCategoriaocupacional planillaCategoriaocupacional;
            try {
                planillaCategoriaocupacional = em.getReference(PlanillaCategoriaocupacional.class, id);
                planillaCategoriaocupacional.getCodiCateOcup();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The planillaCategoriaocupacional with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaCategoriaocupacional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaCategoriaocupacional> findPlanillaCategoriaocupacionalEntities() {
        return findPlanillaCategoriaocupacionalEntities(true, -1, -1);
    }

    public List<PlanillaCategoriaocupacional> findPlanillaCategoriaocupacionalEntities(int maxResults,
            int firstResult) {
        return findPlanillaCategoriaocupacionalEntities(false, maxResults, firstResult);
    }

    private List<PlanillaCategoriaocupacional> findPlanillaCategoriaocupacionalEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaCategoriaocupacional.class));
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

    public PlanillaCategoriaocupacional findPlanillaCategoriaocupacional(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaCategoriaocupacional.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaCategoriaocupacionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaCategoriaocupacional> rt = cq.from(PlanillaCategoriaocupacional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
