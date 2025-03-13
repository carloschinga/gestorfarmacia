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
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.planilla.dao.exceptions.NonexistentEntityException;
import pe.gestor.planilla.dto.PlanillaDia;

/**
 *
 * @author Adria
 */
public class PlanillaDiaJpaController implements Serializable {

    public PlanillaDiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaDia planillaDia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaDia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaDia planillaDia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaDia = em.merge(planillaDia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaDia.getCodiDia();
                if (findPlanillaDia(id) == null) {
                    throw new NonexistentEntityException("The planillaDia with id " + id + " no longer exists.");
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
            PlanillaDia planillaDia;
            try {
                planillaDia = em.getReference(PlanillaDia.class, id);
                planillaDia.getCodiDia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaDia with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaDia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaDia> findPlanillaDiaEntities() {
        return findPlanillaDiaEntities(true, -1, -1);
    }

    public List<PlanillaDia> findPlanillaDiaEntities(int maxResults, int firstResult) {
        return findPlanillaDiaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaDia> findPlanillaDiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaDia.class));
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

    public PlanillaDia findPlanillaDia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaDia.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaDiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaDia> rt = cq.from(PlanillaDia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public PlanillaDia findPlanillaDiaByNomb(String nomb) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("PlanillaDia.findByNombDia");
            q.setParameter("nombDia", nomb);
            return (PlanillaDia) q.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        PlanillaDiaJpaController pc = new PlanillaDiaJpaController(emf);
        PlanillaDia planillaDia = pc.findPlanillaDiaByNomb("LUNES");
        if (planillaDia == null) {
            System.out.println("No existe");
        } else {
            System.out.println(planillaDia.getNombDia());
        }
    }

}
