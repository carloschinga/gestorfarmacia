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

import pe.gestor.planilla.dao.PlanillaAfpDAO;
import pe.gestor.planilla.dto.PlanillaAfp;

@WebServlet(name = "AfpServlet", urlPatterns = { "/afpservlet" })
public class AfpServlet extends HttpServlet {
    private final PlanillaAfpDAO afpDAO;
    private final EntityManagerFactory emf;

    public AfpServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.afpDAO = new PlanillaAfpDAO(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Obtener todos los registros de afp
            List<PlanillaAfp> afpList = afpDAO.findPlanillaAfpEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaAfp afp : afpList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiAfp", afp.getCodiAFP());
                jsonObject.put("nombAfp", afp.getNombAFP());
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
