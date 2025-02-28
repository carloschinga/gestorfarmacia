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
import pe.gestor.ventas.dto.VentasPharma;

/**
 *
 * @author USER
 */
public class VentasPharmaJpaController implements Serializable {

    public VentasPharmaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasPharma comprasVentas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comprasVentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasPharma comprasVentas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comprasVentas = em.merge(comprasVentas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprasVentas.getId();
                if (findComprasVentas(id) == null) {
                    throw new NonexistentEntityException("The comprasVentas with id " + id + " no longer exists.");
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
            VentasPharma comprasVentas;
            try {
                comprasVentas = em.getReference(VentasPharma.class, id);
                comprasVentas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprasVentas with id " + id + " no longer exists.", enfe);
            }
            em.remove(comprasVentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasPharma> findComprasVentasEntities() {
        return findComprasVentasEntities(true, -1, -1);
    }

    public List<VentasPharma> findComprasVentasEntities(int maxResults, int firstResult) {
        return findComprasVentasEntities(false, maxResults, firstResult);
    }

    private List<VentasPharma> findComprasVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasPharma.class));
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

    public VentasPharma findComprasVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasPharma.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasPharma> rt = cq.from(VentasPharma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
