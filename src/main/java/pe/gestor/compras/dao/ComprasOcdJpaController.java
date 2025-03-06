/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.compras.dao.exceptions.NonexistentEntityException;
import pe.gestor.compras.dto.ComprasOcd;

/**
 *
 * @author USER
 */
public class ComprasOcdJpaController implements Serializable {

    public ComprasOcdJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComprasOcd comprasOcd) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comprasOcd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComprasOcd comprasOcd) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comprasOcd = em.merge(comprasOcd);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprasOcd.getCodiOCD();
                if (findComprasOcd(id) == null) {
                    throw new NonexistentEntityException("The comprasOcd with id " + id + " no longer exists.");
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
            ComprasOcd comprasOcd;
            try {
                comprasOcd = em.getReference(ComprasOcd.class, id);
                comprasOcd.getCodiOCD();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprasOcd with id " + id + " no longer exists.", enfe);
            }
            em.remove(comprasOcd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComprasOcd> findComprasOcdEntities() {
        return findComprasOcdEntities(true, -1, -1);
    }

    public List<ComprasOcd> findComprasOcdEntities(int maxResults, int firstResult) {
        return findComprasOcdEntities(false, maxResults, firstResult);
    }

    private List<ComprasOcd> findComprasOcdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComprasOcd.class));
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

    public ComprasOcd findComprasOcd(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComprasOcd.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasOcdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComprasOcd> rt = cq.from(ComprasOcd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
