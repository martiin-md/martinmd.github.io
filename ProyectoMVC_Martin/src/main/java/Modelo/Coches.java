/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author martin
 */
public class Coches {

    private String matricula;
    private String marca;
    private String modelo;
    private int anio;

    // Constructor vacio
    public Coches() {
    }

    //Constructor con todos los campos del coche
    public Coches(String matricula,int anio, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
    }

    
    //Getters y setters
    public int getAnio() {
        return anio;
    }

    public void setAño(int anio) {
        this.anio = anio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    
    //Muestro tambien el toString 
    @Override
    public String toString() {
        return "Coches{" + "matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", a\u00f1o=" + anio + '}';
    }
    
 
    
}
