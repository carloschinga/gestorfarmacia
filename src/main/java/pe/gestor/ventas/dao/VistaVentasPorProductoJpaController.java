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
import pe.gestor.ventas.dto.VistaVentasPorProducto;

/**
 *
 * @author USER
 */
public class VistaVentasPorProductoJpaController implements Serializable {

    public VistaVentasPorProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaVentasPorProducto vistaVentasPorProducto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaVentasPorProducto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaVentasPorProducto(vistaVentasPorProducto.getProducto()) != null) {
                throw new PreexistingEntityException("VistaVentasPorProducto " + vistaVentasPorProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaVentasPorProducto vistaVentasPorProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaVentasPorProducto = em.merge(vistaVentasPorProducto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vistaVentasPorProducto.getProducto();
                if (findVistaVentasPorProducto(id) == null) {
                    throw new NonexistentEntityException("The vistaVentasPorProducto with id " + id + " no longer exists.");
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
            VistaVentasPorProducto vistaVentasPorProducto;
            try {
                vistaVentasPorProducto = em.getReference(VistaVentasPorProducto.class, id);
                vistaVentasPorProducto.getProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaVentasPorProducto with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaVentasPorProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaVentasPorProducto> findVistaVentasPorProductoEntities() {
        return findVistaVentasPorProductoEntities(true, -1, -1);
    }

    public List<VistaVentasPorProducto> findVistaVentasPorProductoEntities(int maxResults, int firstResult) {
        return findVistaVentasPorProductoEntities(false, maxResults, firstResult);
    }

    private List<VistaVentasPorProducto> findVistaVentasPorProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaVentasPorProducto.class));
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

    public VistaVentasPorProducto findVistaVentasPorProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaVentasPorProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaVentasPorProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaVentasPorProducto> rt = cq.from(VistaVentasPorProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
