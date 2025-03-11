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

import pe.gestor.planilla.dao.PlanillaSituacionespecialJpaController;
import pe.gestor.planilla.dto.PlanillaSituacionespecial;

@WebServlet(name = "SituacionEspecialServlet", urlPatterns = { "/situacionespecialservlet" })
public class SituacionEspecialServlet extends HttpServlet {
    private final PlanillaSituacionespecialJpaController situacionEspecialDAO;
    private final EntityManagerFactory emf;

    public SituacionEspecialServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.situacionEspecialDAO = new PlanillaSituacionespecialJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaSituacionespecial> lista = situacionEspecialDAO.findPlanillaSituacionespecialEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaSituacionespecial planillaSituacionespecial : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiSituEspe", planillaSituacionespecial.getCodiSituEspe());
                jsonObject.put("nombSituEspe", planillaSituacionespecial.getNombSituEspe());
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
