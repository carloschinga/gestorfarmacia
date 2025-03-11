/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.servlet;

import java.io.BufferedReader;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Persistence;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.login.dao.LoginRolDAO;
import pe.gestor.login.dto.LoginRol;

/**
 *
 * @author USER
 */
@WebServlet(name = "RolServlet", urlPatterns = {"/rolservlet/*"})
public class RolServlet extends HttpServlet {
    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        try (PrintWriter out = response.getWriter()) {
            LoginRolDAO rolDAO = new LoginRolDAO(emf);
            if (pathInfo == null || pathInfo.equals("/")) {
                List<LoginRol> roles = rolDAO.findLoginRolEntities();
                JSONArray jsonArray = new JSONArray();
                for (LoginRol rol : roles) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiRol", rol.getCodiRol());
                    jsonObject.put("nombRol", rol.getNombRol());
                    jsonObject.put("actiRol", rol.getActiRol());
                    jsonObject.put("admiRol", rol.getAdmiRol());
                    jsonArray.put(jsonObject);
                }
                out.print(jsonArray.toString());
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                LoginRol rol = rolDAO.findLoginRol(id);
                if (rol != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiRol", rol.getCodiRol());
                    jsonObject.put("nombRol", rol.getNombRol());
                    jsonObject.put("actiRol", rol.getActiRol());
                    jsonObject.put("admiRol", rol.getAdmiRol());
                    out.print(jsonObject.toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(new JSONObject().put("error", "Rol no encontrado").toString());
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al obtener los datos").toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            BufferedReader reader = request.getReader();
            String json = reader.lines().collect(Collectors.joining());
            JSONObject jsonObject = new JSONObject(json);

            String nombRol = jsonObject.getString("nombRol");
            short actiRol = (short) jsonObject.getInt("actiRol");
            Boolean admiRol = jsonObject.optBoolean("admiRol", false);

            LoginRol rol = new LoginRol();
            rol.setNombRol(nombRol);
            rol.setActiRol(actiRol);
            rol.setAdmiRol(admiRol);

            LoginRolDAO rolDAO = new LoginRolDAO(emf);
            rolDAO.create(rol);

            response.getWriter().write(new JSONObject().put("message", "Rol creado exitosamente").toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al crear el rol: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            BufferedReader reader = request.getReader();
            String json = reader.lines().collect(Collectors.joining());
            JSONObject jsonObject = new JSONObject(json);

            int codiRol = jsonObject.getInt("codiRol");
            String nombRol = jsonObject.getString("nombRol");
            short actiRol = (short) jsonObject.getInt("actiRol");
            Boolean admiRol = jsonObject.optBoolean("admiRol", false);

            LoginRolDAO rolDAO = new LoginRolDAO(emf);
            LoginRol rol = rolDAO.findLoginRol(codiRol);
            if (rol == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error", "Rol no encontrado").toString());
                return;
            }

            rol.setNombRol(nombRol);
            rol.setActiRol(actiRol);
            rol.setAdmiRol(admiRol);
            rolDAO.edit(rol);

            response.getWriter().write(new JSONObject().put("message", "Rol actualizado exitosamente").toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al actualizar el rol: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        try {
            LoginRolDAO rolDAO = new LoginRolDAO(emf);
            if (pathInfo != null && pathInfo.length() > 1) {
                int id = Integer.parseInt(pathInfo.substring(1));
                rolDAO.destroy(id);
                response.getWriter().write(new JSONObject().put("message", "Rol eliminado exitosamente").toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new JSONObject().put("error", "Debe proporcionar un ID válido").toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al eliminar el rol").toString());
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        super.destroy();
    }
}
