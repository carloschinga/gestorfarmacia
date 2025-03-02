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
import pe.gestor.planilla.dao.PlanillaEntidadbancariaDAO;
import pe.gestor.planilla.dto.PlanillaEntidadbancaria;

/**
 *
 * @author USER
 */
@WebServlet(name = "EntidadbancariaServlet", urlPatterns = {"/entidadbancariaservlet"})
public class EntidadbancariaServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final PlanillaEntidadbancariaDAO entidadBancariaDAO;

    public EntidadbancariaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.entidadBancariaDAO = new PlanillaEntidadbancariaDAO(emf);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<PlanillaEntidadbancaria> entidades = entidadBancariaDAO.findPlanillaEntidadbancariaEntities();
            JSONArray jsonArray = new JSONArray();
            
            for (PlanillaEntidadbancaria entidad : entidades) {
                JSONObject jsonEntidad = new JSONObject();
                jsonEntidad.put("codiEntiBanc", entidad.getCodiEntiBanc());
                jsonEntidad.put("nombEntiBanc", entidad.getNombEntiBanc());
                jsonArray.put(jsonEntidad);
            }
            
            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }
}
