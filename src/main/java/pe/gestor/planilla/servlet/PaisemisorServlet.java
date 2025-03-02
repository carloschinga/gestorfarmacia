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
import pe.gestor.planilla.dao.PlanillaPaisemisorDAO;
import pe.gestor.planilla.dto.PlanillaPaisemisor;

/**
 *
 * @author USER
 */
@WebServlet(name = "PaisemisorServlet", urlPatterns = {"/paisemisorservlet"})
public class PaisemisorServlet extends HttpServlet {

    private final PlanillaPaisemisorDAO paisEmisorDAO;
    private final EntityManagerFactory emf;

    public PaisemisorServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.paisEmisorDAO = new PlanillaPaisemisorDAO(emf);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<PlanillaPaisemisor> paisesEmisores = paisEmisorDAO.findPlanillaPaisemisorEntities();
            JSONArray jsonArray = new JSONArray();
            
            for (PlanillaPaisemisor paisEmisor : paisesEmisores) {
                JSONObject jsonPaisEmisor = new JSONObject();
                jsonPaisEmisor.put("codiPaisEmis", paisEmisor.getCodiPaisEmis());
                jsonPaisEmisor.put("nombPaisEmis", paisEmisor.getNombPaisEmis());
                jsonArray.put(jsonPaisEmisor);
            }
            
            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }
}
