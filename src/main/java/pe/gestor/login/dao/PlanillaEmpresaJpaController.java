/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.login.dao.exceptions.NonexistentEntityException;
import pe.gestor.login.dto.PlanillaEmpresa;

/**
 *
 * @author san21
 */
public class PlanillaEmpresaJpaController implements Serializable {

    public PlanillaEmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaEmpresa planillaEmpresa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaEmpresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaEmpresa planillaEmpresa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaEmpresa = em.merge(planillaEmpresa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaEmpresa.getCodiEmpr();
                if (findPlanillaEmpresa(id) == null) {
                    throw new NonexistentEntityException("The planillaEmpresa with id " + id + " no longer exists.");
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
            PlanillaEmpresa planillaEmpresa;
            try {
                planillaEmpresa = em.getReference(PlanillaEmpresa.class, id);
                planillaEmpresa.getCodiEmpr();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaEmpresa with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaEmpresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaEmpresa> findPlanillaEmpresaEntities() {
        return findPlanillaEmpresaEntities(true, -1, -1);
    }

    public List<PlanillaEmpresa> findPlanillaEmpresaEntities(int maxResults, int firstResult) {
        return findPlanillaEmpresaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaEmpresa> findPlanillaEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaEmpresa.class));
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

    public PlanillaEmpresa findPlanillaEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaEmpresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaEmpresa> rt = cq.from(PlanillaEmpresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
