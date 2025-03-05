package pe.gestor.planilla.servlet;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.gestor.planilla.dao.PlanillaPlantillaJpaController;
import pe.gestor.planilla.dto.PlanillaPlantilla;

@WebServlet(name = "PlantillaServlet", urlPatterns = { "/plantillaservlet" })
public class PlantillaServlet extends HttpServlet {
    private final PlanillaPlantillaJpaController plantillaDAO;
    private final EntityManagerFactory emf;

    public PlantillaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.plantillaDAO = new PlanillaPlantillaJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws javax.servlet.ServletException, java.io.IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaPlantilla> plantillaList = plantillaDAO.findPlanillaPlantillaEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaPlantilla plantilla : plantillaList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiPlant", plantilla.getCodiPlant());
                jsonObject.put("nombPlant", plantilla.getNombPlant());
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
