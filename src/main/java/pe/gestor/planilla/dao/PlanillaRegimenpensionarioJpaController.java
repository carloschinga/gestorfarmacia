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
import pe.gestor.planilla.dto.PlanillaRegimenpensionario;

/**
 *
 * @author Adria
 */
public class PlanillaRegimenpensionarioJpaController implements Serializable {

    public PlanillaRegimenpensionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaRegimenpensionario planillaRegimenpensionario)
            throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaRegimenpensionario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaRegimenpensionario(planillaRegimenpensionario.getCodiRegiPensi()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaRegimenpensionario " + planillaRegimenpensionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaRegimenpensionario planillaRegimenpensionario)
            throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaRegimenpensionario = em.merge(planillaRegimenpensionario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaRegimenpensionario.getCodiRegiPensi();
                if (findPlanillaRegimenpensionario(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaRegimenpensionario with id " + id + " no longer exists.");
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
            PlanillaRegimenpensionario planillaRegimenpensionario;
            try {
                planillaRegimenpensionario = em.getReference(PlanillaRegimenpensionario.class, id);
                planillaRegimenpensionario.getCodiRegiPensi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The planillaRegimenpensionario with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaRegimenpensionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaRegimenpensionario> findPlanillaRegimenpensionarioEntities() {
        return findPlanillaRegimenpensionarioEntities(true, -1, -1);
    }

    public List<PlanillaRegimenpensionario> findPlanillaRegimenpensionarioEntities(int maxResults, int firstResult) {
        return findPlanillaRegimenpensionarioEntities(false, maxResults, firstResult);
    }

    private List<PlanillaRegimenpensionario> findPlanillaRegimenpensionarioEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaRegimenpensionario.class));
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

    public PlanillaRegimenpensionario findPlanillaRegimenpensionario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaRegimenpensionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaRegimenpensionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaRegimenpensionario> rt = cq.from(PlanillaRegimenpensionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
