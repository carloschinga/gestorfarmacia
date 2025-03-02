/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.planilla.servlet;

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
import pe.gestor.planilla.dao.PlanillaTipoviaDAO;
import pe.gestor.planilla.dto.PlanillaTipovia;

/**
 *
 * @author USER
 */
@WebServlet(name = "TipoviaServlet", urlPatterns = {"/tipoviaservlet"})
public class TipoviaServlet extends HttpServlet {

     private final PlanillaTipoviaDAO tipoViaDAO;
    private final EntityManagerFactory emf;

    public TipoviaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tipoViaDAO = new PlanillaTipoviaDAO(emf);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<PlanillaTipovia> tiposVia = tipoViaDAO.findPlanillaTipoviaEntities();
            JSONArray jsonArray = new JSONArray();
            
            for (PlanillaTipovia tipoVia : tiposVia) {
                JSONObject jsonTipoVia = new JSONObject();
                jsonTipoVia.put("codiTipoVia", tipoVia.getCodiTipoVia());
                jsonTipoVia.put("nombTipoVia", tipoVia.getNombTipoVia());
                jsonArray.put(jsonTipoVia);
            }
            
            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }
}
