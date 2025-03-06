/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.planilla.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.planilla.dao.PlanillaPersonaDAO;
import pe.gestor.planilla.dao.PlanillaUbigeoJpaController;
import pe.gestor.planilla.dao.VistaPlanillaPersonaDAO;
import pe.gestor.planilla.dto.PlanillaPersona;
import pe.gestor.planilla.dto.PlanillaUbigeo;
import pe.gestor.planilla.dto.VistaPlanillaPersona;

/**
 *
 * @author USER
 */
@WebServlet(name = "PersonaServlet", urlPatterns = { "/personaservlet/*", "/personaservlet/personas" })
public class PersonaServlet extends HttpServlet {

    private final PlanillaPersonaDAO personaDAO;
    private final VistaPlanillaPersonaDAO vistaPersonaDetalleDAO;
    private final PlanillaUbigeoJpaController ubigeoDAO;
    private final EntityManagerFactory emf;

    public PersonaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.ubigeoDAO = new PlanillaUbigeoJpaController(emf);
        this.personaDAO = new PlanillaPersonaDAO(emf);
        this.vistaPersonaDetalleDAO = new VistaPlanillaPersonaDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        emf.getCache().evictAll();

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                EntityManager em = emf.createEntityManager();
                try {
                    // Deshabilitar caché para esta consulta
                    em.setProperty("javax.persistence.cache.retrieveMode", "BYPASS");

                    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
                    cq.select(cq.from(VistaPlanillaPersona.class));
                    Query q = em.createQuery(cq);

                    List<VistaPlanillaPersona> personaList = q.getResultList();

                    for (VistaPlanillaPersona vistaPlanillaPersona : personaList) {
                        System.out.println(vistaPlanillaPersona.getNombreCompleto());
                    }

                    JSONArray jsonArray = new JSONArray();
                    for (VistaPlanillaPersona persona : personaList) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("codiPers", persona.getCodiPers());
                        jsonObject.put("nombTipoDoc", persona.getNombTipoDoc());
                        jsonObject.put("numeDocu", persona.getNumeDocu());
                        jsonObject.put("nombre_completo", persona.getNombreCompleto());
                        jsonArray.put(jsonObject);
                    }

                    response.getWriter().write(jsonArray.toString());
                } finally {
                    if (em != null && em.isOpen()) {
                        em.close();
                    }
                }
            } else {
                // Obtener un registro específico por codiPers o numeDocu
                String param = pathInfo.substring(1).trim();
                int codiPers = Integer.parseInt(param);

                PlanillaPersona vpm = personaDAO.findPlanillaPersona(codiPers);

                if (vpm == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                PlanillaUbigeo ubiNive2 = ubigeoDAO.getParentUbigeo(vpm.getCodiUbig());

                PlanillaUbigeo ubiNive1 = ubigeoDAO.getParentUbigeo(ubiNive2.getCodiUbig());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiPers", vpm.getCodiPers());
                jsonObject.put("codiTipoDoc", vpm.getCodiTipoDoc());
                jsonObject.put("numeDocu", vpm.getNumeDocu());
                jsonObject.put("appaPers", vpm.getAppaPers());
                jsonObject.put("apmaPers", vpm.getApmaPers());
                jsonObject.put("nombPers", vpm.getNombPers());
                jsonObject.put("codiNaci", vpm.getCodiNaci());
                jsonObject.put("sexoPers", String.valueOf(vpm.getSexoPers()));
                jsonObject.put("codiPaisEmis", vpm.getCodiPaisEmis());
                jsonObject.put("codiCLDN", vpm.getCodiCLDN());
                jsonObject.put("numeCelu", vpm.getNumeCelu());
                jsonObject.put("mailPers", vpm.getMailPers());
                jsonObject.put("codiTipoVia", vpm.getCodiTipoVia());
                jsonObject.put("nombVia", vpm.getNombVia());
                jsonObject.put("numeVia", vpm.getNumeVia());
                jsonObject.put("depaPers", vpm.getDepaPers());
                jsonObject.put("nivel1", ubiNive1.getCodiUbig());
                jsonObject.put("nivel2", ubiNive2.getCodiUbig());
                jsonObject.put("nivel3", vpm.getCodiUbig());
                jsonObject.put("snpPers", vpm.getSnpPers());
                jsonObject.put("codiAFP", vpm.getCodiAFP());
                jsonObject.put("actiPers", vpm.getActiPers());
                jsonObject.put("codiEntiBanc", vpm.getCodiEntiBanc());
                jsonObject.put("codiPlantilla", vpm.getCodiPlant());

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter()
                    .write(new JSONObject().put("error", "Error al obtener los datos: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            String codiTipoDoc = request.getParameter("codiTipoDoc");
            String numeDocu = request.getParameter("numeDocu");
            String appaPers = request.getParameter("appaPers");
            String apmaPers = request.getParameter("apmaPers");
            String nombPers = request.getParameter("nombPers");
            String codiNaci = request.getParameter("codiNaci");
            String sexoPers = request.getParameter("sexoPers");
            String codiPaisEmis = request.getParameter("codiPaisEmis");
            String codiCLDN = request.getParameter("codiCLDN");
            String numeCelu = request.getParameter("numeCelu");
            String mailPers = request.getParameter("mailPers");
            String codiTipoVia = request.getParameter("codiTipoVia");
            String nombVia = request.getParameter("nombVia");
            String numeVia = request.getParameter("numeVia");
            String depaPers = request.getParameter("depaPers");
            String codiUbig = request.getParameter("nivel3");
            boolean snpPers = Boolean.parseBoolean(request.getParameter("snpPers"));
            boolean actiPers = Boolean.parseBoolean(request.getParameter("actiPers"));
            String codiEntiBanc = request.getParameter("codiEntiBanc");
            int codiPlant = Integer.parseInt(request.getParameter("codiPlantilla"));

            // Crear instancia de Persona
            PlanillaPersona persona = new PlanillaPersona();
            persona.setCodiPers(0);
            persona.setCodiTipoDoc(codiTipoDoc);
            persona.setNumeDocu(numeDocu);
            persona.setCodiPaisEmis(codiPaisEmis);
            persona.setAppaPers(appaPers);
            persona.setApmaPers(apmaPers);
            persona.setNombPers(nombPers);
            persona.setSexoPers(sexoPers.charAt(0));
            persona.setCodiNaci(codiNaci);
            persona.setCodiCLDN(codiCLDN);
            persona.setNumeCelu(numeCelu);
            persona.setMailPers(mailPers);
            persona.setCodiTipoVia(codiTipoVia);
            persona.setNombVia(nombVia != null ? nombVia : "");
            persona.setNumeVia(numeVia != null ? numeVia : "");
            persona.setDepaPers(depaPers != null ? depaPers : "");
            persona.setCodiUbig(codiUbig);
            persona.setCodiEntiBanc(codiEntiBanc);
            persona.setSnpPers(snpPers);
            persona.setCodiPlant(codiPlant);
            persona.setActiPers(actiPers);

            // Solo establecer codiAFP si snpPers es false
            if (!snpPers) {
                String strCodiAFP = request.getParameter("codiAFP");
                if (strCodiAFP != null && !strCodiAFP.isEmpty()) {
                    persona.setCodiAFP(Integer.parseInt(strCodiAFP));
                }
            }

            // Guardar persona
            personaDAO.create(persona);

            // Respuesta de éxito
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(new JSONObject().put("success", true).toString());

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter()
                    .write(new JSONObject().put("error", "Formato de número inválido: " + e.getMessage()).toString());
        } catch (Exception e) {
            log("Error al procesar la solicitud POST", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter()
                    .write(new JSONObject().put("error", "Error al crear el registro: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            // Obtener la información de la solicitud
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                throw new IllegalArgumentException("El ID de la persona no está presente en la URL.");
            }

            // Obtener el ID de la persona de la URL
            String codiPers = pathInfo.substring(1);
            System.out.println("ID de la persona recibido: " + codiPers);

            // Leer los parámetros del cuerpo de la solicitud
            Map<String, String> parameters = new HashMap<>();
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            String[] pairs = body.split("&");

            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
                    parameters.put(key, value);
                }
            }

            // Log para verificar los parámetros recibidos
            System.out.println("Parámetros recibidos: " + parameters.toString());

            // Verificar si la persona existe en la base de datos
            PlanillaPersona personaExistente = personaDAO.findPlanillaPersona(Integer.parseInt(codiPers));

            if (personaExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error",
                        "Persona no encontrada").toString());
                return;
            }

            JSONObject jsonResponse = new JSONObject();

            // Actualizar los datos de la persona existente
            personaExistente.setCodiTipoDoc(parameters.get("codiTipoDoc"));
            personaExistente.setNumeDocu(parameters.get("numeDocu"));
            personaExistente.setAppaPers(parameters.get("appaPers"));
            personaExistente.setApmaPers(parameters.get("apmaPers"));
            personaExistente.setNombPers(parameters.get("nombPers"));
            personaExistente.setCodiNaci(parameters.get("codiNaci"));
            personaExistente.setSexoPers(parameters.get("sexoPers").charAt(0));
            personaExistente.setCodiPaisEmis(parameters.get("codiPaisEmis"));
            personaExistente.setCodiCLDN(parameters.get("codiCLDN"));
            personaExistente.setNumeCelu(parameters.get("numeCelu"));
            personaExistente.setMailPers(parameters.get("mailPers"));
            personaExistente.setCodiTipoVia(parameters.get("codiTipoVia"));
            personaExistente.setNombVia(parameters.get("nombVia"));
            personaExistente.setNumeVia(parameters.get("numeVia"));
            personaExistente.setDepaPers(parameters.get("depaPers"));
            personaExistente.setCodiUbig(parameters.get("nivel3"));
            personaExistente.setSnpPers(Boolean.parseBoolean(parameters.get("snpPers")));
            personaExistente.setActiPers(Boolean.parseBoolean(parameters.get("actiPers")));
            personaExistente.setCodiEntiBanc(parameters.get("codiEntiBanc"));
            personaExistente.setCodiPlant(Integer.parseInt(parameters.get("codiPlantilla")));

            // Solo actualizar codiAFP si snpPers es false
            if (!personaExistente.getSnpPers()) {
                personaExistente.setCodiAFP(Integer.parseInt(parameters.get("codiAFP")));
                jsonResponse.put("codiAFP", personaExistente.getCodiAFP());
            } else {
                personaExistente.setCodiAFP(null);
            }

            // Intentar actualizar la persona en la base de datos
            personaDAO.edit(personaExistente);

            // Responder con éxito
            response.setStatus(HttpServletResponse.SC_OK);
            jsonResponse.put("success", true);
            jsonResponse.put("codiPers", personaExistente.getCodiPers());
            jsonResponse.put("codiTipoDoc", personaExistente.getCodiTipoDoc());
            jsonResponse.put("numeDocu", personaExistente.getNumeDocu());
            jsonResponse.put("appaPers", personaExistente.getAppaPers());
            jsonResponse.put("apmaPers", personaExistente.getApmaPers());
            jsonResponse.put("nombPers", personaExistente.getNombPers());
            jsonResponse.put("codiNaci", personaExistente.getCodiNaci());
            jsonResponse.put("sexoPers", String.valueOf(personaExistente.getSexoPers()));
            jsonResponse.put("codiPaisEmis", personaExistente.getCodiPaisEmis());
            jsonResponse.put("codiCLDN", personaExistente.getCodiCLDN());
            jsonResponse.put("numeCelu", personaExistente.getNumeCelu());
            jsonResponse.put("mailPers", personaExistente.getMailPers());
            jsonResponse.put("codiTipoVia", personaExistente.getCodiTipoVia());
            jsonResponse.put("nombVia", personaExistente.getNombVia());
            jsonResponse.put("numeVia", personaExistente.getNumeVia());
            jsonResponse.put("depaPers", personaExistente.getDepaPers());
            jsonResponse.put("codiUbig", personaExistente.getCodiUbig());
            jsonResponse.put("snpPers", personaExistente.getSnpPers());
            jsonResponse.put("actiPers", personaExistente.getActiPers());
            jsonResponse.put("codiEntiBanc", personaExistente.getCodiEntiBanc());
            jsonResponse.put("codiPlantilla", personaExistente.getCodiPlant());

            response.getWriter().write(jsonResponse.toString());

        } catch (IllegalArgumentException e) {
            // Captura errores relacionados con parámetros inválidos
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter()
                    .write(new JSONObject().put("error", "Error en los parámetros: "
                            + e.getMessage()).toString());
            System.err.println("Error en los parámetros: " + e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error inesperado
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new JSONObject().put("error", "Error al procesar la solicitud: "
                            + e.getMessage()).toString());
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                // Eliminar registro por ID
                int id = Integer.parseInt(pathInfo.substring(1));
                personaDAO.destroy(id);
                response.getWriter()
                        .write(new JSONObject().put("message", "Registro eliminado exitosamente").toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new JSONObject().put("error", "Debe proporcionar un ID válido").toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new JSONObject().put("error", "Error al eliminar el registro: " + e.getMessage()).toString());
        }
    }
    /*
     * private final PlanillaPersonaDAO personaDAO;
     * private final VistaPlanillaPersonaDetalleDAO vistaPersonaDetalleDAO;
     * private final EntityManagerFactory emf;
     * 
     * public PersonaServlet() {
     * this.emf = Persistence.createEntityManagerFactory(
     * "com.mycompany_Planilla_war_1.0-SNAPSHOTPU");
     * this.personaDAO = new PlanillaPersonaDAO(emf);
     * this.vistaPersonaDetalleDAO = new VistaPlanillaPersonaDetalleDAO(emf);
     * }
     * 
     * @Override
     * protected void doGet(HttpServletRequest request, HttpServletResponse
     * response)
     * throws ServletException, IOException {
     * response.setContentType("application/json");
     * String pathInfo = request.getPathInfo();
     * 
     * emf.getCache().evict(VistaPlanillaPersonaDetalleDAO.class); // Evitar cache
     * si es necesario
     * 
     * try {
     * if (pathInfo == null || pathInfo.equals("/")) {
     * // Obtener todos los registros de personas con todos los campos
     * List<VistaPlanillaPersonaDetalle> personaList =
     * vistaPersonaDetalleDAO.findVistaPlanillaPersonaDetalleEntities();
     * JSONArray jsonArray = new JSONArray();
     * for (VistaPlanillaPersonaDetalle persona : personaList) {
     * JSONObject jsonObject = new JSONObject();
     * jsonObject.put("codiPers", persona.getCodiPers());
     * jsonObject.put("numeDocu", persona.getNumeDocu());
     * jsonObject.put("nombPers", persona.getNombPers());
     * jsonObject.put("snpPers", Boolean.valueOf(persona.getSnpPers()));
     * jsonObject.put("codiAFP", persona.getCodiAFP());
     * jsonObject.put("nombAFP", persona.getNombAFP());
     * jsonObject.put("codiPlant", persona.getCodiPlant());
     * jsonObject.put("nombPlant", persona.getNombPlant());
     * jsonObject.put("actiPers", persona.getActiPers());
     * jsonObject.put("suelPers", persona.getMontRemuPers());
     * jsonObject.put("asigFamiPers", persona.getAsigFamiPers());
     * jsonArray.put(jsonObject);
     * }
     * response.getWriter().write(jsonArray.toString());
     * } else if (pathInfo.equals("/personas/")) {
     * // Obtener solo los campos 'codiPers' y 'nombPers'
     * List<VistaPlanillaPersonaDetalle> personaList =
     * vistaPersonaDetalleDAO.findVistaPlanillaPersonaDetalleEntities();
     * JSONArray jsonArray = new JSONArray();
     * for (VistaPlanillaPersonaDetalle persona : personaList) {
     * JSONObject jsonObject = new JSONObject();
     * jsonObject.put("Codigo", persona.getCodiPers());
     * jsonObject.put("Nombre", persona.getNombPers());
     * 
     * jsonObject.put("Descripcion", ""); // Campo sin datos
     * jsonObject.put("Monto", ""); // Campo sin datos
     * 
     * // Asegurarse de que el JSON esté en el orden correcto
     * JSONObject orderedJsonObject = new JSONObject();
     * orderedJsonObject.put("Codigo", jsonObject.get("Codigo"));
     * orderedJsonObject.put("Nombre", jsonObject.get("Nombre"));
     * orderedJsonObject.put("Descripcion", jsonObject.get("Descripcion"));
     * orderedJsonObject.put("Monto", jsonObject.get("Monto"));
     * 
     * jsonArray.put(jsonObject);
     * }
     * response.getWriter().write(jsonArray.toString());
     * } else {
     * // Obtener un registro de persona por ID
     * int id = Integer.parseInt(pathInfo.substring(1)); // extrae el ID
     * PlanillaPersona persona = personaDAO.findPlanillaPersona(id);
     * if (persona != null) {
     * JSONObject jsonObject = new JSONObject();
     * jsonObject.put("codiPers", persona.getCodiPers());
     * jsonObject.put("numeDocu", persona.getNumeDocu());
     * jsonObject.put("nombPers", persona.getNombPers());
     * jsonObject.put("snpPers", persona.getSnpPers());
     * jsonObject.put("codiAFP", persona.getCodiAFP());
     * jsonObject.put("codiPlant", persona.getCodiPlant());
     * jsonObject.put("actiPers", persona.getActiPers());
     * jsonObject.put("asigFamiPers", persona.getAsigFamiPers());
     * response.getWriter().write(jsonObject.toString());
     * } else {
     * response.setStatus(HttpServletResponse.SC_NOT_FOUND);
     * }
     * }
     * } catch (Exception e) {
     * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
     * response.getWriter().write(new JSONObject().put("error",
     * "Error al obtener los datos: " + e.getMessage()).toString());
     * }
     * }
     * 
     * @Override
     * protected void doPost(HttpServletRequest request, HttpServletResponse
     * response)
     * throws ServletException, IOException {
     * response.setContentType("application/json");
     * 
     * try {
     * // Leer parámetros enviados en el cuerpo de la solicitud
     * String codiPers = request.getParameter("codiPers");
     * String numeDocu = request.getParameter("numeDocu");
     * String nombPers = request.getParameter("nombPers");
     * String suelPers = request.getParameter("suelPers");
     * String codiAFP = request.getParameter("codiAFP");
     * String codiPlant = request.getParameter("codiPlant");
     * String actiPers = request.getParameter("actiPers");
     * String asigFamiPers = request.getParameter("asigFamiPers");
     * String snpPers = request.getParameter("snpPers"); // Nuevo campo agregado
     * 
     * // Validar datos obligatorios
     * if (numeDocu == null || nombPers == null || suelPers == null || codiPlant ==
     * null || actiPers == null) {
     * response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * response.getWriter().write(new JSONObject().put("error",
     * "Datos incompletos").toString());
     * return;
     * }
     * 
     * // Crear instancia de Persona
     * PlanillaPersona persona = new PlanillaPersona();
     * persona.setNumeDocu(numeDocu);
     * persona.setNombPers(nombPers);
     * persona.setCodiAFP(codiAFP != null ? Integer.parseInt(codiAFP) : 0); // Valor
     * opcional
     * persona.setCodiPlant(Integer.parseInt(codiPlant));
     * persona.setActiPers(Boolean.parseBoolean(actiPers));
     * persona.setAsigFamiPers(Integer.parseInt(asigFamiPers));
     * persona.setSnpPers(snpPers != null && Boolean.parseBoolean(snpPers));
     * // Asignar el nuevo campo
     * 
     * // Validar sueldo positivo
     * // Guardar persona
     * personaDAO.create(persona);
     * emf.createEntityManager().flush(); // Sincroniza los cambios en la base de
     * datos
     * 
     * // Respuesta de éxito
     * response.setStatus(HttpServletResponse.SC_CREATED);
     * response.getWriter().write(new JSONObject().put("message",
     * "Registro creado exitosamente").toString());
     * 
     * } catch (NumberFormatException e) {
     * response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * response.getWriter().write(new JSONObject().put("error",
     * "Formato de número inválido: " + e.getMessage()).toString());
     * } catch (Exception e) {
     * log("Error al procesar la solicitud POST", e);
     * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
     * response.getWriter().write(new JSONObject().put("error",
     * "Error al crear el registro: " + e.getMessage()).toString());
     * }
     * }
     * 
     * @Override
     * protected void doPut(HttpServletRequest request, HttpServletResponse
     * response) throws ServletException, IOException {
     * response.setContentType("application/json");
     * 
     * try {
     * // Obtener la información de la solicitud
     * String pathInfo = request.getPathInfo();
     * if (pathInfo == null || pathInfo.length() <= 1) {
     * throw new
     * IllegalArgumentException("El ID de la persona no está presente en la URL.");
     * }
     * 
     * // Obtener el ID de la persona de la URL
     * String codiPers = pathInfo.substring(1);
     * System.out.println("ID de la persona recibido: " + codiPers); // Log para ver
     * el ID recibido
     * 
     * // Leer los parámetros del cuerpo de la solicitud
     * Map<String, String> parameters = new HashMap<>();
     * String body =
     * request.getReader().lines().collect(Collectors.joining(System.lineSeparator()
     * ));
     * String[] pairs = body.split("&");
     * 
     * for (String pair : pairs) {
     * String[] keyValue = pair.split("=");
     * if (keyValue.length == 2) {
     * String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
     * String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
     * parameters.put(key, value);
     * }
     * }
     * 
     * // Log para verificar los parámetros recibidos
     * System.out.println("Parámetros recibidos: " + parameters.toString());
     * 
     * // Verificar si la persona existe en la base de datos
     * PlanillaPersona personaExistente =
     * personaDAO.findPlanillaPersona(Integer.parseInt(codiPers));
     * if (personaExistente == null) {
     * response.setStatus(HttpServletResponse.SC_NOT_FOUND);
     * response.getWriter().write(new JSONObject().put("error",
     * "Persona no encontrada").toString());
     * return;
     * }
     * 
     * // Actualizar los datos de la persona existente
     * personaExistente.setNumeDocu(parameters.get("numeDocu"));
     * personaExistente.setNombPers(parameters.get("nombPers"));
     * personaExistente.setCodiAFP(Integer.parseInt(parameters.get("codiAFP")));
     * personaExistente.setCodiPlant(Integer.parseInt(parameters.get("codiPlant")));
     * personaExistente.setActiPers(Boolean.parseBoolean(parameters.get("actiPers"))
     * );
     * personaExistente.setAsigFamiPers(Integer.parseInt(parameters.get(
     * "asigFamiPers")));
     * personaExistente.setSnpPers(parameters.get("snpPers") != null &&
     * Boolean.parseBoolean(parameters.get("snpPers")));
     * // Nuevo campo agregado
     * 
     * // Log para verificar los datos antes de la actualización
     * System.out.println("Datos a actualizar: " + personaExistente);
     * 
     * // Intentar actualizar la persona en la base de datos
     * personaDAO.edit(personaExistente);
     * 
     * // Responder con éxito
     * response.setStatus(HttpServletResponse.SC_OK);
     * JSONObject jsonResponse = new JSONObject();
     * jsonResponse.put("codiPers", personaExistente.getCodiPers());
     * jsonResponse.put("numeDocu", personaExistente.getNumeDocu());
     * jsonResponse.put("nombPers", personaExistente.getNombPers());
     * jsonResponse.put("codiAFP", personaExistente.getCodiAFP());
     * jsonResponse.put("codiPlant", personaExistente.getCodiPlant());
     * jsonResponse.put("actiPers", personaExistente.getActiPers());
     * jsonResponse.put("asigFamiPers", personaExistente.getAsigFamiPers());
     * jsonResponse.put("snpPers", personaExistente.getSnpPers()); // Nuevo campo
     * agregado en la respuesta
     * 
     * response.getWriter().write(jsonResponse.toString());
     * 
     * } catch (IllegalArgumentException e) {
     * // Captura errores relacionados con parámetros inválidos
     * response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * response.getWriter().write(new JSONObject().put("error",
     * "Error en los parámetros: " + e.getMessage()).toString());
     * System.err.println("Error en los parámetros: " + e.getMessage());
     * } catch (Exception e) {
     * // Captura cualquier otro error inesperado
     * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
     * response.getWriter().write(new JSONObject().put("error",
     * "Error al procesar la solicitud: " + e.getMessage()).toString());
     * System.err.println("Error inesperado: " + e.getMessage());
     * e.printStackTrace(); // Imprime el stack trace completo para depuración
     * }
     * }
     * 
     * @Override
     * protected void doDelete(HttpServletRequest request, HttpServletResponse
     * response)
     * throws ServletException, IOException {
     * response.setContentType("application/json");
     * 
     * String pathInfo = request.getPathInfo();
     * try {
     * if (pathInfo != null && pathInfo.length() > 1) {
     * // Eliminar registro por ID
     * int id = Integer.parseInt(pathInfo.substring(1));
     * personaDAO.destroy(id);
     * response.getWriter().write(new JSONObject().put("message",
     * "Registro eliminado exitosamente").toString());
     * } else {
     * response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * response.getWriter().write(new JSONObject().put("error",
     * "Debe proporcionar un ID válido").toString());
     * }
     * } catch (Exception e) {
     * response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
     * response.getWriter().write(new JSONObject().put("error",
     * "Error al eliminar el registro: " + e.getMessage()).toString());
     * }
     * }
     * 
     */
}
