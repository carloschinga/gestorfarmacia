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

import pe.gestor.planilla.dao.PlanillaCategoriaocupacionalJpaController;
import pe.gestor.planilla.dto.PlanillaCategoriaocupacional;

@WebServlet(name = "CategoriaOcupacionalServlet", urlPatterns = { "/categoriaocupacional" })
public class CategoriaOcupacionalServlete extends HttpServlet {

    private final PlanillaCategoriaocupacionalJpaController cateOcuDAO;
    private final EntityManagerFactory emf;

    public CategoriaOcupacionalServlete() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.cateOcuDAO = new PlanillaCategoriaocupacionalJpaController(emf);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<PlanillaCategoriaocupacional> categorias = cateOcuDAO.findPlanillaCategoriaocupacionalEntities();
            JSONArray jsonArray = new JSONArray();
            for (PlanillaCategoriaocupacional categoria : categorias) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiCategoria", categoria.getCodiCateOcup());
                jsonObject.put("nombCategoria", categoria.getNombCateOcup());
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
