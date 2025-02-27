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
import pe.gestor.planilla.dto.PlanillaPlantilla;

/**
 *
 * @author USER
 */
public class PlanillaPlantillaJpaController implements Serializable {

    public PlanillaPlantillaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaPlantilla planillaPlantilla) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaPlantilla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaPlantilla planillaPlantilla) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaPlantilla = em.merge(planillaPlantilla);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaPlantilla.getCodiPlant();
                if (findPlanillaPlantilla(id) == null) {
                    throw new NonexistentEntityException("The planillaPlantilla with id " + id + " no longer exists.");
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
            PlanillaPlantilla planillaPlantilla;
            try {
                planillaPlantilla = em.getReference(PlanillaPlantilla.class, id);
                planillaPlantilla.getCodiPlant();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaPlantilla with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaPlantilla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaPlantilla> findPlanillaPlantillaEntities() {
        return findPlanillaPlantillaEntities(true, -1, -1);
    }

    public List<PlanillaPlantilla> findPlanillaPlantillaEntities(int maxResults, int firstResult) {
        return findPlanillaPlantillaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaPlantilla> findPlanillaPlantillaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaPlantilla.class));
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

    public PlanillaPlantilla findPlanillaPlantilla(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaPlantilla.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaPlantillaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaPlantilla> rt = cq.from(PlanillaPlantilla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
