/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.login.dao.exceptions.NonexistentEntityException;
import pe.gestor.login.dao.exceptions.PreexistingEntityException;
import pe.gestor.login.dto.VistaLoginMenuDeta;

/**
 *
 * @author USER
 */
public class VistaLoginMenuDetaJpaController implements Serializable {

    public VistaLoginMenuDetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaLoginMenuDeta vistaLoginMenuDeta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaLoginMenuDeta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaLoginMenuDeta(vistaLoginMenuDeta.getCodiPagi()) != null) {
                throw new PreexistingEntityException("VistaLoginMenuDeta " + vistaLoginMenuDeta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaLoginMenuDeta vistaLoginMenuDeta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaLoginMenuDeta = em.merge(vistaLoginMenuDeta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vistaLoginMenuDeta.getCodiPagi();
                if (findVistaLoginMenuDeta(id) == null) {
                    throw new NonexistentEntityException("The vistaLoginMenuDeta with id " + id + " no longer exists.");
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
            VistaLoginMenuDeta vistaLoginMenuDeta;
            try {
                vistaLoginMenuDeta = em.getReference(VistaLoginMenuDeta.class, id);
                vistaLoginMenuDeta.getCodiPagi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaLoginMenuDeta with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaLoginMenuDeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaLoginMenuDeta> findVistaLoginMenuDetaEntities() {
        return findVistaLoginMenuDetaEntities(true, -1, -1);
    }

    public List<VistaLoginMenuDeta> findVistaLoginMenuDetaEntities(int maxResults, int firstResult) {
        return findVistaLoginMenuDetaEntities(false, maxResults, firstResult);
    }

    private List<VistaLoginMenuDeta> findVistaLoginMenuDetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaLoginMenuDeta.class));
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

    public VistaLoginMenuDeta findVistaLoginMenuDeta(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaLoginMenuDeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaLoginMenuDetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaLoginMenuDeta> rt = cq.from(VistaLoginMenuDeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
