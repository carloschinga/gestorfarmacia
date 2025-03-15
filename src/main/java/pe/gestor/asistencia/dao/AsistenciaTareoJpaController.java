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
import pe.gestor.asistencia.dto.AsistenciaTareo;

/**
 *
 * @author Adria
 */
public class AsistenciaTareoJpaController implements Serializable {

    public AsistenciaTareoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaTareo asistenciaTareo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaTareo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaTareo asistenciaTareo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaTareo = em.merge(asistenciaTareo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaTareo.getCodiTare();
                if (findAsistenciaTareo(id) == null) {
                    throw new NonexistentEntityException("The asistenciaTareo with id " + id + " no longer exists.");
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
            AsistenciaTareo asistenciaTareo;
            try {
                asistenciaTareo = em.getReference(AsistenciaTareo.class, id);
                asistenciaTareo.getCodiTare();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaTareo with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaTareo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaTareo> findAsistenciaTareoEntities() {
        return findAsistenciaTareoEntities(true, -1, -1);
    }

    public List<AsistenciaTareo> findAsistenciaTareoEntities(int maxResults, int firstResult) {
        return findAsistenciaTareoEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaTareo> findAsistenciaTareoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaTareo.class));
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

    public AsistenciaTareo findAsistenciaTareo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaTareo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaTareoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaTareo> rt = cq.from(AsistenciaTareo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
