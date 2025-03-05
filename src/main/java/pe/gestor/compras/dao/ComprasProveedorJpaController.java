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
import pe.gestor.asistencia.dao.exceptions.NonexistentEntityException;
import pe.gestor.compras.dto.ComprasProveedor;

/**
 *
 * @author USER
 */
public class ComprasProveedorJpaController implements Serializable {

    public ComprasProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComprasProveedor comprasProveedor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comprasProveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComprasProveedor comprasProveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comprasProveedor = em.merge(comprasProveedor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprasProveedor.getCodiProv();
                if (findComprasProveedor(id) == null) {
                    throw new NonexistentEntityException("The comprasProveedor with id " + id + " no longer exists.");
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
            ComprasProveedor comprasProveedor;
            try {
                comprasProveedor = em.getReference(ComprasProveedor.class, id);
                comprasProveedor.getCodiProv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprasProveedor with id " + id + " no longer exists.", enfe);
            }
            em.remove(comprasProveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComprasProveedor> findComprasProveedorEntities() {
        return findComprasProveedorEntities(true, -1, -1);
    }

    public List<ComprasProveedor> findComprasProveedorEntities(int maxResults, int firstResult) {
        return findComprasProveedorEntities(false, maxResults, firstResult);
    }

    private List<ComprasProveedor> findComprasProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComprasProveedor.class));
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

    public ComprasProveedor findComprasProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComprasProveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComprasProveedor> rt = cq.from(ComprasProveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
