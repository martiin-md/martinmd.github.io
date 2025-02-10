/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.ConexionBD;
import DTO.PersonaDTO;
import Services.Servicios;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @martin
 */
public class ControladorPersona {

    private Servicios servicios;

    //Constructor que inicializa la conexión a la base de datos a través de la clase ConexiónBD
    public ControladorPersona() throws SQLException {
        ConexionBD conexionBD = new ConexionBD();
        this.servicios = new Servicios(conexionBD);
    }

    /**
     * Se Obtiene todas las personas almacenadas en la base de datos. Este
     * método utiliza el servicio para recuperar los datos de cada persona y los
     * convierte en una lista de objetos PersonaDTO para su fácil manejo.
     *
     * @return List<PersonaDTO> Lista de objetos PersonaDTO esto representan a
     * todas las personas en la base de datos.
     */
    public List<PersonaDTO> obtenerTodasLasPersonas() {
        Object[][] datos = servicios.obtenerPersonas();
        List<PersonaDTO> personasDTO = new ArrayList<>();

        for (Object[] fila : datos) {
            String nombre = (String) fila[0];
            String genero = (String) fila[1];
            String dni = (String) fila[2];
            personasDTO.add(new PersonaDTO(nombre, genero, dni));
        }

        return personasDTO;
    }

    /**
     * Se Agrega una nueva persona a la base de datos. Este método recibe un
     * objeto PersonaDTO con la información de la persona a agregar, y utiliza
     * el servicio para ingresar la persona en la base de datos.
     *
     * @param pDTO Objeto PersonaDTO que contiene la información de la persona a
     * añadir.
     */
    public void agregarPersona(PersonaDTO pDTO) {
        try {
            servicios.añadirPersona(pDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Se elimina una persona mediante su dni este metodo usa el servicio para
     * eliminar la persona con su dni
     *
     * @param dni es la cadena que se usara para eliminar la persona en vista
     * @return devuelve true si la persona fue eliminada correctamente, false si
     * ah ocurrido algun error
     */
    public boolean eliminarPersona(String dni) throws SQLException {
        return servicios.eliminar(dni);
    }

}
