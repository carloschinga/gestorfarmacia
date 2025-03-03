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
import pe.gestor.ventas.dto.VentasProductosPharma;

/**
 *
 * @author USER
 */
public class VentasProductosPharmaJpaController implements Serializable {

    public VentasProductosPharmaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasProductosPharma ventasProductosPharma) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasProductosPharma);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasProductosPharma(ventasProductosPharma.getCodigo()) != null) {
                throw new PreexistingEntityException("VentasProductosPharma " + ventasProductosPharma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasProductosPharma ventasProductosPharma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasProductosPharma = em.merge(ventasProductosPharma);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ventasProductosPharma.getCodigo();
                if (findVentasProductosPharma(id) == null) {
                    throw new NonexistentEntityException("The ventasProductosPharma with id " + id + " no longer exists.");
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
            VentasProductosPharma ventasProductosPharma;
            try {
                ventasProductosPharma = em.getReference(VentasProductosPharma.class, id);
                ventasProductosPharma.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasProductosPharma with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasProductosPharma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasProductosPharma> findVentasProductosPharmaEntities() {
        return findVentasProductosPharmaEntities(true, -1, -1);
    }

    public List<VentasProductosPharma> findVentasProductosPharmaEntities(int maxResults, int firstResult) {
        return findVentasProductosPharmaEntities(false, maxResults, firstResult);
    }

    private List<VentasProductosPharma> findVentasProductosPharmaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasProductosPharma.class));
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

    public VentasProductosPharma findVentasProductosPharma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasProductosPharma.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasProductosPharmaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasProductosPharma> rt = cq.from(VentasProductosPharma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
