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
import pe.gestor.ventas.dao.VentasUbicacionLoteJpaController;
import pe.gestor.ventas.dto.VentasUbicacionLote;

@WebServlet(name = "VentasUbicacionLoteServlet", urlPatterns = { "/ventasubicacionloteservlet/*" })
public class VentasUbicacionLoteServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasUbicacionLoteJpaController ubicacionLoteDAO;

    public VentasUbicacionLoteServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.ubicacionLoteDAO = new VentasUbicacionLoteJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todas las ubicaciones de lote
                List<VentasUbicacionLote> ubicacionesLote = ubicacionLoteDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasUbicacionLote ubicacionLote : ubicacionesLote) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiUbicLote", ubicacionLote.getCodiUbicLote());
                    jsonObject.put("codiStocLote", ubicacionLote.getCodiStocLote());
                    jsonObject.put("cantEnte", ubicacionLote.getCantEnte());
                    jsonObject.put("cantFrac", ubicacionLote.getCantFrac());
                    jsonObject.put("actiUbicLote", ubicacionLote.getActiUbicLote());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener una ubicación de lote específica por codiUbicLote
                String param = pathInfo.substring(1).trim();
                int codiUbicLote = Integer.parseInt(param);

                VentasUbicacionLote ubicacionLote = ubicacionLoteDAO.findVentasUbicacionLote(codiUbicLote);

                if (ubicacionLote == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiUbicLote", ubicacionLote.getCodiUbicLote());
                jsonObject.put("codiStocLote", ubicacionLote.getCodiStocLote());
                jsonObject.put("cantEnte", ubicacionLote.getCantEnte());
                jsonObject.put("cantFrac", ubicacionLote.getCantFrac());
                jsonObject.put("actiUbicLote", ubicacionLote.getActiUbicLote());

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
            int codiStocLote = json.getInt("codiStocLote");
            int cantEnte = json.getInt("cantEnte");
            int cantFrac = json.getInt("cantFrac");
            // boolean actiUbicLote = json.getBoolean("actiUbicLote");

            // Crear instancia de VentasUbicacionLote
            VentasUbicacionLote ubicacionLote = new VentasUbicacionLote();
            ubicacionLote.setCodiStocLote(codiStocLote);
            ubicacionLote.setCantEnte(cantEnte);
            ubicacionLote.setCantFrac(cantFrac);
            ubicacionLote.setActiUbicLote(true);

            // Guardar ubicación de lote
            ubicacionLoteDAO.create(ubicacionLote);

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

            // Obtener el ID de la ubicación de lote del JSON
            int codiUbicLote = json.getInt("codiUbicLote");

            // Verificar si la ubicación de lote existe en la base de datos
            VentasUbicacionLote ubicacionLoteExistente = ubicacionLoteDAO.findVentasUbicacionLote(codiUbicLote);

            if (ubicacionLoteExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error", "Ubicación de lote no encontrada").toString());
                return;
            }

            // Actualizar los datos de la ubicación de lote existente
            ubicacionLoteExistente.setCodiStocLote(json.getInt("codiStocLote"));
            ubicacionLoteExistente.setCantEnte(json.getInt("cantEnte"));
            ubicacionLoteExistente.setCantFrac(json.getInt("cantFrac"));

            // Intentar actualizar la ubicación de lote en la base de datos
            ubicacionLoteDAO.edit(ubicacionLoteExistente);

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
                // Obtener el ID de la ubicación de lote de la URL
                int id = Integer.parseInt(pathInfo.substring(1));

                // Realizar un borrado lógico (desactivar la ubicación de lote)
                VentasUbicacionLote ubicacionLote = ubicacionLoteDAO.findVentasUbicacionLote(id);
                if (ubicacionLote != null) {
                    ubicacionLote.setActiUbicLote(false); // Desactivar
                    ubicacionLoteDAO.edit(ubicacionLote);
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