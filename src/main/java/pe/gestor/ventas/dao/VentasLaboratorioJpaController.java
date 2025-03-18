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
import pe.gestor.ventas.dto.VentasLaboratorio;

/**
 *
 * @author Adria
 */
public class VentasLaboratorioJpaController implements Serializable {

    public VentasLaboratorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasLaboratorio ventasLaboratorio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasLaboratorio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentasLaboratorio(ventasLaboratorio.getCodiLabo()) != null) {
                throw new PreexistingEntityException("VentasLaboratorio " + ventasLaboratorio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasLaboratorio ventasLaboratorio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasLaboratorio = em.merge(ventasLaboratorio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasLaboratorio.getCodiLabo();
                if (findVentasLaboratorio(id) == null) {
                    throw new NonexistentEntityException("The ventasLaboratorio with id " + id + " no longer exists.");
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
            VentasLaboratorio ventasLaboratorio;
            try {
                ventasLaboratorio = em.getReference(VentasLaboratorio.class, id);
                ventasLaboratorio.getCodiLabo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasLaboratorio with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasLaboratorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasLaboratorio> findVentasLaboratorioEntities() {
        return findVentasLaboratorioEntities(true, -1, -1);
    }

    public List<VentasLaboratorio> findVentasLaboratorioEntities(int maxResults, int firstResult) {
        return findVentasLaboratorioEntities(false, maxResults, firstResult);
    }

    private List<VentasLaboratorio> findVentasLaboratorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasLaboratorio.class));
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

    public VentasLaboratorio findVentasLaboratorio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasLaboratorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasLaboratorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasLaboratorio> rt = cq.from(VentasLaboratorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
