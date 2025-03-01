package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "HoraServidorServlet", urlPatterns = {"/horaServidor"})
public class HoraServidorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Formatear la fecha y hora del servidor en formato ISO 8601
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String horaActual = sdf.format(new Date());

        // Crear la respuesta JSON
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("hora", horaActual);

        // Escribir la respuesta JSON al cliente
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
        }
    }
}
