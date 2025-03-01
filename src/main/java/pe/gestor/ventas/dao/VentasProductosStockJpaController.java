/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import pe.gestor.ventas.dao.exceptions.PreexistingEntityException;
import pe.gestor.ventas.dto.VentasProductosStock;

/**
 *
 * @author USER
 */
public class VentasProductosStockJpaController implements Serializable {

    public VentasProductosStockJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasProductosStock ventasProductosStock) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasProductosStock);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasProductosStock(ventasProductosStock.getCodigo()) != null) {
                throw new PreexistingEntityException("VentasProductosStock " + ventasProductosStock + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasProductosStock ventasProductosStock) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasProductosStock = em.merge(ventasProductosStock);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ventasProductosStock.getCodigo();
                if (findVentasProductosStock(id) == null) {
                    throw new NonexistentEntityException("The ventasProductosStock with id " + id + " no longer exists.");
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
            VentasProductosStock ventasProductosStock;
            try {
                ventasProductosStock = em.getReference(VentasProductosStock.class, id);
                ventasProductosStock.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasProductosStock with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasProductosStock);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasProductosStock> findVentasProductosStockEntities() {
        return findVentasProductosStockEntities(true, -1, -1);
    }

    public List<VentasProductosStock> findVentasProductosStockEntities(int maxResults, int firstResult) {
        return findVentasProductosStockEntities(false, maxResults, firstResult);
    }

    private List<VentasProductosStock> findVentasProductosStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasProductosStock.class));
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

    public VentasProductosStock findVentasProductosStock(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasProductosStock.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasProductosStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasProductosStock> rt = cq.from(VentasProductosStock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
