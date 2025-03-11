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

import pe.gestor.planilla.dao.PlanillaTipoocupacionJpaController;
import pe.gestor.planilla.dto.PlanillaTipoocupacion;

@WebServlet(name = "TipoOcupacionServlet", urlPatterns = { "/tipoocupacion" })
public class TipoOcupacionServlet extends HttpServlet {
    private final PlanillaTipoocupacionJpaController tipoOcupacionDAO;
    private final EntityManagerFactory emf;

    public TipoOcupacionServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tipoOcupacionDAO = new PlanillaTipoocupacionJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaTipoocupacion> lista = tipoOcupacionDAO.findPlanillaTipoocupacionEntities();
            JSONArray array = new JSONArray();
            for (PlanillaTipoocupacion planillaTipoocupacion : lista) {
                JSONObject json = new JSONObject();
                json.put("codiTipo", planillaTipoocupacion.getCodiTipoOcup());
                json.put("nombTipo", planillaTipoocupacion.getNombTipoOcup());
                array.put(json);
            }
            resp.getWriter().write(array.toString());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter()
                    .write(new JSONObject().put("error", "Error al obtener los datos: " + e.getMessage()).toString());
        }
    };
}
