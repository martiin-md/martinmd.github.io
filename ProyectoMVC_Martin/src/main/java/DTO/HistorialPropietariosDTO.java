package DTO;

import java.sql.Timestamp;

public class HistorialPropietariosDTO {

    private String matriculaCoche;
    private String matriculaPersona;
    private String nombre;
    private String dni;
    private String genero;
    private Timestamp fecha_compra;
    private Timestamp fecha_fin;
    private int idCoche;

    public HistorialPropietariosDTO() {
    }

    public HistorialPropietariosDTO(String matriculaCoche, String matriculaPersona, String nombre, String dni,
            String genero, Timestamp fecha_compra, Timestamp fecha_fin, int idCoche) {
        this.matriculaCoche = matriculaCoche;
        this.matriculaPersona = matriculaPersona;
        this.nombre = nombre;
        this.dni = dni;
        this.genero = genero;
        this.fecha_compra = fecha_compra;
        this.fecha_fin = fecha_fin;
        this.idCoche = idCoche;
    }

    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    public String getMatriculaPersona() {
        return matriculaPersona;
    }

    public void setMatriculaPersona(String matriculaPersona) {
        this.matriculaPersona = matriculaPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Timestamp getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Timestamp fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public Timestamp getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Timestamp fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getIdCoche() {
        return idCoche;
    }

    public void setIdCoche(int idCoche) {
        this.idCoche = idCoche;
    }


  
}
