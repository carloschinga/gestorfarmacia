/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.login.dao.exceptions.NonexistentEntityException;
import pe.gestor.login.dto.LoginPagina;

/**
 *
 * @author USER
 */
public class LoginPaginaJpaController implements Serializable {

    public LoginPaginaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LoginPagina loginPagina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(loginPagina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LoginPagina loginPagina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            loginPagina = em.merge(loginPagina);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = loginPagina.getCodiPagi();
                if (findLoginPagina(id) == null) {
                    throw new NonexistentEntityException("The loginPagina with id " + id + " no longer exists.");
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
            LoginPagina loginPagina;
            try {
                loginPagina = em.getReference(LoginPagina.class, id);
                loginPagina.getCodiPagi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loginPagina with id " + id + " no longer exists.", enfe);
            }
            em.remove(loginPagina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LoginPagina> findLoginPaginaEntities() {
        return findLoginPaginaEntities(true, -1, -1);
    }

    public List<LoginPagina> findLoginPaginaEntities(int maxResults, int firstResult) {
        return findLoginPaginaEntities(false, maxResults, firstResult);
    }

    private List<LoginPagina> findLoginPaginaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LoginPagina.class));
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

    public LoginPagina findLoginPagina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LoginPagina.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginPaginaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LoginPagina> rt = cq.from(LoginPagina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
