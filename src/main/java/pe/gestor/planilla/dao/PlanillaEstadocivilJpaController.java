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
import pe.gestor.planilla.dto.PlanillaEstadocivil;

/**
 *
 * @author Adria
 */
public class PlanillaEstadocivilJpaController implements Serializable {

    public PlanillaEstadocivilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaEstadocivil planillaEstadocivil) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaEstadocivil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaEstadocivil planillaEstadocivil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaEstadocivil = em.merge(planillaEstadocivil);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaEstadocivil.getCodiEstaCiv();
                if (findPlanillaEstadocivil(id) == null) {
                    throw new NonexistentEntityException("The planillaEstadocivil with id " + id + " no longer exists.");
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
            PlanillaEstadocivil planillaEstadocivil;
            try {
                planillaEstadocivil = em.getReference(PlanillaEstadocivil.class, id);
                planillaEstadocivil.getCodiEstaCiv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaEstadocivil with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaEstadocivil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaEstadocivil> findPlanillaEstadocivilEntities() {
        return findPlanillaEstadocivilEntities(true, -1, -1);
    }

    public List<PlanillaEstadocivil> findPlanillaEstadocivilEntities(int maxResults, int firstResult) {
        return findPlanillaEstadocivilEntities(false, maxResults, firstResult);
    }

    private List<PlanillaEstadocivil> findPlanillaEstadocivilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaEstadocivil.class));
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

    public PlanillaEstadocivil findPlanillaEstadocivil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaEstadocivil.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaEstadocivilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaEstadocivil> rt = cq.from(PlanillaEstadocivil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
