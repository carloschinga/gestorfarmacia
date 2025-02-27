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
import pe.gestor.login.dto.VistaLoginUsuarioRol;

/**
 *
 * @author USER
 */
public class VistaLoginUsuarioRolJpaController implements Serializable {

    public VistaLoginUsuarioRolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaLoginUsuarioRol vistaLoginUsuarioRol) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaLoginUsuarioRol);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaLoginUsuarioRol(vistaLoginUsuarioRol.getCodiUsua()) != null) {
                throw new PreexistingEntityException("VistaLoginUsuarioRol " + vistaLoginUsuarioRol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaLoginUsuarioRol vistaLoginUsuarioRol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaLoginUsuarioRol = em.merge(vistaLoginUsuarioRol);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vistaLoginUsuarioRol.getCodiUsua();
                if (findVistaLoginUsuarioRol(id) == null) {
                    throw new NonexistentEntityException("The vistaLoginUsuarioRol with id " + id + " no longer exists.");
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
            VistaLoginUsuarioRol vistaLoginUsuarioRol;
            try {
                vistaLoginUsuarioRol = em.getReference(VistaLoginUsuarioRol.class, id);
                vistaLoginUsuarioRol.getCodiUsua();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaLoginUsuarioRol with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaLoginUsuarioRol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaLoginUsuarioRol> findVistaLoginUsuarioRolEntities() {
        return findVistaLoginUsuarioRolEntities(true, -1, -1);
    }

    public List<VistaLoginUsuarioRol> findVistaLoginUsuarioRolEntities(int maxResults, int firstResult) {
        return findVistaLoginUsuarioRolEntities(false, maxResults, firstResult);
    }

    private List<VistaLoginUsuarioRol> findVistaLoginUsuarioRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaLoginUsuarioRol.class));
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

    public VistaLoginUsuarioRol findVistaLoginUsuarioRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaLoginUsuarioRol.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaLoginUsuarioRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaLoginUsuarioRol> rt = cq.from(VistaLoginUsuarioRol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
