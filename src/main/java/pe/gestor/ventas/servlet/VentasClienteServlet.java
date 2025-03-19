package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.gestor.ventas.dao.VentasClienteJpaController;
import pe.gestor.ventas.dto.VentasCliente;

@WebServlet(name = "VentasClienteServlet", urlPatterns = { "/ventasclienteservlet/*" })
public class VentasClienteServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasClienteJpaController clienteDAO;

    public VentasClienteServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.clienteDAO = new VentasClienteJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todos los clientes
                List<VentasCliente> clientes = clienteDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasCliente cliente : clientes) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiClie", cliente.getCodiClie());
                    jsonObject.put("codiTipoDoc", cliente.getCodiTipoDoc());
                    jsonObject.put("numeDoc", cliente.getNumeDoc());
                    jsonObject.put("fonoClie", cliente.getFonoClie());
                    jsonObject.put("mailClie", cliente.getMailClie());
                    jsonObject.put("direClie", cliente.getDireClie());
                    jsonObject.put("actiClie", cliente.getActiClie());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un cliente específico por codiClie
                String param = pathInfo.substring(1).trim();
                int codiClie = Integer.parseInt(param);

                VentasCliente cliente = clienteDAO.findVentasCliente(codiClie);

                if (cliente == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiClie", cliente.getCodiClie());
                jsonObject.put("codiTipoDoc", cliente.getCodiTipoDoc());
                jsonObject.put("numeDoc", cliente.getNumeDoc());
                jsonObject.put("fonoClie", cliente.getFonoClie());
                jsonObject.put("mailClie", cliente.getMailClie());
                jsonObject.put("direClie", cliente.getDireClie());
                jsonObject.put("actiClie", cliente.getActiClie());

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(jsonObject.toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter()
                    .write(new JSONObject().put("error", "Error al obtener los datos: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        try {
            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Validar y obtener los campos del JSON
            String codiTipoDoc = json.getString("codiTipoDoc");
            String numeDoc = json.getString("numeDoc");
            String fonoClie = json.getString("fonoClie");
            String mailClie = json.getString("mailClie");
            String direClie = json.getString("direClie");
            boolean actiClie = json.getBoolean("actiClie");

            // Crear instancia de VentasCliente
            VentasCliente cliente = new VentasCliente();
            cliente.setCodiTipoDoc(codiTipoDoc);
            cliente.setNumeDoc(numeDoc);
            cliente.setFonoClie(fonoClie);
            cliente.setMailClie(mailClie);
            cliente.setDireClie(direClie);
            cliente.setActiClie(actiClie);

            // Guardar cliente
            clienteDAO.create(cliente);

            // Respuesta de éxito
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(new JSONObject().put("success", true).toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter()
                    .write(new JSONObject().put("error", "Error al crear el registro: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        try {

            // Leer el cuerpo de la solicitud como un JSON
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            // Verificar si el cliente existe en la base de datos
            VentasCliente clienteExistente = clienteDAO.findVentasCliente(json.getInt("codiClie"));

            if (clienteExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error", "Cliente no encontrado").toString());
                return;
            }

            // Actualizar los datos del cliente existente
            clienteExistente.setCodiTipoDoc(json.getString("codiTipoDoc"));
            clienteExistente.setNumeDoc(json.getString("numeDoc"));
            clienteExistente.setFonoClie(json.getString("fonoClie"));
            clienteExistente.setMailClie(json.getString("mailClie"));
            clienteExistente.setDireClie(json.getString("direClie"));
            clienteExistente.setActiClie(json.getBoolean("actiClie"));

            // Intentar actualizar el cliente en la base de datos
            clienteDAO.edit(clienteExistente);

            // Responder con éxito
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);

            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new JSONObject().put("error", "Error al procesar la solicitud: " + e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                // Eliminar registro por ID
                int id = Integer.parseInt(pathInfo.substring(1));
                VentasCliente cliente = clienteDAO.findVentasCliente(id);
                cliente.setActiClie(false);
                clienteDAO.edit(cliente);
                response.getWriter()
                        .write(new JSONObject().put("message", "Registro eliminado exitosamente").toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new JSONObject().put("error", "Debe proporcionar un ID válido").toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new JSONObject().put("error", "Error al eliminar el registro: " + e.getMessage()).toString());
        }
    }
}