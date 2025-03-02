/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import pe.gestor.planilla.dto.PlanillaTipovia;

/**
 *
 * @author USER
 */
public class PlanillaTipoviaJpaController implements Serializable {

    public PlanillaTipoviaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipovia planillaTipovia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipovia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipovia(planillaTipovia.getCodiTipoVia()) != null) {
                throw new PreexistingEntityException("PlanillaTipovia " + planillaTipovia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipovia planillaTipovia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipovia = em.merge(planillaTipovia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipovia.getCodiTipoVia();
                if (findPlanillaTipovia(id) == null) {
                    throw new NonexistentEntityException("The planillaTipovia with id " + id + " no longer exists.");
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
            PlanillaTipovia planillaTipovia;
            try {
                planillaTipovia = em.getReference(PlanillaTipovia.class, id);
                planillaTipovia.getCodiTipoVia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipovia with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaTipovia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipovia> findPlanillaTipoviaEntities() {
        return findPlanillaTipoviaEntities(true, -1, -1);
    }

    public List<PlanillaTipovia> findPlanillaTipoviaEntities(int maxResults, int firstResult) {
        return findPlanillaTipoviaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipovia> findPlanillaTipoviaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipovia.class));
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

    public PlanillaTipovia findPlanillaTipovia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipovia.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipoviaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipovia> rt = cq.from(PlanillaTipovia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
