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
import pe.gestor.ventas.dto.VentasTipoMovimiento;

/**
 * @author Adria
 */
public class VentasTipoMovimientoJpaController implements Serializable {

    public VentasTipoMovimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasTipoMovimiento ventasTipoMovimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasTipoMovimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasTipoMovimiento ventasTipoMovimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasTipoMovimiento = em.merge(ventasTipoMovimiento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasTipoMovimiento.getCodiTipoMovi();
                if (findVentasTipoMovimiento(id) == null) {
                    throw new NonexistentEntityException(
                            "The ventasTipoMovimiento with id " + id + " no longer exists.");
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
            VentasTipoMovimiento ventasTipoMovimiento;
            try {
                ventasTipoMovimiento = em.getReference(VentasTipoMovimiento.class, id);
                ventasTipoMovimiento.getCodiTipoMovi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasTipoMovimiento with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(ventasTipoMovimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasTipoMovimiento> findVentasTipoMovimientoEntities() {
        return findVentasTipoMovimientoEntities(true, -1, -1);
    }

    public List<VentasTipoMovimiento> findVentasTipoMovimientoEntities(int maxResults, int firstResult) {
        return findVentasTipoMovimientoEntities(false, maxResults, firstResult);
    }

    private List<VentasTipoMovimiento> findVentasTipoMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasTipoMovimiento.class));
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

    public VentasTipoMovimiento findVentasTipoMovimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasTipoMovimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasTipoMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasTipoMovimiento> rt = cq.from(VentasTipoMovimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasTipoMovimiento> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasTipoMovimiento.findByActiTipoMovi");
            q.setParameter("actiTipoMovi", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
