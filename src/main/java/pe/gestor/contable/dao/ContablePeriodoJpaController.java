/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.contable.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.contable.dao.exceptions.NonexistentEntityException;
import pe.gestor.contable.dto.ContablePeriodo;

/**
 *
 * @author Adria
 */
public class ContablePeriodoJpaController implements Serializable {

    public ContablePeriodoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContablePeriodo contablePeriodo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contablePeriodo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContablePeriodo contablePeriodo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contablePeriodo = em.merge(contablePeriodo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contablePeriodo.getCodiPeri();
                if (findContablePeriodo(id) == null) {
                    throw new NonexistentEntityException("The contablePeriodo with id " + id + " no longer exists.");
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
            ContablePeriodo contablePeriodo;
            try {
                contablePeriodo = em.getReference(ContablePeriodo.class, id);
                contablePeriodo.getCodiPeri();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contablePeriodo with id " + id + " no longer exists.", enfe);
            }
            em.remove(contablePeriodo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContablePeriodo> findContablePeriodoEntities() {
        return findContablePeriodoEntities(true, -1, -1);
    }

    public List<ContablePeriodo> findContablePeriodoEntities(int maxResults, int firstResult) {
        return findContablePeriodoEntities(false, maxResults, firstResult);
    }

    private List<ContablePeriodo> findContablePeriodoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContablePeriodo.class));
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

    public ContablePeriodo findContablePeriodo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContablePeriodo.class, id);
        } finally {
            em.close();
        }
    }

    public int getContablePeriodoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContablePeriodo> rt = cq.from(ContablePeriodo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
