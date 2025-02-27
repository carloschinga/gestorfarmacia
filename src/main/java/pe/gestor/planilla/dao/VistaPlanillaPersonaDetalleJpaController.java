/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.planilla.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.planilla.dao.exceptions.NonexistentEntityException;
import pe.gestor.planilla.dao.exceptions.PreexistingEntityException;
import pe.gestor.planilla.dto.VistaPlanillaPersonaDetalle;

/**
 *
 * @author USER
 */
public class VistaPlanillaPersonaDetalleJpaController implements Serializable {

    public VistaPlanillaPersonaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaPlanillaPersonaDetalle vistaPlanillaPersonaDetalle) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaPlanillaPersonaDetalle);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaPlanillaPersonaDetalle(vistaPlanillaPersonaDetalle.getCodiPers()) != null) {
                throw new PreexistingEntityException("VistaPlanillaPersonaDetalle " + vistaPlanillaPersonaDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaPlanillaPersonaDetalle vistaPlanillaPersonaDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaPlanillaPersonaDetalle = em.merge(vistaPlanillaPersonaDetalle);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vistaPlanillaPersonaDetalle.getCodiPers();
                if (findVistaPlanillaPersonaDetalle(id) == null) {
                    throw new NonexistentEntityException("The vistaPlanillaPersonaDetalle with id " + id + " no longer exists.");
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
            VistaPlanillaPersonaDetalle vistaPlanillaPersonaDetalle;
            try {
                vistaPlanillaPersonaDetalle = em.getReference(VistaPlanillaPersonaDetalle.class, id);
                vistaPlanillaPersonaDetalle.getCodiPers();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaPlanillaPersonaDetalle with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaPlanillaPersonaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaPlanillaPersonaDetalle> findVistaPlanillaPersonaDetalleEntities() {
        return findVistaPlanillaPersonaDetalleEntities(true, -1, -1);
    }

    public List<VistaPlanillaPersonaDetalle> findVistaPlanillaPersonaDetalleEntities(int maxResults, int firstResult) {
        return findVistaPlanillaPersonaDetalleEntities(false, maxResults, firstResult);
    }

    private List<VistaPlanillaPersonaDetalle> findVistaPlanillaPersonaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaPlanillaPersonaDetalle.class));
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

    public VistaPlanillaPersonaDetalle findVistaPlanillaPersonaDetalle(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaPlanillaPersonaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaPlanillaPersonaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaPlanillaPersonaDetalle> rt = cq.from(VistaPlanillaPersonaDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
