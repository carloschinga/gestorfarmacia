package pe.gestor.ventas.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import pe.gestor.ventas.dao.VistaVentasGraficosXMesDAO;
import pe.gestor.ventas.dao.VistaVentasPorProductoDAO;
import pe.gestor.ventas.dto.VentasGraficosMensuales;
import pe.gestor.ventas.dto.VistaVentasGraficosXMes;
import pe.gestor.ventas.dto.VistaVentasPorProducto;

@WebServlet(name = "VentasGraficosServlet", urlPatterns = {"/ventasgraficosservlet"})
public class VentasGraficosServlet extends HttpServlet {

    private VentasGraficosMensualesDAO ventasGraficosMensualesDAO;
    private VistaVentasGraficosXMesDAO vistaVentasGraficosXMesDAO;
    private VistaVentasPorProductoDAO vistaVentaproductoDAO;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        ventasGraficosMensualesDAO = new VentasGraficosMensualesDAO(emf);
        vistaVentasGraficosXMesDAO= new VistaVentasGraficosXMesDAO(emf);
        vistaVentaproductoDAO= new VistaVentasPorProductoDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {

            // Ventas por Mes
            List<VistaVentasGraficosXMes> ventasxMeses = vistaVentasGraficosXMesDAO.findVistaVentasGraficosXMesEntities();
            Map<String, BigDecimal> ventasPorMes = new HashMap<>();
            for (VistaVentasGraficosXMes ventasxMese : ventasxMeses) {
                    ventasPorMes.put(ventasxMese.getMes(), ventasxMese.getMonto());

            }
            // Ventas por Mes
            List<VistaVentasPorProducto> ventas = vistaVentaproductoDAO.findVistaVentasPorProductoEntities();
            Map<String, BigInteger> ventasPorProducto = new HashMap<>();
            for (VistaVentasPorProducto ventaxproducto : ventas) {

                // Ventas por Producto (🔹 Aquí aseguramos que se suman correctamente)
                ventasPorProducto.put(ventaxproducto.getProducto(), ventaxproducto.getTotalVendido());
            }
                    
            

           
            Map<String, Integer> ventasPorSede = new HashMap<>();

           

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
