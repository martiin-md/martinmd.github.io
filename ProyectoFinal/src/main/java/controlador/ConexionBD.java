package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class ConexionBD {

    private static final String URL = "jdbc:sqlite::resource:data.sqlite";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.commit();
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    // Prueba de conexión
    public static void main(String[] args) {
        Connection conn = obtenerConexion();
        if (conn != null) {
            System.out.println("Conexión establecida correctamente.");
            cerrarConexion(conn); // Cierre de Conexión
        }
    }
}
