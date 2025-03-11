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

import pe.gestor.planilla.dao.PlanillaTipopagoJpaController;
import pe.gestor.planilla.dto.PlanillaTipopago;

@WebServlet(name = "TipoPagoServlet", urlPatterns = { "/tipopagoservlet" })
public class TipoPagoServlet extends HttpServlet {
    private final PlanillaTipopagoJpaController pagoDAO;
    private final EntityManagerFactory emf;

    public TipoPagoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.pagoDAO = new PlanillaTipopagoJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaTipopago> pagoList = pagoDAO.findPlanillaTipopagoEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaTipopago planillaTipopago : pagoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiTipoPago", planillaTipopago.getCodiTipoPago());
                jsonObject.put("nombTipoPago", planillaTipopago.getNombTipoPago());
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
