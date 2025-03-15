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
import pe.gestor.asistencia.dto.AsistenciaHorariodetalle;

/**
 *
 * @author Adria
 */
public class AsistenciaHorariodetalleJpaController implements Serializable {

    public AsistenciaHorariodetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaHorariodetalle asistenciaHorariodetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaHorariodetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaHorariodetalle asistenciaHorariodetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaHorariodetalle = em.merge(asistenciaHorariodetalle);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaHorariodetalle.getCodiHoraDeta();
                if (findAsistenciaHorariodetalle(id) == null) {
                    throw new NonexistentEntityException(
                            "The asistenciaHorariodetalle with id " + id + " no longer exists.");
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
            AsistenciaHorariodetalle asistenciaHorariodetalle;
            try {
                asistenciaHorariodetalle = em.getReference(AsistenciaHorariodetalle.class, id);
                asistenciaHorariodetalle.getCodiHoraDeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException(
                        "The asistenciaHorariodetalle with id " + id + " no longer exists.", enfe);
            }
            em.remove(asistenciaHorariodetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaHorariodetalle> findAsistenciaHorariodetalleEntities() {
        return findAsistenciaHorariodetalleEntities(true, -1, -1);
    }

    public List<AsistenciaHorariodetalle> findAsistenciaHorariodetalleEntities(int maxResults, int firstResult) {
        return findAsistenciaHorariodetalleEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaHorariodetalle> findAsistenciaHorariodetalleEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaHorariodetalle.class));
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

    public AsistenciaHorariodetalle findAsistenciaHorariodetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaHorariodetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaHorariodetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaHorariodetalle> rt = cq.from(AsistenciaHorariodetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<AsistenciaHorariodetalle> findAllByCodiHora(int codiHora, int codiDia) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("AsistenciaHorariodetalle.findByHoraYDia");
            q.setParameter("codiHora", codiHora);
            q.setParameter("codiDia", codiDia);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<AsistenciaHorariodetalle> allMarcacionesByCargo(int codiDia, int codiHora) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("AsistenciaHorariodetalle.findByDiaAndHora");
            q.setParameter("codiDia", codiDia);
            q.setParameter("codiHora", codiHora);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        AsistenciaHorariodetalleJpaController pc = new AsistenciaHorariodetalleJpaController(emf);
        List<AsistenciaHorariodetalle> lista = pc.allMarcacionesByCargo(1, 1);
        if (lista == null) {
            System.out.println("No existe");
        } else {
            for (AsistenciaHorariodetalle asistenciaHorariodetalle : lista) {
                System.out.println(asistenciaHorariodetalle.getCodiHoraDeta());
            }
        }
    }

}
