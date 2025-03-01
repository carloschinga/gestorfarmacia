package pe.gestor.asistencia.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import pe.gestor.asistencia.dao.VistaAsistenciaMarcacionpersonaDAO;
import pe.gestor.asistencia.dto.AsistenciaMarcacion;
import pe.gestor.planilla.dto.PlanillaPersona;

/**
 *
 * @author USER
 */
@WebServlet(name = "MarcarAsistenciaServlet", urlPatterns = {"/marcarasistencia"})
public class MarcarAsistenciaServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private final VistaAsistenciaMarcacionpersonaDAO vistaPersonaMarcacionDAO;
    private final EntityManagerFactory emf;

    public MarcarAsistenciaServlet() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.vistaPersonaMarcacionDAO = new VistaAsistenciaMarcacionpersonaDAO(emf);
    }

    @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonRequest = new JSONObject(sb.toString());
            String numeDocu = jsonRequest.getString("numeDocu").trim();  // Aseguramos que no tenga espacios adicionales
            String coditipo = jsonRequest.getString("codiTipoDoc").trim();  // Aseguramos que no tenga espacios adicionales

            EntityManager em = emf.createEntityManager();
            try {
                // Buscar la persona por número de documento
                List<PlanillaPersona> personas = em.createNamedQuery("PlanillaPersona.findByNumeDocu", PlanillaPersona.class)
                        .setParameter("numeDocu", numeDocu)
                        .setParameter("codiTipoDoc", coditipo)
                        .getResultList();

                if (!personas.isEmpty()) {
                    PlanillaPersona persona = personas.get(0);  // Tomamos el primer resultado si hay múltiples

                    // Obtener la fecha actual (usaremos Calendar para manejar las horas y fechas correctamente)
                    Calendar calendar = Calendar.getInstance();
                    Date fechaHoy = calendar.getTime();

                    // Configuramos la fecha para verificar solo el día (sin hora, minutos, segundos, milisegundos)
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date fechaInicio = calendar.getTime();  // Fecha a medianoche

                    // Configuración de la fecha de fin (último momento del día, 23:59:59.999)
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    calendar.set(Calendar.MILLISECOND, 999);
                    Date fechaFin = calendar.getTime();  // Último momento del día

                    // Consulta para verificar si ya existe una marcación en el rango de fechas para esta persona
                    String sql = "SELECT * FROM asistencia_marcacion m WHERE m.codiPers = ? "
                            + "AND m.marcIngr IS NOT NULL "
                            + "AND m.marcSald IS NULL "
                            + "AND m.fechMarc BETWEEN ? AND ?";  // Usamos BETWEEN para filtrar el rango de fechas
                    Query query = em.createNativeQuery(sql, AsistenciaMarcacion.class);
                    query.setParameter(1, persona.getCodiPers());
                    query.setParameter(2, fechaInicio);  // Fecha inicio del rango (medianoche)
                    query.setParameter(3, fechaFin);    // Fecha fin del rango (último momento del día)

                    List<AsistenciaMarcacion> marcaciones = query.getResultList();

                    if (!marcaciones.isEmpty()) {
                        // Si ya existe una marcación de entrada para este día, registramos la hora de salida
                        AsistenciaMarcacion marcacionExistente = marcaciones.get(0);
                        if (marcacionExistente.getMarcSald() == null) {  // Si la hora de salida es nula
                            // Registrar la hora de salida
                            marcacionExistente.setMarcSald(new Date()); // Hora actual para salida
                            em.getTransaction().begin();
                            em.merge(marcacionExistente);  // Actualizamos la marcación existente con la hora de salida
                            em.getTransaction().commit();

                            jsonResponse.put("status", "success");
                            jsonResponse.put("message", "Hora de salida registrada");
                            jsonResponse.put("codiMarc", marcacionExistente.getCodiMarc());
                            jsonResponse.put("nombre", persona.getNombPers());
                            jsonResponse.put("hora", marcacionExistente.getMarcSald().toString());  // Hora de salida
                            jsonResponse.put("marcacion", "salida");  // Enviar "salida"
                        } else {
                            jsonResponse.put("status", "error");
                            jsonResponse.put("message", "Ya se registró la salida para este día");
                        }
                    } else {
                        // Si no existe marcación de entrada, registramos una nueva entrada
                        AsistenciaMarcacion nuevaMarcacion = new AsistenciaMarcacion();
                        nuevaMarcacion.setCodiPers(persona.getCodiPers());
                        nuevaMarcacion.setFechMarc(fechaHoy);  // Fecha de hoy
                        nuevaMarcacion.setMarcIngr(new Date());  // Hora de entrada

                        em.getTransaction().begin();
                        em.persist(nuevaMarcacion);  // Guardamos la nueva marcación de entrada
                        em.getTransaction().commit();

                        jsonResponse.put("status", "success");
                        jsonResponse.put("message", "Entrada registrada");
                        jsonResponse.put("codiMarc", nuevaMarcacion.getCodiMarc());
                        jsonResponse.put("nombre", persona.getNombPers());
                        jsonResponse.put("hora", nuevaMarcacion.getMarcIngr().toString());  // Hora de entrada
                        jsonResponse.put("marcacion", "entrada");  // Enviar "entrada"
                    }
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Persona no encontrada para el documento: " + numeDocu);
                }
            } catch (Exception e) {
                e.printStackTrace();  // Imprimir detalles de la excepción
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Error El DNI no figura en la base de datos: " + e.getMessage());
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