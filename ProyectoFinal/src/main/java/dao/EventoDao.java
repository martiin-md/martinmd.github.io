package dao;

import controlador.ConexionBD;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventoDao {

    // Método para obtener los eventos con fechas formateadas
// Ejemplo del método donde obtienes los eventos de la base de datos
    public static List<Object[]> obtenerEventos() {
        List<Object[]> eventos = new ArrayList<>();
        String query = "SELECT id, nombre, fecha, ubicacion, categoria, precio, id_organizador FROM eventos";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement pst = con.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String fecha = rs.getString("fecha"); // Asegúrate de que la fecha no sea null
                String ubicacion = rs.getString("ubicacion");
                String categoria = rs.getString("categoria");
                BigDecimal precio = rs.getBigDecimal("precio");
                int idOrganizador = rs.getInt("id_organizador");

                // Maneja el caso de fecha null
                if (fecha != null) {
                    LocalDateTime fechaEvento = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    eventos.add(new Object[]{id, nombre, fechaEvento, ubicacion, categoria, precio, idOrganizador});
                } else {
                    // Si la fecha es null, agrega un valor predeterminado o maneja de otra forma
                    eventos.add(new Object[]{id, nombre, "Fecha no disponible", ubicacion, categoria, precio, idOrganizador});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    // Método para agregar un evento
    public static boolean agregarEvento(String nombre, LocalDateTime fecha, String ubicacion, String categoria, BigDecimal precio, Integer idOrganizador) {
        String sql = "INSERT INTO eventos (nombre, fecha, ubicacion, categoria, precio, id_organizador) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false); // Desactiva autocommit para asegurar la transacción

            pstmt.setString(1, nombre);
            pstmt.setString(2, fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // Convertimos LocalDateTime a String
            pstmt.setString(3, ubicacion);
            pstmt.setString(4, categoria);
            pstmt.setBigDecimal(5, precio);

            if (idOrganizador != null) {
                pstmt.setInt(6, idOrganizador);
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }

            int filasAfectadas = pstmt.executeUpdate();

            // Obtener el ID generado automáticamente
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int nuevoId = generatedKeys.getInt(1);
                        System.out.println("Evento agregado con ID: " + nuevoId);
                    }
                }
            }

            conn.commit();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("ERROR SQL al agregar evento: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un evento
    public static boolean actualizarEvento(int idEvento, String nombre, LocalDateTime fecha, String ubicacion, String categoria, BigDecimal precio, Integer idOrganizador) {
        String sql = "UPDATE eventos SET nombre = ?, fecha = ?, ubicacion = ?, categoria = ?, precio = ?, id_organizador = ? WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // Convertimos LocalDateTime a String
            pstmt.setString(3, ubicacion);
            pstmt.setString(4, categoria);
            pstmt.setBigDecimal(5, precio);

            if (idOrganizador != null) {
                pstmt.setInt(6, idOrganizador);
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }

            pstmt.setInt(7, idEvento);

            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Filas afectadas al actualizar evento: " + filasAfectadas);

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("ERROR SQL al actualizar evento: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un evento
    public static boolean eliminarEvento(int idEvento) {
        String sql = "DELETE FROM eventos WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvento);
            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Filas afectadas al eliminar evento: " + filasAfectadas);

            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("ERROR SQL al eliminar evento: " + e.getMessage());
            return false;
        }
    }
}
