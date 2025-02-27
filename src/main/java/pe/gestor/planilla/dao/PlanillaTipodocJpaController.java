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
import pe.gestor.planilla.dto.PlanillaTipodoc;

/**
 *
 * @author USER
 */
public class PlanillaTipodocJpaController implements Serializable {

    public PlanillaTipodocJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaTipodoc planillaTipodoc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaTipodoc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaTipodoc(planillaTipodoc.getCodiTipoDoc()) != null) {
                throw new PreexistingEntityException("PlanillaTipodoc " + planillaTipodoc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaTipodoc planillaTipodoc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaTipodoc = em.merge(planillaTipodoc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaTipodoc.getCodiTipoDoc();
                if (findPlanillaTipodoc(id) == null) {
                    throw new NonexistentEntityException("The planillaTipodoc with id " + id + " no longer exists.");
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
            PlanillaTipodoc planillaTipodoc;
            try {
                planillaTipodoc = em.getReference(PlanillaTipodoc.class, id);
                planillaTipodoc.getCodiTipoDoc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaTipodoc with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaTipodoc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaTipodoc> findPlanillaTipodocEntities() {
        return findPlanillaTipodocEntities(true, -1, -1);
    }

    public List<PlanillaTipodoc> findPlanillaTipodocEntities(int maxResults, int firstResult) {
        return findPlanillaTipodocEntities(false, maxResults, firstResult);
    }

    private List<PlanillaTipodoc> findPlanillaTipodocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaTipodoc.class));
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

    public PlanillaTipodoc findPlanillaTipodoc(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaTipodoc.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaTipodocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaTipodoc> rt = cq.from(PlanillaTipodoc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
