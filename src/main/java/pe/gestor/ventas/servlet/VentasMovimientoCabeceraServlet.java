package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import pe.gestor.ventas.dao.VentasMovimientoCabeceraJpaController;
import pe.gestor.ventas.dto.VentasMovimientoCabecera;

@WebServlet(name = "VentasMovimientoCabeceraServlet", urlPatterns = { "/ventasmovimientocabeceraservlet/*" })
public class VentasMovimientoCabeceraServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasMovimientoCabeceraJpaController movimientoCabeceraDAO;

    public VentasMovimientoCabeceraServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.movimientoCabeceraDAO = new VentasMovimientoCabeceraJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todos los movimientos de cabecera
                List<VentasMovimientoCabecera> movimientosCabecera = movimientoCabeceraDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasMovimientoCabecera movimientoCabecera : movimientosCabecera) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiMoviCabe", movimientoCabecera.getCodiMoviCabe());
                    jsonObject.put("codiVentCabe", movimientoCabecera.getCodiVentCabe());
                    jsonObject.put("codiTipoMovi", movimientoCabecera.getCodiTipoMovi());
                    jsonObject.put("codiAlmcOrign", movimientoCabecera.getCodiAlmcOrign());
                    jsonObject.put("codiAlmcDest", movimientoCabecera.getCodiAlmcDest());
                    jsonObject.put("fechMovi",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimientoCabecera.getFechMovi()));
                    jsonObject.put("codiEstd", movimientoCabecera.getCodiEstd());
                    jsonObject.put("obsvMovi", movimientoCabecera.getObsvMovi());
                    jsonObject.put("actiMovi", movimientoCabecera.getActiMovi());
                    jsonObject.put("codiUsuaCrea", movimientoCabecera.getCodiUsuaCrea());
                    jsonObject.put("fechUsuaCrea",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimientoCabecera.getFechUsuaCrea()));
                    jsonObject.put("codiModiCrea", movimientoCabecera.getCodiModiCrea());
                    jsonObject.put("fechModiCrea",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimientoCabecera.getFechModiCrea()));
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un movimiento de cabecera específico por codiMoviCabe
                String param = pathInfo.substring(1).trim();
                int codiMoviCabe = Integer.parseInt(param);

                VentasMovimientoCabecera movimientoCabecera = movimientoCabeceraDAO
                        .findVentasMovimientoCabecera(codiMoviCabe);

                if (movimientoCabecera == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiMoviCabe", movimientoCabecera.getCodiMoviCabe());
                jsonObject.put("codiVentCabe", movimientoCabecera.getCodiVentCabe());
                jsonObject.put("codiTipoMovi", movimientoCabecera.getCodiTipoMovi());
                jsonObject.put("codiAlmcOrign", movimientoCabecera.getCodiAlmcOrign());
                jsonObject.put("codiAlmcDest", movimientoCabecera.getCodiAlmcDest());
                jsonObject.put("fechMovi",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimientoCabecera.getFechMovi()));
                jsonObject.put("codiEstd", movimientoCabecera.getCodiEstd());
                jsonObject.put("obsvMovi", movimientoCabecera.getObsvMovi());
                jsonObject.put("actiMovi", movimientoCabecera.getActiMovi());
                jsonObject.put("codiUsuaCrea", movimientoCabecera.getCodiUsuaCrea());
                jsonObject.put("fechUsuaCrea",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimientoCabecera.getFechUsuaCrea()));
                jsonObject.put("codiModiCrea", movimientoCabecera.getCodiModiCrea());
                jsonObject.put("fechModiCrea",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimientoCabecera.getFechModiCrea()));

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
            int codiVentCabe = json.getInt("codiVentCabe");
            int codiTipoMovi = json.getInt("codiTipoMovi");
            int codiAlmcOrign = json.getInt("codiAlmcOrign");
            int codiAlmcDest = json.getInt("codiAlmcDest");
            String fechMovi = json.getString("fechMovi");
            int codiEstd = json.getInt("codiEstd");
            String obsvMovi = json.getString("obsvMovi");
            // boolean actiMovi = json.getBoolean("actiMovi");
            int codiUsuaCrea = json.getInt("codiUsuaCrea");
            String fechUsuaCrea = json.getString("fechUsuaCrea");
            int codiModiCrea = json.getInt("codiModiCrea");
            String fechModiCrea = json.getString("fechModiCrea");

            // Crear instancia de VentasMovimientoCabecera
            VentasMovimientoCabecera movimientoCabecera = new VentasMovimientoCabecera();
            movimientoCabecera.setCodiVentCabe(codiVentCabe);
            movimientoCabecera.setCodiTipoMovi(codiTipoMovi);
            movimientoCabecera.setCodiAlmcOrign(codiAlmcOrign);
            movimientoCabecera.setCodiAlmcDest(codiAlmcDest);
            movimientoCabecera.setFechMovi(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechMovi));
            movimientoCabecera.setCodiEstd(codiEstd);
            movimientoCabecera.setObsvMovi(obsvMovi);
            movimientoCabecera.setActiMovi(true);
            movimientoCabecera.setCodiUsuaCrea(codiUsuaCrea);
            movimientoCabecera.setFechUsuaCrea(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechUsuaCrea));
            movimientoCabecera.setCodiModiCrea(codiModiCrea);
            movimientoCabecera.setFechModiCrea(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechModiCrea));

            // Guardar movimiento de cabecera
            movimientoCabeceraDAO.create(movimientoCabecera);

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

            // Obtener el ID del movimiento de cabecera del JSON
            int codiMoviCabe = json.getInt("codiMoviCabe");

            // Verificar si el movimiento de cabecera existe en la base de datos
            VentasMovimientoCabecera movimientoCabeceraExistente = movimientoCabeceraDAO.findVentasMovimientoCabecera(codiMoviCabe);

            if (movimientoCabeceraExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter()
                        .write(new JSONObject().put("error", "Movimiento de cabecera no encontrado").toString());
                return;
            }

            // Actualizar los datos del movimiento de cabecera existente
            movimientoCabeceraExistente.setCodiVentCabe(json.getInt("codiVentCabe"));
            movimientoCabeceraExistente.setCodiTipoMovi(json.getInt("codiTipoMovi"));
            movimientoCabeceraExistente.setCodiAlmcOrign(json.getInt("codiAlmcOrign"));
            movimientoCabeceraExistente.setCodiAlmcDest(json.getInt("codiAlmcDest"));
            movimientoCabeceraExistente
                    .setFechMovi(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("fechMovi")));
            movimientoCabeceraExistente.setCodiEstd(json.getInt("codiEstd"));
            movimientoCabeceraExistente.setObsvMovi(json.getString("obsvMovi"));
            movimientoCabeceraExistente.setCodiUsuaCrea(json.getInt("codiUsuaCrea"));
            movimientoCabeceraExistente
                    .setFechUsuaCrea(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("fechUsuaCrea")));
            movimientoCabeceraExistente.setCodiModiCrea(json.getInt("codiModiCrea"));
            movimientoCabeceraExistente
                    .setFechModiCrea(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("fechModiCrea")));

            // Intentar actualizar el movimiento de cabecera en la base de datos
            movimientoCabeceraDAO.edit(movimientoCabeceraExistente);

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
                // Obtener el ID del movimiento de cabecera de la URL
                int id = Integer.parseInt(pathInfo.substring(1));

                // Realizar un borrado lógico (desactivar el movimiento de cabecera)
                VentasMovimientoCabecera movimientoCabecera = movimientoCabeceraDAO.findVentasMovimientoCabecera(id);
                if (movimientoCabecera != null) {
                    movimientoCabecera.setActiMovi(false); // Desactivar
                    movimientoCabeceraDAO.edit(movimientoCabecera);
                }

                response.getWriter()
                        .write(new JSONObject().put("message", "Registro desactivado exitosamente").toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(new JSONObject().put("error", "Debe proporcionar un ID válido").toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new JSONObject().put("error", "Error al desactivar el registro: " + e.getMessage()).toString());
        }
    }
}