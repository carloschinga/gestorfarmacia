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
import pe.gestor.ventas.dto.VentasUbicacionAlmacen;

/**
 * @author Adria
 */
public class VentasUbicacionAlmacenJpaController implements Serializable {

    public VentasUbicacionAlmacenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasUbicacionAlmacen ventasUbicacionAlmacen) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasUbicacionAlmacen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasUbicacionAlmacen ventasUbicacionAlmacen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasUbicacionAlmacen = em.merge(ventasUbicacionAlmacen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasUbicacionAlmacen.getCodiUbicAlmc();
                if (findVentasUbicacionAlmacen(id) == null) {
                    throw new NonexistentEntityException(
                            "The ventasUbicacionAlmacen with id " + id + " no longer exists.");
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
            VentasUbicacionAlmacen ventasUbicacionAlmacen;
            try {
                ventasUbicacionAlmacen = em.getReference(VentasUbicacionAlmacen.class, id);
                ventasUbicacionAlmacen.getCodiUbicAlmc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasUbicacionAlmacen with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(ventasUbicacionAlmacen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasUbicacionAlmacen> findVentasUbicacionAlmacenEntities() {
        return findVentasUbicacionAlmacenEntities(true, -1, -1);
    }

    public List<VentasUbicacionAlmacen> findVentasUbicacionAlmacenEntities(int maxResults, int firstResult) {
        return findVentasUbicacionAlmacenEntities(false, maxResults, firstResult);
    }

    private List<VentasUbicacionAlmacen> findVentasUbicacionAlmacenEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasUbicacionAlmacen.class));
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

    public VentasUbicacionAlmacen findVentasUbicacionAlmacen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasUbicacionAlmacen.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasUbicacionAlmacenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasUbicacionAlmacen> rt = cq.from(VentasUbicacionAlmacen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasUbicacionAlmacen> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasUbicacionAlmacen.findByActiUbicAlmc");
            q.setParameter("actiUbicAlmc", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
