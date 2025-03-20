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
import pe.gestor.ventas.dao.VentasStockLoteJpaController;
import pe.gestor.ventas.dto.VentasStockLote;

@WebServlet(name = "VentasStockLoteServlet", urlPatterns = { "/ventasstockloteservlet/*" })
public class VentasStockLoteServlet extends HttpServlet {

    private final EntityManagerFactory emf;
    private final VentasStockLoteJpaController stockLoteDAO;

    public VentasStockLoteServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.stockLoteDAO = new VentasStockLoteJpaController(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // Obtener todos los lotes de stock
                List<VentasStockLote> lotesStock = stockLoteDAO.findAllActive();

                JSONArray jsonArray = new JSONArray();
                for (VentasStockLote stockLote : lotesStock) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("codiStocLote", stockLote.getCodiStocLote());
                    jsonObject.put("codiProd", stockLote.getCodiProd());
                    jsonObject.put("codiAlmc", stockLote.getCodiAlmc());
                    jsonObject.put("lote", stockLote.getLote());
                    jsonObject.put("fechVent", new SimpleDateFormat("yyyy-MM-dd").format(stockLote.getFechVent()));
                    jsonObject.put("cantEnte", stockLote.getCantEnte());
                    jsonObject.put("cantFrac", stockLote.getCantFrac());
                    jsonObject.put("costoEnte", stockLote.getCostoEnte());
                    jsonObject.put("costoFrac", stockLote.getCostoFrac());
                    jsonObject.put("actiStocLote", stockLote.getActiStocLote());
                    jsonArray.put(jsonObject);
                }

                response.getWriter().write(jsonArray.toString());
            } else {
                // Obtener un lote de stock específico por codiStocLote
                String param = pathInfo.substring(1).trim();
                int codiStocLote = Integer.parseInt(param);

                VentasStockLote stockLote = stockLoteDAO.findVentasStockLote(codiStocLote);

                if (stockLote == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(new JSONObject().put("success", false).toString());
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codiStocLote", stockLote.getCodiStocLote());
                jsonObject.put("codiProd", stockLote.getCodiProd());
                jsonObject.put("codiAlmc", stockLote.getCodiAlmc());
                jsonObject.put("lote", stockLote.getLote());
                jsonObject.put("fechVent", new SimpleDateFormat("yyyy-MM-dd").format(stockLote.getFechVent()));
                jsonObject.put("cantEnte", stockLote.getCantEnte());
                jsonObject.put("cantFrac", stockLote.getCantFrac());
                jsonObject.put("costoEnte", stockLote.getCostoEnte());
                jsonObject.put("costoFrac", stockLote.getCostoFrac());
                jsonObject.put("actiStocLote", stockLote.getActiStocLote());

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
            String codiProd = json.getString("codiProd");
            int codiAlmc = json.getInt("codiAlmc");
            String lote = json.getString("lote");
            String fechVent = json.getString("fechVent");
            int cantEnte = json.getInt("cantEnte");
            int cantFrac = json.getInt("cantFrac");
            int costoEnte = json.getInt("costoEnte");
            int costoFrac = json.getInt("costoFrac");

            // Crear instancia de VentasStockLote
            VentasStockLote stockLote = new VentasStockLote();
            stockLote.setCodiProd(codiProd);
            stockLote.setCodiAlmc(codiAlmc);
            stockLote.setLote(lote);
            stockLote.setFechVent(new SimpleDateFormat("yyyy-MM-dd").parse(fechVent));
            stockLote.setCantEnte(cantEnte);
            stockLote.setCantFrac(cantFrac);
            stockLote.setCostoEnte(costoEnte);
            stockLote.setCostoFrac(costoFrac);
            stockLote.setActiStocLote(true);

            // Guardar lote de stock
            stockLoteDAO.create(stockLote);

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

            // Obtener el ID del lote de stock del JSON
            int codiStocLote = json.getInt("codiStocLote");

            // Verificar si el lote de stock existe en la base de datos
            VentasStockLote stockLoteExistente = stockLoteDAO.findVentasStockLote(codiStocLote);

            if (stockLoteExistente == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(new JSONObject().put("error", "Lote de stock no encontrado").toString());
                return;
            }

            // Actualizar los datos del lote de stock existente
            stockLoteExistente.setCodiProd(json.getString("codiProd"));
            stockLoteExistente.setCodiAlmc(json.getInt("codiAlmc"));
            stockLoteExistente.setLote(json.getString("lote"));
            stockLoteExistente.setFechVent(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("fechVent")));
            stockLoteExistente.setCantEnte(json.getInt("cantEnte"));
            stockLoteExistente.setCantFrac(json.getInt("cantFrac"));
            stockLoteExistente.setCostoEnte(json.getInt("costoEnte"));
            stockLoteExistente.setCostoFrac(json.getInt("costoFrac"));

            // Intentar actualizar el lote de stock en la base de datos
            stockLoteDAO.edit(stockLoteExistente);

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
                // Obtener el ID del lote de stock de la URL
                int id = Integer.parseInt(pathInfo.substring(1));

                // Realizar un borrado lógico (desactivar el lote de stock)
                VentasStockLote stockLote = stockLoteDAO.findVentasStockLote(id);
                if (stockLote != null) {
                    stockLote.setActiStocLote(false); // Desactivar
                    stockLoteDAO.edit(stockLote);
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