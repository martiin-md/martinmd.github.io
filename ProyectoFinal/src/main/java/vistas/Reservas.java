/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import Componentes.TablaPaginada;
import controlador.ReservasControlador;
import dao.ReservasDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.GeneradorInformes;

/**
 *
 * @author martin
 */
public class Reservas extends JInternalFrame {

    TablaPaginada tablaPaginada;
    ReservasControlador reservasControlador = new ReservasControlador();
    GeneradorInformes generadorInformes = new GeneradorInformes();

    /**
     * Creates new form Reservas
     */
    public Reservas() {
        super("Gestion Reservas");
        setSize(700, 500);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setBackground(new Color(200, 230, 255)); // Azul claro

        setLayout(new BorderLayout());

        // Título
        JLabel etiquetaTitulo = new JLabel("Gestión de Reservas", JLabel.CENTER);
        etiquetaTitulo.setForeground(Color.RED);

        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(etiquetaTitulo, BorderLayout.NORTH);

        // Obtener datos de la base de datos
        String[] columnas = {"id", "nombre", "id_usuario", "id_evento", "cantidad", "personas", "fecha reserva"};
        List<Object[]> datos = ReservasDao.obtenerReservas();

        // Integrar la tabla con paginación
        tablaPaginada = new TablaPaginada(columnas, datos); // Usamos la variable de instancia
        add(tablaPaginada, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotones.setBackground(new Color(200, 230, 255)); // Azul claro

        JButton botonAñadir = new JButton("Añadir Reserva");
        JButton botonActualizar = new JButton("Actualizar Reserva");
        JButton botonEliminar = new JButton("Eliminar Reservas");

        JButton botonListaEvento = new JButton("Exportar Lista Reservas");
        JButton botonGraficoVentas = new JButton("Exportar Gráfico Reservas");
        JButton botonListaGenero = new JButton("Exportar Reservas por Fechas");

        estilizarBoton(botonAñadir);
        estilizarBoton(botonActualizar);
        estilizarBoton(botonEliminar);
        estilizarBoton(botonListaEvento);
        estilizarBoton(botonGraficoVentas);
        estilizarBoton(botonListaGenero);

        panelBotones.add(botonAñadir);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);

        panelBotones.add(botonListaEvento);
        panelBotones.add(botonGraficoVentas);
        panelBotones.add(botonListaGenero);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de los botones
        botonAñadir.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Abriendo Ventana Para la Creación de Reservas");

            añadirReserva();
        });

        botonActualizar.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Abriendo Ventana Para la Modificación de Reservas");
            actualizarReserva();
        });

        botonEliminar.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "¿Estas seguro de la Eliminación", "Elininar Reserva", JOptionPane.OK_CANCEL_OPTION);
            eliminarReserva();
        });

        botonListaEvento.addActionListener(e -> {
            generadorInformes.reporteListaReservas();
            JOptionPane.showMessageDialog(this, "Exportando lista de Reservas...");
        });

        botonGraficoVentas.addActionListener(e -> {
            generadorInformes.reporteGraficoReservasVentas();
            JOptionPane.showMessageDialog(this, "Exportando gráfico de reservas y ventas...");
        });

        botonListaGenero.addActionListener(e -> {
            generadorInformes.reporteReservasPorFechas();
            JOptionPane.showMessageDialog(this, "Exportando Reservas por Fechas...");
        });
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Método para añadir una nueva reserva
    public void añadirReserva() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre de la reserva:");
        String idUsuarioStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario:");
        String idEventoStr = JOptionPane.showInputDialog(this, "Ingrese el ID del evento:");
        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad de reservas:");
        String personasStr = JOptionPane.showInputDialog(this, "Ingrese el número de personas:");
        String fechaReserva = JOptionPane.showInputDialog(this, "Ingrese la fecha de la reserva (YYYY-MM-DD HH:MM:SS):");

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            int idEvento = Integer.parseInt(idEventoStr);
            int cantidad = Integer.parseInt(cantidadStr);
            int personas = Integer.parseInt(personasStr);

            boolean resultado = reservasControlador.agregarNuevaReserva(nombre, idUsuario, idEvento, cantidad, personas, fechaReserva);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Reserva agregada con éxito.");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la reserva.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Por favor ingrese números válidos para los IDs, cantidad y personas.");
        }
    }

    // Método para actualizar una reserva existente con selección de campos específicos
    public void actualizarReserva() {
        String idReservaStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la reserva a actualizar:");
        try {
            int idReserva = Integer.parseInt(idReservaStr);

            // Mostrar las opciones de campos a actualizar
            String[] opciones = {"Nombre", "ID Usuario", "ID Evento", "Cantidad", "Personas", "Fecha Reserva"};
            String campoSeleccionado = (String) JOptionPane.showInputDialog(this,
                    "Seleccione el campo que desea actualizar:",
                    "Actualizar Reserva",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            // Si el usuario no selecciona ningún campo
            if (campoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "No se seleccionó ningún campo.");
                return;
            }

            // Variables para los nuevos valores
            String nuevoNombre = null;
            Integer nuevoIdUsuario = null;
            Integer nuevoIdEvento = null;
            Integer nuevaCantidad = null;
            Integer nuevasPersonas = null;
            String nuevaFechaReserva = null;

            // Dependiendo de lo que elija el usuario, pedir solo ese campo
            switch (campoSeleccionado) {
                case "Nombre":
                    nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de la reserva:");
                    break;
                case "ID Usuario":
                    String idUsuarioStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo ID del usuario:");
                    nuevoIdUsuario = Integer.parseInt(idUsuarioStr);
                    break;
                case "ID Evento":
                    String idEventoStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo ID del evento:");
                    nuevoIdEvento = Integer.parseInt(idEventoStr);
                    break;
                case "Cantidad":
                    String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la nueva cantidad de reservas:");
                    nuevaCantidad = Integer.parseInt(cantidadStr);
                    break;
                case "Personas":
                    String personasStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo número de personas:");
                    nuevasPersonas = Integer.parseInt(personasStr);
                    break;
                case "Fecha Reserva":
                    nuevaFechaReserva = JOptionPane.showInputDialog(this, "Ingrese la nueva fecha de la reserva (YYYY-MM-DD HH:MM:SS):");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Error: Campo no válido.");
                    return;
            }

            // Llamada al controlador para actualizar la reserva, con los parámetros correspondientes
            boolean resultado = false;
            if (nuevoNombre != null) {
                resultado = reservasControlador.actualizarReserva(idReserva, nuevoNombre, nuevoIdUsuario, nuevoIdEvento, nuevaCantidad, nuevasPersonas, nuevaFechaReserva);
            } else if (nuevoIdUsuario != null) {
                resultado = reservasControlador.actualizarReserva(idReserva, nuevoNombre, nuevoIdUsuario, nuevoIdEvento, nuevaCantidad, nuevasPersonas, nuevaFechaReserva);
            } else if (nuevoIdEvento != null) {
                resultado = reservasControlador.actualizarReserva(idReserva, nuevoNombre, nuevoIdUsuario, nuevoIdEvento, nuevaCantidad, nuevasPersonas, nuevaFechaReserva);
            } else if (nuevaCantidad != null) {
                resultado = reservasControlador.actualizarReserva(idReserva, nuevoNombre, nuevoIdUsuario, nuevoIdEvento, nuevaCantidad, nuevasPersonas, nuevaFechaReserva);
            } else if (nuevasPersonas != null) {
                resultado = reservasControlador.actualizarReserva(idReserva, nuevoNombre, nuevoIdUsuario, nuevoIdEvento, nuevaCantidad, nuevasPersonas, nuevaFechaReserva);
            } else if (nuevaFechaReserva != null) {
                resultado = reservasControlador.actualizarReserva(idReserva, nuevoNombre, nuevoIdUsuario, nuevoIdEvento, nuevaCantidad, nuevasPersonas, nuevaFechaReserva);
            }

            // Mostrar mensaje de éxito o error
            if (resultado) {
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Reserva actualizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la reserva. Verifique los datos.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Por favor ingrese un valor numérico válido.");
        }
    }

    // Método para eliminar una reserva
    public void eliminarReserva() {
        String idReservaStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la reserva a eliminar:");
        try {
            int idReserva = Integer.parseInt(idReservaStr);

            boolean resultado = reservasControlador.eliminarReserva(idReserva);

            if (resultado) {
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Reserva eliminada con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la reserva.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Por favor ingrese un número válido para el ID de la reserva.");
        }
    }

    //Metodo para actualizar la tabla
    private void actualizarTabla() {
        tablaPaginada.datos = ReservasDao.obtenerReservas();
        tablaPaginada.actualizarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reservas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
