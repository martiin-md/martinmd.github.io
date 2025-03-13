package controlador;

import dao.ReservasDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author martin
 */
public class ReservasControlador {

    // Método para agregar una nueva reserva con validaciones
    public boolean agregarNuevaReserva(String nombre, int idUsuario, int idEvento, int cantidad, int personas, String fechaReserva) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre de la reserva no puede estar vacío.");
            return false;
        }

        if (idUsuario <= 0) {
            System.out.println("Error: El ID de usuario debe ser válido.");
            return false;
        }

        if (idEvento <= 0) {
            System.out.println("Error: El ID de evento debe ser válido.");
            return false;
        }

        if (cantidad <= 0) {
            System.out.println("Error: La cantidad debe ser mayor que cero.");
            return false;
        }

        if (personas <= 0) {
            System.out.println("Error: El número de personas debe ser mayor que cero.");
            return false;
        }

        // Convertir la fecha de String a LocalDateTime
        LocalDateTime fechaReservaDate;
        try {
            fechaReservaDate = LocalDateTime.parse(fechaReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Formato de fecha inválido. Use 'YYYY-MM-DD HH:MM:SS'.");
            return false;
        }

        boolean resultado = ReservasDao.agregarReserva(nombre, idUsuario, idEvento, cantidad, personas, fechaReservaDate);

        if (resultado) {
            System.out.println("Reserva agregada con éxito.");
        } else {
            System.out.println("Error al agregar la reserva.");
        }

        return resultado;
    }

    // Método para obtener y mostrar reservas
    public void mostrarReservas() {
        List<Object[]> reservas = ReservasDao.obtenerReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            System.out.println("Lista de Reservas:");
            for (Object[] reserva : reservas) {
                // Formatear la fecha LocalDateTime para mostrarla de forma legible
                LocalDateTime fecha = (LocalDateTime) reserva[6];
                String fechaFormateada = fecha != null ? fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "Fecha no disponible";
                System.out.println("- " + reserva[1] + " | Usuario ID: " + reserva[2] + " | Evento ID: " + reserva[3] + " | Cantidad: " + reserva[4] + " | Personas: " + reserva[5] + " | Fecha Reserva: " + fechaFormateada);
            }
        }
    }

    // Método para actualizar una reserva
// Método para actualizar una reserva, permitiendo actualizar solo los campos seleccionados
    public boolean actualizarReserva(int idReserva, String nombre, Integer idUsuario, Integer idEvento, Integer cantidad, Integer personas, String fechaReserva) {
        if (idReserva <= 0) {
            System.out.println("Error: ID de reserva inválido.");
            return false;
        }

        // Crear una bandera para verificar si al menos un campo fue actualizado
        boolean algunCampoActualizado = false;

        // Si se pasa un valor válido para cada campo, se actualiza solo ese campo
        if (nombre != null && !nombre.trim().isEmpty()) {
            algunCampoActualizado = true;
            // Actualizar el nombre de la reserva
            ReservasDao.actualizarReserva(idReserva, "nombre", nombre);
        }

        if (idUsuario != null && idUsuario > 0) {
            algunCampoActualizado = true;
            // Actualizar el ID de usuario de la reserva
            ReservasDao.actualizarReserva(idReserva, "id_usuario", idUsuario);
        }

        if (idEvento != null && idEvento > 0) {
            algunCampoActualizado = true;
            // Actualizar el ID de evento de la reserva
            ReservasDao.actualizarReserva(idReserva, "id_evento", idEvento);
        }

        if (cantidad != null && cantidad > 0) {
            algunCampoActualizado = true;
            // Actualizar la cantidad de reservas
            ReservasDao.actualizarReserva(idReserva, "cantidad", cantidad);
        }

        if (personas != null && personas > 0) {
            algunCampoActualizado = true;
            // Actualizar el número de personas
            ReservasDao.actualizarReserva(idReserva, "personas", personas);
        }

        if (fechaReserva != null && !fechaReserva.trim().isEmpty()) {
            try {
                LocalDateTime fechaReservaDate = LocalDateTime.parse(fechaReserva, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                algunCampoActualizado = true;
                // Actualizar la fecha de la reserva
                ReservasDao.actualizarReserva(idReserva, "fecha_reserva", fechaReservaDate);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Formato de fecha inválido. Use 'YYYY-MM-DD HH:MM:SS'.");
                return false;
            }
        }

        if (algunCampoActualizado) {
            System.out.println("Reserva actualizada con éxito.");
            return true;
        } else {
            System.out.println("Error: No se actualizó ningún campo. Asegúrese de ingresar al menos un valor válido.");
            return false;
        }
    }

    // Método para eliminar una reserva con verificación
    public boolean eliminarReserva(int idReserva) {
        if (idReserva <= 0) {
            System.out.println("Error: ID de reserva inválido.");
            return false;
        }

        boolean resultado = ReservasDao.eliminarReserva(idReserva);
        if (resultado) {
            System.out.println("Reserva eliminada con éxito.");
        } else {
            System.out.println("No se pudo eliminar la reserva. Verifica que el ID exista.");
        }
        return resultado;
    }
}
