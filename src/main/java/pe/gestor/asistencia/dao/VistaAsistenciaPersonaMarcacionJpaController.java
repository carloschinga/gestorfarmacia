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
import pe.gestor.asistencia.dao.exceptions.PreexistingEntityException;
import pe.gestor.asistencia.dto.VistaAsistenciaPersonaMarcacion;

/**
 *
 * @author USER
 */
public class VistaAsistenciaPersonaMarcacionJpaController implements Serializable {

    public VistaAsistenciaPersonaMarcacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaAsistenciaPersonaMarcacion vistaAsistenciaPersonaMarcacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaAsistenciaPersonaMarcacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaAsistenciaPersonaMarcacion(vistaAsistenciaPersonaMarcacion.getCodiMarc()) != null) {
                throw new PreexistingEntityException("VistaAsistenciaPersonaMarcacion " + vistaAsistenciaPersonaMarcacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaAsistenciaPersonaMarcacion vistaAsistenciaPersonaMarcacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaAsistenciaPersonaMarcacion = em.merge(vistaAsistenciaPersonaMarcacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vistaAsistenciaPersonaMarcacion.getCodiMarc();
                if (findVistaAsistenciaPersonaMarcacion(id) == null) {
                    throw new NonexistentEntityException("The vistaAsistenciaPersonaMarcacion with id " + id + " no longer exists.");
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
            VistaAsistenciaPersonaMarcacion vistaAsistenciaPersonaMarcacion;
            try {
                vistaAsistenciaPersonaMarcacion = em.getReference(VistaAsistenciaPersonaMarcacion.class, id);
                vistaAsistenciaPersonaMarcacion.getCodiMarc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaAsistenciaPersonaMarcacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaAsistenciaPersonaMarcacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaAsistenciaPersonaMarcacion> findVistaAsistenciaPersonaMarcacionEntities() {
        return findVistaAsistenciaPersonaMarcacionEntities(true, -1, -1);
    }

    public List<VistaAsistenciaPersonaMarcacion> findVistaAsistenciaPersonaMarcacionEntities(int maxResults, int firstResult) {
        return findVistaAsistenciaPersonaMarcacionEntities(false, maxResults, firstResult);
    }

    private List<VistaAsistenciaPersonaMarcacion> findVistaAsistenciaPersonaMarcacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaAsistenciaPersonaMarcacion.class));
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

    public VistaAsistenciaPersonaMarcacion findVistaAsistenciaPersonaMarcacion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaAsistenciaPersonaMarcacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaAsistenciaPersonaMarcacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaAsistenciaPersonaMarcacion> rt = cq.from(VistaAsistenciaPersonaMarcacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
