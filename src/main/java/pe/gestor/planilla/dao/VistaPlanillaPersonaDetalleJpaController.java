package pe.gestor.planilla.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import pe.gestor.planilla.dto.VistaPlanillaPersonaDetalle;

/**
 *
 * @author USER
 */
public class VistaPlanillaPersonaDetalleJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public VistaPlanillaPersonaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Obtener todos los registros (opcional con paginación)
    public List<VistaPlanillaPersonaDetalle> findVistaPlanillaPersonaDetalleEntities() {
        return findVistaPlanillaPersonaDetalleEntities(true, -1, -1);
    }

    // Obtener registros con paginación
    public List<VistaPlanillaPersonaDetalle> findVistaPlanillaPersonaDetalleEntities(int maxResults, int firstResult) {
        return findVistaPlanillaPersonaDetalleEntities(false, maxResults, firstResult);
    }

    // Método privado para ejecutar la consulta de todas las personas (con paginación)
    private List<VistaPlanillaPersonaDetalle> findVistaPlanillaPersonaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            // Usando Criteria API para obtener todas las entidades de la clase VistaPlanillaPersonaDetalle
            Query q = em.createQuery("SELECT v FROM VistaPlanillaPersonaDetalle v", VistaPlanillaPersonaDetalle.class);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // Buscar una persona por su ID (numeDocu) directamente usando el EntityManager
    public VistaPlanillaPersonaDetalle findVistaPlanillaPersonaDetalle(String numeDocu) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaPlanillaPersonaDetalle.class, numeDocu);  // Usamos numeDocu que es un String
        } finally {
            em.close();
        }
    }

    // Método para obtener el conteo de los registros
    public int getVistaPlanillaPersonaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            // Consulta para contar todos los registros
            Query q = em.createQuery("SELECT COUNT(v) FROM VistaPlanillaPersonaDetalle v");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    // Método corregido para buscar por numeDocu usando EntityManager
    public VistaPlanillaPersonaDetalle findVistaPlanillaPersonaDetalleByNumeDocu(String numeDocu) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<VistaPlanillaPersonaDetalle> query = em.createQuery(
                "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.numeDocu = :numeDocu", VistaPlanillaPersonaDetalle.class);
            query.setParameter("numeDocu", numeDocu);
            return query.getSingleResult();  // Devuelve el único resultado encontrado
        } catch (Exception e) {
            return null;  // Si no encuentra el resultado, retorna null
        } finally {
            em.close();
        }
    }
}
