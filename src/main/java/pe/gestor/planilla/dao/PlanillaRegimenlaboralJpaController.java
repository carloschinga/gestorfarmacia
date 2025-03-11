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
import pe.gestor.planilla.dto.PlanillaRegimenlaboral;

/**
 *
 * @author Adria
 */
public class PlanillaRegimenlaboralJpaController implements Serializable {

    public PlanillaRegimenlaboralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaRegimenlaboral planillaRegimenlaboral) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaRegimenlaboral);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaRegimenlaboral(planillaRegimenlaboral.getCodiRegiLabo()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaRegimenlaboral " + planillaRegimenlaboral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaRegimenlaboral planillaRegimenlaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaRegimenlaboral = em.merge(planillaRegimenlaboral);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaRegimenlaboral.getCodiRegiLabo();
                if (findPlanillaRegimenlaboral(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaRegimenlaboral with id " + id + " no longer exists.");
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
            PlanillaRegimenlaboral planillaRegimenlaboral;
            try {
                planillaRegimenlaboral = em.getReference(PlanillaRegimenlaboral.class, id);
                planillaRegimenlaboral.getCodiRegiLabo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaRegimenlaboral with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaRegimenlaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaRegimenlaboral> findPlanillaRegimenlaboralEntities() {
        return findPlanillaRegimenlaboralEntities(true, -1, -1);
    }

    public List<PlanillaRegimenlaboral> findPlanillaRegimenlaboralEntities(int maxResults, int firstResult) {
        return findPlanillaRegimenlaboralEntities(false, maxResults, firstResult);
    }

    private List<PlanillaRegimenlaboral> findPlanillaRegimenlaboralEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaRegimenlaboral.class));
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

    public PlanillaRegimenlaboral findPlanillaRegimenlaboral(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaRegimenlaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaRegimenlaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaRegimenlaboral> rt = cq.from(PlanillaRegimenlaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
