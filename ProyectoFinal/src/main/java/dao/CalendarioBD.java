package dao;

import com.toedter.calendar.JCalendar;
import controlador.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author martin
 */
public class CalendarioBD {

    // Método para mostrar el calendario y marcar los eventos
    public void mostrarCalendario() {
        JInternalFrame ventanaCalendario = new JInternalFrame("Calendario", true, true, true, true);
        ventanaCalendario.setSize(250, 200);
        ventanaCalendario.setLayout(new FlowLayout());

        // Creamos un JCalendar
        JCalendar calendario = new JCalendar();
        calendario.setPreferredSize(new Dimension(200, 150));

        // Crear un conjunto para almacenar las fechas con eventos
        Set<Date> fechasConEventos = new HashSet<>();

        // Obtener los eventos desde la base de datos y agregar las fechas al conjunto
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT nombre, fecha FROM eventos";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Date fechaEvento = rs.getDate("fecha");
                    if (fechaEvento != null) {
                        // Agregar la fecha al conjunto
                        fechasConEventos.add(fechaEvento);
                    }
                }
            }
        } catch (SQLException e) {
        }
        ventanaCalendario.add(calendario);
        ventanaCalendario.setVisible(true);
    }

    /**
     * Obtiene las fechas de eventos desde SQLite.
     *
     * @return Un conjunto de fechas en formato `java.sql.Date`
     */
    public Set<Date> obtenerFechasEventos() {
        Set<Date> fechasEventos = new HashSet<>();

        String sql = "SELECT fecha FROM eventos";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fechasEventos.add(rs.getDate("fecha"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener eventos: " + e.getMessage());
        }

        return fechasEventos;
    }

    /**
     * Obtiene las fechas de reservas desde SQLite.
     *
     * @return Un conjunto de fechas en formato `java.sql.Date`
     */
    public Set<Date> obtenerFechasReservas() {
        Set<Date> fechasReservas = new HashSet<>();

        String sql = "SELECT fecha FROM reservas";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fechasReservas.add(rs.getDate("fecha"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        }

        return fechasReservas;
    }

}
