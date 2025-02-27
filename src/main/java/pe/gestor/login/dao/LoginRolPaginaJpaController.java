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
import pe.gestor.login.dao.exceptions.PreexistingEntityException;
import pe.gestor.login.dto.LoginRolPagina;
import pe.gestor.login.dto.LoginRolPaginaPK;

/**
 *
 * @author USER
 */
public class LoginRolPaginaJpaController implements Serializable {

    public LoginRolPaginaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LoginRolPagina loginRolPagina) throws PreexistingEntityException, Exception {
        if (loginRolPagina.getLoginRolPaginaPK() == null) {
            loginRolPagina.setLoginRolPaginaPK(new LoginRolPaginaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(loginRolPagina);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLoginRolPagina(loginRolPagina.getLoginRolPaginaPK()) != null) {
                throw new PreexistingEntityException("LoginRolPagina " + loginRolPagina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LoginRolPagina loginRolPagina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            loginRolPagina = em.merge(loginRolPagina);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LoginRolPaginaPK id = loginRolPagina.getLoginRolPaginaPK();
                if (findLoginRolPagina(id) == null) {
                    throw new NonexistentEntityException("The loginRolPagina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LoginRolPaginaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LoginRolPagina loginRolPagina;
            try {
                loginRolPagina = em.getReference(LoginRolPagina.class, id);
                loginRolPagina.getLoginRolPaginaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loginRolPagina with id " + id + " no longer exists.", enfe);
            }
            em.remove(loginRolPagina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LoginRolPagina> findLoginRolPaginaEntities() {
        return findLoginRolPaginaEntities(true, -1, -1);
    }

    public List<LoginRolPagina> findLoginRolPaginaEntities(int maxResults, int firstResult) {
        return findLoginRolPaginaEntities(false, maxResults, firstResult);
    }

    private List<LoginRolPagina> findLoginRolPaginaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LoginRolPagina.class));
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

    public LoginRolPagina findLoginRolPagina(LoginRolPaginaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LoginRolPagina.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoginRolPaginaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LoginRolPagina> rt = cq.from(LoginRolPagina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
