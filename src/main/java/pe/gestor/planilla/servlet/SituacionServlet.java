package pe.gestor.planilla.servlet;

import java.io.IOException;
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

import pe.gestor.planilla.dao.PlanillaSituacionJpaController;
import pe.gestor.planilla.dto.PlanillaSituacion;

@WebServlet(name = "SituacionServlet", urlPatterns = { "/situacionservlet" })
public class SituacionServlet extends HttpServlet {
    private final PlanillaSituacionJpaController situacionDAO;
    private final EntityManagerFactory emf;

    public SituacionServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.situacionDAO = new PlanillaSituacionJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaSituacion> situacionList = situacionDAO.findPlanillaSituacionEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaSituacion planillaSituacion : situacionList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiSitua", planillaSituacion.getCodiSitua());
                jsonObject.put("nombSitua", planillaSituacion.getNombSitua());
                jsonArray.put(jsonObject);
            }
            resp.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter()
                    .write(new JSONObject().put("error", "Error al obtener los datos: " + e.getMessage()).toString());
        }
    };
}
