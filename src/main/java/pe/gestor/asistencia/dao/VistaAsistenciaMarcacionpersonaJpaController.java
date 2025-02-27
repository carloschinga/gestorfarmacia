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
import pe.gestor.asistencia.dto.VistaAsistenciaMarcacionpersona;

/**
 *
 * @author USER
 */
public class VistaAsistenciaMarcacionpersonaJpaController implements Serializable {

    public VistaAsistenciaMarcacionpersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaAsistenciaMarcacionpersona vistaAsistenciaMarcacionpersona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaAsistenciaMarcacionpersona);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaAsistenciaMarcacionpersona(vistaAsistenciaMarcacionpersona.getCodiMarc()) != null) {
                throw new PreexistingEntityException("VistaAsistenciaMarcacionpersona " + vistaAsistenciaMarcacionpersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaAsistenciaMarcacionpersona vistaAsistenciaMarcacionpersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaAsistenciaMarcacionpersona = em.merge(vistaAsistenciaMarcacionpersona);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vistaAsistenciaMarcacionpersona.getCodiMarc();
                if (findVistaAsistenciaMarcacionpersona(id) == null) {
                    throw new NonexistentEntityException("The vistaAsistenciaMarcacionpersona with id " + id + " no longer exists.");
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
            VistaAsistenciaMarcacionpersona vistaAsistenciaMarcacionpersona;
            try {
                vistaAsistenciaMarcacionpersona = em.getReference(VistaAsistenciaMarcacionpersona.class, id);
                vistaAsistenciaMarcacionpersona.getCodiMarc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaAsistenciaMarcacionpersona with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaAsistenciaMarcacionpersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaAsistenciaMarcacionpersona> findVistaAsistenciaMarcacionpersonaEntities() {
        return findVistaAsistenciaMarcacionpersonaEntities(true, -1, -1);
    }

    public List<VistaAsistenciaMarcacionpersona> findVistaAsistenciaMarcacionpersonaEntities(int maxResults, int firstResult) {
        return findVistaAsistenciaMarcacionpersonaEntities(false, maxResults, firstResult);
    }

    private List<VistaAsistenciaMarcacionpersona> findVistaAsistenciaMarcacionpersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaAsistenciaMarcacionpersona.class));
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

    public VistaAsistenciaMarcacionpersona findVistaAsistenciaMarcacionpersona(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaAsistenciaMarcacionpersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaAsistenciaMarcacionpersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaAsistenciaMarcacionpersona> rt = cq.from(VistaAsistenciaMarcacionpersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
