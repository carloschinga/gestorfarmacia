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
import pe.gestor.asistencia.dto.AsistenciaHorario;

/**
 *
 * @author Adria
 */
public class AsistenciaHorarioJpaController implements Serializable {

    public AsistenciaHorarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaHorario asistenciaHorario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaHorario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaHorario asistenciaHorario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaHorario = em.merge(asistenciaHorario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaHorario.getCodiHora();
                if (findAsistenciaHorario(id) == null) {
                    throw new NonexistentEntityException("The asistenciaHorario with id " + id + " no longer exists.");
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
            AsistenciaHorario asistenciaHorario;
            try {
                asistenciaHorario = em.getReference(AsistenciaHorario.class, id);
                asistenciaHorario.getCodiHora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaHorario with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(asistenciaHorario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaHorario> findAsistenciaHorarioEntities() {
        return findAsistenciaHorarioEntities(true, -1, -1);
    }

    public List<AsistenciaHorario> findAsistenciaHorarioEntities(int maxResults, int firstResult) {
        return findAsistenciaHorarioEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaHorario> findAsistenciaHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaHorario.class));
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

    public AsistenciaHorario findAsistenciaHorario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaHorario.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaHorario> rt = cq.from(AsistenciaHorario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");

        AsistenciaHorarioJpaController dao = new AsistenciaHorarioJpaController(emf);

        List<AsistenciaHorario> lista = dao.findAsistenciaHorarioEntities();
        for (AsistenciaHorario asistenciaHorario : lista) {
            System.out.println(asistenciaHorario.getNombHora());
        }
    }

}
