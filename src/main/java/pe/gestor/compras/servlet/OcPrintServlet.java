/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.compras.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gestor.compras.dto.ComprasOc;
import pe.gestor.compras.dto.ComprasOcd;

/**
 *
 * @author USER
 */
@WebServlet(name = "OcPrintServlet", urlPatterns = {"/ocprintservlet/*"})
public class OcPrintServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";

    public OcPrintServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        if (pathInfo == null || pathInfo.equals("/")) {  
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro codiOC debe ser un número");
      
        } else {

            int id = Integer.parseInt(pathInfo.substring(1));
            EntityManager em = emf.createEntityManager();
            try {
                // Buscar la orden de compra
                ComprasOc compra = em.find(ComprasOc.class, id);
                if (compra == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden de compra no encontrada");
                    return;
                }

                // Buscar detalles de la orden de compra
                TypedQuery<ComprasOcd> query = em.createQuery("SELECT d FROM ComprasOcd d WHERE d.codiOC = :codiOC", ComprasOcd.class);
                query.setParameter("codiOC", id);
                List<ComprasOcd> detalles = query.getResultList();

                // Construir JSON
                JsonObject jsonCompra = new JsonObject();
                jsonCompra.addProperty("codiOC", compra.getCodiOC());
                jsonCompra.addProperty("fechOC", compra.getFechOC().toString());
                jsonCompra.addProperty("nombProv", "Proveedor"); // Debería obtenerse de la BD
                jsonCompra.addProperty("nombEstdOC", "Aprobada"); // Debería obtenerse de la BD

                double total = 0;
                for (ComprasOcd detalle : detalles) {
                    total += detalle.getTotaProd();
                }
                jsonCompra.addProperty("total", total);

                JsonArray jsonDetalles = new JsonArray();
                for (ComprasOcd detalle : detalles) {
                    JsonObject jsonDetalle = new JsonObject();
                    jsonDetalle.addProperty("nombProd", detalle.getNombProd());
                    jsonDetalle.addProperty("cantiProd", detalle.getCantiProd());
                    jsonDetalle.addProperty("precProd", detalle.getPrecProd());
                    jsonDetalle.addProperty("totaProd", detalle.getTotaProd());
                    jsonDetalles.add(jsonDetalle);
                }
                jsonCompra.add("detalles", jsonDetalles);

                out.print(gson.toJson(jsonCompra));
            } finally {
                em.close();
            }
        }
    }

}
