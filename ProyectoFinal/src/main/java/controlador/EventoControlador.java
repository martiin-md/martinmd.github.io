package controlador;

import dao.EventoDao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoControlador {

    // Método para agregar un nuevo evento con validaciones
    public boolean agregarNuevoEvento(String nombre, String fecha, String ubicacion, String categoria, float precio, int idOrganizador) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre del evento no puede estar vacío.");
            return false;
        }

        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            System.out.println("Error: La ubicación no puede estar vacía.");
            return false;
        }

        // Convertir la fecha de String a LocalDateTime
        LocalDateTime fechaEvento;
        try {
            fechaEvento = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Formato de fecha inválido. Use 'YYYY-MM-DD HH:MM:SS'.");
            return false;
        }

        // Validar que el precio sea positivo
        if (precio < 0) {
            System.out.println("Error: El precio no puede ser negativo.");
            return false;
        }

        boolean resultado = EventoDao.agregarEvento(nombre, fechaEvento, ubicacion, categoria, BigDecimal.valueOf(precio), idOrganizador);

        if (resultado) {
            System.out.println("Evento agregado con éxito.");
        } else {
            System.out.println("Error al agregar el evento.");
        }

        return resultado;
    }

    // Método para obtener y mostrar eventos
    public void mostrarEventos() {
        List<Object[]> eventos = EventoDao.obtenerEventos();
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos registrados.");
        } else {
            System.out.println("Lista de Eventos:");
            for (Object[] evento : eventos) {
                // Formatear la fecha LocalDateTime para mostrarla de forma legible
                LocalDateTime fecha = (LocalDateTime) evento[2];
                String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println("- " + evento[1] + " | Fecha: " + fechaFormateada + " | Ubicación: " + evento[3] + " | Precio: $" + evento[5]);
            }
        }
    }

    // Método para actualizar un evento
    public boolean actualizarEvento(int idEvento, String nombre, String fecha, String ubicacion, String categoria, float precio, Integer idOrganizador) {
        if (idEvento <= 0) {
            System.out.println("Error: ID de evento inválido.");
            return false;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre del evento no puede estar vacío.");
            return false;
        }

        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            System.out.println("Error: La ubicación no puede estar vacía.");
            return false;
        }

        // Convertir la fecha de String a LocalDateTime
        LocalDateTime fechaEvento;
        try {
            fechaEvento = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Formato de fecha inválido. Use 'YYYY-MM-DD HH:MM:SS'.");
            return false;
        }

        // Validar que el precio sea positivo
        if (precio < 0) {
            System.out.println("Error: El precio no puede ser negativo.");
            return false;
        }

        boolean resultado = EventoDao.actualizarEvento(idEvento, nombre, fechaEvento, ubicacion, categoria, BigDecimal.valueOf(precio), idOrganizador);

        if (resultado) {
            System.out.println("Evento actualizado con éxito.");
        } else {
            System.out.println("Error al actualizar el evento. Verifica que el ID exista.");
        }

        return resultado;
    }

    // Método para eliminar un evento con verificación
    public boolean eliminarEvento(int idEvento) {
        if (idEvento <= 0) {
            System.out.println("Error: ID de evento inválido.");
            return false;
        }

        boolean resultado = EventoDao.eliminarEvento(idEvento);
        if (resultado) {
            System.out.println("Evento eliminado con éxito.");
        } else {
            System.out.println("No se pudo eliminar el evento. Verifica que el ID exista.");
        }
        return resultado;
    }
}
