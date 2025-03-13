package pe.gestor.asistencia.servlet;

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

import pe.gestor.asistencia.dao.AsistenciaHorarioJpaController;
import pe.gestor.asistencia.dto.AsistenciaHorario;

@WebServlet(name = "HorarioCargoServlet", urlPatterns = { "/horariocargo" })
public class HorarioCargoServlet extends HttpServlet {
    private final AsistenciaHorarioJpaController horaDAO;
    private final EntityManagerFactory emf;

    public HorarioCargoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.horaDAO = new AsistenciaHorarioJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<AsistenciaHorario> lista = horaDAO.findAsistenciaHorarioEntities();
        JSONArray jsonArray = new JSONArray();
        for (AsistenciaHorario asistenciaHorario : lista) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("codihora", asistenciaHorario.getCodiHora());
            jsonObject.put("nombhora", asistenciaHorario.getNombHora());
            jsonArray.put(jsonObject);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonArray.toString());
    };
}
