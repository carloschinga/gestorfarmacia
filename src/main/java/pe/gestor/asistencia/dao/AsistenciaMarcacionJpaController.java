/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import pe.gestor.asistencia.dto.AsistenciaMarcacion;

/**
 *
 * @author USER
 */
public class AsistenciaMarcacionJpaController implements Serializable {

    public AsistenciaMarcacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaMarcacion asistenciaMarcacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaMarcacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaMarcacion asistenciaMarcacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaMarcacion = em.merge(asistenciaMarcacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaMarcacion.getCodiMarc();
                if (findAsistenciaMarcacion(id) == null) {
                    throw new NonexistentEntityException("The asistenciaMarcacion with id " + id + " no longer exists.");
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
            AsistenciaMarcacion asistenciaMarcacion;
            try {
                asistenciaMarcacion = em.getReference(AsistenciaMarcacion.class, id);
                asistenciaMarcacion.getCodiMarc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaMarcacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaMarcacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaMarcacion> findAsistenciaMarcacionEntities() {
        return findAsistenciaMarcacionEntities(true, -1, -1);
    }

    public List<AsistenciaMarcacion> findAsistenciaMarcacionEntities(int maxResults, int firstResult) {
        return findAsistenciaMarcacionEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaMarcacion> findAsistenciaMarcacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaMarcacion.class));
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

    public AsistenciaMarcacion findAsistenciaMarcacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaMarcacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaMarcacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaMarcacion> rt = cq.from(AsistenciaMarcacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
