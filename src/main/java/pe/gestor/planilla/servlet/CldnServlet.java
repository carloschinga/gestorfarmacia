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
import pe.gestor.planilla.dao.PlanillaCldnDAO;
import pe.gestor.planilla.dto.PlanillaCldn;

/**
 *
 * @author USER
 */
@WebServlet(name = "CldnServlet", urlPatterns = {"/cldnservlet"})
public class CldnServlet extends HttpServlet {

    private final PlanillaCldnDAO planillaCldnDAO;
    private final EntityManagerFactory emf;

    public CldnServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.planillaCldnDAO = new PlanillaCldnDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaCldn> planillas = planillaCldnDAO.findPlanillaCldnEntities();
            JSONArray jsonArray = new JSONArray();

            for (PlanillaCldn planilla : planillas) {
                JSONObject jsonPlanilla = new JSONObject();
                jsonPlanilla.put("codiCldn", planilla.getCodiCldn());
                jsonPlanilla.put("nombCldn", planilla.getNombCldn());
                jsonArray.put(jsonPlanilla);
            }

            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }

}
