/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package pe.gestor.ventas.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.ventas.dao.exceptions.NonexistentEntityException;
import pe.gestor.ventas.dto.VentasCliente;

/**
 * @author Adria
 */
public class VentasClienteJpaController implements Serializable {

    public VentasClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentasCliente ventasCliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ventasCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentasCliente ventasCliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ventasCliente = em.merge(ventasCliente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventasCliente.getCodiClie();
                if (findVentasCliente(id) == null) {
                    throw new NonexistentEntityException("The ventasCliente with id " + id + " no longer exists.");
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
            VentasCliente ventasCliente;
            try {
                ventasCliente = em.getReference(VentasCliente.class, id);
                ventasCliente.getCodiClie();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventasCliente with id " + id + " no longer exists.", enfe);
            }
            em.remove(ventasCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentasCliente> findVentasClienteEntities() {
        return findVentasClienteEntities(true, -1, -1);
    }

    public List<VentasCliente> findVentasClienteEntities(int maxResults, int firstResult) {
        return findVentasClienteEntities(false, maxResults, firstResult);
    }

    private List<VentasCliente> findVentasClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentasCliente.class));
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

    public VentasCliente findVentasCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentasCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentasCliente> rt = cq.from(VentasCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<VentasCliente> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VentasCliente.findByActiClie");
            q.setParameter("actiClie", true);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        VentasClienteJpaController vcj = new VentasClienteJpaController(emf);
        List<VentasCliente> vc = vcj.findAllActive();
        if (vc != null) {
            System.out.println(vc.size());
        } else {
            System.out.println("No se encontraron registros");
        }
    }

}
