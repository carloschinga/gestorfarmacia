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
import pe.gestor.planilla.dto.PlanillaJornadalaboral;

/**
 *
 * @author Adria
 */
public class PlanillaJornadalaboralJpaController implements Serializable {

    public PlanillaJornadalaboralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaJornadalaboral planillaJornadalaboral) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaJornadalaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaJornadalaboral planillaJornadalaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaJornadalaboral = em.merge(planillaJornadalaboral);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaJornadalaboral.getCodiJornadLab();
                if (findPlanillaJornadalaboral(id) == null) {
                    throw new NonexistentEntityException("The planillaJornadalaboral with id " + id + " no longer exists.");
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
            PlanillaJornadalaboral planillaJornadalaboral;
            try {
                planillaJornadalaboral = em.getReference(PlanillaJornadalaboral.class, id);
                planillaJornadalaboral.getCodiJornadLab();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaJornadalaboral with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaJornadalaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaJornadalaboral> findPlanillaJornadalaboralEntities() {
        return findPlanillaJornadalaboralEntities(true, -1, -1);
    }

    public List<PlanillaJornadalaboral> findPlanillaJornadalaboralEntities(int maxResults, int firstResult) {
        return findPlanillaJornadalaboralEntities(false, maxResults, firstResult);
    }

    private List<PlanillaJornadalaboral> findPlanillaJornadalaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaJornadalaboral.class));
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

    public PlanillaJornadalaboral findPlanillaJornadalaboral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaJornadalaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaJornadalaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaJornadalaboral> rt = cq.from(PlanillaJornadalaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
