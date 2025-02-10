/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.ConexionBD;
import DTO.VehiculoDTO;
import Services.Servicios;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 */
public class ControladorVehiculo {

    private Servicios servicios;

    //Constructor que realiza la conexión a la base de datos mediante la clase servicios
    public ControladorVehiculo() throws SQLException {
        ConexionBD conexionBD = new ConexionBD();
        this.servicios = new Servicios(conexionBD);
    }

    /**
     * Metodo que obtiene y controla los vehiculos almacenados en la base de
     * datos Uso el servicio para recuperar los datos de cada vehiculo y los
     * convierto en una lista de objetos VehiculoDTO
     *
     * @return
     */
    public List<VehiculoDTO> obtenerTodosLosVehiculos() {
        Object[][] datos = servicios.obtenerTodos();
        List<VehiculoDTO> vehiculosDTO = new ArrayList<>();

        for (Object[] fila : datos) {
            String matricula = (String) fila[0];
            int anioInt = Integer.parseInt((String) fila[1]);
            String marca = (String) fila[2];
            String modelo = (String) fila[3];
            vehiculosDTO.add(new VehiculoDTO(marca, anioInt, matricula, modelo));
        }

        return vehiculosDTO;
    }

    /**
     * Metodo que controla para agregar un vehiculo a la base de datos Este
     * método recibe un objeto VehiculoDTO con la información del vehículo para
     * agregar y utilizar el servicio para después insertar el vehículo en la
     * base de datos.
     *
     * @param vDTO
     */
    public void agregarVehiculo(VehiculoDTO vDTO) {
        try {
            servicios.añadir(vDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo elimina un vehículo de la base de datos según su matrícula.
     * Este método lo uso llamando al servicio para eliminar un vehículo
     * mediante su matrícula.
     *
     * @param matricula
     * @return devuelve boolean true si el vehículo fue eliminado correctamente
     * si no false si ha ocurrido algun error.
     */
    public boolean eliminarVehiculo(String matricula) throws SQLException {
        return servicios.eliminar(matricula);
    }

    /**
     * Metodo que actualiza la información de un vehículo en la base de datos.
     * Este método recibe un objeto VehiculoDTO con la información actualizada
     * del vehículo que llama a al servicio para modificar los datos en la base
     * de datos.
     *
     * @param vDTO Objeto VehiculoDTO que tiene la información actualizada del
     * vehículo.
     */
    public boolean actualizarVehiculo(VehiculoDTO vDTO) {
        try {
             servicios.actualizar(vDTO);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
