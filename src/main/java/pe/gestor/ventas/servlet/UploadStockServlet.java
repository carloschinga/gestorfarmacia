/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.ventas.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import pe.gestor.ventas.dto.VentasProductosStock;

/**
 *
 * @author USER
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "UploadStockServlet", urlPatterns = {"/uploadstockservlet"})
public class UploadStockServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "gestorFarmacia";
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = null;
        try {
            filePart = request.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                response.getWriter().write("No se ha seleccionado un archivo.");
                return;
            }
        } catch (Exception ex) {
            System.out.println("Error al obtener el archivo: " + ex.getMessage());
            response.getWriter().write("Error al obtener el archivo.");
            return;
        }
        String linemayor="";
        try (InputStream inputStream = filePart.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber <= 1) { // Omitimos las dos primeras líneas
                    continue;
                }
                linemayor="";
                linemayor=line;
                String[] data = line.split(",");
                if (data.length >= 14) {
                    VentasProductosStock stock = new VentasProductosStock();
                    stock.setCodigo(data[0].replaceAll("[\"%\\s]", ""));
                    stock.setCategoria(data[1].replaceAll("[\"%\\s]", ""));
                    stock.setLaboratorio(data[2].replaceAll("[\"%\\s]", ""));
                    stock.setNombreProducto(data[3].substring(1, 3).replaceAll("[\"%\\s]", ""));
                    stock.setPvp(new BigDecimal(data[4].replaceAll("[\"\\s]", "")));
                    stock.setStockSuc1(Integer.parseInt(data[5].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc2(Integer.parseInt(data[6].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc3(Integer.parseInt(data[7].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc4(Integer.parseInt(data[8].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc5(Integer.parseInt(data[9].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc6(Integer.parseInt(data[10].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc7(Integer.parseInt(data[11].replaceAll("[\"%\\s]", "")));
                    stock.setStockSuc8(Integer.parseInt(data[12].replaceAll("[\"%\\s]", "")));
                    stock.setStockTotal(Integer.parseInt(data[13].replaceAll("[\"%\\s]", "")));

                    em.persist(stock);
                }
            }

            tx.commit();
            em.close();
            response.getWriter().write("Archivo cargado correctamente.");
        } catch (Exception e) {
            System.out.println(linemayor);
            response.getWriter().write("Error al procesar el archivo: " + e.getMessage());
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
