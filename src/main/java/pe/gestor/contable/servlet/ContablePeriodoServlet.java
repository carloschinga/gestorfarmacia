package pe.gestor.contable.servlet;

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

import pe.gestor.contable.dao.ContablePeriodoJpaController;
import pe.gestor.contable.dto.ContablePeriodo;

@WebServlet(name = "ContablePeriodoServlet", urlPatterns = { "/contableperiodo" })
public class ContablePeriodoServlet extends HttpServlet {
    private final EntityManagerFactory emf;
    private final ContablePeriodoJpaController dao;

    public ContablePeriodoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.dao = new ContablePeriodoJpaController(emf);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        List<ContablePeriodo> lista = dao.findContablePeriodoEntities();

        JSONArray jsonArray = new JSONArray();
        for (ContablePeriodo item : lista) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codiPeri", item.getCodiPeri());
            jsonObject.put("nombPeri", item.getNombPeri());
            jsonArray.put(jsonObject);
        }

        response.getWriter().write(jsonArray.toString());
    };
}
