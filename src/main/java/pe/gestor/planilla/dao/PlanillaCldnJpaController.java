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
import pe.gestor.planilla.dto.PlanillaCldn;

/**
 *
 * @author USER
 */
public class PlanillaCldnJpaController implements Serializable {

    public PlanillaCldnJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaCldn planillaCldn) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaCldn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaCldn(planillaCldn.getCodiCldn()) != null) {
                throw new PreexistingEntityException("PlanillaCldn " + planillaCldn + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaCldn planillaCldn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaCldn = em.merge(planillaCldn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaCldn.getCodiCldn();
                if (findPlanillaCldn(id) == null) {
                    throw new NonexistentEntityException("The planillaCldn with id " + id + " no longer exists.");
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
            PlanillaCldn planillaCldn;
            try {
                planillaCldn = em.getReference(PlanillaCldn.class, id);
                planillaCldn.getCodiCldn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaCldn with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaCldn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaCldn> findPlanillaCldnEntities() {
        return findPlanillaCldnEntities(true, -1, -1);
    }

    public List<PlanillaCldn> findPlanillaCldnEntities(int maxResults, int firstResult) {
        return findPlanillaCldnEntities(false, maxResults, firstResult);
    }

    private List<PlanillaCldn> findPlanillaCldnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaCldn.class));
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

    public PlanillaCldn findPlanillaCldn(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaCldn.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaCldnCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaCldn> rt = cq.from(PlanillaCldn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
