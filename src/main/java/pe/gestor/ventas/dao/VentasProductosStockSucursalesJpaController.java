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
import pe.gestor.ventas.dto.VentasProductosStockSucursales;

/**
 *
 * @author san21
 */
public class VentasProductosStockSucursalesJpaController implements Serializable {

    public VentasProductosStockSucursalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasProductosStockSucursales ventasProductosStockSucursales) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasProductosStockSucursales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasProductosStockSucursales(ventasProductosStockSucursales.getCodigo()) != null) {
                throw new PreexistingEntityException("VentasProductosStockSucursales " + ventasProductosStockSucursales + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasProductosStockSucursales ventasProductosStockSucursales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasProductosStockSucursales = em.merge(ventasProductosStockSucursales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ventasProductosStockSucursales.getCodigo();
                if (findVentasProductosStockSucursales(id) == null) {
                    throw new NonexistentEntityException("The ventasProductosStockSucursales with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VentasProductosStockSucursales ventasProductosStockSucursales;
            try {
                ventasProductosStockSucursales = em.getReference(VentasProductosStockSucursales.class, id);
                ventasProductosStockSucursales.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasProductosStockSucursales with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasProductosStockSucursales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasProductosStockSucursales> findVentasProductosStockSucursalesEntities() {
        return findVentasProductosStockSucursalesEntities(true, -1, -1);
    }

    public List<VentasProductosStockSucursales> findVentasProductosStockSucursalesEntities(int maxResults, int firstResult) {
        return findVentasProductosStockSucursalesEntities(false, maxResults, firstResult);
    }

    private List<VentasProductosStockSucursales> findVentasProductosStockSucursalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasProductosStockSucursales.class));
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

    public VentasProductosStockSucursales findVentasProductosStockSucursales(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasProductosStockSucursales.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasProductosStockSucursalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasProductosStockSucursales> rt = cq.from(VentasProductosStockSucursales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
