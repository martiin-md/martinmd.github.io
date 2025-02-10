package Services;

import DAO.ConexionBD;
import DAO.VehiculosYPersonasDAO;
import DTO.PersonaDTO;
import DTO.VehiculoDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author martin
 */
public class Servicios {

    private final VehiculosYPersonasDAO vehiculoDAO;

    // Constructor que recibe la conexión a la base de datos y el DAO de historicoPropietario
    public Servicios(ConexionBD conexionBD) throws SQLException {
        this.vehiculoDAO = new VehiculosYPersonasDAO(conexionBD);
    }

    //=============================================
    //         MÉTODOS DE BÚSQUEDA Y FILTRADO
    //=============================================
    /**
     * Obtiene vehículos filtrados según los parámetros de búsqueda.
     *
     * @param nombre Nombre de la persona
     * @param genero Género de la persona
     * @param marca Marca del vehiculo
     * @param modelo Modelo del vehiculo
     * @param anio Año del vehiculo
     * @return Matriz con los datos de vehiculo filtrados
     */
    public Object[][] obtenerVehiculosFiltrados(String nombre, String genero, String marca, String modelo, String anio) {
        return vehiculoDAO.obtenerDatosFiltrados(nombre, genero, marca, modelo, anio);
    }

    /**
     * Obtiene todos los vehiculo de la base de datos.
     *
     * @return Matriz con todos los datos de vehiculo
     */
    public Object[][] obtenerTodos() {
        return vehiculoDAO.obtenerDatos();
    }

    /**
     * Obtiene todos los datos de personas en la base de datos.
     *
     * @return Matriz con los datos de personas
     */
    public Object[][] obtenerPersonas() {
        return vehiculoDAO.obtenerDatosTablaPersona();
    }

    /**
     * Obtiene todos los vehiculo disponibles (libres) en la base de datos.
     *
     * @return Matriz con los vehiculo libres
     */
    public Object[][] obtenerVehiculosLibres() {
        return vehiculoDAO.obtenerVehiculosLibres();
    }

    /**
     * Asocia un vehiculo a una persona.
     *
     * @param nombre Nombre de la persona
     * @param dni DNI de la persona
     * @param genero Genero de la persona
     * @param matricula Matricula del vehiculo
     * @param anio Año del vehiculo
     * @param marca Marca del vehiculo
     * @param modelo Modelo del vehiculo
     * @return true si la asociacion fue exitosa, false de lo contrario
     */
    public boolean asociarVehiculoAPersona(String nombre, String dni, String genero, String matricula, int anio, String marca, String modelo) {
        return vehiculoDAO.asociarVehiculoConPersona(nombre, dni, genero, matricula, anio, marca, modelo);
    }

    /**
     * Convierte una entidad de Persona en un DTO.
     *
     * @param persona Objeto Persona
     * @return PersonaDTO con los datos de la persona
     */
    public PersonaDTO convertirAPersonaDTO(Modelo.Personas persona) {
        return new PersonaDTO(persona.getNombre(), persona.getGenero(), persona.getDni());
    }

    //=============================================
    //                    CRUD
    //=============================================
    public void añadir(VehiculoDTO vehiculo) throws SQLException {
        try {
            vehiculoDAO.añadirVehiculo(vehiculo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No es posible crear vehiculo .", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void añadirPersona(PersonaDTO persona) throws SQLException {
        try {
            vehiculoDAO.añadirPersona(persona);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No es posible crear Persona.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean eliminar(String matricula) throws SQLException {
        try {

            return vehiculoDAO.eliminar(matricula);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No es posible Eliminar Vehiculo Persona.", "ERROR", JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    public boolean actualizar(VehiculoDTO vehiculo) throws SQLException {
        try {
            return vehiculoDAO.actualizar(vehiculo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No es posible Actualizar Persona.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //=============================================
    //       MÉTODOS PARA OBTENER MARCAS Y MODELOS
    //=============================================
    public ArrayList<String> obtenerMarcas() {
        return vehiculoDAO.obtenerMarcas();
    }

    public ArrayList<String> obtenerModelos() {
        return vehiculoDAO.obtenerModelos();
    }

    //=============================================
    //        METODOS DE PAGINACION
    //=============================================
    public List<VehiculoDTO> obtenerVehiculosConPaginacion(int limit, int offset) throws SQLException {
        return vehiculoDAO.obtenerVehiculosConPaginacion(limit, offset);
    }

    //=============================================
    //       METODO PARA VEHICULOS ASOCIADOS
    //=============================================
    public Object[][] obtenerVehiculosAsociados() {
        return vehiculoDAO.obtenerVehiculosAsociados();
    }

    //=============================================
    //    METODO PARA HISTORIAL DE PROPIETARIOS
    //=============================================
    /**
     * Obtiene el historial de propietarios de un vehículo por su matrícula.
     *
     * @param matricula Matrícula del vehículo para buscar su historial
     * @return Matriz con el historial de propietarios del vehículo
     */
    public Object[][] obtenerHistorialPropietarios(String matricula) {
        return vehiculoDAO.obtenerHistorialPorMatricula(matricula);
    }

    public int contarCantidadRegistros() {
        return vehiculoDAO.contarTotalRegistros();
    }

}
