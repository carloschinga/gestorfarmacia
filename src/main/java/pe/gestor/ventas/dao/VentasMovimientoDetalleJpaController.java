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
import pe.gestor.ventas.dto.VentasMovimientoDetalle;

/**
 * @author Adria
 */
public class VentasMovimientoDetalleJpaController implements Serializable {

    public VentasMovimientoDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasMovimientoDetalle ventasMovimientoDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasMovimientoDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasMovimientoDetalle ventasMovimientoDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasMovimientoDetalle = em.merge(ventasMovimientoDetalle);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasMovimientoDetalle.getCodiMoviDeta();
                if (findVentasMovimientoDetalle(id) == null) {
                    throw new NonexistentEntityException(
                            "The ventasMovimientoDetalle with id " + id + " no longer exists.");
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
            VentasMovimientoDetalle ventasMovimientoDetalle;
            try {
                ventasMovimientoDetalle = em.getReference(VentasMovimientoDetalle.class, id);
                ventasMovimientoDetalle.getCodiMoviDeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasMovimientoDetalle with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(ventasMovimientoDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasMovimientoDetalle> findVentasMovimientoDetalleEntities() {
        return findVentasMovimientoDetalleEntities(true, -1, -1);
    }

    public List<VentasMovimientoDetalle> findVentasMovimientoDetalleEntities(int maxResults, int firstResult) {
        return findVentasMovimientoDetalleEntities(false, maxResults, firstResult);
    }

    private List<VentasMovimientoDetalle> findVentasMovimientoDetalleEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasMovimientoDetalle.class));
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

    public VentasMovimientoDetalle findVentasMovimientoDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasMovimientoDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasMovimientoDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasMovimientoDetalle> rt = cq.from(VentasMovimientoDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasMovimientoDetalle> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasMovimientoDetalle.findByActiMoviDeta");
            q.setParameter("actiMoviDeta", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
