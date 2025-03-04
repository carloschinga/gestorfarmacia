package pe.gestor.ventas.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gestor.ventas.dao.VentasGraficosMensualesDAO;
import pe.gestor.ventas.dto.VentasGraficosMensuales;

@WebServlet(name = "VentasGraficosServlet", urlPatterns = {"/ventasgraficosservlet"})
public class VentasGraficosServlet extends HttpServlet {

    private VentasGraficosMensualesDAO ventasGraficosMensualesDAO;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        ventasGraficosMensualesDAO = new VentasGraficosMensualesDAO(emf);
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    try {
        List<VentasGraficosMensuales> ventas = ventasGraficosMensualesDAO.findAllVentas();
        Map<String, Integer> ventasPorMes = new HashMap<>();
        Map<String, Integer> ventasPorProducto = new HashMap<>();
        Map<String, Integer> ventasPorSede = new HashMap<>();

        for (VentasGraficosMensuales venta : ventas) {
            // Ventas por Mes
            ventasPorMes.put(venta.getMes(), ventasPorMes.getOrDefault(venta.getMes(), 0) + venta.getTotalVendido().intValue());

            // Ventas por Producto (🔹 Aquí aseguramos que se suman correctamente)
            ventasPorProducto.put(venta.getProducto(), ventasPorProducto.getOrDefault(venta.getProducto(), 0) + venta.getTotalVendido().intValue());

            // Ventas por Sede
            ventasPorSede.put(String.valueOf(venta.getCodiSede()), ventasPorSede.getOrDefault(String.valueOf(venta.getCodiSede()), 0) + venta.getTotalVendido().intValue());
        }

        // Convertir datos a JSON y enviarlos
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("ventasPorMes", ventasPorMes);
        jsonResponse.put("ventasPorProducto", ventasPorProducto);
        jsonResponse.put("ventasPorSede", ventasPorSede);

        out.print(new Gson().toJson(jsonResponse));

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print("{\"error\": \"Error al obtener los datos: " + e.getMessage() + "\"}");
        e.printStackTrace();
    }
}
}

