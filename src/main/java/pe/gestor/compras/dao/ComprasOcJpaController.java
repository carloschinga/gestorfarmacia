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
import pe.gestor.compras.dto.ComprasOc;

/**
 *
 * @author USER
 */
public class ComprasOcJpaController implements Serializable {

    public ComprasOcJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComprasOc comprasOc) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comprasOc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComprasOc comprasOc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comprasOc = em.merge(comprasOc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprasOc.getCodiOC();
                if (findComprasOc(id) == null) {
                    throw new NonexistentEntityException("The comprasOc with id " + id + " no longer exists.");
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
            ComprasOc comprasOc;
            try {
                comprasOc = em.getReference(ComprasOc.class, id);
                comprasOc.getCodiOC();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprasOc with id " + id + " no longer exists.", enfe);
            }
            em.remove(comprasOc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComprasOc> findComprasOcEntities() {
        return findComprasOcEntities(true, -1, -1);
    }

    public List<ComprasOc> findComprasOcEntities(int maxResults, int firstResult) {
        return findComprasOcEntities(false, maxResults, firstResult);
    }

    private List<ComprasOc> findComprasOcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComprasOc.class));
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

    public ComprasOc findComprasOc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComprasOc.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasOcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComprasOc> rt = cq.from(ComprasOc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
