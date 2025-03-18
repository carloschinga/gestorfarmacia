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

import pe.gestor.ventas.dao.VentasLaboratorioJpaController;
import pe.gestor.ventas.dto.VentasLaboratorio;

@WebServlet(name = "VentasLaboratorioServlet", urlPatterns = { "/ventaslaboratorio" })
public class VentasLaboratorioServlet extends HttpServlet {
    private final VentasLaboratorioJpaController labDAO;
    private final EntityManagerFactory emf;

    public VentasLaboratorioServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.labDAO = new VentasLaboratorioJpaController(emf);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        JSONArray jsonArray = new JSONArray();
        List<VentasLaboratorio> lista = labDAO.findVentasLaboratorioEntities();
        for (VentasLaboratorio ventasLaboratorio : lista) {
            JSONObject json = new JSONObject();
            json.put("codigo", ventasLaboratorio.getCodiLabo());
            json.put("nombre", ventasLaboratorio.getNombLabo());
            jsonArray.put(json);
        }
        response.getWriter().write(jsonArray.toString());
    };
}
