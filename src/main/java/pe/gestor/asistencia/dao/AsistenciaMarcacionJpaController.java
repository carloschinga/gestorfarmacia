/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.asistencia.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.gestor.asistencia.dao.exceptions.NonexistentEntityException;
import pe.gestor.asistencia.dto.AsistenciaHorariodetalle;
import pe.gestor.asistencia.dto.AsistenciaMarcacion;

/**
 *
 * @author USER
 */
public class AsistenciaMarcacionJpaController implements Serializable {

    public AsistenciaMarcacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AsistenciaMarcacion asistenciaMarcacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(asistenciaMarcacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AsistenciaMarcacion asistenciaMarcacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            asistenciaMarcacion = em.merge(asistenciaMarcacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistenciaMarcacion.getCodiMarc();
                if (findAsistenciaMarcacion(id) == null) {
                    throw new NonexistentEntityException(
                            "The asistenciaMarcacion with id " + id + " no longer exists.");
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
            AsistenciaMarcacion asistenciaMarcacion;
            try {
                asistenciaMarcacion = em.getReference(AsistenciaMarcacion.class, id);
                asistenciaMarcacion.getCodiMarc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistenciaMarcacion with id " + id + " no longer exists.",
                        enfe);
            }
            em.remove(asistenciaMarcacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AsistenciaMarcacion> findAsistenciaMarcacionEntities() {
        return findAsistenciaMarcacionEntities(true, -1, -1);
    }

    public List<AsistenciaMarcacion> findAsistenciaMarcacionEntities(int maxResults, int firstResult) {
        return findAsistenciaMarcacionEntities(false, maxResults, firstResult);
    }

    private List<AsistenciaMarcacion> findAsistenciaMarcacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AsistenciaMarcacion.class));
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

    public AsistenciaMarcacion findAsistenciaMarcacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AsistenciaMarcacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaMarcacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AsistenciaMarcacion> rt = cq.from(AsistenciaMarcacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<AsistenciaMarcacion> findMarcacionIncompleta(int codiPers, Date fechaInicio, Date fechDateFin) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM AsistenciaMarcacion m "
                    + "WHERE m.codiPers = :codiPers "
                    + "AND m.marcIngr IS NOT NULL "
                    + "AND m.marcSald IS NULL "
                    + "AND m.fechMarc BETWEEN :fechaInicio AND :fechaFin "
                    + "ORDER BY m.fechMarc ASC";

            TypedQuery<AsistenciaMarcacion> query = em.createQuery(jpql, AsistenciaMarcacion.class);
            query.setParameter("codiPers", codiPers);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechDateFin);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public AsistenciaMarcacion findMarcacionIncompletaOne(int codiPers, Date fechaInicio, Date fechDateFin) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM AsistenciaMarcacion m "
                    + "WHERE m.codiPers = :codiPers "
                    + "AND m.marcIngr IS NOT NULL "
                    + "AND m.marcSald IS NULL "
                    + "AND m.fechMarc BETWEEN :fechaInicio AND :fechaFin ";

            TypedQuery<AsistenciaMarcacion> query = em.createQuery(jpql, AsistenciaMarcacion.class);
            query.setParameter("codiPers", codiPers);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechDateFin);

            return (AsistenciaMarcacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<AsistenciaMarcacion> findAllMarcacionCompletaByRange(Date fechaInicio, Date fechaFin) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM AsistenciaMarcacion m "
                    + "WHERE m.marcIngr IS NOT NULL "
                    + "AND m.marcSald IS NOT NULL "
                    + "AND m.fechMarc BETWEEN :fechaInicio AND :fechaFin ";
            TypedQuery<AsistenciaMarcacion> query = em.createQuery(jpql, AsistenciaMarcacion.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<AsistenciaMarcacion> listarXdia(Date fechaMarcacion) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT m FROM AsistenciaMarcacion m "
                    + "WHERE m.marcIngr IS NOT NULL "
                    + "AND m.marcSald IS NOT NULL "
                    + "AND m.fechMarc = :fechMarc";
            TypedQuery<AsistenciaMarcacion> q = em.createQuery(jpql, AsistenciaMarcacion.class);
            q.setParameter("fechMarc", fechaMarcacion);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws ParseException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        AsistenciaMarcacionJpaController pc = new AsistenciaMarcacionJpaController(emf);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date fechaInicio = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date fechaFin = calendar.getTime();

        AsistenciaMarcacion marca = pc.findMarcacionIncompletaOne(5, fechaInicio, fechaFin);
        if (marca == null) {
            System.out.println("No existe");
        } else {
            System.out.println(marca.getCodiMarc());
        }
    }

    public List<AsistenciaMarcacion> findMarcacionListaConPersonayFecha(int codiPers, Date fechaInicio) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT m FROM AsistenciaMarcacion m "
                            + "WHERE m.codiPers = :codiPers "
                            + "AND m.marcIngr IS NOT NULL "
                            + "AND m.marcSald IS NOT NULL "
                            + "AND m.fechMarc = :fechaInicio");
            query.setParameter("codiPers", codiPers);
            query.setParameter("fechaInicio", fechaInicio);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // public static void main(String[] args) {
    // EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory("gestorFarmacia");
    // AsistenciaMarcacionJpaController pc = new
    // AsistenciaMarcacionJpaController(emf);
    // List<AsistenciaMarcacion> lista = pc.findMarcacionListaConPersonayFecha(28,
    // new Date());
    // if (lista == null) {
    // System.out.println("No existe");
    // } else {
    // System.out.println(lista.size());
    // for (AsistenciaMarcacion asistenciaMarcacion : lista) {
    // System.out.println("Codi marcacion: " + asistenciaMarcacion.getCodiMarc());
    // }
    // }
    // }
}
