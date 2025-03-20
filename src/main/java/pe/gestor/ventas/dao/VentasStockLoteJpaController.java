/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package pe.gestor.ventas.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.ventas.dao.exceptions.NonexistentEntityException;
import pe.gestor.ventas.dto.VentasStockLote;

/**
 * @author Adria
 */
public class VentasStockLoteJpaController implements Serializable {

    public VentasStockLoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasStockLote ventasStockLote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasStockLote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasStockLote ventasStockLote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasStockLote = em.merge(ventasStockLote);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasStockLote.getCodiStocLote();
                if (findVentasStockLote(id) == null) {
                    throw new NonexistentEntityException("The ventasStockLote with id " + id + " no longer exists.");
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
            VentasStockLote ventasStockLote;
            try {
                ventasStockLote = em.getReference(VentasStockLote.class, id);
                ventasStockLote.getCodiStocLote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasStockLote with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasStockLote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasStockLote> findVentasStockLoteEntities() {
        return findVentasStockLoteEntities(true, -1, -1);
    }

    public List<VentasStockLote> findVentasStockLoteEntities(int maxResults, int firstResult) {
        return findVentasStockLoteEntities(false, maxResults, firstResult);
    }

    private List<VentasStockLote> findVentasStockLoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasStockLote.class));
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

    public VentasStockLote findVentasStockLote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasStockLote.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasStockLoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasStockLote> rt = cq.from(VentasStockLote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasStockLote> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasStockLote.findByActiStocLote");
            q.setParameter("actiStocLote", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
