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

import pe.gestor.planilla.dao.PlanillaPeriocidadremuneracionJpaController;
import pe.gestor.planilla.dto.PlanillaPeriocidadremuneracion;

@WebServlet(name = "PeriocidadRemuneracionServlet", urlPatterns = { "/periocidadremuneracion" })
public class PeriocidadRemuneracionServlet extends HttpServlet {
    private final PlanillaPeriocidadremuneracionJpaController periocidadDAO;
    private final EntityManagerFactory emf;

    public PeriocidadRemuneracionServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.periocidadDAO = new PlanillaPeriocidadremuneracionJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaPeriocidadremuneracion> periocidadList = periocidadDAO
                    .findPlanillaPeriocidadremuneracionEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaPeriocidadremuneracion planillaPeriocidadremuneracion : periocidadList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiPeriRemu", planillaPeriocidadremuneracion.getCodiPeriRemu());
                jsonObject.put("nombPeriRemu", planillaPeriocidadremuneracion.getNombPeriRemu());
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
