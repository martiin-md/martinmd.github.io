package dao;

import controlador.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistasDao {

    public static List<Object[]> obtenerArtistas() {
        List<Object[]> artistas = new ArrayList<>();
        // Usamos COALESCE en lugar de IFNULL, ya que SQLite no soporta IFNULL
        String sql = "SELECT id, nombre, COALESCE(categoria, 'Desconocido') AS categoria, evento, hora, id_evento FROM artistas";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                artistas.add(new Object[]{
                    rs.getInt("id"), // ID del artista
                    rs.getString("nombre"), // Nombre del artista
                    rs.getString("categoria"), // Categoría (manejo de valores NULL)
                    rs.getString("evento"), // Evento asociado
                    rs.getString("hora"), // Hora (SQLite almacena TIME como TEXT)
                    rs.getObject("id_evento") // ID del evento (puede ser NULL)
                });
            }

            System.out.println("✅ Artistas obtenidos: " + artistas.size());

        } catch (SQLException e) {
            System.err.println("❌ ERROR SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return artistas;
    }
}