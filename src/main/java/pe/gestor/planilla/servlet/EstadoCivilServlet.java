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

import pe.gestor.planilla.dao.PlanillaEstadocivilJpaController;
import pe.gestor.planilla.dto.PlanillaEstadocivil;

@WebServlet(name = "EstadoCivilServlet", urlPatterns = { "/estadocivil" })
public class EstadoCivilServlet extends HttpServlet {
    private final PlanillaEstadocivilJpaController dao;
    private final EntityManagerFactory emf;

    public EstadoCivilServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.dao = new PlanillaEstadocivilJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaEstadocivil> lista = dao.findPlanillaEstadocivilEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaEstadocivil estado : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiEstadoCivil", estado.getCodiEstaCiv());
                jsonObject.put("nombEstadoCivil", estado.getNombEstaCiv());
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
