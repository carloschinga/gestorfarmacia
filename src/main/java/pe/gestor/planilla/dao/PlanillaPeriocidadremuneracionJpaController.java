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
import pe.gestor.planilla.dto.PlanillaPeriocidadremuneracion;

/**
 *
 * @author Adria
 */
public class PlanillaPeriocidadremuneracionJpaController implements Serializable {

    public PlanillaPeriocidadremuneracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaPeriocidadremuneracion planillaPeriocidadremuneracion)
            throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaPeriocidadremuneracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaPeriocidadremuneracion(planillaPeriocidadremuneracion.getCodiPeriRemu()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaPeriocidadremuneracion " + planillaPeriocidadremuneracion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaPeriocidadremuneracion planillaPeriocidadremuneracion)
            throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaPeriocidadremuneracion = em.merge(planillaPeriocidadremuneracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaPeriocidadremuneracion.getCodiPeriRemu();
                if (findPlanillaPeriocidadremuneracion(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaPeriocidadremuneracion with id " + id + " no longer exists.");
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
            PlanillaPeriocidadremuneracion planillaPeriocidadremuneracion;
            try {
                planillaPeriocidadremuneracion = em.getReference(PlanillaPeriocidadremuneracion.class, id);
                planillaPeriocidadremuneracion.getCodiPeriRemu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The planillaPeriocidadremuneracion with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaPeriocidadremuneracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaPeriocidadremuneracion> findPlanillaPeriocidadremuneracionEntities() {
        return findPlanillaPeriocidadremuneracionEntities(true, -1, -1);
    }

    public List<PlanillaPeriocidadremuneracion> findPlanillaPeriocidadremuneracionEntities(int maxResults,
            int firstResult) {
        return findPlanillaPeriocidadremuneracionEntities(false, maxResults, firstResult);
    }

    private List<PlanillaPeriocidadremuneracion> findPlanillaPeriocidadremuneracionEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaPeriocidadremuneracion.class));
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

    public PlanillaPeriocidadremuneracion findPlanillaPeriocidadremuneracion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaPeriocidadremuneracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaPeriocidadremuneracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaPeriocidadremuneracion> rt = cq.from(PlanillaPeriocidadremuneracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
