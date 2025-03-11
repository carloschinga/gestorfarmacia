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
import pe.gestor.planilla.dto.PlanillaArea;

/**
 *
 * @author Adria
 */
public class PlanillaAreaJpaController implements Serializable {

    public PlanillaAreaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaArea planillaArea) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaArea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaArea planillaArea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaArea = em.merge(planillaArea);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaArea.getCodiArea();
                if (findPlanillaArea(id) == null) {
                    throw new NonexistentEntityException("The planillaArea with id " + id + " no longer exists.");
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
            PlanillaArea planillaArea;
            try {
                planillaArea = em.getReference(PlanillaArea.class, id);
                planillaArea.getCodiArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaArea with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaArea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaArea> findPlanillaAreaEntities() {
        return findPlanillaAreaEntities(true, -1, -1);
    }

    public List<PlanillaArea> findPlanillaAreaEntities(int maxResults, int firstResult) {
        return findPlanillaAreaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaArea> findPlanillaAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaArea.class));
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

    public PlanillaArea findPlanillaArea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaArea.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaArea> rt = cq.from(PlanillaArea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
