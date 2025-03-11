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
import pe.gestor.planilla.dto.PlanillaTipoocupprivado;

/**
 *
 * @author Adria
 */
public class PlanillaTipoocupprivadoJpaController implements Serializable {

    public PlanillaTipoocupprivadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipoocupprivado planillaTipoocupprivado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipoocupprivado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipoocupprivado(planillaTipoocupprivado.getCodiTipoOcupPriv()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaTipoocupprivado " + planillaTipoocupprivado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipoocupprivado planillaTipoocupprivado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipoocupprivado = em.merge(planillaTipoocupprivado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipoocupprivado.getCodiTipoOcupPriv();
                if (findPlanillaTipoocupprivado(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaTipoocupprivado with id " + id + " no longer exists.");
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
            PlanillaTipoocupprivado planillaTipoocupprivado;
            try {
                planillaTipoocupprivado = em.getReference(PlanillaTipoocupprivado.class, id);
                planillaTipoocupprivado.getCodiTipoOcupPriv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipoocupprivado with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaTipoocupprivado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipoocupprivado> findPlanillaTipoocupprivadoEntities() {
        return findPlanillaTipoocupprivadoEntities(true, -1, -1);
    }

    public List<PlanillaTipoocupprivado> findPlanillaTipoocupprivadoEntities(int maxResults, int firstResult) {
        return findPlanillaTipoocupprivadoEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipoocupprivado> findPlanillaTipoocupprivadoEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipoocupprivado.class));
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

    public PlanillaTipoocupprivado findPlanillaTipoocupprivado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipoocupprivado.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipoocupprivadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipoocupprivado> rt = cq.from(PlanillaTipoocupprivado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
