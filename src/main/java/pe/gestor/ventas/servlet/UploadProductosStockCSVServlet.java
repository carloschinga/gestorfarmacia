package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
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
import pe.gestor.ventas.dao.VentasProductosStockJpaController;
import pe.gestor.ventas.dto.VentasProductosStock;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB - Se mantiene en memoria antes de escribir a disco
        maxFileSize = 1024 * 1024 * 10, // 10MB - Tamaño máximo del archivo
        maxRequestSize = 1024 * 1024 * 50 // 50MB - Tamaño máximo de la solicitud
)
@WebServlet(name = "UploadProductosStockCSVServlet", urlPatterns = {"/uploadproductosstockcsvservlet"})
public class UploadProductosStockCSVServlet extends HttpServlet {

    private VentasProductosStockJpaController ventasProductosStockDAO;

    @Override
    public void init() throws ServletException {
        // Inicializa el EntityManagerFactory y el controlador JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        ventasProductosStockDAO = new VentasProductosStockJpaController(emf);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("csvFile");
        if (filePart != null) {
            try (InputStream fileContent = filePart.getInputStream()) {
                Reader reader = new InputStreamReader(fileContent);
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

                // Procesar cada registro
                for (CSVRecord record : records) {
                    try {
                        VentasProductosStock producto = new VentasProductosStock();

                        // Mapea los valores de las columnas
                        producto.setCodigo(record.get("Codigo"));
                        producto.setCategoria(record.get("Categoria"));

                        // Validar que el campo Laboratorio no esté vacío
                        String laboratorio = record.get("Laboratorio");
                        if (laboratorio == null || laboratorio.trim().isEmpty()) {
                            producto.setLaboratorio("Desconocido"); // O asigna un valor por defecto
                        } else {
                            producto.setLaboratorio(laboratorio);
                        }

                        producto.setNombreProducto(record.get("Nombre"));

                        // Manejo de PVP1 y valores numéricos con control de errores
                        try {
                            producto.setPvp(new BigDecimal(record.get("PVP1")));
                        } catch (NumberFormatException e) {
                            System.err.println("Error al convertir PVP1: " + record.get("PVP1"));
                            producto.setPvp(BigDecimal.ZERO); // Valor por defecto
                        }

                        // Procesar los stocks, asegurando que el valor sea numérico
                        producto.setStockSuc1(parseIntegerWithDefault(record.get("stock suc1")));
                        producto.setStockSuc2(parseIntegerWithDefault(record.get("stock suc2")));
                        producto.setStockSuc3(parseIntegerWithDefault(record.get("stock suc3")));
                        producto.setStockSuc4(parseIntegerWithDefault(record.get("stock suc4")));
                        producto.setStockSuc5(parseIntegerWithDefault(record.get("stock suc5")));
                        producto.setStockSuc6(parseIntegerWithDefault(record.get("stock suc6")));
                        producto.setStockSuc7(parseIntegerWithDefault(record.get("stock suc7")));
                        producto.setStockSuc8(parseIntegerWithDefault(record.get("stock suc8")));
                        producto.setStockTotal(parseIntegerWithDefault(record.get("Stock Total")));

                        // Guardar la entidad en la base de datos
                        ventasProductosStockDAO.create(producto);
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

    // Método de utilidad para parsear enteros con un valor predeterminado (0 si no es válido)
    private Integer parseIntegerWithDefault(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value) : 0; // Devuelve 0 si el valor no es numérico o está vacío
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el valor a entero: " + value);
            return 0; // Devuelve 0 si hay un error en la conversión
        }
    }
}
