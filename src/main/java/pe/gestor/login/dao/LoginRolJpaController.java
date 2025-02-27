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
import pe.gestor.login.dto.LoginRol;

/**
 *
 * @author USER
 */
public class LoginRolJpaController implements Serializable {

    public LoginRolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LoginRol loginRol) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(loginRol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LoginRol loginRol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            loginRol = em.merge(loginRol);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = loginRol.getCodiRol();
                if (findLoginRol(id) == null) {
                    throw new NonexistentEntityException("The loginRol with id " + id + " no longer exists.");
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
            LoginRol loginRol;
            try {
                loginRol = em.getReference(LoginRol.class, id);
                loginRol.getCodiRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loginRol with id " + id + " no longer exists.", enfe);
            }
            em.remove(loginRol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LoginRol> findLoginRolEntities() {
        return findLoginRolEntities(true, -1, -1);
    }

    public List<LoginRol> findLoginRolEntities(int maxResults, int firstResult) {
        return findLoginRolEntities(false, maxResults, firstResult);
    }

    private List<LoginRol> findLoginRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LoginRol.class));
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

    public LoginRol findLoginRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LoginRol.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LoginRol> rt = cq.from(LoginRol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
