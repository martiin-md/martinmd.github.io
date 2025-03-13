package com.mycompany.GestionEventos.dao;


import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author martin
 */
public class BusquedaBD {

    // Método para realizar la búsqueda en la base de datos
    public void realizarBusqueda(String consulta) {
        if (consulta != null && !consulta.trim().isEmpty()) {
            // Consulta para almacenar los resultados de la búsqueda
            StringBuilder resultadosTotales = new StringBuilder();

            try (Connection conn = ConexionBD.obtenerConexion()) {
                // Consulta de eventos
                String eventosSql = "SELECT nombre FROM eventos WHERE nombre LIKE ?";
                try (PreparedStatement stmt = conn.prepareStatement(eventosSql)) {
                    stmt.setString(1, "%" + consulta + "%");
                    ResultSet rs = stmt.executeQuery();
                    StringBuilder eventosEncontrados = new StringBuilder("Eventos encontrados:\n");
                    boolean hayEventos = false;
                    while (rs.next()) {
                        eventosEncontrados.append(rs.getString("nombre")).append("\n");
                        hayEventos = true;
                    }
                    if (hayEventos) {
                        resultadosTotales.append(eventosEncontrados).append("\n");
                    } else {
                        resultadosTotales.append("No se encontraron eventos.\n");
                    }
                }

                // Consulta de artistas
                String artistasSql = "SELECT nombre FROM artistas WHERE nombre LIKE ?";
                try (PreparedStatement stmt = conn.prepareStatement(artistasSql)) {
                    stmt.setString(1, "%" + consulta + "%");
                    ResultSet rs = stmt.executeQuery();
                    StringBuilder artistasEncontrados = new StringBuilder("Artistas encontrados:\n");
                    boolean hayArtistas = false;
                    while (rs.next()) {
                        artistasEncontrados.append(rs.getString("nombre")).append("\n");
                        hayArtistas = true;
                    }
                    if (hayArtistas) {
                        resultadosTotales.append(artistasEncontrados).append("\n");
                    } else {
                        resultadosTotales.append("No se encontraron artistas.\n");
                    }
                }

                // Consulta de reservas
                String reservasSql = "SELECT nombre FROM reservas WHERE nombre LIKE ?";
                try (PreparedStatement stmt = conn.prepareStatement(reservasSql)) {
                    stmt.setString(1, "%" + consulta + "%");
                    ResultSet rs = stmt.executeQuery();
                    StringBuilder reservasEncontradas = new StringBuilder("Reservas encontradas:\n");
                    boolean hayReservas = false;
                    while (rs.next()) {
                        reservasEncontradas.append(rs.getString("descripcion")).append("\n");
                        hayReservas = true;
                    }
                    if (hayReservas) {
                        resultadosTotales.append(reservasEncontradas).append("\n");
                    } else {
                        resultadosTotales.append("No se encontraron reservas.\n");
                    }
                }

                // Mostrar los resultados en un solo cuadro de mensaje
                if (resultadosTotales.length() > 0) {
                    JOptionPane.showMessageDialog(null, resultadosTotales.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados para: " + consulta);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Introduce un término de búsqueda.");
        }
    }
}
