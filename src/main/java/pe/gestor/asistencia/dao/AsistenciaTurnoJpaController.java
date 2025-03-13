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
import pe.gestor.asistencia.dto.AsistenciaTurno;

/**
 *
 * @author Adria
 */
public class AsistenciaTurnoJpaController implements Serializable {

    public AsistenciaTurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaTurno asistenciaTurno) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaTurno);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsistenciaTurno(asistenciaTurno.getCodiTurn()) != null) {
                throw new PreexistingEntityException("AsistenciaTurno " + asistenciaTurno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaTurno asistenciaTurno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaTurno = em.merge(asistenciaTurno);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaTurno.getCodiTurn();
                if (findAsistenciaTurno(id) == null) {
                    throw new NonexistentEntityException("The asistenciaTurno with id " + id + " no longer exists.");
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
            AsistenciaTurno asistenciaTurno;
            try {
                asistenciaTurno = em.getReference(AsistenciaTurno.class, id);
                asistenciaTurno.getCodiTurn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaTurno with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaTurno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaTurno> findAsistenciaTurnoEntities() {
        return findAsistenciaTurnoEntities(true, -1, -1);
    }

    public List<AsistenciaTurno> findAsistenciaTurnoEntities(int maxResults, int firstResult) {
        return findAsistenciaTurnoEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaTurno> findAsistenciaTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaTurno.class));
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

    public AsistenciaTurno findAsistenciaTurno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaTurno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaTurno> rt = cq.from(AsistenciaTurno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
