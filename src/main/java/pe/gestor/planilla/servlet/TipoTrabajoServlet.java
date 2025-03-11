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

import pe.gestor.planilla.dao.PlanillaTipotrabajadorJpaController;
import pe.gestor.planilla.dto.PlanillaTipotrabajador;

@WebServlet(name = "TipoTrabajoServlet", urlPatterns = { "/tipotrabajoservlet" })
public class TipoTrabajoServlet extends HttpServlet {
    private final PlanillaTipotrabajadorJpaController dao;
    private final EntityManagerFactory emf;

    public TipoTrabajoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.dao = new PlanillaTipotrabajadorJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaTipotrabajador> lista = dao.findPlanillaTipotrabajadorEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaTipotrabajador planillaTipotrabajador : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiTipoTrab", planillaTipotrabajador.getCodiTipoTrab());
                jsonObject.put("nombTipoTrab", planillaTipotrabajador.getNombTipoTrab());
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
