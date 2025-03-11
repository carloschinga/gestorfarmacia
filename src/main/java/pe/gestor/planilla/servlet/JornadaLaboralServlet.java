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

import pe.gestor.planilla.dao.PlanillaJornadalaboralJpaController;
import pe.gestor.planilla.dto.PlanillaJornadalaboral;

@WebServlet(name = "JornadaLaboralServlet", urlPatterns = { "/jornadalaboral" })
public class JornadaLaboralServlet extends HttpServlet {
    private final PlanillaJornadalaboralJpaController dao;
    private final EntityManagerFactory emf;

    public JornadaLaboralServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.dao = new PlanillaJornadalaboralJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaJornadalaboral> lista = dao.findPlanillaJornadalaboralEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaJornadalaboral jornada : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiJornadLab", jornada.getCodiJornadLab());
                jsonObject.put("nombJornadLab", jornada.getNombJornadLab());
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
