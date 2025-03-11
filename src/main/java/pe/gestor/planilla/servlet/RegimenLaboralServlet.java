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

import pe.gestor.planilla.dao.PlanillaRegimenlaboralJpaController;
import pe.gestor.planilla.dto.PlanillaRegimenlaboral;

@WebServlet(name = "RegimenLaboralServlet", urlPatterns = { "/regimenlaboralservlet" })
public class RegimenLaboralServlet extends HttpServlet {
    private final PlanillaRegimenlaboralJpaController regiDAO;
    private final EntityManagerFactory emf;

    public RegimenLaboralServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.regiDAO = new PlanillaRegimenlaboralJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaRegimenlaboral> regiList = regiDAO.findPlanillaRegimenlaboralEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaRegimenlaboral planillaRegimenlaboral : regiList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiRegiLabo", planillaRegimenlaboral.getCodiRegiLabo());
                jsonObject.put("nombRegiLabo", planillaRegimenlaboral.getNombRegiLabo());
                jsonArray.put(jsonObject);
            }
            resp.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
        }
    };
}
