/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.planilla.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.planilla.dao.exceptions.NonexistentEntityException;
import pe.gestor.planilla.dao.exceptions.PreexistingEntityException;
import pe.gestor.planilla.dto.VistaPlanillaPersona;

/**
 *
 * @author USER
 */
public class VistaPlanillaPersonaJpaController implements Serializable {

    public VistaPlanillaPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaPlanillaPersona vistaPlanillaPersona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vistaPlanillaPersona);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVistaPlanillaPersona(vistaPlanillaPersona.getCodiPers()) != null) {
                throw new PreexistingEntityException("VistaPlanillaPersona " + vistaPlanillaPersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaPlanillaPersona vistaPlanillaPersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vistaPlanillaPersona = em.merge(vistaPlanillaPersona);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = vistaPlanillaPersona.getCodiPers();
                if (findVistaPlanillaPersona(id) == null) {
                    throw new NonexistentEntityException("The vistaPlanillaPersona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VistaPlanillaPersona vistaPlanillaPersona;
            try {
                vistaPlanillaPersona = em.getReference(VistaPlanillaPersona.class, id);
                vistaPlanillaPersona.getCodiPers();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaPlanillaPersona with id " + id + " no longer exists.", enfe);
            }
            em.remove(vistaPlanillaPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaPlanillaPersona> findVistaPlanillaPersonaEntities() {
        return findVistaPlanillaPersonaEntities(true, -1, -1);
    }

    public List<VistaPlanillaPersona> findVistaPlanillaPersonaEntities(int maxResults, int firstResult) {
        return findVistaPlanillaPersonaEntities(false, maxResults, firstResult);
    }

    private List<VistaPlanillaPersona> findVistaPlanillaPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaPlanillaPersona.class));
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

    public VistaPlanillaPersona findVistaPlanillaPersona(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaPlanillaPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaPlanillaPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaPlanillaPersona> rt = cq.from(VistaPlanillaPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
