package pe.gestor.ventas.servlet;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Persistence;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.ventas.dao.VentasSedeDAO;
import pe.gestor.ventas.dto.VentasSede;

@WebServlet(name = "VentasSedeServlet", urlPatterns = {"/sedeservlet/*"})
public class SedeServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        try (PrintWriter out = response.getWriter()) {
            try {
                VentasSedeDAO SedeDAO = new VentasSedeDAO(emf);
                if (pathInfo == null || pathInfo.equals("/")) {
                    List<VentasSede> sedes = SedeDAO.findVentasSedeEntities();
                    JSONArray jsonArray = new JSONArray();
                    for (VentasSede sede : sedes) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("codiSede", sede.getCodiSede());
                        jsonObject.put("nombSede", sede.getNombSede());
                        jsonArray.put(jsonObject);
                    }
                    out.print(jsonArray.toString());
                } else {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    VentasSede sede = SedeDAO.findVentasSede(id);
                    if (sede != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("codiSede", sede.getCodiSede());
                        jsonObject.put("nombSede", sede.getNombSede());
                        out.print(jsonObject.toString());
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(new JSONObject().put("error", "Sede no encontrada").toString());
                    }
                }
            } catch (Exception ex) {

            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al obtener los datos").toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            // Leer el cuerpo de la solicitud y procesarlo como parámetros
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

            // Obtener parámetros
            int codiSede = Integer.parseInt(parameters.get("codiSede"));
            String nombSede = parameters.get("nombSede");

            // Buscar sede en la base de datos
            VentasSedeDAO sedeDAO = new VentasSedeDAO(emf);
            VentasSede sede = sedeDAO.findVentasSede(codiSede);
            if (sede == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error", "Sede no encontrada").toString());
                return;
            }

            // Actualizar valores
            sede.setNombSede(nombSede);
            sedeDAO.edit(sede);

            response.getWriter().write(new JSONObject().put("message", "Sede actualizada exitosamente").toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al actualizar la sede: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        try {
            VentasSedeDAO SedeDAO = new VentasSedeDAO(emf);
            if (pathInfo != null && pathInfo.length() > 1) {
                int id = Integer.parseInt(pathInfo.substring(1));
                SedeDAO.destroy(id);
                response.getWriter().write(new JSONObject().put("message", "Sede eliminada exitosamente").toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new JSONObject().put("error", "Debe proporcionar un ID válido").toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al eliminar la sede").toString());
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        super.destroy();
    }

}
