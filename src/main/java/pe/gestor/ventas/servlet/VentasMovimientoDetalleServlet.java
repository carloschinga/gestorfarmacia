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
import pe.gestor.ventas.dao.VentasMovimientoDetalleJpaController;
import pe.gestor.ventas.dto.VentasMovimientoDetalle;

@WebServlet(name = "VentasMovimientoDetalleServlet", urlPatterns = { "/ventasmovimientodetalletervlet/*" })
public class VentasMovimientoDetalleServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasMovimientoDetalleJpaController movimientoDetalleDAO;

    public VentasMovimientoDetalleServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.movimientoDetalleDAO = new VentasMovimientoDetalleJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todos los detalles de movimiento
                List<VentasMovimientoDetalle> detallesMovimiento = movimientoDetalleDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasMovimientoDetalle detalleMovimiento : detallesMovimiento) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiMoviDeta", detalleMovimiento.getCodiMoviDeta());
                    jsonObject.put("codiMoviCabe", detalleMovimiento.getCodiMoviCabe());
                    jsonObject.put("codiProdu", detalleMovimiento.getCodiProdu());
                    jsonObject.put("cantEnte", detalleMovimiento.getCantEnte());
                    jsonObject.put("cantFrac", detalleMovimiento.getCantFrac());
                    jsonObject.put("costoEnte", detalleMovimiento.getCostoEnte());
                    jsonObject.put("costoFrac", detalleMovimiento.getCostoFrac());
                    jsonObject.put("actiMoviDeta", detalleMovimiento.getActiMoviDeta());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un detalle de movimiento específico por codiMoviDeta
                String param = pathInfo.substring(1).trim();
                int codiMoviDeta = Integer.parseInt(param);

                VentasMovimientoDetalle detalleMovimiento = movimientoDetalleDAO
                        .findVentasMovimientoDetalle(codiMoviDeta);

                if (detalleMovimiento == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiMoviDeta", detalleMovimiento.getCodiMoviDeta());
                jsonObject.put("codiMoviCabe", detalleMovimiento.getCodiMoviCabe());
                jsonObject.put("codiProdu", detalleMovimiento.getCodiProdu());
                jsonObject.put("cantEnte", detalleMovimiento.getCantEnte());
                jsonObject.put("cantFrac", detalleMovimiento.getCantFrac());
                jsonObject.put("costoEnte", detalleMovimiento.getCostoEnte());
                jsonObject.put("costoFrac", detalleMovimiento.getCostoFrac());
                jsonObject.put("actiMoviDeta", detalleMovimiento.getActiMoviDeta());

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
            int codiMoviCabe = json.getInt("codiMoviCabe");
            String codiProdu = json.getString("codiProdu");
            int cantEnte = json.getInt("cantEnte");
            int cantFrac = json.getInt("cantFrac");
            long costoEnte = json.getLong("costoEnte");
            long costoFrac = json.getLong("costoFrac");
            // boolean actiMoviDeta = json.getBoolean("actiMoviDeta");

            // Crear instancia de VentasMovimientoDetalle
            VentasMovimientoDetalle detalleMovimiento = new VentasMovimientoDetalle();
            detalleMovimiento.setCodiMoviCabe(codiMoviCabe);
            detalleMovimiento.setCodiProdu(codiProdu);
            detalleMovimiento.setCantEnte(cantEnte);
            detalleMovimiento.setCantFrac(cantFrac);
            detalleMovimiento.setCostoEnte(costoEnte);
            detalleMovimiento.setCostoFrac(costoFrac);
            detalleMovimiento.setActiMoviDeta(true);

            // Guardar detalle de movimiento
            movimientoDetalleDAO.create(detalleMovimiento);

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

            // Obtener el ID del detalle de movimiento del JSON
            int codiMoviDeta = json.getInt("codiMoviDeta");

            // Verificar si el detalle de movimiento existe en la base de datos
            VentasMovimientoDetalle detalleMovimientoExistente = movimientoDetalleDAO
                    .findVentasMovimientoDetalle(codiMoviDeta);

            if (detalleMovimientoExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter()
                        .write(new JSONObject().put("error", "Detalle de movimiento no encontrado").toString());
                return;
            }

            // Actualizar los datos del detalle de movimiento existente
            detalleMovimientoExistente.setCodiMoviCabe(json.getInt("codiMoviCabe"));
            detalleMovimientoExistente.setCodiProdu(json.getString("codiProdu"));
            detalleMovimientoExistente.setCantEnte(json.getInt("cantEnte"));
            detalleMovimientoExistente.setCantFrac(json.getInt("cantFrac"));
            detalleMovimientoExistente.setCostoEnte(json.getLong("costoEnte"));
            detalleMovimientoExistente.setCostoFrac(json.getLong("costoFrac"));

            // Intentar actualizar el detalle de movimiento en la base de datos
            movimientoDetalleDAO.edit(detalleMovimientoExistente);

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
                // Obtener el ID del detalle de movimiento de la URL
                int id = Integer.parseInt(pathInfo.substring(1));

                // Realizar un borrado lógico (desactivar el detalle de movimiento)
                VentasMovimientoDetalle detalleMovimiento = movimientoDetalleDAO.findVentasMovimientoDetalle(id);
                if (detalleMovimiento != null) {
                    detalleMovimiento.setActiMoviDeta(false); // Desactivar
                    movimientoDetalleDAO.edit(detalleMovimiento);
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