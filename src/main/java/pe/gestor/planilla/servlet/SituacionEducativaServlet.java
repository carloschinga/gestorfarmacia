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

import pe.gestor.planilla.dao.PlanillaSituacioneducativaJpaController;
import pe.gestor.planilla.dto.PlanillaSituacioneducativa;

@WebServlet(name = "SituacionEducativaServlet", urlPatterns = { "/situacioneducativaservlet" })
public class SituacionEducativaServlet extends HttpServlet {
    private final PlanillaSituacioneducativaJpaController situacionEducativaDAO;
    private final EntityManagerFactory emf;

    public SituacionEducativaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.situacionEducativaDAO = new PlanillaSituacioneducativaJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaSituacioneducativa> lista = situacionEducativaDAO.findPlanillaSituacioneducativaEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaSituacioneducativa planillaSituacioneducativa : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiSituEduc", planillaSituacioneducativa.getCodiSituEduc());
                jsonObject.put("nombSituEduc", planillaSituacioneducativa.getNombSituEduc());
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
