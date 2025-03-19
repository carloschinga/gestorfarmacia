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
import pe.gestor.ventas.dto.VentasTipoComprobante;

/**
 * @author Adria
 */
public class VentasTipoComprobanteJpaController implements Serializable {

    public VentasTipoComprobanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasTipoComprobante ventasTipoComprobante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasTipoComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasTipoComprobante ventasTipoComprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasTipoComprobante = em.merge(ventasTipoComprobante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasTipoComprobante.getCodiTipoComp();
                if (findVentasTipoComprobante(id) == null) {
                    throw new NonexistentEntityException(
                            "The ventasTipoComprobante with id " + id + " no longer exists.");
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
            VentasTipoComprobante ventasTipoComprobante;
            try {
                ventasTipoComprobante = em.getReference(VentasTipoComprobante.class, id);
                ventasTipoComprobante.getCodiTipoComp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasTipoComprobante with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(ventasTipoComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasTipoComprobante> findVentasTipoComprobanteEntities() {
        return findVentasTipoComprobanteEntities(true, -1, -1);
    }

    public List<VentasTipoComprobante> findVentasTipoComprobanteEntities(int maxResults, int firstResult) {
        return findVentasTipoComprobanteEntities(false, maxResults, firstResult);
    }

    private List<VentasTipoComprobante> findVentasTipoComprobanteEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasTipoComprobante.class));
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

    public VentasTipoComprobante findVentasTipoComprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasTipoComprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasTipoComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasTipoComprobante> rt = cq.from(VentasTipoComprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasTipoComprobante> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasTipoComprobante.findByActiTipoComp");
            q.setParameter("actiTipoComp", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
