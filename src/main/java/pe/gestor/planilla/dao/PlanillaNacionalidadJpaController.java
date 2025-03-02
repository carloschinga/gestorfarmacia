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
import pe.gestor.planilla.dto.PlanillaNacionalidad;

/**
 *
 * @author USER
 */
public class PlanillaNacionalidadJpaController implements Serializable {

    public PlanillaNacionalidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaNacionalidad planillaNacionalidad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaNacionalidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaNacionalidad(planillaNacionalidad.getCodiNaci()) != null) {
                throw new PreexistingEntityException("PlanillaNacionalidad " + planillaNacionalidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaNacionalidad planillaNacionalidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaNacionalidad = em.merge(planillaNacionalidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaNacionalidad.getCodiNaci();
                if (findPlanillaNacionalidad(id) == null) {
                    throw new NonexistentEntityException("The planillaNacionalidad with id " + id + " no longer exists.");
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
            PlanillaNacionalidad planillaNacionalidad;
            try {
                planillaNacionalidad = em.getReference(PlanillaNacionalidad.class, id);
                planillaNacionalidad.getCodiNaci();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaNacionalidad with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaNacionalidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaNacionalidad> findPlanillaNacionalidadEntities() {
        return findPlanillaNacionalidadEntities(true, -1, -1);
    }

    public List<PlanillaNacionalidad> findPlanillaNacionalidadEntities(int maxResults, int firstResult) {
        return findPlanillaNacionalidadEntities(false, maxResults, firstResult);
    }

    private List<PlanillaNacionalidad> findPlanillaNacionalidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaNacionalidad.class));
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

    public PlanillaNacionalidad findPlanillaNacionalidad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaNacionalidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaNacionalidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaNacionalidad> rt = cq.from(PlanillaNacionalidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
