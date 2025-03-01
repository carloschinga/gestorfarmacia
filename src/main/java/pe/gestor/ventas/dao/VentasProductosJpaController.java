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
import pe.gestor.ventas.dto.VentasProductos;

/**
 *
 * @author USER
 */
public class VentasProductosJpaController implements Serializable {

    public VentasProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasProductos ventasProductos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasProductos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasProductos(ventasProductos.getCodigo()) != null) {
                throw new PreexistingEntityException("VentasProductos " + ventasProductos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasProductos ventasProductos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasProductos = em.merge(ventasProductos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ventasProductos.getCodigo();
                if (findVentasProductos(id) == null) {
                    throw new NonexistentEntityException("The ventasProductos with id " + id + " no longer exists.");
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
            VentasProductos ventasProductos;
            try {
                ventasProductos = em.getReference(VentasProductos.class, id);
                ventasProductos.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasProductos with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasProductos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasProductos> findVentasProductosEntities() {
        return findVentasProductosEntities(true, -1, -1);
    }

    public List<VentasProductos> findVentasProductosEntities(int maxResults, int firstResult) {
        return findVentasProductosEntities(false, maxResults, firstResult);
    }

    private List<VentasProductos> findVentasProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasProductos.class));
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

    public VentasProductos findVentasProductos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasProductos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasProductos> rt = cq.from(VentasProductos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
