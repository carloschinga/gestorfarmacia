package pe.gestor.asistencia.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import pe.gestor.asistencia.dao.AsistenciaParametrosJpaController;
import pe.gestor.asistencia.dto.AsistenciaParametros;

@WebServlet(name = "AsistenciaParametrosServlet", urlPatterns = { "/asistenciaparametros" })
public class AsistenciaParametrosServlet extends HttpServlet {
    private final AsistenciaParametrosJpaController paramDAO;
    private final EntityManagerFactory emf;

    public AsistenciaParametrosServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.paramDAO = new AsistenciaParametrosJpaController(emf);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject json = new JSONObject();
        String opcion = request.getParameter("opcion");
        if (opcion == null || opcion.trim().isEmpty()) {
            json.put("status", "error");
            json.put("message", "El campo 'minutos' es requerido");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(json.toString());
            return;
        }
        int opc;
        try {
            opc = Integer.parseInt(opcion);
            if (opc < 0) {
                throw new NumberFormatException("Los minutos no pueden ser negativos");
            }
        } catch (Exception e) {
            json.put("status", "error");
            json.put("message", "Opcion no valida, ingrese un numero positivo");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(json.toString());
            return;
        }
        switch (opc) {
            case 1:
                // Traer el value del codigo uno
                AsistenciaParametros asisParam = paramDAO.findAsistenciaParametros(1);
                json.put("minutos", asisParam.getValuTareoPara());
                break;
            default:
                json.put("message", "Opción no reconocida");
                break;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(json.toString());
    };

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();

        try {
            StringBuilder requestBody = new StringBuilder();
            String line;
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            JSONObject jsonRequest = new JSONObject(requestBody.toString());

            String minutes = jsonRequest.optString("minutos");

            if (minutes == null || minutes.trim().isEmpty()) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "El campo 'minutos' es requerido");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(jsonResponse.toString());
                return;
            }

            int minutos;
            try {
                minutos = Integer.parseInt(minutes);
                if (minutos < 0) {
                    throw new NumberFormatException("Los minutos no pueden ser negativos");
                }
            } catch (NumberFormatException e) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Minutos no válidos, ingrese un valor numérico positivo");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(jsonResponse.toString());
                return;
            }

            final int PARAMETRO_ID = 1;
            AsistenciaParametros asisParam = paramDAO.findAsistenciaParametros(PARAMETRO_ID);

            if (asisParam == null) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "No se encontraron los parámetros de asistencia");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write(jsonResponse.toString());
                return;
            }

            asisParam.setValuTareoPara(String.valueOf(minutos));
            paramDAO.edit(asisParam);

            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Minutos de marcación actualizados correctamente");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Error interno al procesar la solicitud: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        resp.getWriter().write(jsonResponse.toString());
    }
}
