/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.compras.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.compras.dao.ComprasProveedorDAO;
import pe.gestor.compras.dto.ComprasProveedor;

/**
 *
 * @author USER
 */
@WebServlet(name = "ProveedorServlet", urlPatterns = {"/proveedorservlet"})
public class ProveedorServlet extends HttpServlet {

    private final ComprasProveedorDAO comprasProveedorDAO;
    private final EntityManagerFactory emf;

    public ProveedorServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.comprasProveedorDAO = new ComprasProveedorDAO(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<ComprasProveedor> proveedores = comprasProveedorDAO.findComprasProveedorEntities();
                JSONArray jsonArray = new JSONArray();
                for (ComprasProveedor proveedor : proveedores) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiProv", proveedor.getCodiProv());
                    jsonObject.put("nrucProv", proveedor.getNrucProv());
                    jsonObject.put("nombProv", proveedor.getNombProv());
                    jsonObject.put("direProv", proveedor.getDireProv());
                    jsonObject.put("celuProv", proveedor.getCeluProv());
                    jsonObject.put("obsvProv", proveedor.getObsvProv());
                    jsonObject.put("estdProv", proveedor.getEstdProv());
                    jsonArray.put(jsonObject);
                }
                response.getWriter().write(jsonArray.toString());
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                ComprasProveedor proveedor = comprasProveedorDAO.findComprasProveedor(id);
                if (proveedor != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiProv", proveedor.getCodiProv());
                    jsonObject.put("nrucProv", proveedor.getNrucProv());
                    jsonObject.put("nombProv", proveedor.getNombProv());
                    jsonObject.put("direProv", proveedor.getDireProv());
                    jsonObject.put("celuProv", proveedor.getCeluProv());
                    jsonObject.put("obsvProv", proveedor.getObsvProv());
                    jsonObject.put("estdProv", proveedor.getEstdProv());
                    response.getWriter().write(jsonObject.toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("error", "Proveedor no encontrado").toString());
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al obtener los datos: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            BufferedReader reader = request.getReader();
            JSONObject jsonObject = new JSONObject(reader.lines().collect(Collectors.joining()));

            int codiProv = jsonObject.getInt("codiProv");
            String nrucProv = jsonObject.getString("nrucProv");
            String nombProv = jsonObject.getString("nombProv");
            String direProv = jsonObject.getString("direProv");
            String celuProv = jsonObject.getString("celuProv");
            String obsvProv = jsonObject.getString("obsvProv");
            short estdProv = (short) jsonObject.getInt("estdProv");

            ComprasProveedor proveedor = new ComprasProveedor(codiProv, nrucProv, nombProv, direProv, celuProv, obsvProv, estdProv);
            comprasProveedorDAO.edit(proveedor);

            response.getWriter().write(new JSONObject().put("message", "Registro actualizado exitosamente").toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al actualizar el registro: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                int id = Integer.parseInt(pathInfo.substring(1));
                comprasProveedorDAO.destroy(id);
                response.getWriter().write(new JSONObject().put("message", "Registro eliminado exitosamente").toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new JSONObject().put("error", "Debe proporcionar un ID válido").toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", "Error al eliminar el registro: " + e.getMessage()).toString());
        }
    }

}
