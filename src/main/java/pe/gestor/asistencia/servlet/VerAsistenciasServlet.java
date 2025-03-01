package pe.gestor.asistencia.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.asistencia.dto.AsistenciaMarcacion;
import pe.gestor.planilla.dto.PlanillaPersona;

@WebServlet(name = "VerAsistenciasServlet", urlPatterns = {"/verAsistencias"})
public class VerAsistenciasServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private final EntityManagerFactory emf;

    public VerAsistenciasServlet() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String numeDocu = request.getParameter("numeDocu").trim();  // Número de documento
            String coditipo = request.getParameter("codiTipoDoc").trim();  // Tipo de documento

            if (numeDocu == null || coditipo == null || numeDocu.isEmpty() || coditipo.isEmpty()) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Los parámetros numeDocu y codiTipoDoc son obligatorios.");
                out.print(jsonResponse.toString());
                return;
            }

            EntityManager em = emf.createEntityManager();
            try {
                // Buscar la persona por número de documento
                List<PlanillaPersona> personas = em.createNamedQuery("PlanillaPersona.findByNumeDocu", PlanillaPersona.class)
                        .setParameter("numeDocu", numeDocu)
                        .setParameter("codiTipoDoc", coditipo)
                        .getResultList();

                if (!personas.isEmpty()) {
                    PlanillaPersona persona = personas.get(0);  // Tomamos el primer resultado si hay múltiples

                    // Obtener la fecha de hoy (sin la hora, solo la fecha)
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date fechaInicio = calendar.getTime();  // Fecha de inicio (medianoche de hoy)

                    // Configuración de la fecha de fin (último momento del día, 23:59:59.999)
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    calendar.set(Calendar.MILLISECOND, 999);
                    Date fechaFin = calendar.getTime();  // Fecha final (último segundo de hoy)

                    // Consulta para obtener todas las asistencias de la persona solo para hoy
                    String sql = "SELECT * FROM asistencia_marcacion m WHERE m.codiPers = ? "
                            + "AND m.fechMarc BETWEEN ? AND ?";  // Usamos BETWEEN para filtrar solo las asistencias de hoy
                    Query query = em.createNativeQuery(sql, AsistenciaMarcacion.class);
                    query.setParameter(1, persona.getCodiPers());
                    query.setParameter(2, fechaInicio);  // Fecha inicio de hoy (medianoche)
                    query.setParameter(3, fechaFin);    // Fecha fin de hoy (último segundo)

                    List<AsistenciaMarcacion> marcaciones = query.getResultList();

                    if (!marcaciones.isEmpty()) {
                        // Convertir la lista de marcaciones a un JSON para la respuesta
                        JSONArray asistencias = new JSONArray();
                        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "PE")); // Fecha en formato español
                        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss"); // Hora en formato de 24 horas

                        for (AsistenciaMarcacion marcacion : marcaciones) {
                            JSONObject asistencia = new JSONObject();

                            // Verificar si las fechas son nulas y devolver un valor adecuado
                            String fechaFormateada = (marcacion.getFechMarc() != null) ? sdfFecha.format(marcacion.getFechMarc()) : "No registrada";
                            String entradaFormateada = (marcacion.getMarcIngr() != null) ? sdfHora.format(marcacion.getMarcIngr()) : "No registrada";
                            String salidaFormateada = (marcacion.getMarcSald() != null) ? sdfHora.format(marcacion.getMarcSald()) : "No registrada";

                            asistencia.put("fecha", fechaFormateada);
                            asistencia.put("entrada", entradaFormateada);
                            asistencia.put("salida", salidaFormateada);
                            asistencias.put(asistencia);
                        }

                        jsonResponse.put("status", "success");
                        jsonResponse.put("asistencias", asistencias);
                    } else {
                        jsonResponse.put("status", "error");
                        jsonResponse.put("message", "No se encontraron asistencias para esta persona hoy.");
                    }
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Persona no encontrada para el documento: " + numeDocu);
                }
            } catch (Exception e) {
                e.printStackTrace();  // Imprimir detalles de la excepción
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Error al consultar las asistencias: " + e.getMessage());
            }

            out.print(jsonResponse.toString());
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("status", "error");
            jsonResponse.put("message", ex.getMessage());
            out.print(jsonResponse.toString());
        } finally {
            out.close();
        }
    }
}