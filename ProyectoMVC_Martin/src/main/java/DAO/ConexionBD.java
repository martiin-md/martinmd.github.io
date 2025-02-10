package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class ConexionBD {

    private static PoolDataSource pds;

    // Configuración inicial del pool de conexiones
    static {
        try {
            pds = PoolDataSourceFactory.getPoolDataSource();
            pds.setConnectionFactoryClassName("com.mysql.cj.jdbc.MysqlDataSource");
            pds.setURL("jdbc:mysql://localhost:3306/bdcoches");
            pds.setUser("profe");
            pds.setPassword("1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PoolDataSource getDataSource() {
        return pds;
    }

    // Método para obtener una conexión del pool
    public Connection getConnection() throws SQLException {
        return pds.getConnection();
    }

    // Método seguro para ejecutar consultas SELECT con parámetros
    public ResultSet ejecutarSelect(String query, String[] parameters) {
        ResultSet resultSet = null;
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            // Establezco parámetros de manera segura
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setString(i + 1, parameters[i]);
            }

            resultSet = pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("Error ejecutando consulta: " + e.getMessage());
        }
        return resultSet;
    }

    // Método seguro para ejecutar actualizaciones con parámetros
    public int ejecutarUpdate(String query, String[] parameters) {
        int rowsAffected = 0;
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            // Establezco parámetros de manera segura
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setString(i + 1, parameters[i]);
            }

            rowsAffected = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error ejecutando actualización: " + e.getMessage());
        }
        return rowsAffected;
    }

    // Prueba de conexión
    public static void main(String[] args) {
        try {
            ConexionBD conn = new ConexionBD();
            Connection con = conn.getConnection();
            System.out.println("\nConnection obtained from " + "UniversalConnectionPool\n" + "¡Conexión establecida!");
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM personas");
            
            con.close();
            con = null;
            System.out.println("Connection returned to the" + "UniversalConnectionPool\n");
            
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
