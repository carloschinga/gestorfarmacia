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

import pe.gestor.planilla.dao.PlanillaSucursalJpaController;
import pe.gestor.planilla.dto.PlanillaSucursal;

@WebServlet(name = "SucursalServlet", urlPatterns = { "/sucursalservlet" })
public class SucursalServlet extends HttpServlet {
    private final PlanillaSucursalJpaController dao;
    private final EntityManagerFactory emf;

    public SucursalServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.dao = new PlanillaSucursalJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaSucursal> lista = dao.findPlanillaSucursalEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaSucursal sucursal : lista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiSucurs", sucursal.getCodiSucurs());
                jsonObject.put("nombSucurs", sucursal.getNombSucurs());
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
