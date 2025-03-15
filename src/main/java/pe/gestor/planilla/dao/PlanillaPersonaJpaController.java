/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.planilla.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.planilla.dao.exceptions.NonexistentEntityException;
import pe.gestor.planilla.dto.PlanillaPersona;

/**
 *
 * @author Adria
 */
public class PlanillaPersonaJpaController implements Serializable {

    public PlanillaPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanillaPersona planillaPersona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planillaPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanillaPersona planillaPersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planillaPersona = em.merge(planillaPersona);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planillaPersona.getCodiPers();
                if (findPlanillaPersona(id) == null) {
                    throw new NonexistentEntityException("The planillaPersona with id " + id + " no longer exists.");
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
            PlanillaPersona planillaPersona;
            try {
                planillaPersona = em.getReference(PlanillaPersona.class, id);
                planillaPersona.getCodiPers();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planillaPersona with id " + id + " no longer exists.", enfe);
            }
            em.remove(planillaPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanillaPersona> findPlanillaPersonaEntities() {
        return findPlanillaPersonaEntities(true, -1, -1);
    }

    public List<PlanillaPersona> findPlanillaPersonaEntities(int maxResults, int firstResult) {
        return findPlanillaPersonaEntities(false, maxResults, firstResult);
    }

    private List<PlanillaPersona> findPlanillaPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanillaPersona.class));
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

    public PlanillaPersona findPlanillaPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanillaPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanillaPersona> rt = cq.from(PlanillaPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public PlanillaPersona findPlanillaPersonaByNumeDocu(String numeDocu, String codiTipoDocu) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("PlanillaPersona.findByNumeDocu");
            q.setParameter("numeDocu", numeDocu);
            q.setParameter("codiTipoDoc", codiTipoDocu);
            return (PlanillaPersona) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<PlanillaPersona> findActivos() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("PlanillaPersona.activos");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        PlanillaPersonaJpaController plaPersoDAO = new PlanillaPersonaJpaController(emf);
        List<PlanillaPersona> lista = plaPersoDAO.findActivos();
        System.out.println(lista.size());
    }

}
