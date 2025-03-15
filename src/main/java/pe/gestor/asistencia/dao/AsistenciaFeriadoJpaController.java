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
import pe.gestor.asistencia.dto.AsistenciaFeriado;

/**
 *
 * @author Adria
 */
public class AsistenciaFeriadoJpaController implements Serializable {

    public AsistenciaFeriadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaFeriado asistenciaFeriado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaFeriado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaFeriado asistenciaFeriado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaFeriado = em.merge(asistenciaFeriado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaFeriado.getCodiFeri();
                if (findAsistenciaFeriado(id) == null) {
                    throw new NonexistentEntityException("The asistenciaFeriado with id " + id + " no longer exists.");
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
            AsistenciaFeriado asistenciaFeriado;
            try {
                asistenciaFeriado = em.getReference(AsistenciaFeriado.class, id);
                asistenciaFeriado.getCodiFeri();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaFeriado with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaFeriado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaFeriado> findAsistenciaFeriadoEntities() {
        return findAsistenciaFeriadoEntities(true, -1, -1);
    }

    public List<AsistenciaFeriado> findAsistenciaFeriadoEntities(int maxResults, int firstResult) {
        return findAsistenciaFeriadoEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaFeriado> findAsistenciaFeriadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaFeriado.class));
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

    public AsistenciaFeriado findAsistenciaFeriado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaFeriado.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaFeriadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaFeriado> rt = cq.from(AsistenciaFeriado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
