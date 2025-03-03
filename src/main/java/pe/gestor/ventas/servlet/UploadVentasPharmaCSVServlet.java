/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import pe.gestor.ventas.dao.VentasPharmaJpaController;
import pe.gestor.ventas.dto.VentasPharma;

/**
 *
 * @author USER
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB - Se mantiene en memoria antes de escribir a disco
        maxFileSize = 1024 * 1024 * 10, // 10MB - Tamaño máximo del archivo
        maxRequestSize = 1024 * 1024 * 50 // 50MB - Tamaño máximo de la solicitud
)
@WebServlet(name = "UploadVentasPharmaCSVServlet", urlPatterns = {"/uploadventaspharmacsvservlet"})
public class UploadVentasPharmaCSVServlet extends HttpServlet {

    private VentasPharmaJpaController ventasPharmaDAO;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        ventasPharmaDAO = new VentasPharmaJpaController(emf);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Part filePart = request.getPart("csvFile");
    String sede = request.getParameter("sede"); // Captura la sede

    if (filePart != null) {
        try (InputStream fileContent = filePart.getInputStream()) {
            Reader reader = new InputStreamReader(fileContent);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                try {
                    // Evita insertar registros de totales (generalmente marcados con "Total", "Resumen", etc.)
                    if (getColumnValue(record, "Doc Venta", "Doc. Venta").equalsIgnoreCase("Totales") ) {
                        continue; // Salta el registro si es un total
                    }
                    
                    VentasPharma venta = new VentasPharma();
                    venta.setCodiSede(Integer.parseInt(sede));
                    venta.setFechaVenta(parseDate(record.get("Fecha Venta")));
                    venta.setUsuario(record.get("Usuario"));
                    venta.setLaboratorio(getColumnValue(record, "Laboratorio", "Lab."));
                    venta.setCodigo(record.get("Codigo"));
                    venta.setProducto(record.get("Producto"));
                    venta.setDocVenta(getColumnValue(record, "Doc Venta", "Doc. Venta"));
                    venta.setComprobante(record.get("Comprobante"));
                    venta.setPago(record.get("Pago"));
                    venta.setEstado(record.get("Estado"));
                    venta.setCantidadVendida(parseInteger(getColumnValue(record, "Cantidad Vendida", "C. Vendida")));
                    venta.setCostoUnitario(parseBigDecimal(record.get("Costo Unitario")));
                    venta.setPrecioVenta(parseBigDecimal(record.get("Precio Venta")));
                    venta.setStockActual(parseInteger(record.get("Stock Actual")));

                    // Solo se inserta si no es un total
                    ventasPharmaDAO.create(venta);
                } catch (Exception e) {
                    System.err.println("Error al procesar el registro: " + e.getMessage());
                }
            }
            response.getWriter().write("Archivo CSV importado correctamente.");
        } catch (Exception e) {
            response.getWriter().write("\u274C Error al procesar el archivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        response.getWriter().write("\u274C No se recibi\u00f3 el archivo CSV.");
    }
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

    private Integer parseInteger(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir a Integer: " + value);
            return null;
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
    
    private String getColumnValue(CSVRecord record, String... possibleNames) {
    for (String name : possibleNames) {
        if (record.isMapped(name)) {
            return record.get(name);
        }
    }
    return null; // Devuelve null si ninguna columna coincide
}

}
