/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.asistencia.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.asistencia.dao.VistaAsistenciaPersonaMarcacionDAO;
import pe.gestor.asistencia.dto.VistaAsistenciaPersonaMarcacion;

/**
 *
 * @author USER
 */
@WebServlet(name = "MarcacionPersonalServlet", urlPatterns = {"/marcacionpersonalservlet"})
public class MarcacionPersonalServlet extends HttpServlet {

    private final VistaAsistenciaPersonaMarcacionDAO vistaPersonaMarcacionDAO;
    private final EntityManagerFactory emf;

    public MarcacionPersonalServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.vistaPersonaMarcacionDAO = new VistaAsistenciaPersonaMarcacionDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaFinParam = request.getParameter("fechaFin");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*Date fechaInicio = null;
        Date fechaFin = null;

        try {
            if (fechaInicioParam != null && fechaFinParam != null) {
                fechaInicio = sdf.parse(fechaInicioParam);
                fechaFin = sdf.parse(fechaFinParam);
            }
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Formato de fecha inválido. Debe ser yyyy-MM-dd.\"}");
            return;
        }*/

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Listar todos los registros con posible filtro por fechas
                List<VistaAsistenciaPersonaMarcacion> lista = vistaPersonaMarcacionDAO.findByFechaRange(fechaInicioParam, fechaFinParam);
                JSONArray jsonArray = new JSONArray();
                for (VistaAsistenciaPersonaMarcacion vpm : lista) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiMarc", vpm.getCodiMarc());
                    jsonObject.put("codiPers", vpm.getCodiPers());
                    jsonObject.put("nombPers", vpm.getNombPers());
                    jsonObject.put("fechMarc", vpm.getFechMarc() != null ? sdf.format(vpm.getFechMarc()) : JSONObject.NULL);
                    jsonObject.put("codiHoraDeta", vpm.getCodiHoraDeta());
                    jsonObject.put("marcIngr", vpm.getMarcIngr() != null ? new SimpleDateFormat("HH:mm:ss").format(vpm.getMarcIngr()) : JSONObject.NULL);
                    jsonObject.put("marcSald", vpm.getMarcSald() != null ? new SimpleDateFormat("HH:mm:ss").format(vpm.getMarcSald()) : JSONObject.NULL);
                    jsonArray.put(jsonObject);
                }
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un registro específico por codiMarc
                int id = Integer.parseInt(pathInfo.substring(1));
                VistaAsistenciaPersonaMarcacion vpm = vistaPersonaMarcacionDAO.findVistaAsistenciaPersonaMarcacion(id);
                if (vpm == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Registro no encontrado\"}");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiMarc", vpm.getCodiMarc());
                jsonObject.put("codiPers", vpm.getCodiPers());
                jsonObject.put("nombPers", vpm.getNombPers());
                jsonObject.put("fechMarc", vpm.getFechMarc() != null ? sdf.format(vpm.getFechMarc()) : JSONObject.NULL);
                jsonObject.put("codiHoraDeta", vpm.getCodiHoraDeta());
                jsonObject.put("marcIngr", vpm.getMarcIngr() != null ? new SimpleDateFormat("HH:mm:ss").format(vpm.getMarcIngr()) : JSONObject.NULL);
                jsonObject.put("marcSald", vpm.getMarcSald() != null ? new SimpleDateFormat("HH:mm:ss").format(vpm.getMarcSald()) : JSONObject.NULL);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(jsonObject.toString());
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"El identificador debe ser un número entero válido\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }
}
