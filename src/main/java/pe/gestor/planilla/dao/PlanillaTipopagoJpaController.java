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
import pe.gestor.planilla.dto.PlanillaTipopago;

/**
 *
 * @author Adria
 */
public class PlanillaTipopagoJpaController implements Serializable {

    public PlanillaTipopagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipopago planillaTipopago) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipopago);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipopago(planillaTipopago.getCodiTipoPago()) != null) {
                throw new PreexistingEntityException("PlanillaTipopago " + planillaTipopago + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipopago planillaTipopago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipopago = em.merge(planillaTipopago);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipopago.getCodiTipoPago();
                if (findPlanillaTipopago(id) == null) {
                    throw new NonexistentEntityException("The planillaTipopago with id " + id + " no longer exists.");
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
            PlanillaTipopago planillaTipopago;
            try {
                planillaTipopago = em.getReference(PlanillaTipopago.class, id);
                planillaTipopago.getCodiTipoPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipopago with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaTipopago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipopago> findPlanillaTipopagoEntities() {
        return findPlanillaTipopagoEntities(true, -1, -1);
    }

    public List<PlanillaTipopago> findPlanillaTipopagoEntities(int maxResults, int firstResult) {
        return findPlanillaTipopagoEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipopago> findPlanillaTipopagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipopago.class));
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

    public PlanillaTipopago findPlanillaTipopago(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipopago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipopagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipopago> rt = cq.from(PlanillaTipopago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
