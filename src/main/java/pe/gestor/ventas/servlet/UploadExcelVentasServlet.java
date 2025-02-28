/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.ventas.servlet;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "UploadExcelVentasServlet", urlPatterns = {"/uploadexcelventasservlet"})
public class UploadExcelVentasServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("Este servlet solo acepta peticiones POST.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean importo = true;
        String mensaje = "";
        Part filePart = request.getPart("file");
        String sede = request.getParameter("sede"); // Captura la sede

        if (sede == null || sede.isEmpty()) {
            response.getWriter().write("Error: No se seleccionó una sede.");
            return;
        }

        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().write("Error: No se seleccionó un archivo.");
            return;
        }

        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().write("Error: No se seleccionó un archivo.");
            return;
        }
        EntityManager em = emf.createEntityManager();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try (InputStream fileContent = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            em.getTransaction().begin();

            // Saltar la primera fila (encabezados)
            if (rowIterator.hasNext()) {
                rowIterator.next();
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                VentasPharma compra = new VentasPharma();

                try {
                    String dateString = row.getCell(0).getStringCellValue();

                    compra.setSede(Integer.parseInt(sede));
                    Date date = formatter.parse(dateString);
                    compra.setFechaVenta(date);
                    compra.setUsuario(row.getCell(1).getStringCellValue());
                     try {
                    compra.setLaboratorio(row.getCell(2).getStringCellValue());
                     } catch (Exception ex) {
                         compra.setLaboratorio("");
                     }
                    try {
                        compra.setCodigo(row.getCell(3).getStringCellValue());
                    } catch (Exception ex) {
                        try {
                            double codigo = row.getCell(3).getNumericCellValue();
                            compra.setCodigo(String.valueOf(codigo));
                        } catch (Exception exx) {
                            importo = false;
                            mensaje = exx.getMessage();
                            break;
                        }
                    }
                    compra.setProducto(row.getCell(4).getStringCellValue());
                    compra.setDocVenta(row.getCell(5).getStringCellValue());
                    compra.setComprobante(row.getCell(6).getStringCellValue());
                    compra.setPago(row.getCell(7).getStringCellValue());
                    compra.setEstado(row.getCell(8).getStringCellValue());
                    compra.setCantidadVendida((int) row.getCell(9).getNumericCellValue());
                    compra.setCostoUnitario(BigDecimal.valueOf(row.getCell(10).getNumericCellValue()));
                    compra.setTotalCosto(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
                    compra.setPrecioVenta(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
                    compra.setDescuento(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));
                    compra.setTotalVenta(BigDecimal.valueOf(row.getCell(14).getNumericCellValue()));
                    compra.setGanancia(BigDecimal.valueOf(row.getCell(15).getNumericCellValue()));
                    compra.setPorcentajeGanancia(BigDecimal.valueOf(row.getCell(16).getNumericCellValue()));
                    compra.setStockActual((int) row.getCell(17).getNumericCellValue());
                    em.persist(compra);
                } catch (java.lang.NullPointerException nullex) {
                    //no hay mas registros
                } catch (Exception ex) {

                    importo = false;
                    mensaje = ex.getMessage();
                    break;
                    //validar si es error o es ultimo registro.
                }

            }

            if (importo) {
                em.getTransaction().commit();
                response.getWriter().write("{\"resultado\":\"ok\",\"mensaje\":\"Archivo procesado y datos guardados correctamente.\"}");
            } else {
                response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"Error al al procesar el archivo: " + mensaje +" \"}");
            }

        } catch (Exception e) {
            response.getWriter().write("{\"resultado\":\"error\",\"mensaje\":\"Error al al procesar el archivo: " + e.getMessage() +" \"}");
        } finally {
            em.close();

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
