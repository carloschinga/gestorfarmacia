/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.gestor.compras.servlet;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.gestor.compras.dao.ComprasOcDAO;
import pe.gestor.compras.dao.ComprasOcdDAO;
import pe.gestor.compras.dto.ComprasOc;
import pe.gestor.compras.dto.ComprasOcDTO;
import pe.gestor.compras.dto.ComprasOcd;
import pe.gestor.compras.dto.ComprasOcdDTO;

/**
 *
 * @author USER
 */
@WebServlet(name = "OcHtServlet", urlPatterns = {"/ochtservlet"})
public class OcHtServlet extends HttpServlet {

    private final ComprasOcDAO comprasOcDAO;
    private final ComprasOcdDAO comprasOcdDAO;
    private final EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();

    }

    public OcHtServlet() {
        this.emf = Persistence.createEntityManagerFactory("gestorFarmacia");
        this.comprasOcDAO = new ComprasOcDAO(emf);
        this.comprasOcdDAO = new ComprasOcdDAO(emf);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = comprasOcDAO.getEntityManager();
        try {
            HttpSession session = request.getSession();
            int codiUsua = (int) session.getAttribute("codiUsua");
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            ComprasOcDTO data = gson.fromJson(reader, ComprasOcDTO.class);

            em.getTransaction().begin();
            ComprasOc orden = new ComprasOc();
            orden.setCodiProv(data.getCodiProv());
            orden.setFechOC(java.sql.Date.valueOf(data.getFechOC()));
            orden.setCodiEstdOC(1); // Estado por defecto
            orden.setCodiUsuaRegi(codiUsua); // Usuario de prueba
            orden.setFechUsuaRegi(new Date());
            orden.setActiOC(Short.parseShort("1"));
            comprasOcDAO.create(orden);

            for (ComprasOcdDTO detalle : data.getDetalles()) {
                ComprasOcd item = new ComprasOcd();
                item.setCodiOC(orden.getCodiOC());
                item.setCodiProd(detalle.getCodiProd());
                item.setNombProd(detalle.getNombProd());
                item.setCantiProd(detalle.getCantiProd());
                item.setPrecProd(detalle.getPrecProd());
                item.setSubtProd(detalle.getSubtProd());
                item.setIgvProd(detalle.getIgvProd());
                item.setTotaProd(detalle.getTotaProd());
                comprasOcdDAO.create(item);
            }
            em.getTransaction().commit();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Orden de Compra generada de número: " + orden.getCodiOC());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al registrar la Orden de Compra: " + e.getMessage());
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
