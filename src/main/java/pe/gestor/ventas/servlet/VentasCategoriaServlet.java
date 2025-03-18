package pe.gestor.ventas.servlet;

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

import pe.gestor.ventas.dao.VentasCategoriaJpaController;
import pe.gestor.ventas.dto.VentasCategoria;

@WebServlet(name = "VentasCategoriaServlet", urlPatterns = { "/ventascategoria" })
public class VentasCategoriaServlet extends HttpServlet {
    private final VentasCategoriaJpaController cateDAO;
    private final EntityManagerFactory emf;

    public VentasCategoriaServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.cateDAO = new VentasCategoriaJpaController(emf);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        List<VentasCategoria> lista = cateDAO.findVentasCategoriaEntities();
        JSONArray jsonArray = new JSONArray();
        for (VentasCategoria ventasCategoria : lista) {
            JSONObject json = new JSONObject();
            json.put("codigo", ventasCategoria.getCodiCate());
            json.put("nombre", ventasCategoria.getNombCate());
            jsonArray.put(json);
        }

        response.getWriter().write(jsonArray.toString());
    };
}
