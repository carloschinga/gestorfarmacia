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

import pe.gestor.planilla.dao.PlanillaRegimenpensionarioJpaController;
import pe.gestor.planilla.dto.PlanillaRegimenpensionario;

@WebServlet(name = "RegimenPensionarioServlet", urlPatterns = { "/regimenpensionarioservlet" })
public class RegimenPensionarioServlet extends HttpServlet {
    private final PlanillaRegimenpensionarioJpaController regiDAO;
    private final EntityManagerFactory emf;

    public RegimenPensionarioServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.regiDAO = new PlanillaRegimenpensionarioJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaRegimenpensionario> lista = regiDAO.findPlanillaRegimenpensionarioEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaRegimenpensionario planillaRegimenpensionario : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiRegiPensi", planillaRegimenpensionario.getCodiRegiPensi());
                jsonObject.put("nombRegiPensi", planillaRegimenpensionario.getNombRegiPensi());
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
