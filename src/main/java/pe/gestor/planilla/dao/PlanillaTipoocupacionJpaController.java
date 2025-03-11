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
import pe.gestor.planilla.dto.PlanillaTipoocupacion;

/**
 *
 * @author Adria
 */
public class PlanillaTipoocupacionJpaController implements Serializable {

    public PlanillaTipoocupacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipoocupacion planillaTipoocupacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipoocupacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipoocupacion(planillaTipoocupacion.getCodiTipoOcup()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaTipoocupacion " + planillaTipoocupacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipoocupacion planillaTipoocupacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipoocupacion = em.merge(planillaTipoocupacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipoocupacion.getCodiTipoOcup();
                if (findPlanillaTipoocupacion(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaTipoocupacion with id " + id + " no longer exists.");
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
            PlanillaTipoocupacion planillaTipoocupacion;
            try {
                planillaTipoocupacion = em.getReference(PlanillaTipoocupacion.class, id);
                planillaTipoocupacion.getCodiTipoOcup();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipoocupacion with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaTipoocupacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipoocupacion> findPlanillaTipoocupacionEntities() {
        return findPlanillaTipoocupacionEntities(true, -1, -1);
    }

    public List<PlanillaTipoocupacion> findPlanillaTipoocupacionEntities(int maxResults, int firstResult) {
        return findPlanillaTipoocupacionEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipoocupacion> findPlanillaTipoocupacionEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipoocupacion.class));
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

    public PlanillaTipoocupacion findPlanillaTipoocupacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipoocupacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipoocupacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipoocupacion> rt = cq.from(PlanillaTipoocupacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
