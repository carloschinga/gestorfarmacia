package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.ventas.dto.VentasTipoComprobante;
import pe.gestor.ventas.dao.VentasTipoComprobanteJpaController;

@WebServlet(name = "VentasTipoComprobanteServlet", urlPatterns = { "/ventastipocomprobanteservlet/*" })
public class VentasTipoComprobanteServlet extends HttpServlet {

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CHARACTER_ENCONDING = "UTF-8";
    private final EntityManagerFactory emf;
    private final VentasTipoComprobanteJpaController tipoComprobanteDAO;

    public VentasTipoComprobanteServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tipoComprobanteDAO = new VentasTipoComprobanteJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(CHARACTER_ENCONDING);

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todos los tipos de comprobante para el combo
                List<VentasTipoComprobante> tipoComprobanteList = tipoComprobanteDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (pe.gestor.ventas.dto.VentasTipoComprobante tipoComprobante : tipoComprobanteList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id_tipo_comprobante", tipoComprobante.getCodiTipoComp());
                    jsonObject.put("descripcion", tipoComprobante.getDescTipoComp());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un registro específico por id_tipo_comprobante
                String param = pathInfo.substring(1).trim();
                int idTipoComprobante = Integer.parseInt(param);

                VentasTipoComprobante tipoComprobante = tipoComprobanteDAO.findVentasTipoComprobante(idTipoComprobante);

                if (tipoComprobante == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id_tipo_comprobante", tipoComprobante.getCodiTipoComp());
                jsonObject.put("descripcion", tipoComprobante.getDescTipoComp());
                jsonObject.put("actiTipoComp", tipoComprobante.getActiTipoComp());

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
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(CHARACTER_ENCONDING);

        try {
            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Validar y obtener los campos del JSON
            String descripcion = json.getString("descripcion");
            String actiTipoComp = json.getString("actiTipoComp");

            // Crear instancia de VentasTipoComprobante
            VentasTipoComprobante tipoComprobante = new VentasTipoComprobante();
            tipoComprobante.setDescTipoComp(descripcion);
            tipoComprobante.setActiTipoComp(true);

            // Guardar tipo de comprobante
            tipoComprobanteDAO.create(tipoComprobante);

            // Respuesta de éxito
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(new JSONObject().put("success", true).toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter()
                    .write(new JSONObject().put("error", "Error al crear el registro: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(CHARACTER_ENCONDING);

        try {
            // Obtener la información de la solicitud
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                throw new IllegalArgumentException("El ID del tipo de comprobante no está presente en la URL.");
            }

            // Obtener el ID del tipo de comprobante de la URL
            String idTipoComprobante = pathInfo.substring(1);

            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Verificar si el tipo de comprobante existe en la base de datos
            VentasTipoComprobante tipoComprobanteExistente = tipoComprobanteDAO
                    .findVentasTipoComprobante(Integer.parseInt(idTipoComprobante));

            if (tipoComprobanteExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter()
                        .write(new JSONObject().put("error", "Tipo de comprobante no encontrado").toString());
                return;
            }

            // Actualizar los datos del tipo de comprobante existente
            tipoComprobanteExistente.setDescTipoComp(json.getString("descripcion"));
            tipoComprobanteExistente.setActiTipoComp(json.getBoolean("active"));

            // Intentar actualizar el tipo de comprobante en la base de datos
            tipoComprobanteDAO.edit(tipoComprobanteExistente);

            // Responder con éxito
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);

            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new JSONObject().put("error", "Error al procesar la solicitud: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        response.setCharacterEncoding(CHARACTER_ENCONDING);

        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                // Eliminar registro por ID (eliminación lógica)
                int id = Integer.parseInt(pathInfo.substring(1));
                VentasTipoComprobante tipoComprobante = tipoComprobanteDAO.findVentasTipoComprobante(id);
                if (tipoComprobante != null) {
                    tipoComprobante.setActiTipoComp(false);
                    tipoComprobanteDAO.edit(tipoComprobante);
                }
                response.getWriter()
                        .write(new JSONObject().put("message", "Registro desactivado exitosamente").toString());
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