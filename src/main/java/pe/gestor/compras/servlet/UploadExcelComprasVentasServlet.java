/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.compras.servlet;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.math.BigDecimal;
import javax.servlet.annotation.WebServlet;
import pe.gestor.compras.dto.ComprasVentas;

/**
 *
 * @author USER
 */
@WebServlet(name = "UploadExcelComprasVentasServlet", urlPatterns = {"/uploadexcelcomprasventasservlet"})
public class UploadExcelComprasVentasServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;
    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().write("Error: No se seleccionó un archivo.");
            return;
        }
        EntityManager em = emf.createEntityManager();

        try (InputStream fileContent = filePart.getInputStream(); Workbook workbook = new XSSFWorkbook(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            em.getTransaction().begin();

            // Saltar la primera fila (encabezados)
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                ComprasVentas compra = new ComprasVentas();

                compra.setSede((int) row.getCell(0).getNumericCellValue());
                compra.setFechaVenta(row.getCell(1).getDateCellValue());
                compra.setUsuario(row.getCell(2).getStringCellValue());
                compra.setLaboratorio(row.getCell(3).getStringCellValue());
                compra.setCodigo(row.getCell(4).getStringCellValue());
                compra.setProducto(row.getCell(5).getStringCellValue());
                compra.setDocVenta(row.getCell(6).getStringCellValue());
                compra.setComprobante(row.getCell(7).getStringCellValue());
                compra.setPago(row.getCell(8).getStringCellValue());
                compra.setEstado(row.getCell(9).getStringCellValue());
                compra.setCantidadVendida((int) row.getCell(10).getNumericCellValue());
                compra.setCostoUnitario(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
                compra.setTotalCosto(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
                compra.setPrecioVenta(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()));
                compra.setDescuento(BigDecimal.valueOf(row.getCell(14).getNumericCellValue()));
                compra.setTotalVenta(BigDecimal.valueOf(row.getCell(15).getNumericCellValue()));
                compra.setGanancia(BigDecimal.valueOf(row.getCell(16).getNumericCellValue()));
                compra.setPorcentajeGanancia(BigDecimal.valueOf(row.getCell(17).getNumericCellValue()));
                compra.setStockActual((int) row.getCell(18).getNumericCellValue());

                em.persist(compra);
            }
            em.getTransaction().commit();

            response.getWriter().write("Archivo procesado y datos guardados correctamente.");
        } catch (Exception e) {
            response.getWriter().write("Error al procesar el archivo: " + e.getMessage());
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
