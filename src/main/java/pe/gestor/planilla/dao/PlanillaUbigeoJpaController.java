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
import pe.gestor.planilla.dto.PlanillaUbigeo;

/**
 *
 * @author USER
 */
public class PlanillaUbigeoJpaController implements Serializable {

    public PlanillaUbigeoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaUbigeo planillaUbigeo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaUbigeo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaUbigeo(planillaUbigeo.getCodiUbig()) != null) {
                throw new PreexistingEntityException("PlanillaUbigeo " + planillaUbigeo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaUbigeo planillaUbigeo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaUbigeo = em.merge(planillaUbigeo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaUbigeo.getCodiUbig();
                if (findPlanillaUbigeo(id) == null) {
                    throw new NonexistentEntityException("The planillaUbigeo with id " + id + " no longer exists.");
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
            PlanillaUbigeo planillaUbigeo;
            try {
                planillaUbigeo = em.getReference(PlanillaUbigeo.class, id);
                planillaUbigeo.getCodiUbig();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaUbigeo with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaUbigeo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaUbigeo> findPlanillaUbigeoEntities() {
        return findPlanillaUbigeoEntities(true, -1, -1);
    }

    public List<PlanillaUbigeo> findPlanillaUbigeoEntities(int maxResults, int firstResult) {
        return findPlanillaUbigeoEntities(false, maxResults, firstResult);
    }

    private List<PlanillaUbigeo> findPlanillaUbigeoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaUbigeo.class));
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

    public PlanillaUbigeo findPlanillaUbigeo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaUbigeo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaUbigeoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaUbigeo> rt = cq.from(PlanillaUbigeo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
