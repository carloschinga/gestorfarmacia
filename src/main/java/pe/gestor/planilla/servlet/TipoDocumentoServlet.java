/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.planilla.servlet;


import java.io.IOException;
import java.io.PrintWriter;
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
import pe.gestor.planilla.dao.PlanillaTipoDocDAO;
import pe.gestor.planilla.dto.PlanillaTipodoc;

/**
 *
 * @author san21
 */
@WebServlet(name = "TipoDocumentoServlet", urlPatterns = {"/tipodocumentoservlet"})
public class TipoDocumentoServlet extends HttpServlet {

    private final PlanillaTipoDocDAO tipoDocDAO;
    private final EntityManagerFactory emf;

    public TipoDocumentoServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.tipoDocDAO = new PlanillaTipoDocDAO(emf);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener todos los tipos de documento desde la base de datos
            List<PlanillaTipodoc> tiposDoc = tipoDocDAO.findPlanillaTipodocEntities();

            // Crear un array JSON para contener los tipos de documentos
            JSONArray jsonArray = new JSONArray();

            // Llenar el array con los tipos de documento
            for (PlanillaTipodoc tipoDoc : tiposDoc) {
                JSONObject jsonTipoDoc = new JSONObject();
                jsonTipoDoc.put("codiTipoDoc", tipoDoc.getCodiTipoDoc());
                jsonTipoDoc.put("nombTipoDoc", tipoDoc.getNombTipoDoc());
                jsonArray.put(jsonTipoDoc);
            }
             response.getWriter().write(jsonArray.toString());

        } catch (Exception e) {
            // En caso de error, establecer el código de estado 500 (Internal Server Error)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Error al procesar la solicitud: " + e.getMessage() + "\"}");
        }

        
    }
}
