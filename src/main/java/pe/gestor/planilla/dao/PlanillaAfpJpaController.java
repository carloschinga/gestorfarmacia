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
import pe.gestor.planilla.dto.PlanillaAfp;

/**
 *
 * @author Adria
 */
public class PlanillaAfpJpaController implements Serializable {

    public PlanillaAfpJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaAfp planillaAfp) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaAfp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaAfp planillaAfp) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaAfp = em.merge(planillaAfp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaAfp.getCodiAFP();
                if (findPlanillaAfp(id) == null) {
                    throw new NonexistentEntityException("The planillaAfp with id " + id + " no longer exists.");
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
            PlanillaAfp planillaAfp;
            try {
                planillaAfp = em.getReference(PlanillaAfp.class, id);
                planillaAfp.getCodiAFP();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaAfp with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaAfp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaAfp> findPlanillaAfpEntities() {
        return findPlanillaAfpEntities(true, -1, -1);
    }

    public List<PlanillaAfp> findPlanillaAfpEntities(int maxResults, int firstResult) {
        return findPlanillaAfpEntities(false, maxResults, firstResult);
    }

    private List<PlanillaAfp> findPlanillaAfpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaAfp.class));
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

    public PlanillaAfp findPlanillaAfp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaAfp.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaAfpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaAfp> rt = cq.from(PlanillaAfp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        List<PlanillaAfp> list = new PlanillaAfpJpaController(emf).findPlanillaAfpEntities();
        for (PlanillaAfp p : list) {
            System.out.println(p.getCodiAFP() + " " + p.getNombAFP());
        }
    }

}
