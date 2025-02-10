package DTO;

public class PersonaDTO {

    private String nombre;
    private String genero;
    private String dni;

    // Constructor vacío
    public PersonaDTO(String nombre, String genero, String dni) {
        this.nombre = nombre;
        this.genero = genero;
        this.dni = dni;
    }

    // Getters y setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
