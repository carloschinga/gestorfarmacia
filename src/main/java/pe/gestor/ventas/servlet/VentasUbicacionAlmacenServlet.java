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
import pe.gestor.ventas.dao.VentasUbicacionAlmacenJpaController;
import pe.gestor.ventas.dto.VentasUbicacionAlmacen;

@WebServlet(name = "VentasUbicacionAlmacenServlet", urlPatterns = { "/ventasubicacionalmacenservlet/*" })
public class VentasUbicacionAlmacenServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasUbicacionAlmacenJpaController ubicacionAlmacenDAO;

    public VentasUbicacionAlmacenServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.ubicacionAlmacenDAO = new VentasUbicacionAlmacenJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todas las ubicaciones de almacén
                List<VentasUbicacionAlmacen> ubicacionesAlmacen = ubicacionAlmacenDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasUbicacionAlmacen ubicacionAlmacen : ubicacionesAlmacen) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiUbicAlmc", ubicacionAlmacen.getCodiUbicAlmc());
                    jsonObject.put("codiAlmc", ubicacionAlmacen.getCodiAlmc());
                    jsonObject.put("descUbicAlmc", ubicacionAlmacen.getDescUbicAlmc());
                    jsonObject.put("actiUbicAlmc", ubicacionAlmacen.getActiUbicAlmc());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener una ubicación de almacén específica por codiUbicAlmc
                String param = pathInfo.substring(1).trim();
                int codiUbicAlmc = Integer.parseInt(param);

                VentasUbicacionAlmacen ubicacionAlmacen = ubicacionAlmacenDAO.findVentasUbicacionAlmacen(codiUbicAlmc);

                if (ubicacionAlmacen == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiUbicAlmc", ubicacionAlmacen.getCodiUbicAlmc());
                jsonObject.put("codiAlmc", ubicacionAlmacen.getCodiAlmc());
                jsonObject.put("descUbicAlmc", ubicacionAlmacen.getDescUbicAlmc());
                jsonObject.put("actiUbicAlmc", ubicacionAlmacen.getActiUbicAlmc());

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
            int codiAlmc = json.getInt("codiAlmc");
            String descUbicAlmc = json.getString("descUbicAlmc");
            // boolean actiUbicAlmc = json.getBoolean("actiUbicAlmc");

            // Crear instancia de VentasUbicacionAlmacen
            VentasUbicacionAlmacen ubicacionAlmacen = new VentasUbicacionAlmacen();
            ubicacionAlmacen.setCodiAlmc(codiAlmc);
            ubicacionAlmacen.setDescUbicAlmc(descUbicAlmc);
            ubicacionAlmacen.setActiUbicAlmc(true);

            // Guardar ubicación de almacén
            ubicacionAlmacenDAO.create(ubicacionAlmacen);

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

            // Obtener el ID de la ubicación de almacén del JSON
            int codiUbicAlmc = json.getInt("codiUbicAlmc");

            // Verificar si la ubicación de almacén existe en la base de datos
            VentasUbicacionAlmacen ubicacionAlmacenExistente = ubicacionAlmacenDAO
                    .findVentasUbicacionAlmacen(codiUbicAlmc);

            if (ubicacionAlmacenExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter()
                        .write(new JSONObject().put("error", "Ubicación de almacén no encontrada").toString());
                return;
            }

            // Actualizar los datos de la ubicación de almacén existente
            ubicacionAlmacenExistente.setCodiAlmc(json.getInt("codiAlmc"));
            ubicacionAlmacenExistente.setDescUbicAlmc(json.getString("descUbicAlmc"));

            // Intentar actualizar la ubicación de almacén en la base de datos
            ubicacionAlmacenDAO.edit(ubicacionAlmacenExistente);

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
                // Obtener el ID de la ubicación de almacén de la URL
                int id = Integer.parseInt(pathInfo.substring(1));

                // Realizar un borrado lógico (desactivar la ubicación de almacén)
                VentasUbicacionAlmacen ubicacionAlmacen = ubicacionAlmacenDAO.findVentasUbicacionAlmacen(id);
                if (ubicacionAlmacen != null) {
                    ubicacionAlmacen.setActiUbicAlmc(false); // Desactivar
                    ubicacionAlmacenDAO.edit(ubicacionAlmacen);
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
                    new JSONObject().put("error", "Error al desactivar el registro: " + e.getMessage()).toString());
        }
    }
}