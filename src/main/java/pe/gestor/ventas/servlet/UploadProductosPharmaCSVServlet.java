/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pe.gestor.ventas.dao.VentasProductosPharmaJpaController;
import pe.gestor.ventas.dto.VentasProductosPharma;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB - Se mantiene en memoria antes de escribir a disco
        maxFileSize = 1024 * 1024 * 10, // 10MB - Tamaño máximo del archivo
        maxRequestSize = 1024 * 1024 * 50 // 50MB - Tamaño máximo de la solicitud
)
@WebServlet(name = "UploadProductosPharmaCSVServlet", urlPatterns = {"/uploadproductospharmacsvservlet"})
public class UploadProductosPharmaCSVServlet extends HttpServlet {

    private VentasProductosPharmaJpaController ventasProductosPharmaDAO;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        ventasProductosPharmaDAO = new VentasProductosPharmaJpaController(emf);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("csvFile");
        if (filePart != null) {
            try (InputStream fileContent = filePart.getInputStream()) {
                Reader reader = new InputStreamReader(fileContent);
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

                for (CSVRecord record : records) {
                    try {
                        VentasProductosPharma producto = new VentasProductosPharma();

                        producto.setCodigo(record.get("Codigo"));
                        producto.setCategoria(record.get("Categoria"));
                        producto.setLaboratorio(getValueOrDefault(record.get("Laboratorio"), "Desconocido"));
                        producto.setNombre(record.get("Nombre"));
                        producto.setPrecioCaja(parseBigDecimal(record.get("Precio Caja")));
                        producto.setPvp1(parseBigDecimal(record.get("PVP1")));
                        producto.setPvp2(parseBigDecimal(record.get("PVP2")));
                        producto.setPrecioCostoUnitario(parseBigDecimal(record.get("Precio Costo Unitario")));
                        producto.setPrecioBlister(parseBigDecimal(record.get("Precio Blister")));
                        producto.setStock(parseBigDecimal(record.get("Stock")));
                        producto.setStockMin(parseBigDecimal(getColumnValue(record, "Stock Min", "Stock min", "stock_min")));
                        producto.setLote(record.get("Lote"));
                        producto.setFechaVencimiento(parseDate(getColumnValue(record, "Fecha Vencimiento", "F. Vencimiento")));
                       producto.setPrincA(getColumnValue(record, "Princ A", "Prin A."));

                        ventasProductosPharmaDAO.create(producto);
                    } catch (Exception e) {
                        System.err.println("Error al procesar el registro: " + e.getMessage());
                    }
                }
                response.getWriter().write("Archivo CSV importado correctamente.");
            } catch (Exception e) {
                response.getWriter().write("❌ Error al procesar el archivo CSV: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            response.getWriter().write("❌ No se recibió el archivo CSV.");
        }
    }

    private String getColumnValue(CSVRecord record, String... possibleNames) {
        for (String name : possibleNames) {
            if (record.isMapped(name)) {
                return record.get(name);
            }
        }
        return null;
    }

    private String getValueOrDefault(String value, String defaultValue) {
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? new BigDecimal(value) : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir a BigDecimal: " + value);
            return BigDecimal.ZERO;
        }
    }

    private Date parseDate(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? new SimpleDateFormat("yyyy-MM-dd").parse(value) : null;
        } catch (ParseException e) {
            System.err.println("Error al convertir fecha: " + value);
            return null;
        }
    }

}
