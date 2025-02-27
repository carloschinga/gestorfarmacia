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
import pe.gestor.ventas.dto.VentasSede;

/**
 *
 * @author USER
 */
public class VentasSedeJpaController implements Serializable {

    public VentasSedeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasSede ventasSede) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasSede);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasSede(ventasSede.getCodiSede()) != null) {
                throw new PreexistingEntityException("VentasSede " + ventasSede + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasSede ventasSede) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasSede = em.merge(ventasSede);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasSede.getCodiSede();
                if (findVentasSede(id) == null) {
                    throw new NonexistentEntityException("The ventasSede with id " + id + " no longer exists.");
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
            VentasSede ventasSede;
            try {
                ventasSede = em.getReference(VentasSede.class, id);
                ventasSede.getCodiSede();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasSede with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasSede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasSede> findVentasSedeEntities() {
        return findVentasSedeEntities(true, -1, -1);
    }

    public List<VentasSede> findVentasSedeEntities(int maxResults, int firstResult) {
        return findVentasSedeEntities(false, maxResults, firstResult);
    }

    private List<VentasSede> findVentasSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasSede.class));
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

    public VentasSede findVentasSede(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasSede.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasSede> rt = cq.from(VentasSede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
