/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.compras.dao.exceptions.NonexistentEntityException;
import pe.gestor.compras.dao.exceptions.PreexistingEntityException;
import pe.gestor.compras.dto.VistaComprasHojaTrabajo;

/**
 *
 * @author USER
 */
public class VistaComprasHojaTrabajoJpaController implements Serializable {

    public VistaComprasHojaTrabajoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaComprasHojaTrabajo vistaComprasHojaTrabajo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaComprasHojaTrabajo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaComprasHojaTrabajo(vistaComprasHojaTrabajo.getCodigo()) != null) {
                throw new PreexistingEntityException(
                        "VistaComprasHojaTrabajo " + vistaComprasHojaTrabajo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaComprasHojaTrabajo vistaComprasHojaTrabajo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaComprasHojaTrabajo = em.merge(vistaComprasHojaTrabajo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vistaComprasHojaTrabajo.getCodigo();
                if (findVistaComprasHojaTrabajo(id) == null) {
                    throw new NonexistentEntityException(
                            "The vistaComprasHojaTrabajo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VistaComprasHojaTrabajo vistaComprasHojaTrabajo;
            try {
                vistaComprasHojaTrabajo = em.getReference(VistaComprasHojaTrabajo.class, id);
                vistaComprasHojaTrabajo.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaComprasHojaTrabajo with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(vistaComprasHojaTrabajo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaComprasHojaTrabajo> findVistaComprasHojaTrabajoEntities() {
        return findVistaComprasHojaTrabajoEntities(true, -1, -1);
    }

    public List<VistaComprasHojaTrabajo> findVistaComprasHojaTrabajoEntities(int maxResults, int firstResult) {
        return findVistaComprasHojaTrabajoEntities(false, maxResults, firstResult);
    }

    private List<VistaComprasHojaTrabajo> findVistaComprasHojaTrabajoEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaComprasHojaTrabajo.class));
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

    public VistaComprasHojaTrabajo findVistaComprasHojaTrabajo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaComprasHojaTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaComprasHojaTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaComprasHojaTrabajo> rt = cq.from(VistaComprasHojaTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
