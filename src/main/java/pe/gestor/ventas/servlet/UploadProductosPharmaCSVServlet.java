package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import pe.gestor.ventas.dao.VentasProductosPharmaJpaController;
import pe.gestor.ventas.dto.VentasProductosPharma;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB - Se mantiene en memoria antes de escribir a disco
        maxFileSize = 1024 * 1024 * 10, // 10MB - Tamaño máximo del archivo
        maxRequestSize = 1024 * 1024 * 50 // 50MB - Tamaño máximo de la solicitud
)
@WebServlet(name = "UploadProductosPharmaExcelServlet", urlPatterns = { "/uploadproductospharmaexcelservlet" })
public class UploadProductosPharmaCSVServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UploadProductosPharmaCSVServlet.class.getName());
    private VentasProductosPharmaJpaController ventasProductosPharmaDAO;

    @Override
    public void init() throws ServletException {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
            ventasProductosPharmaDAO = new VentasProductosPharmaJpaController(emf);
            logger.info("Servlet inicializado correctamente.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar el servlet: " + e.getMessage(), e);
            throw new ServletException("Error al inicializar el servlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("excelFile");
        if (filePart != null) {
            try (InputStream fileContent = filePart.getInputStream()) {
                String fileName = filePart.getSubmittedFileName();
                logger.info("Procesando archivo: " + fileName);

                // Verificar si el archivo es Excel
                if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                    String errorMsg = "❌ Formato de archivo no soportado. Solo se permiten archivos .xls y .xlsx.";
                    logger.warning(errorMsg);
                    response.getWriter().write(errorMsg);
                    return;
                }

                // Crear el Workbook (Excel)
                Workbook workbook = fileName.endsWith(".xls") ? new HSSFWorkbook(fileContent)
                        : new XSSFWorkbook(fileContent);
                Sheet sheet = workbook.getSheetAt(0); // Obtener la primera hoja
                Row headerRow = sheet.getRow(0); // Obtener la fila de encabezados

                // Procesar cada fila del archivo Excel
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        try {
                            VentasProductosPharma producto = new VentasProductosPharma();

                            // Mapear las columnas del Excel a los campos del objeto
                            producto.setCodigo(getCellValue(row, headerRow, "Código Producto"));
                            producto.setNombre(getCellValue(row, headerRow, "Nombre Producto"));
                            producto.setStock(parseBigDecimal(getCellValue(row, headerRow, "Stock")));
                            producto.setStockMin(parseBigDecimal(getCellValue(row, headerRow, "Stock Minimo")));
                            producto.setPrecioCaja(parseBigDecimal(getCellValue(row, headerRow, "Costo por caja")));
                            producto.setPrecioCostoUnitario(
                                    parseBigDecimal(getCellValue(row, headerRow, "Costo Unitario")));
                            producto.setPvp1(parseBigDecimal(getCellValue(row, headerRow, "Precio venta unitario 1")));
                            producto.setPvp2(parseBigDecimal(getCellValue(row, headerRow, "Precio venta unitario 2")));
                            producto.setPrecioBlister(parseBigDecimal(getCellValue(row, headerRow, "Precio Blister")));
                            producto.setCategoria(getCellValue(row, headerRow, "Categoria id"));
                            producto.setLaboratorio(getCellValue(row, headerRow, "Laboratorio ID"));
                            producto.setLote(getCellValue(row, headerRow, "Indicador de lote"));
                            producto.setFechaVencimiento(parseDate(getCellValue(row, headerRow, "Fecha de Caducidad")));
                            producto.setPrincA(getCellValue(row, headerRow, "Principio activo"));

                            // Guardar el producto en la base de datos
                            ventasProductosPharmaDAO.create(producto);
                            // logger.info("Producto creado: " + producto.getCodigo());
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Error al procesar la fila " + i + ": " + e.getMessage(), e);
                        }
                    }
                }

                workbook.close();
                String successMsg = "Archivo Excel importado correctamente.";
                logger.info(successMsg);
                response.getWriter().write(successMsg);
            } catch (Exception e) {
                String errorMsg = "❌ Error al procesar el archivo Excel: " + e.getMessage();
                logger.log(Level.SEVERE, errorMsg, e);
                response.getWriter().write(errorMsg);
            }
        } else {
            String errorMsg = "❌ No se recibió el archivo Excel.";
            logger.warning(errorMsg);
            response.getWriter().write(errorMsg);
        }
    }

    private String getCellValue(Row row, Row headerRow, String columnName) {
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell headerCell = headerRow.getCell(i);
            if (headerCell != null && columnName.equals(headerCell.getStringCellValue())) {
                Cell cell = row.getCell(i);
                return (cell != null) ? cell.toString() : null;
            }
        }
        logger.warning("Columna no encontrada: " + columnName);
        return null;
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? new BigDecimal(value) : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Error al convertir a BigDecimal: " + value, e);
            return BigDecimal.ZERO;
        }
    }

    private Date parseDate(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? new SimpleDateFormat("yyyy-MM-dd").parse(value) : null;
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Error al convertir fecha: " + value, e);
            return null;
        }
    }
}