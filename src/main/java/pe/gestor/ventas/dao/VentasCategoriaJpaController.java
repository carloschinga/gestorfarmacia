/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import pe.gestor.ventas.dto.VentasCategoria;

/**
 *
 * @author Adria
 */
public class VentasCategoriaJpaController implements Serializable {

    public VentasCategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasCategoria ventasCategoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasCategoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasCategoria(ventasCategoria.getCodiCate()) != null) {
                throw new PreexistingEntityException("VentasCategoria " + ventasCategoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasCategoria ventasCategoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasCategoria = em.merge(ventasCategoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasCategoria.getCodiCate();
                if (findVentasCategoria(id) == null) {
                    throw new NonexistentEntityException("The ventasCategoria with id " + id + " no longer exists.");
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
            VentasCategoria ventasCategoria;
            try {
                ventasCategoria = em.getReference(VentasCategoria.class, id);
                ventasCategoria.getCodiCate();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasCategoria with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasCategoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasCategoria> findVentasCategoriaEntities() {
        return findVentasCategoriaEntities(true, -1, -1);
    }

    public List<VentasCategoria> findVentasCategoriaEntities(int maxResults, int firstResult) {
        return findVentasCategoriaEntities(false, maxResults, firstResult);
    }

    private List<VentasCategoria> findVentasCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasCategoria.class));
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

    public VentasCategoria findVentasCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasCategoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasCategoria> rt = cq.from(VentasCategoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
