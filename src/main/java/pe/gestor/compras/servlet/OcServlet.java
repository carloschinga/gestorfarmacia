/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.compras.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.compras.dao.ComprasOcDAO;
import pe.gestor.compras.dto.ComprasOc;
import pe.gestor.compras.dto.VistaComprasOc;

/**
 *
 * @author USER
 */
@WebServlet(name = "OcServlet", urlPatterns = {"/ocservlet"})
public class OcServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;
    private ComprasOcDAO comprasOcDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        comprasOcDAO = new ComprasOcDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        try (PrintWriter out = response.getWriter()) {
            EntityManager em = emf.createEntityManager();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                TypedQuery<VistaComprasOc> query;
                if (pathInfo == null || pathInfo.equals("/")) {
                    query = em.createNamedQuery("VistaComprasOc.findAll", VistaComprasOc.class);
                    List<VistaComprasOc> compras = query.getResultList();
                    JSONArray jsonArray = new JSONArray();

                    for (VistaComprasOc compra : compras) {
                        jsonArray.put(convertToJson(compra));
                    }
                    out.print(jsonArray.toString());
                    /*List<ComprasOc> compras = comprasOcDAO.findComprasOcEntities();
                    JSONArray jsonArray = new JSONArray();
                    
                    for (ComprasOc compra : compras) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("codiOC", compra.getCodiOC());
                        jsonObject.put("fechOC", (compra.getFechOC() != null) ? dateFormat.format(compra.getFechOC()) : "");
                        jsonObject.put("codiProv", compra.getCodiProv());
                        jsonObject.put("codiEstdOC", compra.getCodiEstdOC());
                        jsonObject.put("codiUsuaRegi", compra.getCodiUsuaRegi());
                        jsonObject.put("fechUsuaRegi", (compra.getFechUsuaRegi() != null) ? dateFormat.format(compra.getFechUsuaRegi()) : "");
                        jsonObject.put("actiOC", compra.getActiOC());
                        jsonArray.put(jsonObject);
                    }
                    out.print(jsonArray.toString());*/
                } else {
                    int id = Integer.parseInt(pathInfo.substring(1));
                    ComprasOc compra = comprasOcDAO.findComprasOc(id);

                    if (compra != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("codiOC", compra.getCodiOC());
                        jsonObject.put("fechOC", (compra.getFechOC() != null) ? dateFormat.format(compra.getFechOC()) : "");
                        jsonObject.put("codiProv", compra.getCodiProv());
                        jsonObject.put("codiEstdOC", compra.getCodiEstdOC());
                        jsonObject.put("codiUsuaRegi", compra.getCodiUsuaRegi());
                        jsonObject.put("fechUsuaRegi", (compra.getFechUsuaRegi() != null) ? dateFormat.format(compra.getFechUsuaRegi()) : "");
                        jsonObject.put("actiOC", compra.getActiOC());
                        out.print(jsonObject.toString());
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print(new JSONObject().put("error", "Orden de compra no encontrada").toString());
                    }
                }
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(new JSONObject().put("error", "Error al obtener los datos").toString());
                ex.printStackTrace();
            } finally {
                em.close();
            }
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        super.destroy();
    }

    private JSONObject convertToJson(VistaComprasOc compra) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("codiOC", compra.getCodiOC());
        jsonObject.put("fechOC", dateFormat.format(compra.getFechOC()));
        jsonObject.put("codiProv", compra.getCodiProv());
        jsonObject.put("nombProv", compra.getNombProv());
        jsonObject.put("codiEstdOC", compra.getCodiEstdOC());
        jsonObject.put("nombEstdOC", compra.getNombEstdOC());
        jsonObject.put("actiOC", compra.getActiOC());
        jsonObject.put("codiUsuaRegi", compra.getCodiUsuaRegi());
        jsonObject.put("fechUsuaRegi", dateFormat.format(compra.getFechUsuaRegi()));
        return jsonObject;
    }

}
