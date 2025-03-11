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
import pe.gestor.planilla.dto.PlanillaRegimensalud;

/**
 *
 * @author Adria
 */
public class PlanillaRegimensaludJpaController implements Serializable {

    public PlanillaRegimensaludJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaRegimensalud planillaRegimensalud) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaRegimensalud);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaRegimensalud(planillaRegimensalud.getCodiRegiSal()) != null) {
                throw new PreexistingEntityException(
                        "PlanillaRegimensalud " + planillaRegimensalud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaRegimensalud planillaRegimensalud) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaRegimensalud = em.merge(planillaRegimensalud);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaRegimensalud.getCodiRegiSal();
                if (findPlanillaRegimensalud(id) == null) {
                    throw new NonexistentEntityException(
                            "The planillaRegimensalud with id " + id + " no longer exists.");
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
            PlanillaRegimensalud planillaRegimensalud;
            try {
                planillaRegimensalud = em.getReference(PlanillaRegimensalud.class, id);
                planillaRegimensalud.getCodiRegiSal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaRegimensalud with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(planillaRegimensalud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaRegimensalud> findPlanillaRegimensaludEntities() {
        return findPlanillaRegimensaludEntities(true, -1, -1);
    }

    public List<PlanillaRegimensalud> findPlanillaRegimensaludEntities(int maxResults, int firstResult) {
        return findPlanillaRegimensaludEntities(false, maxResults, firstResult);
    }

    private List<PlanillaRegimensalud> findPlanillaRegimensaludEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaRegimensalud.class));
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

    public PlanillaRegimensalud findPlanillaRegimensalud(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaRegimensalud.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaRegimensaludCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaRegimensalud> rt = cq.from(PlanillaRegimensalud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
