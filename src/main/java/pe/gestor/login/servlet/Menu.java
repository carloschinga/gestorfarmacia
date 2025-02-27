/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.gestor.login.dao.VistaLoginMenuDetaDAO;
import pe.gestor.login.dto.VistaLoginMenuDeta;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "Menu", urlPatterns = {"/menu"})
public class Menu extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        } catch (Exception ex) {
            System.out.println("Error al inicializar DAOs: " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            String codi = session.getAttribute("codiUsua").toString();

            VistaLoginMenuDetaDAO webMenuDetaDAO = new VistaLoginMenuDetaDAO(emf);
            List<VistaLoginMenuDeta> lista = webMenuDetaDAO.listarMenuXUsua(Integer.parseInt(codi));

            Map<Integer, List<VistaLoginMenuDeta>> menuMap = new HashMap<>();

            List<VistaLoginMenuDeta> cabeceras = new ArrayList<>();

            try{
            for (VistaLoginMenuDeta webMenuDeta : lista) {
                if (webMenuDeta.getTipoMenu().equals('T')) { // Es una cabecera
                    cabeceras.add(webMenuDeta);
                    menuMap.put(webMenuDeta.getCodiPagi(), new ArrayList<VistaLoginMenuDeta>()); // Especificar el tipo aquí
                } else if (webMenuDeta.getTipoMenu().equals('M')) { // Es un elemento de menú
                    menuMap.get(webMenuDeta.getCodmas()).add(webMenuDeta);
                }
            }
            }
            catch(Exception ex){
                String m= ex.getMessage();
                
            }

            StringBuilder resultado = new StringBuilder();
            for (VistaLoginMenuDeta cabecera : cabeceras) {
                resultado.append("<li class=\"nav-item dropdown no-arrow\">");
                resultado.append("<a class=\"nav-link dropdown-toggle text-gray-900\" href=\"#\" id=\"userDropdown\" data-toggle=\"dropdown\" >")
                        .append(cabecera.getNombPagi())
                        .append("</a>");
                resultado.append("<div class=\"dropdown-menu dropdown-menu-left shadow animated--grow-in\" aria-labelledby=\"userDropdown\">");

                List<VistaLoginMenuDeta> elementosMenu = menuMap.get(cabecera.getCodiPagi());
                for (VistaLoginMenuDeta elemento : elementosMenu) {
                    resultado.append("<a id=\"asignarpermiso\" class=\"dropdown-item\" href=\"")
                            .append(elemento.getHtml())
                            .append("\">")
                            .append(elemento.getNombPagi())
                            .append("</a>");
                }
                resultado.append("</div></li>");
            }

            out.print(resultado.toString());

        }
    }

}
