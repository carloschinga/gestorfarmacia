/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.compras.dao.exceptions.NonexistentEntityException;
import pe.gestor.compras.dto.ComprasVentas;

/**
 *
 * @author USER
 */
public class ComprasVentasJpaController implements Serializable {

    public ComprasVentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComprasVentas comprasVentas) {
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

    public void edit(ComprasVentas comprasVentas) throws NonexistentEntityException, Exception {
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
            ComprasVentas comprasVentas;
            try {
                comprasVentas = em.getReference(ComprasVentas.class, id);
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

    public List<ComprasVentas> findComprasVentasEntities() {
        return findComprasVentasEntities(true, -1, -1);
    }

    public List<ComprasVentas> findComprasVentasEntities(int maxResults, int firstResult) {
        return findComprasVentasEntities(false, maxResults, firstResult);
    }

    private List<ComprasVentas> findComprasVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComprasVentas.class));
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

    public ComprasVentas findComprasVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComprasVentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComprasVentas> rt = cq.from(ComprasVentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
