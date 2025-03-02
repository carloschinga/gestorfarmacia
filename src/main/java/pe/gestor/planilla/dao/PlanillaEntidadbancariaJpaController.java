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
import pe.gestor.planilla.dto.PlanillaEntidadbancaria;

/**
 *
 * @author USER
 */
public class PlanillaEntidadbancariaJpaController implements Serializable {

    public PlanillaEntidadbancariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaEntidadbancaria planillaEntidadbancaria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaEntidadbancaria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanillaEntidadbancaria(planillaEntidadbancaria.getCodiEntiBanc()) != null) {
                throw new PreexistingEntityException("PlanillaEntidadbancaria " + planillaEntidadbancaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaEntidadbancaria planillaEntidadbancaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaEntidadbancaria = em.merge(planillaEntidadbancaria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = planillaEntidadbancaria.getCodiEntiBanc();
                if (findPlanillaEntidadbancaria(id) == null) {
                    throw new NonexistentEntityException("The planillaEntidadbancaria with id " + id + " no longer exists.");
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
            PlanillaEntidadbancaria planillaEntidadbancaria;
            try {
                planillaEntidadbancaria = em.getReference(PlanillaEntidadbancaria.class, id);
                planillaEntidadbancaria.getCodiEntiBanc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaEntidadbancaria with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaEntidadbancaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaEntidadbancaria> findPlanillaEntidadbancariaEntities() {
        return findPlanillaEntidadbancariaEntities(true, -1, -1);
    }

    public List<PlanillaEntidadbancaria> findPlanillaEntidadbancariaEntities(int maxResults, int firstResult) {
        return findPlanillaEntidadbancariaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaEntidadbancaria> findPlanillaEntidadbancariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaEntidadbancaria.class));
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

    public PlanillaEntidadbancaria findPlanillaEntidadbancaria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaEntidadbancaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaEntidadbancariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaEntidadbancaria> rt = cq.from(PlanillaEntidadbancaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
