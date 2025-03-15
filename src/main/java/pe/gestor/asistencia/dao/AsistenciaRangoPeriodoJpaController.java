/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.asistencia.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.asistencia.dao.exceptions.NonexistentEntityException;
import pe.gestor.asistencia.dao.exceptions.PreexistingEntityException;
import pe.gestor.asistencia.dto.AsistenciaRangoPeriodo;

/**
 *
 * @author Adria
 */
public class AsistenciaRangoPeriodoJpaController implements Serializable {

    public AsistenciaRangoPeriodoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaRangoPeriodo asistenciaRangoPeriodo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaRangoPeriodo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsistenciaRangoPeriodo(asistenciaRangoPeriodo.getCodiRang()) != null) {
                throw new PreexistingEntityException("AsistenciaRangoPeriodo " + asistenciaRangoPeriodo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaRangoPeriodo asistenciaRangoPeriodo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaRangoPeriodo = em.merge(asistenciaRangoPeriodo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaRangoPeriodo.getCodiRang();
                if (findAsistenciaRangoPeriodo(id) == null) {
                    throw new NonexistentEntityException("The asistenciaRangoPeriodo with id " + id + " no longer exists.");
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
            AsistenciaRangoPeriodo asistenciaRangoPeriodo;
            try {
                asistenciaRangoPeriodo = em.getReference(AsistenciaRangoPeriodo.class, id);
                asistenciaRangoPeriodo.getCodiRang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaRangoPeriodo with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaRangoPeriodo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaRangoPeriodo> findAsistenciaRangoPeriodoEntities() {
        return findAsistenciaRangoPeriodoEntities(true, -1, -1);
    }

    public List<AsistenciaRangoPeriodo> findAsistenciaRangoPeriodoEntities(int maxResults, int firstResult) {
        return findAsistenciaRangoPeriodoEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaRangoPeriodo> findAsistenciaRangoPeriodoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaRangoPeriodo.class));
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

    public AsistenciaRangoPeriodo findAsistenciaRangoPeriodo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaRangoPeriodo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaRangoPeriodoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaRangoPeriodo> rt = cq.from(AsistenciaRangoPeriodo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
