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
import pe.gestor.planilla.dto.PlanillaCargo;

/**
 *
 * @author Adria
 */
public class PlanillaCargoJpaController implements Serializable {

    public PlanillaCargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaCargo planillaCargo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaCargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaCargo planillaCargo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaCargo = em.merge(planillaCargo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaCargo.getCodiCargo();
                if (findPlanillaCargo(id) == null) {
                    throw new NonexistentEntityException("The planillaCargo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanillaCargo planillaCargo;
            try {
                planillaCargo = em.getReference(PlanillaCargo.class, id);
                planillaCargo.getCodiCargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaCargo with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaCargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaCargo> findPlanillaCargoEntities() {
        return findPlanillaCargoEntities(true, -1, -1);
    }

    public List<PlanillaCargo> findPlanillaCargoEntities(int maxResults, int firstResult) {
        return findPlanillaCargoEntities(false, maxResults, firstResult);
    }

    private List<PlanillaCargo> findPlanillaCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaCargo.class));
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

    public PlanillaCargo findPlanillaCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaCargo> rt = cq.from(PlanillaCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
