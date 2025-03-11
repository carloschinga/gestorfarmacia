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

import pe.gestor.planilla.dao.PlanillaRegimensaludJpaController;
import pe.gestor.planilla.dto.PlanillaRegimensalud;

@WebServlet(name = "regimensaludservlet", urlPatterns = { "/regimensaludservlet" })
public class RegimenSaludServlet extends HttpServlet {
    private final PlanillaRegimensaludJpaController regiDAO;
    private final EntityManagerFactory emf;

    public RegimenSaludServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.regiDAO = new PlanillaRegimensaludJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaRegimensalud> regiList = regiDAO.findPlanillaRegimensaludEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaRegimensalud planillaRegimensalud : regiList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiRegiSal", planillaRegimensalud.getCodiRegiSal());
                jsonObject.put("nombRegiSal", planillaRegimensalud.getNombRegiSal());
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
