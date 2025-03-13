package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class ConexionBD {

    // Ruta del archivo de la base de datos SQLite
    private static final String URL = "jdbc:sqlite:src/main/java/recursos/data.sqlite"; 

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // No es necesario cargar el driver manualmente, se carga automáticamente
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa a la base de datos SQLite.");
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Prueba de conexión
        Connection conn = obtenerConexion();
        if (conn != null) {
            System.out.println("Conexión establecida correctamente.");
            cerrarConexion(conn); // Cerrar la conexión después de la prueba
        }
    }
}
