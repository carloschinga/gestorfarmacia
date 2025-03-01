package pe.gestor.ventas.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.google.gson.Gson;
import pe.gestor.asistencia.dao.exceptions.PreexistingEntityException;
import pe.gestor.ventas.dao.VentasProductosDAO;
import pe.gestor.ventas.dto.VentasProductos;

@WebServlet("/VentasProductosServlet")
@MultipartConfig
public class VentasProductosServlet extends HttpServlet {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestorFarmacia");
    private VentasProductosDAO ventasProductosDAO = new VentasProductosDAO(emf);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Crear un nuevo producto
            createProducto(request, response);
        }
    }

    // Crear un nuevo producto
    private void createProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String codigo = request.getParameter("codigo");
            String categoria = request.getParameter("categoria");
            String laboratorio = request.getParameter("laboratorio");
            String nombre = request.getParameter("nombre");
            BigDecimal precioCaja = new BigDecimal(request.getParameter("precioCaja"));
            Integer stock = Integer.valueOf(request.getParameter("stock"));
            Integer stockMin = Integer.valueOf(request.getParameter("stockMin"));
            String lote = request.getParameter("lote");

            VentasProductos producto = new VentasProductos();
            producto.setCodigo(codigo);
            producto.setCategoria(categoria);
            producto.setLaboratorio(laboratorio);
            producto.setNombre(nombre);
            producto.setPrecioCaja(precioCaja);
            producto.setStock(stock);
            producto.setStockMin(stockMin);
            producto.setLote(lote);

            ventasProductosDAO.create(producto);
            response.sendRedirect("VentasProductos?action=list");
        } catch (PreexistingEntityException e) {
            request.setAttribute("errorMessage", "El producto ya existe.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error al crear el producto.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    
   
}
