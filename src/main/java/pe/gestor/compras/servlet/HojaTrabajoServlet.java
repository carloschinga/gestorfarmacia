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
                jsonCompra.put("codigo", compra.getCodigo());
                jsonCompra.put("categoria", compra.getCategoria());
                jsonCompra.put("laboratorio", compra.getLaboratorio());
                jsonCompra.put("nombre", compra.getNombre());
                jsonCompra.put("precioCaja", compra.getPrecioCaja());
                jsonCompra.put("pvp1", compra.getPvp1());
                jsonCompra.put("pvp2", compra.getPvp2());
                jsonCompra.put("precioCostoUnitario", compra.getPrecioCostoUnitario());
                jsonCompra.put("precioBlister", compra.getPrecioBlister());
                jsonCompra.put("margenGanancia", compra.getMargenGanancia());
                jsonCompra.put("stockTotal", compra.getStockTotal());
                jsonCompra.put("ventas", compra.getVentas());
                jsonCompra.put("mes1", compra.getMes1());
                jsonCompra.put("mes2", compra.getMes2());
                jsonArray.put(jsonCompra);
            }

            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }

}
