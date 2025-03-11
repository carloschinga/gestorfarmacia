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

import pe.gestor.planilla.dao.PlanillaCargoJpaController;
import pe.gestor.planilla.dto.PlanillaCargo;

@WebServlet(name = "CargoServlet", urlPatterns = { "/cargoservlet" })
public class CargoServlet extends HttpServlet {
    private final PlanillaCargoJpaController cargoDAO;
    private final EntityManagerFactory emf;

    public CargoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.cargoDAO = new PlanillaCargoJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaCargo> cargoList = cargoDAO.findPlanillaCargoEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaCargo cargo : cargoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiCargo", cargo.getCodiCargo());
                jsonObject.put("nombCargo", cargo.getNombCargo());
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
