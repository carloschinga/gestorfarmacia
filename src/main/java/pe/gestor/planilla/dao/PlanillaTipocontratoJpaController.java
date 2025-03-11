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
import pe.gestor.planilla.dto.PlanillaTipocontrato;

/**
 *
 * @author Adria
 */
public class PlanillaTipocontratoJpaController implements Serializable {

    public PlanillaTipocontratoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipocontrato planillaTipocontrato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipocontrato);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipocontrato(planillaTipocontrato.getCodiTipoCntr()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaTipocontrato " + planillaTipocontrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipocontrato planillaTipocontrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipocontrato = em.merge(planillaTipocontrato);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipocontrato.getCodiTipoCntr();
                if (findPlanillaTipocontrato(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaTipocontrato with id " + id + " no longer exists.");
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
            PlanillaTipocontrato planillaTipocontrato;
            try {
                planillaTipocontrato = em.getReference(PlanillaTipocontrato.class, id);
                planillaTipocontrato.getCodiTipoCntr();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipocontrato with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaTipocontrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipocontrato> findPlanillaTipocontratoEntities() {
        return findPlanillaTipocontratoEntities(true, -1, -1);
    }

    public List<PlanillaTipocontrato> findPlanillaTipocontratoEntities(int maxResults, int firstResult) {
        return findPlanillaTipocontratoEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipocontrato> findPlanillaTipocontratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipocontrato.class));
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

    public PlanillaTipocontrato findPlanillaTipocontrato(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipocontrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipocontratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipocontrato> rt = cq.from(PlanillaTipocontrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
