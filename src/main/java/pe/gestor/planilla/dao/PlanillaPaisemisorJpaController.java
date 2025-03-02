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
import pe.gestor.planilla.dto.PlanillaPaisemisor;

/**
 *
 * @author USER
 */
public class PlanillaPaisemisorJpaController implements Serializable {

    public PlanillaPaisemisorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaPaisemisor planillaPaisemisor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaPaisemisor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaPaisemisor(planillaPaisemisor.getCodiPaisEmis()) != null) {
                throw new PreexistingEntityException("PlanillaPaisemisor " + planillaPaisemisor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaPaisemisor planillaPaisemisor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaPaisemisor = em.merge(planillaPaisemisor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaPaisemisor.getCodiPaisEmis();
                if (findPlanillaPaisemisor(id) == null) {
                    throw new NonexistentEntityException("The planillaPaisemisor with id " + id + " no longer exists.");
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
            PlanillaPaisemisor planillaPaisemisor;
            try {
                planillaPaisemisor = em.getReference(PlanillaPaisemisor.class, id);
                planillaPaisemisor.getCodiPaisEmis();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaPaisemisor with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaPaisemisor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaPaisemisor> findPlanillaPaisemisorEntities() {
        return findPlanillaPaisemisorEntities(true, -1, -1);
    }

    public List<PlanillaPaisemisor> findPlanillaPaisemisorEntities(int maxResults, int firstResult) {
        return findPlanillaPaisemisorEntities(false, maxResults, firstResult);
    }

    private List<PlanillaPaisemisor> findPlanillaPaisemisorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaPaisemisor.class));
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

    public PlanillaPaisemisor findPlanillaPaisemisor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaPaisemisor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaPaisemisorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaPaisemisor> rt = cq.from(PlanillaPaisemisor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
