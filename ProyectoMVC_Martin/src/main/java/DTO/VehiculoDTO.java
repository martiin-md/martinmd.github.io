/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author martin
 */
public class VehiculoDTO {

    private String nombre;
    private String marca;
    private int anio;
    private String matricula;
    private String modelo;
    private String dni;
    private String genero;
    private int cantidad_Propietarios;
    private String nuevaMatricula;

    // Constructor principal con todos los campos
    public VehiculoDTO(String nombre, String dni, String genero, String matricula, int anio, String marca, String modelo, int cantidad_Propietarios) {
        this.nombre = nombre;
        this.dni = dni;
        this.genero = genero;
        this.matricula = matricula;
        this.anio = anio;
        this.marca = marca;
        this.modelo = modelo;
        this.cantidad_Propietarios = cantidad_Propietarios;
    }

    // Constructor que no se inicializa el dni ni el genero de la persona
    public VehiculoDTO(String nombre, String marca, int anio, String matricula, String modelo) {
        this.nombre = nombre;
        this.marca = marca;
        this.anio = anio;
        this.matricula = matricula;
        this.modelo = modelo;
    }

    // Constructor para el vehiculo del coche que inicia marca, año, matricula y modelo
    public VehiculoDTO(String matricula, int anio, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.anio = anio;
        this.modelo = modelo;
    }

    // Getters y setters

    public String getNuevaMatricula() {
        return nuevaMatricula;
    }

    public void setNuevaMatricula(String nuevaMatricula) {
        this.nuevaMatricula = nuevaMatricula;
    }
    
    public int getCantidadPropietarios() {
        return cantidad_Propietarios;
    }

    public void setCantidadPropietarios() {
        this.cantidad_Propietarios = cantidad_Propietarios;
    }

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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;

    }
}
