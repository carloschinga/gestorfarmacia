/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.login.servlet;

import pe.gestor.login.dao.EmpresaDAO;
import pe.gestor.login.dao.VistaUsuarioRolDAO;
import pe.gestor.login.dto.PlanillaEmpresa;
import pe.gestor.login.dto.VistaUsuarioRol;
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

/**
 *
 * @author USER
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginservlet"})
public class LoginServlet extends HttpServlet {

    private VistaUsuarioRolDAO vistaUsuarioRolDAO;
    private final EmpresaDAO empresaDAO;
    private final EntityManagerFactory emf;

    public LoginServlet() {
        EntityManagerFactory tempEmf = null;
        VistaUsuarioRolDAO tempVistaUsuarioRolDAO = null;
        EmpresaDAO tempEmpresaDAO = null;

        try {
            // Inicialización de las fábricas de entidades y los DAOs.
            tempEmf = Persistence.createEntityManagerFactory("com.mycompany_GestorFarmacia_war_1.0-SNAPSHOTPU");
            tempVistaUsuarioRolDAO = new VistaUsuarioRolDAO(tempEmf);
            tempEmpresaDAO = new EmpresaDAO(tempEmf);
        } catch (Exception ex) {
            System.out.println("Error al inicializar DAOs: " + ex.getMessage());
        }

        this.emf = tempEmf;
        this.vistaUsuarioRolDAO = tempVistaUsuarioRolDAO;
        this.empresaDAO = tempEmpresaDAO;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperamos los parámetros enviados en la solicitud
        String logiUsua = request.getParameter("logiUsua");
        String passUsua = request.getParameter("passUsua");

        // Verificamos si los DAOs han sido correctamente inicializados
        if (vistaUsuarioRolDAO == null || empresaDAO == null) {
            response.getWriter().print("Error de configuración del servidor");
            return;
        }

        // Validamos el usuario con el login y la contraseña
        VistaUsuarioRol usuario = vistaUsuarioRolDAO.validarUsuario(logiUsua, passUsua);

        // Configuramos la respuesta como tipo texto plano
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Si el usuario existe, procedemos con la lógica del login
        if (usuario != null) {
            // Cargar la empresa activa (por defecto)
            List<PlanillaEmpresa> listaEmpresa = empresaDAO.listaEmpresaActivas();

            if (listaEmpresa != null && !listaEmpresa.isEmpty()) {
                // Obtenemos la primera empresa activa
                PlanillaEmpresa empresa = listaEmpresa.get(0);

                // Creamos la sesión del usuario
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

                // Retornamos un mensaje de éxito
                out.print("success");
            } else {
                // Si no hay empresas activas
                out.print("No hay empresas activas");
            }
        } else {
            // Si el usuario no es válido
            out.print("error");
        }
    }
}
