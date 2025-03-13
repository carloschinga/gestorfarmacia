/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.planilla.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.json.JSONException;
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
                        jsonObject.put("actiPers", persona.getActiPers());
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
                jsonObject.put("sexo", String.valueOf(vpm.getSexoPers()));
                jsonObject.put("nacionalidad", vpm.getCodiNaci());
                jsonObject.put("codiCLDN", vpm.getCodiCLDN());
                jsonObject.put("numeCelu", vpm.getNumeCelu());
                jsonObject.put("mailPers", vpm.getMailPers());
                jsonObject.put("codiTipoVia", vpm.getCodiTipoVia());
                jsonObject.put("nombVia", vpm.getNombVia());
                jsonObject.put("nivel1", ubiNive1.getCodiUbig());
                jsonObject.put("nivel2", ubiNive2.getCodiUbig());
                jsonObject.put("nivel3", vpm.getCodiUbig());
                jsonObject.put("cuspp", vpm.getCupsPers());
                jsonObject.put("bancoSueldo", vpm.getCodiEntiBanc());
                jsonObject.put("cuentaSueldo", vpm.getNumeCuen());
                jsonObject.put("estadoCivil", vpm.getCodiEstaCivil());
                jsonObject.put("codiArea", vpm.getCodiArea());
                jsonObject.put("codiCargo", vpm.getCodiCargo());
                jsonObject.put("categoriaOcupacion", vpm.getCodiCateOcup());
                jsonObject.put("remuneracion", vpm.getMontRemuPers());
                jsonObject.put("periodicidadIngreso", vpm.getCodiPeriRemu());
                jsonObject.put("regimenLaboral", vpm.getCodiRegiLab());
                jsonObject.put("regimenPensionario", vpm.getCodiRegiPens());
                jsonObject.put("regimenSalud", vpm.getCodiRegiSal());
                jsonObject.put("nivelEducativo", vpm.getCodiSituEdu());
                jsonObject.put("situacionEspecial", vpm.getCodiSituEspe());
                jsonObject.put("tipoSituacion", vpm.getCodiTipoSitu());
                jsonObject.put("codiTipoContrato", vpm.getCodiTipoContrato());
                jsonObject.put("ocupacion", vpm.getCodiTipoOcup());
                jsonObject.put("tipoPago", vpm.getCodiTipoPago());
                jsonObject.put("tipoTrabajador", vpm.getCodiTipoTrab());
                jsonObject.put("jornadaLaboral", vpm.getCodiJornadLab());
                jsonObject.put("sucursal", vpm.getCodiSucurs());
                jsonObject.put("fechNaci", vpm.getFechNaci());
                jsonObject.put("fechaIngreso", vpm.getFechIngr());
                jsonObject.put("cuentaCts", vpm.getCuentaCTS());
                jsonObject.put("bancoCts", vpm.getCodiCuenCTS());
                jsonObject.put("fechaCese", vpm.getFechCese());
                jsonObject.put("cuspp", vpm.getCupsPers());
                jsonObject.put("discapacidad", String.valueOf(vpm.getDiscapacidad()));
                jsonObject.put("sindicalizado", String.valueOf(vpm.getSindicalizado()));
                jsonObject.put("statcExonerado", String.valueOf(vpm.getStatcExonerado()));
                jsonObject.put("convenio", String.valueOf(vpm.getConvenio()));
                jsonObject.put("aportaSctr", String.valueOf(vpm.getAportaSctr()));
                jsonObject.put("polizaSeguro", String.valueOf(vpm.getPolizaSeguro()));
                jsonObject.put("asignacionFamiliar", String.valueOf(vpm.getAsignacionFamiliar()));

                // No se esta usando
                jsonObject.put("actiPers", String.valueOf(vpm.getActiPers()));
                jsonObject.put("codiPaisEmis", vpm.getCodiPaisEmis());
                jsonObject.put("depaPers", vpm.getDepaPers());
                jsonObject.put("numeVia", vpm.getNumeVia());
                jsonObject.put("snpPers", vpm.getSnpPers());
                jsonObject.put("codiAFP", vpm.getCodiAFP());
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
        response.setCharacterEncoding("utf-8");

        try {
            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Validar y obtener los campos del JSON
            String codiTipoDoc = json.getString("codiTipoDoc");
            String numeDocu = json.getString("numeDocu");
            String appaPers = json.getString("appaPers");
            String apmaPers = json.getString("apmaPers");
            String nombPers = json.getString("nombPers");
            String fechNaci = json.getString("fechNaci");
            String nombVia = json.getString("nombVia");
            String numeCelu = json.getString("numeCelu");
            String mailPers = json.getString("mailPers");
            String nivel3 = json.getString("nivel3");
            String codiArea = json.getString("codiArea");
            String codiCargo = json.getString("codiCargo");
            String codiTipoContrato = json.getString("codiTipoContrato");
            String tipoSituacion = json.getString("tipoSituacion");
            String fechaIngreso = json.getString("fechaIngreso");
            String remuneracion = json.getString("remuneracion");
            String tipoPago = json.getString("tipoPago");

            String regimenLaboral = json.getString("regimenLaboral");
            String regimenSalud = json.getString("regimenSalud");
            String regimenPensionario = json.getString("regimenPensionario");
            String nacionalidad = json.getString("nacionalidad");
            String estadoCivil = json.getString("estadoCivil");
            String sexo = json.getString("sexo");
            String nivelEducativo = json.getString("nivelEducativo");
            String ocupacion = json.getString("ocupacion");
            String tipoTrabajador = json.getString("tipoTrabajador");
            String categoriaOcupacion = json.getString("categoriaOcupacion");
            String periodicidadIngreso = json.getString("periodicidadIngreso");
            String bancoCts = json.getString("bancoCts");
            String cuentaCts = json.getString("cuentaCts");
            String cupps = json.getString("cuspp");
            String jornadaLaboral = json.getString("jornadaLaboral");
            String situacionEspecial = json.getString("situacionEspecial");
            boolean discapacidad = json.getBoolean("discapacidad");
            boolean sindicalizado = json.getBoolean("sindicalizado");
            boolean statcExonerado = json.getBoolean("statcExonerado");
            boolean convenio = json.getBoolean("convenio");
            boolean aportaSctr = json.getBoolean("aportaSctr");
            boolean polizaSeguro = json.getBoolean("polizaSeguro");
            boolean asignacionFamiliar = json.getBoolean("asignacionFamiliar");
            String sucursal = json.getString("sucursal");
            boolean actiPers = json.getBoolean("actiPers");

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-DD");
            PlanillaPersona persona = new PlanillaPersona();

            if (tipoPago.equals("2")) {
                String bancoSueldo = json.getString("bancoSueldo");
                String cuentaSueldo = json.getString("cuentaSueldo");
                persona.setCodiEntiBanc(bancoSueldo);
                persona.setNumeCuen(cuentaSueldo);
            }

            if (bancoCts != null || cuentaCts != null) {
                persona.setCodiCuenCTS(bancoCts);
                persona.setCuentaCTS(cuentaCts);
            }

            double remu = Double.parseDouble(remuneracion);

            // Crear instancia de Persona
            persona.setCodiPers(0);
            persona.setCodiTipoDoc(codiTipoDoc);
            persona.setNumeDocu(numeDocu);
            persona.setAppaPers(appaPers);
            persona.setApmaPers(apmaPers);
            persona.setNombPers(nombPers);
            persona.setFechNaci(sdf.parse(fechNaci));
            persona.setNombVia(nombVia);
            persona.setNumeCelu(numeCelu);
            persona.setMailPers(mailPers);
            persona.setCodiUbig(nivel3);
            persona.setCodiArea(Integer.parseInt(codiArea));
            persona.setCodiCargo(Integer.parseInt(codiCargo));
            persona.setCodiTipoContrato(codiTipoContrato);
            persona.setCodiTipoSitu(tipoSituacion.charAt(0));
            persona.setFechIngr(sdf.parse(fechaIngreso));
            persona.setMontRemuPers(BigDecimal.valueOf(remu));
            persona.setCodiTipoPago(tipoPago.charAt(0));
            persona.setCodiRegiLab(regimenLaboral);
            persona.setCodiRegiSal(regimenSalud);
            persona.setCodiRegiPens(regimenPensionario);
            persona.setCodiNaci(nacionalidad);
            persona.setCodiEstaCivil(Integer.parseInt(estadoCivil));
            persona.setSexoPers(sexo.charAt(0));
            persona.setCodiSituEdu(nivelEducativo);
            persona.setCodiTipoOcup(ocupacion.charAt(0));
            persona.setCodiTipoTrab(tipoTrabajador);
            persona.setCodiCateOcup(categoriaOcupacion);
            persona.setCodiPeriRemu(periodicidadIngreso.charAt(0));
            persona.setCodiJornadLab(Integer.parseInt(jornadaLaboral));
            persona.setCodiSituEspe(situacionEspecial.charAt(0));
            persona.setDiscapacidad(discapacidad);
            persona.setSindicalizado(sindicalizado);
            persona.setStatcExonerado(statcExonerado);
            persona.setConvenio(convenio);
            persona.setAportaSctr(aportaSctr);
            persona.setPolizaSeguro(polizaSeguro);
            persona.setAsignacionFamiliar(asignacionFamiliar);
            persona.setCodiSucurs(Integer.parseInt(sucursal));
            persona.setCupsPers(cupps);
            persona.setActiPers(actiPers);

            // Guardar persona
            personaDAO.create(persona);

            // Respuesta de éxito
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(new JSONObject().put("success", true).toString());

        } catch (JSONException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter()
                    .write(new JSONObject().put("error", "Formato JSON inválido: " + e.getMessage()).toString());
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

            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Verificar si la persona existe en la base de datos
            PlanillaPersona personaExistente = personaDAO.findPlanillaPersona(Integer.parseInt(codiPers));

            if (personaExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error",
                        "Persona no encontrada").toString());
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-DD");

            // Actualizar los datos de la persona existente
            personaExistente.setCodiTipoDoc(json.getString("codiTipoDoc"));
            personaExistente.setNumeDocu(json.getString("numeDocu"));
            personaExistente.setAppaPers(json.getString("appaPers"));
            personaExistente.setApmaPers(json.getString("apmaPers"));
            personaExistente.setNombPers(json.getString("nombPers"));
            personaExistente.setFechNaci(sdf.parse(json.getString("fechNaci")));
            personaExistente.setNombVia(json.getString("nombVia"));
            personaExistente.setNumeCelu(json.getString("numeCelu"));
            personaExistente.setMailPers(json.getString("mailPers"));
            personaExistente.setCodiUbig(json.getString("nivel3"));
            personaExistente.setCodiArea(Integer.parseInt(json.getString("codiArea")));
            personaExistente.setCodiCargo(Integer.parseInt(json.getString("codiCargo")));
            personaExistente.setCodiTipoContrato(json.getString("codiTipoContrato"));
            personaExistente.setCodiTipoSitu(json.getString("tipoSituacion").charAt(0));
            personaExistente.setFechIngr(sdf.parse(json.getString("fechaIngreso")));
            personaExistente.setMontRemuPers(BigDecimal.valueOf(Double.parseDouble(json.getString("remuneracion"))));
            personaExistente.setCodiTipoPago(json.getString("tipoPago").charAt(0));
            if (personaExistente.getCodiTipoPago().equals('2')) {
                personaExistente.setCodiEntiBanc(json.getString("bancoSueldo"));
                personaExistente.setNumeCuen(json.getString("cuentaSueldo"));
            }
            personaExistente.setCodiRegiLab(json.getString("regimenLaboral"));
            personaExistente.setCodiRegiSal(json.getString("regimenSalud"));
            personaExistente.setCodiRegiPens(json.getString("regimenPensionario"));
            personaExistente.setCodiNaci(json.getString("nacionalidad"));
            personaExistente.setCodiEstaCivil(Integer.parseInt(json.getString("estadoCivil")));
            personaExistente.setSexoPers(json.getString("sexo").charAt(0));
            personaExistente.setCodiSituEdu(json.getString("nivelEducativo"));
            personaExistente.setCodiTipoOcup(json.getString("ocupacion").charAt(0));
            personaExistente.setCodiTipoTrab(json.getString("tipoTrabajador"));
            personaExistente.setCodiCateOcup(json.getString("categoriaOcupacion"));
            personaExistente.setCodiPeriRemu(json.getString("periodicidadIngreso").charAt(0));

            if (json.has("cuentaCts") || json.has("bancoCts")) {
                personaExistente.setCodiCuenCTS(json.getString("bancoCts"));
                personaExistente.setCuentaCTS(json.getString("cuentaCts"));
            }

            String fechaCese = json.optString("fechaCese", null); // Devuelve null si el campo no existe
            if (fechaCese != null && !fechaCese.isEmpty()) {
                try {
                    personaExistente.setFechCese(sdf.parse(fechaCese));
                } catch (ParseException e) {
                    // Manejar el error de parseo de fecha
                    System.err.println("Error al parsear la fecha de cese: " + e.getMessage());
                }
            }

            personaExistente.setCodiJornadLab(Integer.parseInt(json.getString("jornadaLaboral")));
            personaExistente.setCodiSituEspe(json.getString("situacionEspecial").charAt(0));
            personaExistente.setDiscapacidad(json.getBoolean("discapacidad"));
            personaExistente.setSindicalizado(json.getBoolean("sindicalizado"));
            personaExistente.setStatcExonerado(json.getBoolean("statcExonerado"));
            personaExistente.setConvenio(json.getBoolean("convenio"));
            personaExistente.setAportaSctr(json.getBoolean("aportaSctr"));
            personaExistente.setPolizaSeguro(json.getBoolean("polizaSeguro"));
            personaExistente.setAsignacionFamiliar(json.getBoolean("asignacionFamiliar"));
            personaExistente.setCodiSucurs(Integer.parseInt(json.getString("sucursal")));
            personaExistente.setActiPers(json.getBoolean("actiPers"));

            // Solo actualizar codiAFP si snpPers es false
            if (!personaExistente.getSnpPers()) {
                personaExistente.setCodiAFP(json.optInt("codiAFP"));
            } else {
                personaExistente.setCodiAFP(null);
            }

            // Intentar actualizar la persona en la base de datos
            personaDAO.edit(personaExistente);

            // Responder con éxito
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);

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

}
