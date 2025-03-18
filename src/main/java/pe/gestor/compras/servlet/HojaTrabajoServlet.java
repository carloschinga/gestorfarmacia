/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.compras.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import pe.gestor.compras.dao.VistaComprasHojaTrabajoDAO;
import pe.gestor.compras.dto.VistaComprasHojaTrabajo;

/**
 *
 * @author USER
 */
@WebServlet(name = "HojaTrabajoServlet", urlPatterns = { "/hojatrabajoservlet" })
public class HojaTrabajoServlet extends HttpServlet {

    private final VistaComprasHojaTrabajoDAO vistaComprasHojaTrabajoDAO;
    private final EntityManagerFactory emf;

    public HojaTrabajoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.vistaComprasHojaTrabajoDAO = new VistaComprasHojaTrabajoDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<VistaComprasHojaTrabajo> compras = vistaComprasHojaTrabajoDAO.findVistaComprasHojaTrabajoEntities();
            JSONArray jsonArray = new JSONArray();

            for (VistaComprasHojaTrabajo compra : compras) {
                JSONObject jsonCompra = new JSONObject();
                putSafely(jsonCompra, "codigo", compra.getCodigo(), "");
                putSafely(jsonCompra, "nombre", compra.getNombreProducto(), "");
                putSafely(jsonCompra, "cateId", compra.getCodiCate(), 0);
                putSafely(jsonCompra, "categoria", compra.getCategoria(), "");
                putSafely(jsonCompra, "laboId", compra.getCodiLabo(), 0);
                putSafely(jsonCompra, "laboratorio", compra.getLaboratorio(), "");
                putSafely(jsonCompra, "unidxcaja", compra.getUnidxcaja(), 0);
                putSafely(jsonCompra, "pvc", compra.getPvc(), 0);
                putSafely(jsonCompra, "pcc", compra.getPcc(), 0);
                putSafely(jsonCompra, "pvu", compra.getPvu(), 0);
                putSafely(jsonCompra, "pcu", compra.getPcu(), 0);
                putSafely(jsonCompra, "ventas30ultmdias", compra.getVentas30ultmdias(), 0);
                putSafely(jsonCompra, "ventas", compra.getVentas(), 0);
                putSafely(jsonCompra, "mes1", compra.getMes1(), 0);
                putSafely(jsonCompra, "mes2", compra.getMes2(), 0);
                putSafely(jsonCompra, "mes3", compra.getMes3(), 0);
                putSafely(jsonCompra, "stock", compra.getStock(), 0);
                putSafely(jsonCompra, "gananciacaja", compra.getGananciacaja(), 0);
                putSafely(jsonCompra, "gananciauni", compra.getGananciauni(), 0);
                putSafely(jsonCompra, "stockmin", compra.getStockmin(), 0);
                putSafely(jsonCompra, "indiinve", compra.getIndiinve(), 0);
                putSafely(jsonCompra, "indirota", compra.getIndirota(), 0);
                jsonArray.put(jsonCompra);
            }

            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }

    private void putSafely(JSONObject json, String key, Object value, Object defaultValue) {
        json.put(key, value != null ? value : defaultValue);
    }
}
