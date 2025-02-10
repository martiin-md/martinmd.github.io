/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.PersonaDTO;
import DTO.VehiculoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 */
public class VehiculosYPersonasDAO {

    private final Connection con;
    ConexionBD conexionBD = new ConexionBD();

    public VehiculosYPersonasDAO() {
        this.con = null;
    }

    public VehiculosYPersonasDAO(ConexionBD conexionBD) throws SQLException {
        this.con = conexionBD.getConnection();
    }

    // Metodo para obtener todos los registros de la tabla principal
    public Object[][] obtenerDatos() {
        ArrayList<Object[]> listaDatos = new ArrayList<>();

        // Agrega una subconsulta que cuenta los propietarios en el historial de propietarios
        String sql = """
        SELECT nombre, dni, genero, matricula, año, marca, modelo,
                 (SELECT COUNT(*) FROM historico_propietarios WHERE historico_propietarios.matricula = coches.matricula) AS total_propietarios
        FROM coches""";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = new Object[8]; // Ahora la fila tiene 8 columnas
                fila[0] = rs.getString("nombre");
                fila[1] = rs.getString("dni");
                fila[2] = rs.getString("genero");
                fila[3] = rs.getString("matricula");
                fila[4] = rs.getInt("año");
                fila[5] = rs.getString("marca");
                fila[6] = rs.getString("modelo");
                fila[7] = rs.getInt("total_propietarios"); // La nueva columna que contiene el total de propietarios
                listaDatos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] datos = new Object[listaDatos.size()][8]; // Ahora el array es de 8 columnas
        for (int i = 0; i < listaDatos.size(); i++) {
            datos[i] = listaDatos.get(i);
        }
        return datos;
    }

    /**
     * METODOS DEL CRUD AÑADIR VEHICULO AÑADIR PERSONA ELIMINAR POR MATRICULA
     * ACTUALIZAR "EDITAR"
     *
     * @param vehiculo
     * @throws SQLException
     */
    public void añadirVehiculo(VehiculoDTO vehiculo) throws SQLException {
        String regexMatricula = "^[0-9]{4}[A-Z]{3}$";  // Matricula 4 números y 3 letras MAYÚSCULAS

        // Validaciones de matrícula
        if (!vehiculo.getMatricula().matches(regexMatricula)) {
            throw new SQLException("La matricula debe contener exactamente 7 caracteres: 4 dígitos y 3 letras mayusculas.");
        }

        String anioStr = String.valueOf(vehiculo.getAnio()); // Convertimos el año a String

        // Validación del año
        if (anioStr.length() != 4 || !isNumeric(anioStr)) {
            throw new SQLException("El año debe contener exactamente 4 dígitos numericos.");
        }

        int anio = Integer.parseInt(anioStr);  // Convertimos el año a int

        // SQL para insertar el vehiculo
        String sql = "INSERT INTO cocheslibres (matricula, año, marca, modelo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement sentencia = con.prepareStatement(sql)) {
            sentencia.setString(1, vehiculo.getMatricula());
            sentencia.setInt(2, anio);
            sentencia.setString(3, vehiculo.getMarca());
            sentencia.setString(4, vehiculo.getModelo());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al añadir el vehículo a la base de datos: " + e.getMessage());
        }
    }

    // Método  para comprobar si una cadena es numerico
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);  // Intenta convertir la cadena a un número entero
            return true;  // Si tiene éxito, es numérico
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void añadirPersona(PersonaDTO persona) throws SQLException {
        // 
        String regexNombre = "^[\\p{L} ]+$";  // Expresion nombre que solo incluye letras incluyendo tildes, ñ y espacios
        //  DNI español 8 dígitos + 1 letra mayúscula al final
        String regexDniEspañol = "^[0-9]{8}[A-Z]$";
        //  DNI extranjero 1 letra mayúscula al principio, 8 dígitos, 1a letra mayúscula al final
        String regexDniExtranjero = "^[A-Z][0-9]{8}[A-Z]$";

        // Validaciones para el nombre
        if (persona.getNombre().length() > 25) {
            throw new SQLException("El nombre no puede exceder los 25 caracteres.");
        }

        if (!persona.getNombre().matches(regexNombre)) {
            throw new SQLException("El nombre contiene caracteres no permitidos. Solo se permiten letras y espacios.");
        }

        // Validación del DNI (español o extranjero)
        if (!persona.getDni().matches(regexDniEspañol) && !persona.getDni().matches(regexDniExtranjero)) {
            throw new SQLException("El DNI debe tener el formato correcto: 8 dígitos seguidos de 1 letra mayúscula (español) o 1 letra mayúscula al principio y al final (extranjero).");
        }

        // SQL para insertar la persona en la base de datos
        String sql = "INSERT INTO personas (nombre, dni, genero) VALUES (?, ?, ?)";
        try (PreparedStatement sentencia = con.prepareStatement(sql)) {
            sentencia.setString(1, persona.getNombre());
            sentencia.setString(2, persona.getDni());
            sentencia.setString(3, persona.getGenero());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al añadir la persona a la base de datos.");
        }
    }

    public boolean eliminar(String matricula) throws SQLException {
        String sqlActualizarHistorico = "UPDATE historico_propietarios SET id_coche = NULL WHERE id_coche = (SELECT id FROM coches WHERE matricula = ?)";
        String sql = "DELETE FROM coches WHERE matricula = ?";

        try {
            con.setAutoCommit(false); // Inicio la transaccion

            try (PreparedStatement stmt = con.prepareStatement(sqlActualizarHistorico)) {
                stmt.setString(1, matricula);
                stmt.executeUpdate();
            }

            // Elimina el coche
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, matricula);
                int filasEliminadas = stmt.executeUpdate();
                if (filasEliminadas > 0) {
                    con.commit(); // Confirma cambios si todo salio bien
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new SQLException("Error al Actualizar la persona de la base de datos.");
        }
    }

    public boolean actualizar(VehiculoDTO vehiculo) throws SQLException {
        // Expresión regular para validar la matrícula: 4 números y 3 letras mayúsculas
        String regexMatricula = "^[0-9]{4}[A-Z]{3}$";

        // Convertimos el año a String para la validación
        String anioStr = String.valueOf(vehiculo.getAnio());

        // Validación del año
        if (anioStr.length() != 4 || !isNumeric(anioStr)) {
            throw new SQLException("El año debe contener exactamente 4 dígitos numéricos.");
        }

        int anio = Integer.parseInt(anioStr);  // Convertimos el año a int
        String sql = "UPDATE coches SET año = ?, marca = ?, modelo = ? WHERE matricula = ?";
        try (PreparedStatement sentencia = con.prepareStatement(sql)) {
            sentencia.setInt(1, vehiculo.getAnio());
            sentencia.setString(2, vehiculo.getMarca());
            sentencia.setString(3, vehiculo.getModelo());
            sentencia.setString(4, vehiculo.getMatricula());

            int filasActualizadas = sentencia.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            throw new SQLException("Error al Actualizar la persona de la base de datos.");
        }
    }

    //METODO PARA OBTENER FILTROS DESDE LA BASE DE DATOS CON LAS VALIDACIONES Y EXCEPCIONES
    public Object[][] obtenerDatosFiltrados(String nombre, String genero, String marca, String modelo, String anio) {
        ArrayList<Object[]> listaDatos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT nombre, dni, genero, matricula, año, marca, modelo,total_propietarios FROM coches WHERE 1=1");
        ArrayList<Object> parametros = new ArrayList<>();

        try {
            // Validación de nombre
            if (nombre != null && !nombre.isEmpty()) {
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚ0-9 ]*")) {
                    throw new IllegalArgumentException("El nombre contiene caracteres no válidos.");
                }
                sql.append(" AND nombre LIKE ?");
                parametros.add("%" + nombre + "%");
            }

            // Validación de género
            if (genero != null && !genero.isEmpty()) {
                if (!genero.equalsIgnoreCase("HOMBRE") && !genero.equalsIgnoreCase("MUJER")) {
                    throw new IllegalArgumentException("Género no válido.");
                }
                sql.append(" AND genero = ?");
                parametros.add(genero.toUpperCase());
            }

            // Validación de marca
            if (marca != null && !marca.equals("Todas")) {
                if (!marca.matches("[a-zA-ZáéíóúÁÉÍÓÚ0-9 ]*")) {
                    throw new IllegalArgumentException("Marca no válida.");
                }
                sql.append(" AND marca = ?");
                parametros.add(marca);
            }

            // Validación de modelo
            if (modelo != null && !modelo.equals("Todos")) {
                if (!modelo.matches("[a-zA-ZáéíóúÁÉÍÓÚ0-9 ]*")) {
                    throw new IllegalArgumentException("Modelo no válido.");
                }
                sql.append(" AND modelo = ?");
                parametros.add(modelo);
            }

            // Validación de año
            if (anio != null && !anio.isEmpty()) {
                try {
                    int anioInt = Integer.parseInt(anio);
                    if (anioInt < 1900 || anioInt > 2024) {
                        throw new IllegalArgumentException("Año no válido.");
                    }
                    sql.append(" AND año = ?");
                    parametros.add(anio);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Año debe ser un número.", e);
                }
            }

            try (PreparedStatement sentencia = con.prepareStatement(sql.toString())) {
                for (int i = 0; i < parametros.size(); i++) {
                    sentencia.setObject(i + 1, parametros.get(i));
                }

                try (ResultSet rs = sentencia.executeQuery()) {
                    while (rs.next()) {
                        Object[] fila = new Object[8];
                        fila[0] = rs.getString("nombre");
                        fila[1] = rs.getString("dni");
                        fila[2] = rs.getString("genero");
                        fila[3] = rs.getString("matricula");
                        fila[4] = rs.getInt("año");
                        fila[5] = rs.getString("marca");
                        fila[6] = rs.getString("modelo");
                        fila[7] = rs.getInt("total_propietarios");
                        listaDatos.add(fila);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error en la validación de los parámetros: " + e.getMessage());
            return new Object[0][0];
        } catch (SQLException e) {
            System.err.println("Error en la ejecución de la consulta SQL: " + e.getMessage());
            e.printStackTrace();
            return new Object[0][0];
        }

        Object[][] datos = new Object[listaDatos.size()][8];
        for (int i = 0; i < listaDatos.size(); i++) {
            datos[i] = listaDatos.get(i);
        }
        return datos;
    }

//Metodo para obtener la tabla datos para asociar
    public Object[][] obtenerDatosTablaPersona() {
        ArrayList<Object[]> listaDatos = new ArrayList<>();
        String query = "SELECT nombre, dni, genero FROM personas";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(query)) {
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getString("nombre");
                fila[1] = rs.getString("dni");
                fila[2] = rs.getString("genero");
                listaDatos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] datos = new Object[listaDatos.size()][3];
        for (int i = 0; i < listaDatos.size(); i++) {
            datos[i] = listaDatos.get(i);
        }
        return datos;
    }

    //Metodo para obtener los vehiculos libres
    public Object[][] obtenerVehiculosLibres() {
        ArrayList<Object[]> listaDatos = new ArrayList<>();
        String query = "SELECT matricula, año, marca, modelo FROM cocheslibres";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(query)) {
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("matricula");
                fila[1] = rs.getInt("año");
                fila[2] = rs.getString("marca");
                fila[3] = rs.getString("modelo");
                listaDatos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] datos = new Object[listaDatos.size()][4];
        for (int i = 0; i < listaDatos.size(); i++) {
            datos[i] = listaDatos.get(i);
        }
        return datos;
    }

    /**
     * METODO PARA OBTENER LAS MARCAS METODO PARA OBTENER LOS MODELOS
     *
     * @return
     */
    public ArrayList<String> obtenerMarcas() {
        ArrayList<String> marcas = new ArrayList<>();
        String query = "SELECT DISTINCT marca FROM coches";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(query)) {
            while (rs.next()) {
                marcas.add(rs.getString("marca"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marcas;
    }

    public ArrayList<String> obtenerModelos() {
        ArrayList<String> modelos = new ArrayList<>();
        String query = "SELECT DISTINCT modelo FROM coches";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(query)) {
            while (rs.next()) {
                modelos.add(rs.getString("modelo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelos;
    }

    //Metodo para asociar coche con la persona
    public boolean asociarVehiculoConPersona(String nombre, String dni, String genero, String matricula, int anio, String marca, String modelo) {
        // Usamos INSERT INTO para agregar un nuevo registro en la tabla coches
        String sql = "INSERT INTO coches (nombre, dni, genero, matricula, año, marca, modelo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = con.prepareStatement(sql)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, dni);
            sentencia.setString(3, genero);
            sentencia.setString(4, matricula);
            sentencia.setInt(5, anio);
            sentencia.setString(6, marca);
            sentencia.setString(7, modelo);

            int filasInsertadas = sentencia.executeUpdate();
            return filasInsertadas > 0; // Devuelve true si la inserción fue exitosa
        } catch (SQLException e) {
            System.out.println("Error al asociar el vehículo con la persona: " + e.getMessage());
            return false; // Devuelve false si hubo un error
        }
    }

    //Metodo para obtener Vehiculos con personas
    public Object[][] obtenerVehiculosConPersonas() {
        // Recupera los vehiculos con sus personas asociadas
        String sql = "SELECT p.nombre, p.dni, p.genero, v.matricula, v.anio, v.marca, v.modelo "
                + "FROM personas p "
                + "INNER JOIN vehiculos v ON p.dni = v.dni_persona";

        List<Object[]> datos = new ArrayList<>();
        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {
            while (rs.next()) {
                datos.add(new Object[]{
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("genero"),
                    rs.getString("matricula"),
                    rs.getInt("anio"),
                    rs.getString("marca"),
                    rs.getString("modelo")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos.toArray(new Object[0][0]);
    }

    //---------------------------------------------------------------------------------------------------------------------
    // Metodo en VehiculosDAO para obtener datos con paginación
    public List<VehiculoDTO> obtenerVehiculosConPaginacion(int limit, int offset) throws SQLException {
        List<VehiculoDTO> vehiculos = new ArrayList<>();
        String sql = "SELECT nombre, dni, genero, matricula, año, marca, modelo, total_Propietarios FROM coches ORDER BY matricula LIMIT ? OFFSET ?";

        try (PreparedStatement sentencia = con.prepareStatement(sql)) {
            sentencia.setInt(1, limit);
            sentencia.setInt(2, offset);
            try (ResultSet rs = sentencia.executeQuery()) {
                while (rs.next()) {
                    VehiculoDTO vehiculo = new VehiculoDTO(
                            rs.getString("nombre"),
                            rs.getString("dni"),
                            rs.getString("genero"),
                            rs.getString("matricula"),
                            rs.getInt("año"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("total_Propietarios")
                    );
                    vehiculos.add(vehiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculos;
    }

    ////////////////////////////////////////////////////////////////////////////
    public Object[][] obtenerVehiculosAsociados() {
        ArrayList<Object[]> listaDatos = new ArrayList<>();
        String sql = "SELECT matricula, año, marca, modelo FROM vehiculos WHERE asociado = true";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("matricula");
                fila[1] = rs.getInt("año");
                fila[2] = rs.getString("marca");
                fila[3] = rs.getString("modelo");
                listaDatos.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("No se han podido realizar");
        }

        // Convertimos el ArrayList a un Object[][] para devolver los resultados en el formato esperado.
        Object[][] datos = new Object[listaDatos.size()][4];
        for (int i = 0; i < listaDatos.size(); i++) {
            datos[i] = listaDatos.get(i);
        }
        return datos;
    }

    // Metodo para obtener el historial de propietarios de un vehículo por matrícula
    public Object[][] obtenerHistorialPorMatricula(String matricula) {
        System.out.println("Matricula" + matricula);
        ArrayList<Object[]> listaDatos = new ArrayList<>();
        String query = "SELECT * FROM historico_propietarios WHERE matricula = '" + matricula + "'";

        System.out.println("Consultando la base de datos con la matrícula: '" + matricula + "'");

        try (PreparedStatement sentencia = con.prepareStatement(query)) {
            System.out.println("QUERY: " + sentencia.toString());
            try (ResultSet rs = sentencia.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No se encontraron resultados para la matrícula.");
                } else {
                    do {
                        System.out.println("Resultado: " + rs.getString("matricula"));
                        Object[] fila = new Object[7];
                        fila[0] = rs.getString("id");
                        fila[1] = rs.getString("matricula");
                        fila[2] = rs.getString("nombre");
                        fila[3] = rs.getString("dni");
                        fila[4] = rs.getDate("fecha_compra");
                        fila[5] = rs.getDate("fecha_fin");
                        fila[6] = rs.getInt("id_coche");
                        listaDatos.add(fila);
                    } while (rs.next());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el historial del propietario: " + e.getMessage());
        }

        // Convertir ArrayList a Object[][] para retornar en el formato esperado
        Object[][] datos = new Object[listaDatos.size()][7];
        for (int i = 0; i < listaDatos.size(); i++) {
            datos[i] = listaDatos.get(i);
        }
        return datos;
    }

    //Metodo para obtener la cantidad total de registros por pagina
    public int contarTotalRegistros() {
        int totalRegistros = 0;
        String sql = "SELECT COUNT(*) AS total FROM coches";

        try (Statement sentencia = con.createStatement(); ResultSet rs = sentencia.executeQuery(sql)) {
            if (rs.next()) {
                totalRegistros = rs.getInt("total"); // Obtiene el total de registros
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRegistros;
    }

}
