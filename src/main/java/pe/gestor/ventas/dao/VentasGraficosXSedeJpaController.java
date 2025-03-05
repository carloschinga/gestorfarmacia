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
import pe.gestor.ventas.dto.VentasGraficosXSede;

/**
 *
 * @author san21
 */
public class VentasGraficosXSedeJpaController implements Serializable {

    public VentasGraficosXSedeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasGraficosXSede ventasGraficosXSede) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasGraficosXSede);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasGraficosXSede(ventasGraficosXSede.getSede()) != null) {
                throw new PreexistingEntityException("VentasGraficosXSede " + ventasGraficosXSede + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasGraficosXSede ventasGraficosXSede) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasGraficosXSede = em.merge(ventasGraficosXSede);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = ventasGraficosXSede.getSede();
                if (findVentasGraficosXSede(id) == null) {
                    throw new NonexistentEntityException("The ventasGraficosXSede with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VentasGraficosXSede ventasGraficosXSede;
            try {
                ventasGraficosXSede = em.getReference(VentasGraficosXSede.class, id);
                ventasGraficosXSede.getSede();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasGraficosXSede with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasGraficosXSede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasGraficosXSede> findVentasGraficosXSedeEntities() {
        return findVentasGraficosXSedeEntities(true, -1, -1);
    }

    public List<VentasGraficosXSede> findVentasGraficosXSedeEntities(int maxResults, int firstResult) {
        return findVentasGraficosXSedeEntities(false, maxResults, firstResult);
    }

    private List<VentasGraficosXSede> findVentasGraficosXSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasGraficosXSede.class));
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

    public VentasGraficosXSede findVentasGraficosXSede(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasGraficosXSede.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasGraficosXSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasGraficosXSede> rt = cq.from(VentasGraficosXSede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
