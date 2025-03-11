/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import pe.gestor.planilla.dto.PlanillaSucursal;

/**
 *
 * @author Adria
 */
public class PlanillaSucursalJpaController implements Serializable {

    public PlanillaSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaSucursal planillaSucursal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaSucursal planillaSucursal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaSucursal = em.merge(planillaSucursal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaSucursal.getCodiSucurs();
                if (findPlanillaSucursal(id) == null) {
                    throw new NonexistentEntityException("The planillaSucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanillaSucursal planillaSucursal;
            try {
                planillaSucursal = em.getReference(PlanillaSucursal.class, id);
                planillaSucursal.getCodiSucurs();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaSucursal with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaSucursal> findPlanillaSucursalEntities() {
        return findPlanillaSucursalEntities(true, -1, -1);
    }

    public List<PlanillaSucursal> findPlanillaSucursalEntities(int maxResults, int firstResult) {
        return findPlanillaSucursalEntities(false, maxResults, firstResult);
    }

    private List<PlanillaSucursal> findPlanillaSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaSucursal.class));
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

    public PlanillaSucursal findPlanillaSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaSucursal> rt = cq.from(PlanillaSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
