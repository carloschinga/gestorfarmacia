package pe.gestor.asistencia.servlet;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pe.gestor.asistencia.dao.AsistenciaMarcacionJpaController;
import pe.gestor.asistencia.dao.AsistenciaRangoPeriodoJpaController;
import pe.gestor.asistencia.dto.AsistenciaMarcacion;
import pe.gestor.asistencia.dto.AsistenciaRangoPeriodo;

public class pruebas {
    public static void main(String[] args) {
        // Date date = new Date();
        // LocalDateTime dateLocal =
        // date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // DayOfWeek diaSemana = dateLocal.getDayOfWeek();
        // String nombreDia = diaSemana.getDisplayName(TextStyle.FULL,
        // Locale.forLanguageTag("es-PE"));
        // System.out.println(nombreDia.toUpperCase());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        AsistenciaMarcacionJpaController marcaDAO = new AsistenciaMarcacionJpaController(emf);
        List<AsistenciaMarcacion> marcacionesRango = marcaDAO.listarXdia(new Date());
        if (marcacionesRango == null) {
            System.out.println("No existe");
        } else {
            System.out.println(marcacionesRango.size());
            for (AsistenciaMarcacion asistenciaMarcacion : marcacionesRango) {
                System.out.println(asistenciaMarcacion.getCodiPers());
            }
        }
    }
}
