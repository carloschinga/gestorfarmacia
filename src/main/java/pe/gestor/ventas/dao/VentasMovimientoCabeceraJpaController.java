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
import pe.gestor.ventas.dto.VentasMovimientoCabecera;

/**
 * @author Adria
 */
public class VentasMovimientoCabeceraJpaController implements Serializable {

    public VentasMovimientoCabeceraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasMovimientoCabecera ventasMovimientoCabecera) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasMovimientoCabecera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasMovimientoCabecera ventasMovimientoCabecera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasMovimientoCabecera = em.merge(ventasMovimientoCabecera);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasMovimientoCabecera.getCodiMoviCabe();
                if (findVentasMovimientoCabecera(id) == null) {
                    throw new NonexistentEntityException(
                            "The ventasMovimientoCabecera with id " + id + " no longer exists.");
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
            VentasMovimientoCabecera ventasMovimientoCabecera;
            try {
                ventasMovimientoCabecera = em.getReference(VentasMovimientoCabecera.class, id);
                ventasMovimientoCabecera.getCodiMoviCabe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The ventasMovimientoCabecera with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasMovimientoCabecera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasMovimientoCabecera> findVentasMovimientoCabeceraEntities() {
        return findVentasMovimientoCabeceraEntities(true, -1, -1);
    }

    public List<VentasMovimientoCabecera> findVentasMovimientoCabeceraEntities(int maxResults, int firstResult) {
        return findVentasMovimientoCabeceraEntities(false, maxResults, firstResult);
    }

    private List<VentasMovimientoCabecera> findVentasMovimientoCabeceraEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasMovimientoCabecera.class));
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

    public VentasMovimientoCabecera findVentasMovimientoCabecera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasMovimientoCabecera.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasMovimientoCabeceraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasMovimientoCabecera> rt = cq.from(VentasMovimientoCabecera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasMovimientoCabecera> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasMovimientoCabecera.findByActiMovi");
            q.setParameter("actiMovi", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
