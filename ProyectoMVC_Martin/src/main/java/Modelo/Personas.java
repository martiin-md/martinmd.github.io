/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author martin
 */
public class Personas {

    private String nombre;
    private String genero;
    private String dni;

    //Constructor Vacio
    public Personas() {
    }

    //Constructor con todos los campos
    public Personas(String nombre, String genero, String dni) {
        this.nombre = nombre;
        this.genero = genero;
        this.dni = dni;
    }

    //Getters y setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Metodo toString
    @Override
    public String toString() {
        return "Personas{" + "nombre=" + nombre + ", dni=" + dni + '}';
    }

}
