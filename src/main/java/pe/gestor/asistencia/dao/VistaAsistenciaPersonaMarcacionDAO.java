/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.asistencia.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import pe.gestor.asistencia.dto.VistaAsistenciaPersonaMarcacion;

/**
 *
 * @author USER
 */
public class VistaAsistenciaPersonaMarcacionDAO extends VistaAsistenciaPersonaMarcacionJpaController {

    public VistaAsistenciaPersonaMarcacionDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public List<VistaAsistenciaPersonaMarcacion> findByFechaRange(String fechaInicio, String fechaFin) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "SELECT codiMarc, codiPers, nombPers, "
                    + "DATE_FORMAT(fechMarc, '%Y-%m-%d') AS fechMarc, "
                    + "codiHoraDeta, marcIngr, marcSald "
                    + "FROM vista_asistencia_persona_marcacion "
                    + "WHERE fechMarc BETWEEN ? AND ?",
                    VistaAsistenciaPersonaMarcacion.class // Mapea los resultados a la entidad
            );
            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaFin);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList(); // Evita retornar null, mejor retorna una lista vacía
        } finally {
            em.close();
        }
    }

}
