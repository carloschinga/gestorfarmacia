package pe.gestor.ventas.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import pe.gestor.ventas.dto.VentasGraficosMensuales;
import pe.gestor.ventas.dao.exceptions.NonexistentEntityException;
import pe.gestor.ventas.dao.exceptions.PreexistingEntityException;

public class VentasGraficosMensualesJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public VentasGraficosMensualesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para crear una venta
    public void create(VentasGraficosMensuales ventasGraficosMensuales) throws PreexistingEntityException, Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ventasGraficosMensuales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new PreexistingEntityException("Error al crear la venta: " + ventasGraficosMensuales, ex);
        } finally {
            em.close();
        }
    }

    // Método para editar una venta
    public void edit(VentasGraficosMensuales ventasGraficosMensuales) throws NonexistentEntityException, Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ventasGraficosMensuales = em.merge(ventasGraficosMensuales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new NonexistentEntityException("Error al actualizar la venta con mes: " + ventasGraficosMensuales.getMes(), ex);
        } finally {
            em.close();
        }
    }

    // Método para eliminar una venta por mes
    public void destroy(String mes) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            VentasGraficosMensuales ventasGraficosMensuales = em.find(VentasGraficosMensuales.class, mes);
            if (ventasGraficosMensuales == null) {
                throw new NonexistentEntityException("No se encontró la venta con mes: " + mes);
            }
            em.remove(ventasGraficosMensuales);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Método para obtener todas las ventas ordenadas por mes (Para gráficos mensuales)
    public List<VentasGraficosMensuales> findAllVentas() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<VentasGraficosMensuales> query = em.createQuery(
                "SELECT v FROM VentasGraficosMensuales v ORDER BY v.mes ASC", 
                VentasGraficosMensuales.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para obtener ventas diarias (si los datos están detallados por día)
    public List<Object[]> findVentasDiarias() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT v.mes, SUM(v.totalVendido) FROM VentasGraficosMensuales v GROUP BY v.mes ORDER BY v.mes ASC", 
                Object[].class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para obtener ventas en un rango de fechas (Útil para filtros en Chart.js)
    public List<VentasGraficosMensuales> findVentasByRangoFechas(String fechaInicio, String fechaFin) {
        EntityManager em = getEntityManager();
        try {
            // Consulta ajustada para trabajar con 'mes' en el formato 'MM/YYYY'
            TypedQuery<VentasGraficosMensuales> query = em.createQuery(
                "SELECT v FROM VentasGraficosMensuales v WHERE v.mes BETWEEN :fechaInicio AND :fechaFin ORDER BY v.mes ASC", 
                VentasGraficosMensuales.class
            );
            query.setParameter("fechaInicio", fechaInicio); // 'MM/YYYY'
            query.setParameter("fechaFin", fechaFin); // 'MM/YYYY'
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para buscar ventas por mes específico
    public List<VentasGraficosMensuales> findVentasByMes(String mes) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<VentasGraficosMensuales> query = em.createQuery(
                "SELECT v FROM VentasGraficosMensuales v WHERE v.mes = :mes ORDER BY v.mes ASC", 
                VentasGraficosMensuales.class
            );
            query.setParameter("mes", mes); // 'MM/YYYY'
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para obtener ventas en un rango de fechas con paginación
    public List<VentasGraficosMensuales> findVentasByRangoFechasWithPagination(String fechaInicio, String fechaFin, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            // Consulta ajustada para trabajar con 'mes' en el formato 'MM/YYYY'
            TypedQuery<VentasGraficosMensuales> query = em.createQuery(
                "SELECT v FROM VentasGraficosMensuales v WHERE v.mes BETWEEN :fechaInicio AND :fechaFin ORDER BY v.mes ASC", 
                VentasGraficosMensuales.class
            );
            query.setParameter("fechaInicio", fechaInicio); // 'MM/YYYY'
            query.setParameter("fechaFin", fechaFin); // 'MM/YYYY'
            query.setMaxResults(maxResults); 
            query.setFirstResult(firstResult); // Paginación
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
