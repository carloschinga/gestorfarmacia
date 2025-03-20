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
import pe.gestor.ventas.dto.VentasUbicacionLote;

/**
 * @author Adria
 */
public class VentasUbicacionLoteJpaController implements Serializable {

    public VentasUbicacionLoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasUbicacionLote ventasUbicacionLote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasUbicacionLote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasUbicacionLote ventasUbicacionLote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasUbicacionLote = em.merge(ventasUbicacionLote);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasUbicacionLote.getCodiUbicLote();
                if (findVentasUbicacionLote(id) == null) {
                    throw new NonexistentEntityException(
                            "The ventasUbicacionLote with id " + id + " no longer exists.");
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
            VentasUbicacionLote ventasUbicacionLote;
            try {
                ventasUbicacionLote = em.getReference(VentasUbicacionLote.class, id);
                ventasUbicacionLote.getCodiUbicLote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasUbicacionLote with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(ventasUbicacionLote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasUbicacionLote> findVentasUbicacionLoteEntities() {
        return findVentasUbicacionLoteEntities(true, -1, -1);
    }

    public List<VentasUbicacionLote> findVentasUbicacionLoteEntities(int maxResults, int firstResult) {
        return findVentasUbicacionLoteEntities(false, maxResults, firstResult);
    }

    private List<VentasUbicacionLote> findVentasUbicacionLoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasUbicacionLote.class));
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

    public VentasUbicacionLote findVentasUbicacionLote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasUbicacionLote.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasUbicacionLoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasUbicacionLote> rt = cq.from(VentasUbicacionLote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasUbicacionLote> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasUbicacionLote.findByActiUbicLote");
            q.setParameter("actiUbicLote", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
