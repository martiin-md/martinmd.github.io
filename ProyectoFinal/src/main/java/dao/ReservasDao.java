package dao;

import controlador.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author martin
 */
public class ReservasDao {

    // Método para obtener las reservas
    public static List<Object[]> obtenerReservas() {
        List<Object[]> reservas = new ArrayList<>();
        String query = "SELECT id, nombre, id_usuario, id_evento, cantidad, personas, fecha_reserva FROM reservas";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement pst = con.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int idUsuario = rs.getInt("id_usuario");
                int idEvento = rs.getInt("id_evento");
                int cantidad = rs.getInt("cantidad");
                int personas = rs.getInt("personas");
                String fechaReserva = rs.getString("fecha_reserva");

                // Manejo de la fecha de la reserva
                LocalDateTime fechaReservaDate = null;
                if (fechaReserva != null) {
                    fechaReservaDate = LocalDateTime.parse(fechaReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }

                reservas.add(new Object[]{id, nombre, idUsuario, idEvento, cantidad, personas, fechaReservaDate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // Método para agregar una reserva
    public static boolean agregarReserva(String nombre, int idUsuario, int idEvento, int cantidad, int personas, LocalDateTime fechaReserva) {
        String sql = "INSERT INTO reservas (nombre, id_usuario, id_evento, cantidad, personas, fecha_reserva) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false); // Desactiva autocommit para asegurar la transacción

            pstmt.setString(1, nombre);
            pstmt.setInt(2, idUsuario);
            pstmt.setInt(3, idEvento);
            pstmt.setInt(4, cantidad);
            pstmt.setInt(5, personas);
            pstmt.setString(6, fechaReserva.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // Convertimos LocalDateTime a String

            int filasAfectadas = pstmt.executeUpdate();

            // Obtener el ID generado automáticamente
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int nuevoId = generatedKeys.getInt(1);
                        System.out.println("Reserva agregada con ID: " + nuevoId);
                    }
                }
            }

            conn.commit();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("ERROR SQL al agregar reserva: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar una reserva
    public static boolean actualizarReserva(int idReserva, String campo, Object valor) {
        String sql = "UPDATE reservas SET " + campo + " = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, valor); // Valor que se pasa para actualizar
            pstmt.setInt(2, idReserva); // ID de la reserva que se va a actualizar

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar reserva: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar una reserva
    public static boolean eliminarReserva(int idReserva) {
        String sql = "DELETE FROM reservas WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idReserva);
            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Filas afectadas al eliminar reserva: " + filasAfectadas);

            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("ERROR SQL al eliminar reserva: " + e.getMessage());
            return false;
        }
    }
}
