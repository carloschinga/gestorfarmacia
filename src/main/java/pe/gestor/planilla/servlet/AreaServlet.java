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

import pe.gestor.planilla.dao.PlanillaAreaJpaController;
import pe.gestor.planilla.dto.PlanillaArea;

@WebServlet(name = "AreaServlet", urlPatterns = { "/areaservlet" })
public class AreaServlet extends HttpServlet {

    private final PlanillaAreaJpaController areaDAO;
    private final EntityManagerFactory emf;

    public AreaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.areaDAO = new PlanillaAreaJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaArea> listaAreas = areaDAO.findPlanillaAreaEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaArea area : listaAreas) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiArea", area.getCodiArea());
                jsonObject.put("nombArea", area.getNombArea());
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
