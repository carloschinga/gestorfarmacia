package pe.gestor.asistencia.servlet;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import pe.gestor.asistencia.dao.*;
import pe.gestor.asistencia.dto.*;
import pe.gestor.contable.dao.ContablePeriodoJpaController;
import pe.gestor.contable.dto.ContablePeriodo;
import pe.gestor.planilla.dao.*;
import pe.gestor.planilla.dto.*;

@WebServlet(name = "AsistenciaTareoServlet", urlPatterns = { "/asistenciatareo" })
public class AsistenciaTareoServlet extends HttpServlet {
    private final EntityManagerFactory emf;
    private final AsistenciaTareoJpaController tareoDAO;
    private final ContablePeriodoJpaController periDAO;
    private final AsistenciaRangoPeriodoJpaController rangoDAO;
    private final AsistenciaMarcacionJpaController marcaDAO;
    private final AsistenciaHorariodetalleJpaController detalleDAO;
    private final AsistenciaTurnoJpaController turnoDAO;
    private final VistaPlanillaPersonaJpaController personaDAO;
    private final AsistenciaFeriadoJpaController feriadoDAO;
    private final PlanillaPersonaJpaController plaPersoDAO;
    private final PlanillaDiaJpaController diaDAO;

    public AsistenciaTareoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tareoDAO = new AsistenciaTareoJpaController(emf);
        this.periDAO = new ContablePeriodoJpaController(emf);
        this.rangoDAO = new AsistenciaRangoPeriodoJpaController(emf);
        this.marcaDAO = new AsistenciaMarcacionJpaController(emf);
        this.detalleDAO = new AsistenciaHorariodetalleJpaController(emf);
        this.turnoDAO = new AsistenciaTurnoJpaController(emf);
        this.personaDAO = new VistaPlanillaPersonaJpaController(emf);
        this.feriadoDAO = new AsistenciaFeriadoJpaController(emf);
        this.plaPersoDAO = new PlanillaPersonaJpaController(emf);
        this.diaDAO = new PlanillaDiaJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String codiPeri = request.getParameter("codiPeriodo");
        int codiRang = Integer.parseInt(codiPeri);

        AsistenciaRangoPeriodo rango = rangoDAO.findAsistenciaRangoPeriodo(codiRang);
        Date inicioRango = rango.getInicRang();
        Date finalRango = ajustarFinDeDia(rango.getFinaRang());

        List<Date> fechas = crearListaFechas(inicioRango, finalRango);
        List<Date> fechasOficiales = listaSinFeriados(fechas);

        // Mapa para acumular minutos totales y días faltantes por persona
        Map<Integer, ResponsePersona> responseMap = new HashMap<>();

        for (Date date : fechasOficiales) {
            procesarDia(date, responseMap);
        }

        // Convertir el mapa a JSON
        JSONArray jsonArray = new JSONArray();
        for (ResponsePersona persona : responseMap.values()) {
            JSONObject jsonPersona = new JSONObject();
            jsonPersona.put("codiPers", persona.getCodiPers());
            jsonPersona.put("nombre", persona.getNombre());
            jsonPersona.put("minutosTotales", persona.getMinutosTotales());
            jsonPersona.put("diasFaltantes", persona.getDiasFaltantes());
            jsonArray.put(jsonPersona);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonArray.toString());
    }

    private void procesarDia(Date date, Map<Integer, ResponsePersona> responseMap) {
        LocalDateTime dateLocal = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DayOfWeek diaSemana = dateLocal.getDayOfWeek();
        String nombreDia = diaSemana.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-PE"));
        PlanillaDia dia = diaDAO.findPlanillaDiaByNomb(nombreDia.toUpperCase());

        List<AsistenciaMarcacion> marcacionesRango = marcaDAO.listarXdia(date);

        List<PlanillaPersona> listaDePersonasActivas = plaPersoDAO.findActivos();

        // Agrupar marcaciones por persona
        Map<Integer, List<AsistenciaMarcacion>> marcacionesPorPersona = new HashMap<>();
        if (marcacionesRango != null) {
            for (AsistenciaMarcacion item : marcacionesRango) {
                int codiPers = item.getCodiPers();
                List<AsistenciaMarcacion> listaMarcaciones = marcacionesPorPersona.get(codiPers);
                if (listaMarcaciones == null) {
                    listaMarcaciones = new ArrayList<>();
                    marcacionesPorPersona.put(codiPers, listaMarcaciones);
                }
                listaMarcaciones.add(item);
            }
        }

        // Procesar marcaciones por persona
        for (PlanillaPersona persona : listaDePersonasActivas) {
            int codiPers = persona.getCodiPers();

            // Obtener o crear el objeto ResponsePersona para esta persona
            ResponsePersona responsePersona = responseMap.get(codiPers);
            if (responsePersona == null) {
                String nombreCompleto = personaDAO.findVistaPlanillaPersona(codiPers).getNombreCompleto();
                responsePersona = new ResponsePersona(codiPers, nombreCompleto, 0, 0);
                responseMap.put(codiPers, responsePersona);
            }

            // Verificar si la persona tiene marcaciones para este día
            List<AsistenciaMarcacion> marcaciones = marcacionesPorPersona.get(codiPers);

            // Si la persona no tiene marcaciones, es un día faltante
            if (marcaciones == null || marcaciones.isEmpty()) {
                responsePersona.setDiasFaltantes(responsePersona.getDiasFaltantes() + 1);
                continue;
            }

            // Si la persona tiene marcaciones, calcular tardanzas y marcaciones faltantes
            int cargo = persona.getCodiHora();
            List<AsistenciaHorariodetalle> marcacionesDisponibles = detalleDAO.allMarcacionesByCargo(dia.getCodiDia(),
                    cargo);
            Duration duracionTotal = calcularTardanzasYFaltantes(marcaciones, marcacionesDisponibles);

            responsePersona.setMinutosTotales(responsePersona.getMinutosTotales() + (int) duracionTotal.toMinutes());
        }
    }

    private Duration calcularTardanzasYFaltantes(List<AsistenciaMarcacion> marcaciones,
            List<AsistenciaHorariodetalle> marcacionesDisponibles) {
        Duration duracionTotal = Duration.ZERO;
        Duration duracionParaSalida = Duration.ZERO;
        Set<AsistenciaHorariodetalle> marcacionesRealizadas = new HashSet<>();

        for (AsistenciaMarcacion item : marcaciones) {
            AsistenciaHorariodetalle itemDetalle = detalleDAO.findAsistenciaHorariodetalle(item.getCodiHoraDeta());
            marcacionesRealizadas.add(itemDetalle);

            AsistenciaTurno itemTurno = turnoDAO.findAsistenciaTurno(itemDetalle.getCodiTurn());
            LocalTime horaIngreso = convertDateToLocalTime(itemTurno.getHoraIngr());
            LocalTime horaSalida = convertDateToLocalTime(itemTurno.getHoraSald());
            LocalTime marcaIngreso = convertDateToLocalTime(item.getMarcIngr());
            LocalTime marcaSalida = convertDateToLocalTime(item.getMarcSald());

            Duration duracionEntrada = Duration.between(horaIngreso, marcaIngreso);
            if (!duracionEntrada.isNegative()) {
                duracionTotal = duracionTotal.plus(duracionEntrada);
            }

            Duration duracionSalida = Duration.between(horaSalida, marcaSalida);
            if (duracionSalida.isNegative()) {
                duracionParaSalida = duracionSalida.abs();
            }
            duracionTotal = duracionTotal.plus(duracionParaSalida);
            System.out.println(duracionParaSalida.toMinutes());
        }

        // Calcular marcaciones faltantes
        for (AsistenciaHorariodetalle detalle : marcacionesDisponibles) {
            if (!marcacionesRealizadas.contains(detalle)) {
                AsistenciaTurno itemTurno = turnoDAO.findAsistenciaTurno(detalle.getCodiTurn());
                LocalTime horaIngreso = convertDateToLocalTime(itemTurno.getHoraIngr());
                LocalTime horaSalida = convertDateToLocalTime(itemTurno.getHoraSald());
                Duration duracionFalta = Duration.between(horaIngreso, horaSalida);
                duracionTotal = duracionTotal.plus(duracionFalta);
            }
        }

        return duracionTotal;
    }

    private List<Date> crearListaFechas(Date inicio, Date finalizar) {
        List<Date> fechas = new ArrayList<>();
        Date fecha = inicio;

        while (fecha.before(finalizar)) {
            fechas.add(fecha);
            fecha = DateUtils.addDays(fecha, 1);
        }

        return fechas;
    }

    private List<Date> listaSinFeriados(List<Date> fechas) {
        List<Date> listadoFechas = new ArrayList<>();
        List<AsistenciaFeriado> feriados = feriadoDAO.findAsistenciaFeriadoEntities();

        for (Date fecha : fechas) {
            boolean esFeriado = false;
            for (AsistenciaFeriado item : feriados) {
                if (item.getFechFeri().equals(fecha)) {
                    esFeriado = true;
                    break;
                }
            }
            if (!esFeriado) {
                listadoFechas.add(fecha);
            }
        }

        return listadoFechas;
    }

    private Date ajustarFinDeDia(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private LocalTime convertDateToLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    // Clase ResponsePersona
    private static class ResponsePersona {
        private int codiPers;
        private String nombre;
        private int minutosTotales;
        private int diasFaltantes;

        public ResponsePersona(int codiPers, String nombre, int minutosTotales, int diasFaltantes) {
            this.codiPers = codiPers;
            this.nombre = nombre;
            this.minutosTotales = minutosTotales;
            this.diasFaltantes = diasFaltantes;
        }

        public int getCodiPers() {
            return codiPers;
        }

        public String getNombre() {
            return nombre;
        }

        public int getMinutosTotales() {
            return minutosTotales;
        }

        public void setMinutosTotales(int minutosTotales) {
            this.minutosTotales = minutosTotales;
        }

        public int getDiasFaltantes() {
            return diasFaltantes;
        }

        public void setDiasFaltantes(int diasFaltantes) {
            this.diasFaltantes = diasFaltantes;
        }
    }
}