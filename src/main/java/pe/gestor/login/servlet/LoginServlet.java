/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.login.servlet;

import pe.gestor.login.dao.EmpresaDAO;
import pe.gestor.login.dto.Empresa;

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
import javax.servlet.http.HttpSession;
import pe.gestor.login.dao.VistaLoginUsuarioRolDAO;
import pe.gestor.login.dto.VistaLoginUsuarioRol;

/**
 *
 * @author USER
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginservlet"})
public class LoginServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;
    VistaLoginUsuarioRolDAO vistaLoginUsuarioRolDAO = null;
    EmpresaDAO empresaDAO = null;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            vistaLoginUsuarioRolDAO = new VistaLoginUsuarioRolDAO(emf);
            empresaDAO = new EmpresaDAO(emf);
        } catch (Exception ex) {
            System.out.println("Error al inicializar DAOs: " + ex.getMessage());
        }
    }

    public LoginServlet() {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String logiUsua = request.getParameter("logiUsua");
        String passUsua = request.getParameter("passUsua");

        if (vistaLoginUsuarioRolDAO == null || empresaDAO == null) {
            response.getWriter().print("Error de configuración del servidor");
            return;
        }

        VistaLoginUsuarioRol usuario = vistaLoginUsuarioRolDAO.validarUsuario(logiUsua, passUsua);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (usuario != null) {
            // Cargar la empresa por defecto
            List<Empresa> listaEmpresa = empresaDAO.listaEmpresaActivas();

            if (listaEmpresa != null && !listaEmpresa.isEmpty()) {
                Empresa empresa = listaEmpresa.get(0);

                HttpSession session = request.getSession();
                session.setAttribute("codiUsua", usuario.getCodiUsua());
                session.setAttribute("logiUsua", usuario.getLogiUsua());
                session.setAttribute("nombUsua", usuario.getNombUsua());
                session.setAttribute("codiRol", usuario.getCodiRol());
                session.setAttribute("nombRol", usuario.getNombRol());
                session.setAttribute("admiRol", usuario.getAdmiRol());
                session.setAttribute("codiEmpr", empresa.getCodiEmpr());
                session.setAttribute("nrucEmpr", empresa.getNrucEmpr());
                session.setAttribute("nombEmpr", empresa.getNombEmpr());
                session.setAttribute("logueado", "si");

                out.print("success");
            } else {
                out.print("No hay empresas activas");
            }
        } else {
            out.print("error");
        }
    }
}
