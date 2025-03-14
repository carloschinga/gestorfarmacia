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
import pe.gestor.planilla.dao.PlanillaUbigeoDAO;
import pe.gestor.planilla.dto.PlanillaUbigeo;

/**
 *
 * @author USER
 */
@WebServlet(name = "UbigeoServlet", urlPatterns = { "/ubigeoservlet" })
public class UbigeoServlet extends HttpServlet {

    private final PlanillaUbigeoDAO ubigeoDAO;
    private final EntityManagerFactory emf;

    public UbigeoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.ubigeoDAO = new PlanillaUbigeoDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nivel = request.getParameter("nivel");
        String codiUbig = request.getParameter("codiUbig");

        try {
            List<PlanillaUbigeo> ubigeos;
            if (nivel.equals("1")) {
                ubigeos = ubigeoDAO.getListNive(nivel.charAt(0));
            } else if (nivel.equals("2")) {
                ubigeos = ubigeoDAO.getListNiveByParent(codiUbig);
            } else if (nivel.equals("3")) {
                ubigeos = ubigeoDAO.getListNiveByParent(codiUbig);
            } else {
                throw new IllegalArgumentException("Nivel no valido");
            }
            JSONArray jsonArray = new JSONArray();

            for (PlanillaUbigeo ubigeo : ubigeos) {
                JSONObject jsonUbigeo = new JSONObject();
                jsonUbigeo.put("codiUbig", ubigeo.getCodiUbig());
                jsonUbigeo.put("nombUbig", ubigeo.getNombUbig());
                jsonUbigeo.put("niveUbig", ubigeo.getNiveUbig());
                jsonArray.put(jsonUbigeo);
            }

            response.getWriter().write(jsonArray.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", e.getMessage()).toString());
        }
    }
}
