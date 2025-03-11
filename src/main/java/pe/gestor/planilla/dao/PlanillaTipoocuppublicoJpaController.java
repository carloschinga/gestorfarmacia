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
import pe.gestor.planilla.dto.PlanillaTipoocuppublico;

/**
 *
 * @author Adria
 */
public class PlanillaTipoocuppublicoJpaController implements Serializable {

    public PlanillaTipoocuppublicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipoocuppublico planillaTipoocuppublico) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipoocuppublico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipoocuppublico(planillaTipoocuppublico.getCodiTipoOcupPubl()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaTipoocuppublico " + planillaTipoocuppublico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipoocuppublico planillaTipoocuppublico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipoocuppublico = em.merge(planillaTipoocuppublico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipoocuppublico.getCodiTipoOcupPubl();
                if (findPlanillaTipoocuppublico(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaTipoocuppublico with id " + id + " no longer exists.");
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
            PlanillaTipoocuppublico planillaTipoocuppublico;
            try {
                planillaTipoocuppublico = em.getReference(PlanillaTipoocuppublico.class, id);
                planillaTipoocuppublico.getCodiTipoOcupPubl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipoocuppublico with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaTipoocuppublico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipoocuppublico> findPlanillaTipoocuppublicoEntities() {
        return findPlanillaTipoocuppublicoEntities(true, -1, -1);
    }

    public List<PlanillaTipoocuppublico> findPlanillaTipoocuppublicoEntities(int maxResults, int firstResult) {
        return findPlanillaTipoocuppublicoEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipoocuppublico> findPlanillaTipoocuppublicoEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipoocuppublico.class));
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

    public PlanillaTipoocuppublico findPlanillaTipoocuppublico(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipoocuppublico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipoocuppublicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipoocuppublico> rt = cq.from(PlanillaTipoocuppublico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
