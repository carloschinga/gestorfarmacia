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
import pe.gestor.planilla.dto.PlanillaTipotrabajador;

/**
 *
 * @author Adria
 */
public class PlanillaTipotrabajadorJpaController implements Serializable {

    public PlanillaTipotrabajadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipotrabajador planillaTipotrabajador) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipotrabajador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipotrabajador(planillaTipotrabajador.getCodiTipoTrab()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaTipotrabajador " + planillaTipotrabajador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipotrabajador planillaTipotrabajador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipotrabajador = em.merge(planillaTipotrabajador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipotrabajador.getCodiTipoTrab();
                if (findPlanillaTipotrabajador(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaTipotrabajador with id " + id + " no longer exists.");
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
            PlanillaTipotrabajador planillaTipotrabajador;
            try {
                planillaTipotrabajador = em.getReference(PlanillaTipotrabajador.class, id);
                planillaTipotrabajador.getCodiTipoTrab();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipotrabajador with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaTipotrabajador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipotrabajador> findPlanillaTipotrabajadorEntities() {
        return findPlanillaTipotrabajadorEntities(true, -1, -1);
    }

    public List<PlanillaTipotrabajador> findPlanillaTipotrabajadorEntities(int maxResults, int firstResult) {
        return findPlanillaTipotrabajadorEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipotrabajador> findPlanillaTipotrabajadorEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipotrabajador.class));
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

    public PlanillaTipotrabajador findPlanillaTipotrabajador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipotrabajador.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipotrabajadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipotrabajador> rt = cq.from(PlanillaTipotrabajador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
