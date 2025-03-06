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
import pe.gestor.compras.dto.ComprasOcEstado;

/**
 *
 * @author USER
 */
public class ComprasOcEstadoJpaController implements Serializable {

    public ComprasOcEstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComprasOcEstado comprasOcEstado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comprasOcEstado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComprasOcEstado comprasOcEstado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comprasOcEstado = em.merge(comprasOcEstado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprasOcEstado.getCodiEstdOC();
                if (findComprasOcEstado(id) == null) {
                    throw new NonexistentEntityException("The comprasOcEstado with id " + id + " no longer exists.");
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
            ComprasOcEstado comprasOcEstado;
            try {
                comprasOcEstado = em.getReference(ComprasOcEstado.class, id);
                comprasOcEstado.getCodiEstdOC();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprasOcEstado with id " + id + " no longer exists.", enfe);
            }
            em.remove(comprasOcEstado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComprasOcEstado> findComprasOcEstadoEntities() {
        return findComprasOcEstadoEntities(true, -1, -1);
    }

    public List<ComprasOcEstado> findComprasOcEstadoEntities(int maxResults, int firstResult) {
        return findComprasOcEstadoEntities(false, maxResults, firstResult);
    }

    private List<ComprasOcEstado> findComprasOcEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComprasOcEstado.class));
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

    public ComprasOcEstado findComprasOcEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComprasOcEstado.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasOcEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComprasOcEstado> rt = cq.from(ComprasOcEstado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
