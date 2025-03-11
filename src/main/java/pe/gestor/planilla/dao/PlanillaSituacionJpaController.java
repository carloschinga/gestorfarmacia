package pe.gestor.planilla.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.planilla.dao.exceptions.NonexistentEntityException;
import pe.gestor.planilla.dao.exceptions.PreexistingEntityException;
import pe.gestor.planilla.dto.PlanillaSituacion;

/**
 *
 * @author Adria
 */
public class PlanillaSituacionJpaController implements Serializable {

    public PlanillaSituacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaSituacion planillaSituacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaSituacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaSituacion(planillaSituacion.getCodiSitua()) != null) {
                throw new PreexistingEntityException("PlanillaSituacion " + planillaSituacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaSituacion planillaSituacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaSituacion = em.merge(planillaSituacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaSituacion.getCodiSitua();
                if (findPlanillaSituacion(id) == null) {
                    throw new NonexistentEntityException("The planillaSituacion with id " + id + " no longer exists.");
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
            PlanillaSituacion planillaSituacion;
            try {
                planillaSituacion = em.getReference(PlanillaSituacion.class, id);
                planillaSituacion.getCodiSitua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaSituacion with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaSituacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaSituacion> findPlanillaSituacionEntities() {
        return findPlanillaSituacionEntities(true, -1, -1);
    }

    public List<PlanillaSituacion> findPlanillaSituacionEntities(int maxResults, int firstResult) {
        return findPlanillaSituacionEntities(false, maxResults, firstResult);
    }

    private List<PlanillaSituacion> findPlanillaSituacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaSituacion.class));
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

    public PlanillaSituacion findPlanillaSituacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaSituacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaSituacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaSituacion> rt = cq.from(PlanillaSituacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        PlanillaSituacionJpaController psc = new PlanillaSituacionJpaController(emf);

        List<PlanillaSituacion> lista = psc.findPlanillaSituacionEntities();
        for (PlanillaSituacion planillaSituacion : lista) {
            System.out.println(planillaSituacion.getNombSitua());
        }
    }

}
