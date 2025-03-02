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
import pe.gestor.planilla.dao.PlanillaNacionalidadDAO;
import pe.gestor.planilla.dto.PlanillaNacionalidad;

/**
 *
 * @author USER
 */
@WebServlet(name = "NacionalidadServlet", urlPatterns = {"/nacionalidadservlet"})
public class NacionalidadServlet extends HttpServlet {

     
    private final PlanillaNacionalidadDAO nacionalidadDAO;
    private final EntityManagerFactory emf;

    public NacionalidadServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.nacionalidadDAO = new PlanillaNacionalidadDAO(emf);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<PlanillaNacionalidad> nacionalidades = nacionalidadDAO.findPlanillaNacionalidadEntities();
            JSONArray jsonArray = new JSONArray();
            
            for (PlanillaNacionalidad nacionalidad : nacionalidades) {
                JSONObject jsonNacionalidad = new JSONObject();
                jsonNacionalidad.put("codiNaci", nacionalidad.getCodiNaci());
                jsonNacionalidad.put("nombNaci", nacionalidad.getNombNaci());
                jsonArray.put(jsonNacionalidad);
            }
            
            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }

}
