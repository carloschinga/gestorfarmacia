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

import pe.gestor.planilla.dao.PlanillaTipocontratoJpaController;
import pe.gestor.planilla.dto.PlanillaTipocontrato;

@WebServlet(name = "TipoContratoServlet", urlPatterns = { "/tipocontrato" })
public class TipoContratoServlet extends HttpServlet {
    private final PlanillaTipocontratoJpaController tipoContratoDAO;
    private final EntityManagerFactory emf;

    public TipoContratoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tipoContratoDAO = new PlanillaTipocontratoJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaTipocontrato> lista = tipoContratoDAO.findPlanillaTipocontratoEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaTipocontrato planillaTipocontrato : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiTipoCntr", planillaTipocontrato.getCodiTipoCntr());
                jsonObject.put("nombTipoCntr", planillaTipocontrato.getNombTipoCntr());
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
