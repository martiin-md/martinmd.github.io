package dao;

import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 */
public class UsuarioDao {

    public boolean autenticarUsuario(String nombre, String password) {
        String sql = "SELECT id, nombre, password, tipo, fecha_registro FROM usuarios WHERE nombre = ? AND password = ?";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idUsuario = resultSet.getInt("id");
                    String tipo = resultSet.getString("tipo");

                    System.out.println("Autenticado: " + nombre + " con tipo: " + tipo);  // Verificación de autenticación

                    // Actualizar la fecha de registro a la fecha y hora actual
                    actualizarFechaRegistro(idUsuario);

                    return true; // Usuario autenticado
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }

        return false; // Autenticación fallida
    }

    // Método para registrar un nuevo usuario
    public static boolean registrarUsuario(String nombre, String password) {
        String verificarSql = "SELECT * FROM usuarios WHERE nombre = ?";
        String insertarSql = "INSERT INTO usuarios (nombre, password) VALUES (?, ?)";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement verificarStmt = connection.prepareStatement(verificarSql)) {

            verificarStmt.setString(1, nombre);
            try (ResultSet resultSet = verificarStmt.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("El usuario ya existe.");
                    return false; // Usuario ya registrado
                }
            }

            try (PreparedStatement insertarStmt = connection.prepareStatement(insertarSql)) {
                insertarStmt.setString(1, nombre);
                insertarStmt.setString(2, password);

                int filasInsertadas = insertarStmt.executeUpdate();
                return filasInsertadas > 0; // Retorna true si la inserción fue exitosa
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
        return false; // Falló el registro
    }

    // Método para actualizar la fecha de registro del usuario
    private void actualizarFechaRegistro(int idUsuario) {
        // Modificación para SQLite: usamos CURRENT_TIMESTAMP
        String sql = "UPDATE usuarios SET fecha_registro = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.executeUpdate(); // Ejecuta la actualización
            System.out.println("Fecha de registro actualizada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar la fecha de registro: " + e.getMessage());
        }
    }

    public String obtenerRolPorUsuario(String nombre, String password) {
        String tipo = null;
        String sql = "SELECT tipo FROM usuarios WHERE nombre = ? AND password = ?";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                tipo = rs.getString("tipo");  // Obtener el rol
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipo;
    }

    // Método para obtener todos los usuarios
    public static List<Object[]> obtenerUsuarios() {
        List<Object[]> usuarios = new ArrayList<>();
        String query = "SELECT id, nombre, email, password, tipo, fecha_registro FROM usuarios ";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement pst = con.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String tipo = rs.getString("tipo");
                String fechaRegistro = rs.getString("fecha_registro");

                // Agregar a la lista de usuarios
                usuarios.add(new Object[]{id, nombre, email, password, tipo, fechaRegistro});
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    // Método para añadir un nuevo usuario
    public static boolean añadirUsuario(String nombre, String email, String password, String tipo) {
        // Verificar si el email es nulo o vacío
        if (email == null || email.trim().isEmpty()) {
            System.out.println("El correo electrónico es obligatorio.");
            return false;
        }

        String verificarSql = "SELECT * FROM usuarios WHERE nombre = ?";
        String insertarSql = "INSERT INTO usuarios (nombre, email, password, tipo) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement verificarStmt = connection.prepareStatement(verificarSql)) {

            // Verificar si el nombre de usuario ya existe
            verificarStmt.setString(1, nombre);
            try (ResultSet resultSet = verificarStmt.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("El usuario ya existe.");
                    return false; // Usuario ya registrado
                }
            }

            // Insertar el nuevo usuario
            try (PreparedStatement insertarStmt = connection.prepareStatement(insertarSql)) {
                insertarStmt.setString(1, nombre);
                insertarStmt.setString(2, email);
                insertarStmt.setString(3, password);
                insertarStmt.setString(4, tipo);

                int filasInsertadas = insertarStmt.executeUpdate();
                return filasInsertadas > 0; // Retorna true si la inserción fue exitosa
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
        return false; // Falló el registro
    }

    // Método para actualizar un usuario
    public static boolean actualizarUsuario(int idUsuario, String nombre, String email, String password, String tipo) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ?, tipo = ? WHERE id = ?";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, tipo);
            preparedStatement.setInt(5, idUsuario);

            int filasActualizadas = preparedStatement.executeUpdate();
            return filasActualizadas > 0; // Retorna true si la actualización fue exitosa

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
        return false; // Falló la actualización
    }

    // Método para eliminar un usuario
    public static boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = ConexionBD.obtenerConexion(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int filasEliminadas = preparedStatement.executeUpdate();
            return filasEliminadas > 0; // Retorna true si la eliminación fue exitosa

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
        return false; // Falló la eliminación
    }

}
