/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.ventas.dao.exceptions.NonexistentEntityException;
import pe.gestor.ventas.dao.exceptions.PreexistingEntityException;
import pe.gestor.ventas.dto.VistaVentasGraficosXMes;

/**
 *
 * @author USER
 */
public class VistaVentasGraficosXMesJpaController implements Serializable {

    public VistaVentasGraficosXMesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaVentasGraficosXMes vistaVentasGraficosXMes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaVentasGraficosXMes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaVentasGraficosXMes(vistaVentasGraficosXMes.getMes()) != null) {
                throw new PreexistingEntityException("VistaVentasGraficosXMes " + vistaVentasGraficosXMes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaVentasGraficosXMes vistaVentasGraficosXMes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaVentasGraficosXMes = em.merge(vistaVentasGraficosXMes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vistaVentasGraficosXMes.getMes();
                if (findVistaVentasGraficosXMes(id) == null) {
                    throw new NonexistentEntityException("The vistaVentasGraficosXMes with id " + id + " no longer exists.");
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
            VistaVentasGraficosXMes vistaVentasGraficosXMes;
            try {
                vistaVentasGraficosXMes = em.getReference(VistaVentasGraficosXMes.class, id);
                vistaVentasGraficosXMes.getMes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaVentasGraficosXMes with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaVentasGraficosXMes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaVentasGraficosXMes> findVistaVentasGraficosXMesEntities() {
        return findVistaVentasGraficosXMesEntities(true, -1, -1);
    }

    public List<VistaVentasGraficosXMes> findVistaVentasGraficosXMesEntities(int maxResults, int firstResult) {
        return findVistaVentasGraficosXMesEntities(false, maxResults, firstResult);
    }

    private List<VistaVentasGraficosXMes> findVistaVentasGraficosXMesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaVentasGraficosXMes.class));
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

    public VistaVentasGraficosXMes findVistaVentasGraficosXMes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaVentasGraficosXMes.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaVentasGraficosXMesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaVentasGraficosXMes> rt = cq.from(VistaVentasGraficosXMes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
