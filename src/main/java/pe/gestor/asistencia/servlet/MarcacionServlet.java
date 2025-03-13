package pe.gestor.asistencia.servlet;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import pe.gestor.asistencia.dao.AsistenciaHorariodetalleJpaController;
import pe.gestor.asistencia.dao.AsistenciaMarcacionJpaController;
import pe.gestor.asistencia.dao.AsistenciaParametrosJpaController;
import pe.gestor.asistencia.dao.AsistenciaTurnoJpaController;
import pe.gestor.asistencia.dao.VistaAsistenciaMarcacionpersonaDAO;
import pe.gestor.asistencia.dto.AsistenciaHorariodetalle;
import pe.gestor.asistencia.dto.AsistenciaMarcacion;
import pe.gestor.asistencia.dto.AsistenciaParametros;
import pe.gestor.asistencia.dto.AsistenciaTurno;
import pe.gestor.planilla.dao.PlanillaDiaJpaController;
import pe.gestor.planilla.dao.PlanillaPersonaDAO;
import pe.gestor.planilla.dto.PlanillaDia;
import pe.gestor.planilla.dto.PlanillaPersona;

/**
 * Servlet para gestionar marcaciones de asistencia de entrada y salida
 */
@WebServlet(name = "Marcacion", urlPatterns = { "/marcacion" })
public class MarcacionServlet extends HttpServlet {

    private static final Locale LOCALE_PE = new Locale("es", "PE");
    private static final ZoneId ZONE_LIMA = ZoneId.of("America/Lima");

    // DAOs
    private final VistaAsistenciaMarcacionpersonaDAO vistaPersonaMarcacionDAO;
    private final PlanillaPersonaDAO planillaPersonaDAO;
    private final AsistenciaMarcacionJpaController marcacionDAO;
    private final AsistenciaParametrosJpaController parametrosDAO;
    private final AsistenciaHorariodetalleJpaController horarioDetalleDAO;
    private final PlanillaDiaJpaController diaDAO;
    private final AsistenciaTurnoJpaController turnoDAO;

    public MarcacionServlet() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.vistaPersonaMarcacionDAO = new VistaAsistenciaMarcacionpersonaDAO(emf);
        this.marcacionDAO = new AsistenciaMarcacionJpaController(emf);
        this.planillaPersonaDAO = new PlanillaPersonaDAO(emf);
        this.parametrosDAO = new AsistenciaParametrosJpaController(emf);
        this.horarioDetalleDAO = new AsistenciaHorariodetalleJpaController(emf);
        this.diaDAO = new PlanillaDiaJpaController(emf);
        this.turnoDAO = new AsistenciaTurnoJpaController(emf);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configureResponse(response);

        try {
            // Extraer datos del request
            JSONObject jsonRequest = readRequestBody(request);
            String numeDocu = jsonRequest.getString("numeDocu");
            String codiTipo = jsonRequest.getString("codiTipoDoc");

            // Buscar persona
            PlanillaPersona persona = planillaPersonaDAO.findPlanillaPersonaByNumeDocu(numeDocu, codiTipo);
            if (persona == null) {
                sendErrorResponse(response, "Persona no encontrada");
                return;
            }

            // Configurar fechas para el día actual
            DateRange dateRange = getDateRangeForToday();

            // Buscar marcaciones existentes
            List<AsistenciaMarcacion> marcaciones = marcacionDAO.findMarcacionIncompleta(
                    persona.getCodiPers(), dateRange.getStart(), dateRange.getEnd());

            // Obtener información de horario y turno
            LocalDateTime now = LocalDateTime.now();
            HorarioInfo horarioInfo = getHorarioInfo(now, persona);

            if (horarioInfo.getDetalleHorario() == null) {
                sendErrorResponse(response, "Lo sentimos pero no hay turnos disponibles a esta hora");
                return;
            }

            // Procesar marcación (entrada o salida)
            if (marcaciones.isEmpty()) {
                procesarMarcacionEntrada(response, persona, horarioInfo, now);
            } else {
                procesarMarcacionSalida(response, persona, horarioInfo, marcaciones.get(0), now);
            }

        } catch (Exception ex) {
            sendInternalErrorResponse(response, ex);
        }
    }

    /**
     * Configura los parámetros de respuesta
     */
    private void configureResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
    }

    /**
     * Lee el cuerpo de la petición y lo convierte a JSONObject
     */
    private JSONObject readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    /**
     * Envía una respuesta de error
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new JSONObject()
                .put("status", "error")
                .put("message", message)
                .toString());
    }

    /**
     * Envía una respuesta de error interno
     */
    private void sendInternalErrorResponse(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(new JSONObject()
                .put("error", "Error al crear el registro: " + ex.getMessage())
                .toString());
    }

    /**
     * Obtiene el rango de fechas para el día actual (inicio y fin del día)
     */
    private DateRange getDateRangeForToday() {
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

        return new DateRange(fechaInicio, fechaFin);
    }

    /**
     * Obtiene la información de horario y turno para la persona
     */
    private HorarioInfo getHorarioInfo(LocalDateTime now, PlanillaPersona persona) {
        // Obtener el día de la semana
        DayOfWeek diaSemana = now.getDayOfWeek();
        String nombreDia = diaSemana.getDisplayName(TextStyle.FULL, LOCALE_PE);
        PlanillaDia planillaDia = diaDAO.findPlanillaDiaByNomb(nombreDia.toUpperCase());

        // Obtener el horario por cargo y día
        int cargoHorario = persona.getCodiCargo();
        List<AsistenciaHorariodetalle> detallesHorario = horarioDetalleDAO.findAllByCodiHora(
                cargoHorario, planillaDia.getCodiDia());

        // Obtener los parámetros de tolerancia
        AsistenciaParametros parametros = parametrosDAO.findAsistenciaParametros(1);
        long toleranciaMinutos = Long.parseLong(parametros.getValuTareoPara());

        // Encontrar el turno correspondiente a la hora actual
        LocalTime horaActual = now.toLocalTime();
        AsistenciaHorariodetalle detalleDefinitivo = null;
        AsistenciaTurno turnoDefinitivo = null;

        for (AsistenciaHorariodetalle detalleHorario : detallesHorario) {
            AsistenciaTurno turno = turnoDAO.findAsistenciaTurno(detalleHorario.getCodiTurn());

            LocalTime horaIngreso = turno.getHoraIngr().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
                    .minusMinutes(toleranciaMinutos);

            LocalTime horaSalida = turno.getHoraSald().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
                    .plusMinutes(toleranciaMinutos);

            if (horaActual.isAfter(horaIngreso) && horaActual.isBefore(horaSalida)) {
                detalleDefinitivo = detalleHorario;
                turnoDefinitivo = turno;
                break;
            }
        }

        return new HorarioInfo(detalleDefinitivo, turnoDefinitivo, toleranciaMinutos);
    }

    /**
     * Procesa la marcación de entrada
     */
    private void procesarMarcacionEntrada(
            HttpServletResponse response,
            PlanillaPersona persona,
            HorarioInfo horarioInfo,
            LocalDateTime now) throws Exception {

        LocalTime horaActual = now.toLocalTime();
        AsistenciaTurno turno = horarioInfo.getTurno();
        long toleranciaMinutos = horarioInfo.getToleranciaMinutos();

        // Verificar si está dentro del rango para marcar entrada
        LocalTime horaEntrada = turno.getHoraIngr().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
        LocalTime inicioRango = horaEntrada.minusMinutes(toleranciaMinutos);
        LocalTime finRango = horaEntrada.plusMinutes(toleranciaMinutos);

        if (toleranciaMinutos > 59) {
            sendErrorResponse(response, "La tolerancia no debe ser mayor a 59 minutos.");
            return;
        }

        if (horaActual.isAfter(finRango) || horaActual.isBefore(inicioRango)) {
            sendErrorResponse(response, "No esta dentro del rango para marcar la entrada en su asistencia");
            return;
        }

        // Crear y guardar la marcación de entrada
        Date fechaActual = convertLocalDateTimeToDate(now, ZONE_LIMA);
        AsistenciaMarcacion nuevaMarcacion = new AsistenciaMarcacion();
        nuevaMarcacion.setCodiPers(persona.getCodiPers());
        nuevaMarcacion.setFechMarc(fechaActual);
        nuevaMarcacion.setCodiHoraDeta(horarioInfo.getDetalleHorario().getCodiHoraDeta());
        nuevaMarcacion.setMarcIngr(fechaActual);

        marcacionDAO.create(nuevaMarcacion);

        // Preparar respuesta exitosa
        enviarRespuestaExitosa(response,
                nuevaMarcacion.getCodiMarc(),
                persona.getNombPers(),
                nuevaMarcacion.getMarcIngr(),
                "entrada",
                "Entrada registrada");
    }

    /**
     * Procesa la marcación de salida
     */
    private void procesarMarcacionSalida(
            HttpServletResponse response,
            PlanillaPersona persona,
            HorarioInfo horarioInfo,
            AsistenciaMarcacion marcacionExistente,
            LocalDateTime now) throws Exception {

        LocalTime horaActual = now.toLocalTime();
        AsistenciaTurno turno = horarioInfo.getTurno();
        long toleranciaMinutos = horarioInfo.getToleranciaMinutos();

        // Verificar si está dentro del rango para marcar salida
        LocalTime horaSalida = turno.getHoraSald().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
        LocalTime inicioRango = horaSalida.minusMinutes(toleranciaMinutos);
        LocalTime finRango = horaSalida.plusMinutes(toleranciaMinutos);

        if (horaActual.isAfter(finRango) || horaActual.isBefore(inicioRango)) {
            sendErrorResponse(response, "No esta dentro del rango para marcar la salida en su asistencia");
            return;
        }

        // Verificar si ya se registró la salida
        if (marcacionExistente.getMarcSald() != null) {
            sendErrorResponse(response, "Ya se registro la salida para este día");
            return;
        }

        // Actualizar la marcación con la hora de salida
        marcacionExistente.setMarcSald(convertLocalDateTimeToDate(now, ZONE_LIMA));
        marcacionDAO.edit(marcacionExistente);

        // Preparar respuesta exitosa
        enviarRespuestaExitosa(response,
                marcacionExistente.getCodiMarc(),
                persona.getNombPers(),
                marcacionExistente.getMarcSald(),
                "salida",
                "Hora de salida registrada");
    }

    /**
     * Envía una respuesta exitosa con formato JSONObject
     */
    private void enviarRespuestaExitosa(
            HttpServletResponse response,
            Integer codiMarc,
            String nombre,
            Date hora,
            String tipoMarcacion,
            String mensaje) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", LOCALE_PE);

        JSONObject jsonResponse = new JSONObject()
                .put("status", "success")
                .put("message", mensaje)
                .put("codiMarc", codiMarc)
                .put("nombre", nombre)
                .put("hora", sdf.format(hora))
                .put("marcacion", tipoMarcacion);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonResponse.toString());
    }

    /**
     * Convierte LocalDateTime a Date utilizando la zona horaria especificada
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime, ZoneId zoneId) {
        // Convertir LocalDateTime a ZonedDateTime usando la zona horaria especificada
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        // Convertir ZonedDateTime a Instant
        Instant instant = zonedDateTime.toInstant();

        // Convertir Instant a Date
        return Date.from(instant);
    }

    /**
     * Clase para representar un rango de fechas
     */
    private static class DateRange {
        private final Date start;
        private final Date end;

        public DateRange(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        public Date getStart() {
            return start;
        }

        public Date getEnd() {
            return end;
        }
    }

    /**
     * Clase para encapsular la información de horario y turno
     */
    private static class HorarioInfo {
        private final AsistenciaHorariodetalle detalleHorario;
        private final AsistenciaTurno turno;
        private final long toleranciaMinutos;

        public HorarioInfo(AsistenciaHorariodetalle detalleHorario, AsistenciaTurno turno, long toleranciaMinutos) {
            this.detalleHorario = detalleHorario;
            this.turno = turno;
            this.toleranciaMinutos = toleranciaMinutos;
        }

        public AsistenciaHorariodetalle getDetalleHorario() {
            return detalleHorario;
        }

        public AsistenciaTurno getTurno() {
            return turno;
        }

        public long getToleranciaMinutos() {
            return toleranciaMinutos;
        }
    }
}