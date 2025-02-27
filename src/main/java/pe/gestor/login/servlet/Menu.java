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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "Menu", urlPatterns = {"/menu"})
public class Menu extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*
         
            HttpSession session = request.getSession(true);
            String codi = session.getAttribute("codi").toString();
            ViewWebMenuDetaJpaController webMenuDetaDAO = new ViewWebMenuDetaJpaController();
            List<ViewWebMenuDeta> lista = webMenuDetaDAO.listarMenuXUsua(Integer.parseInt(codi));

            Map<Integer, List<ViewWebMenuDeta>> menuMap = new HashMap<>();

            List<ViewWebMenuDeta> cabeceras = new ArrayList<>();

            for (ViewWebMenuDeta webMenuDeta : lista) {
                if (webMenuDeta.getTipmen().equals("T")) { // Es una cabecera
                    cabeceras.add(webMenuDeta);
                    menuMap.put(webMenuDeta.getCodmen(), new ArrayList<>());
                } else if (webMenuDeta.getTipmen().equals("M")) { // Es un elemento de menú
                    menuMap.get(webMenuDeta.getCodmas()).add(webMenuDeta);
                }
            }

            StringBuilder resultado = new StringBuilder();
            for (ViewWebMenuDeta cabecera : cabeceras) {
                resultado.append("<li class=\"nav-item dropdown no-arrow\">");
                resultado.append("<a class=\"nav-link dropdown-toggle text-gray-900\" href=\"#\" id=\"userDropdown\" data-toggle=\"dropdown\" >")
                        .append(cabecera.getNommen())
                        .append("</a>");
                resultado.append("<div class=\"dropdown-menu dropdown-menu-left shadow animated--grow-in\" aria-labelledby=\"userDropdown\">");

                List<ViewWebMenuDeta> elementosMenu = menuMap.get(cabecera.getCodmen());
                for (ViewWebMenuDeta elemento : elementosMenu) {
                    resultado.append("<a id=\"asignarpermiso\" class=\"dropdown-item\" href=\"")
                            .append(elemento.getHtml())
                            .append("\">")
                            .append(elemento.getNommen())
                            .append("</a>");
                }
                resultado.append("</div></li>");
            }

            out.print(resultado.toString());
*/
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
