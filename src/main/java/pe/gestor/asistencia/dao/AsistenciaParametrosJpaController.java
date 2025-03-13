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
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.asistencia.dao.exceptions.NonexistentEntityException;
import pe.gestor.asistencia.dto.AsistenciaParametros;

/**
 *
 * @author Adria
 */
public class AsistenciaParametrosJpaController implements Serializable {

    public AsistenciaParametrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaParametros asistenciaParametros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaParametros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaParametros asistenciaParametros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaParametros = em.merge(asistenciaParametros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaParametros.getCodiPara();
                if (findAsistenciaParametros(id) == null) {
                    throw new NonexistentEntityException(
                            "The asistenciaParametros with id " + id + " no longer exists.");
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
            AsistenciaParametros asistenciaParametros;
            try {
                asistenciaParametros = em.getReference(AsistenciaParametros.class, id);
                asistenciaParametros.getCodiPara();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaParametros with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(asistenciaParametros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaParametros> findAsistenciaParametrosEntities() {
        return findAsistenciaParametrosEntities(true, -1, -1);
    }

    public List<AsistenciaParametros> findAsistenciaParametrosEntities(int maxResults, int firstResult) {
        return findAsistenciaParametrosEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaParametros> findAsistenciaParametrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaParametros.class));
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

    public AsistenciaParametros findAsistenciaParametros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaParametros.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaParametrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaParametros> rt = cq.from(AsistenciaParametros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
