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
import pe.gestor.ventas.dao.VentasTipoMovimientoJpaController;
import pe.gestor.ventas.dto.VentasTipoMovimiento;

@WebServlet(name = "VentasTipoMovimientoServlet", urlPatterns = { "/ventastipomovimientoservlet/*" })
public class VentasTipoMovimientoServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasTipoMovimientoJpaController tipoMovimientoDAO;

    public VentasTipoMovimientoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tipoMovimientoDAO = new VentasTipoMovimientoJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todos los tipos de movimiento
                List<VentasTipoMovimiento> tiposMovimiento = tipoMovimientoDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasTipoMovimiento tipoMovimiento : tiposMovimiento) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiTipoMovi", tipoMovimiento.getCodiTipoMovi());
                    jsonObject.put("nombTipoMovi", tipoMovimiento.getNombTipoMovi());
                    // 1 -> suma al kardex, 0 -> resta al kardex
                    jsonObject.put("signTipoMovi", tipoMovimiento.getSignTipoMovi());
                    jsonObject.put("actiTipoMovi", tipoMovimiento.getActiTipoMovi());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un tipo de movimiento específico por codiTipoMovi
                String param = pathInfo.substring(1).trim();
                int codiTipoMovi = Integer.parseInt(param);

                VentasTipoMovimiento tipoMovimiento = tipoMovimientoDAO.findVentasTipoMovimiento(codiTipoMovi);

                if (tipoMovimiento == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiTipoMovi", tipoMovimiento.getCodiTipoMovi());
                jsonObject.put("nombTipoMovi", tipoMovimiento.getNombTipoMovi());
                jsonObject.put("signTipoMovi", tipoMovimiento.getSignTipoMovi());
                jsonObject.put("actiTipoMovi", tipoMovimiento.getActiTipoMovi());

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
            String nombTipoMovi = json.getString("nombTipoMovi");
            boolean signTipoMovi = json.getBoolean("signTipoMovi");

            // Crear instancia de VentasTipoMovimiento
            VentasTipoMovimiento tipoMovimiento = new VentasTipoMovimiento();
            tipoMovimiento.setNombTipoMovi(nombTipoMovi);
            tipoMovimiento.setSignTipoMovi(signTipoMovi);
            tipoMovimiento.setActiTipoMovi(true);

            // Guardar tipo de movimiento
            tipoMovimientoDAO.create(tipoMovimiento);

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
        response.setContentType("application/json");

        try {
            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Verificar si el tipo de movimiento existe en la base de datos
            VentasTipoMovimiento tipoMovimientoExistente = tipoMovimientoDAO.findVentasTipoMovimiento(json.getInt("codiTipoMovi"));

            if (tipoMovimientoExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter()
                        .write(new JSONObject().put("error", "Tipo de movimiento no encontrado").toString());
                return;
            }

            // Actualizar los datos del tipo de movimiento existente
            tipoMovimientoExistente.setNombTipoMovi(json.getString("nombTipoMovi"));
            tipoMovimientoExistente.setSignTipoMovi(json.getBoolean("signTipoMovi"));

            // Intentar actualizar el tipo de movimiento en la base de datos
            tipoMovimientoDAO.edit(tipoMovimientoExistente);

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
        response.setContentType("application/json");

        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                // Eliminar registro por ID
                int id = Integer.parseInt(pathInfo.substring(1));
                VentasTipoMovimiento tipoMovimiento = tipoMovimientoDAO.findVentasTipoMovimiento(id);
                if (tipoMovimiento != null) {
                    tipoMovimiento.setActiTipoMovi(false);
                    tipoMovimientoDAO.edit(tipoMovimiento);
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