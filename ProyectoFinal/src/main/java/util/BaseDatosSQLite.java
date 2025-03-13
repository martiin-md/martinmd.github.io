package util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author martin
 */
public class BaseDatosSQLite {

    public static void BaseDatosSQLite(String sqlFilePath) {
        Connection connection = null;
        try {
            // Obtener conexión a la base de datos
            connection = controlador.ConexionBD.obtenerConexion();
            if (connection == null) {
                System.out.println("No se pudo conectar a la base de datos.");
                return;
            }

            // Leer el archivo SQL línea por línea
            StringBuilder sqlScript = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlScript.append(line).append("\n");
                }
            }

            // Dividir el script en consultas individuales
            String[] queries = sqlScript.toString().split(";");

            // Ejecutar cada consulta
            try (Statement statement = connection.createStatement()) {
                for (String query : queries) {
                    if (!query.trim().isEmpty()) {
                        statement.execute(query);
                    }
                }
            }

            System.out.println("Base de datos inicializada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        } finally {
            controlador.ConexionBD.cerrarConexion(connection);
        }
    }

    public static void main(String[] args) {
        // Ruta al archivo SQL
        String sqlFilePath = "src/main/java/recursos/data.sqlite"; 
        BaseDatosSQLite(sqlFilePath);
    }
}
