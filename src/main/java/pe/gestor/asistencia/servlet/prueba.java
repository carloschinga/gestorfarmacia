package pe.gestor.asistencia.servlet;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class prueba {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // Obtenemos que dia es hoy
        DayOfWeek diaSemana = localDateTime.getDayOfWeek();
        String nombreDia = diaSemana.getDisplayName(TextStyle.FULL, new Locale("es", "PE"));
        System.out.println(nombreDia.toUpperCase());
    }
}
