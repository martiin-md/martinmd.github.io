/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import Componentes.TablaPaginada;
import controlador.EventoControlador;
import dao.EventoDao;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import util.GeneradorInformes;

/**
 *
 * @author martin
 */
public class Eventos extends JInternalFrame {

    TablaPaginada tablaPaginada;
    GeneradorInformes generadorInforme = new GeneradorInformes();
    EventoControlador eventoControlador = new EventoControlador();

    /**
     * Creates new form Evento
     */
    public Eventos() {
        super("Gestionar Eventos");
        setSize(700, 500);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setBackground(new Color(200, 230, 255)); // Azul claro
        setLayout(new BorderLayout());

        // Título
        JLabel etiquetTitulo = new JLabel("Gestión de Eventos", JLabel.CENTER);
        etiquetTitulo.setForeground(Color.RED);

        etiquetTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        etiquetTitulo.setBackground(Color.WHITE); // Azul claro

        add(etiquetTitulo, BorderLayout.NORTH);

        // Obtener datos de la base de datos
        String[] columnas = {"id", "nombre", "fecha", "ubicacion", "categoria", "precio", "id_organizador"};
        List<Object[]> datos = EventoDao.obtenerEventos();

        // Inserto la tabla con paginación
        this.tablaPaginada = new TablaPaginada(columnas, datos);
        add(tablaPaginada, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotones.setBackground(new Color(200, 230, 255)); // Azul claro

        JButton botonCrearEvento = new JButton("Crear Evento");
        JButton botonModificarEvento = new JButton("Modificar Evento");
        JButton botonEliminarEvento = new JButton("Eliminar Evento");

        JButton botonListaEvento = new JButton("Exportar Lista Eventos");
        JButton botonGraficoVentas = new JButton("Exportar Gráfico Ventas-Reservas");
        JButton botonListaGenero = new JButton("Exportar Lista por Género");

        panelBotones.add(botonCrearEvento);
        panelBotones.add(botonModificarEvento);
        panelBotones.add(botonEliminarEvento);

        panelBotones.add(botonListaEvento);
        panelBotones.add(botonGraficoVentas);
        panelBotones.add(botonListaGenero);

        //Dar Estilos a los botones
        estilizarBoton(botonCrearEvento);
        estilizarBoton(botonModificarEvento);
        estilizarBoton(botonEliminarEvento);
        estilizarBoton(botonListaEvento);
        estilizarBoton(botonGraficoVentas);
        estilizarBoton(botonListaGenero);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de los botones
        botonListaEvento.addActionListener(e -> {
            generadorInforme.reporteEventos();
            JOptionPane.showMessageDialog(this, "Exportando lista de eventos...");
        });

        botonGraficoVentas.addActionListener(e -> {
            generadorInforme.reporteGraficoVentasReservas();
            JOptionPane.showMessageDialog(this, "Exportando gráfico de ventas y reservas...");
        });

        botonListaGenero.addActionListener(e -> {
            generadorInforme.reporteListaGenero();
            JOptionPane.showMessageDialog(this, "Exportando lista por género...");
        });

        botonCrearEvento.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo Ventana Para la Creación de Eventos");
            añadirEvento();
        });

        botonModificarEvento.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abriendo Ventana Para la Modificación de Eventos");
            actualizarEvento();
        });

        botonEliminarEvento.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "¿Estas seguro de la Eliminación", "Elininar Evento", JOptionPane.OK_CANCEL_OPTION);
            eliminarEvento();
        });
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void añadirEvento() {
        JTextField nombreField = new JTextField();
        JTextField fechaField = new JTextField();
        JTextField ubicacionField = new JTextField();
        JTextField categoriaField = new JTextField();
        JTextField precioField = new JTextField();
        JTextField idOrganizadorField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Fecha (YYYY-MM-DD HH:MM:SS):"));
        panel.add(fechaField);
        panel.add(new JLabel("Ubicación:"));
        panel.add(ubicacionField);
        panel.add(new JLabel("Categoría:"));
        panel.add(categoriaField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);
        panel.add(new JLabel("ID Organizador:"));
        panel.add(idOrganizadorField);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Evento", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombre = nombreField.getText();
                String fechaTexto = fechaField.getText();
                String ubicacion = ubicacionField.getText();
                String categoria = categoriaField.getText();
                float precio = Float.parseFloat(precioField.getText());
                Integer idOrganizador = idOrganizadorField.getText().isEmpty() ? null : Integer.parseInt(idOrganizadorField.getText());

                boolean agregado = eventoControlador.agregarNuevoEvento(nombre, fechaTexto, ubicacion, categoria, precio, idOrganizador);

                if (agregado) {
                    JOptionPane.showMessageDialog(this, "Evento agregado con éxito.");
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el evento.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEvento() {
        if (tablaPaginada == null || tablaPaginada.tabla == null) {
            JOptionPane.showMessageDialog(this, "Error: la tabla no está inicializada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int filaSeleccionada = tablaPaginada.tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int indiceReal = (tablaPaginada.paginaActual - 1) * tablaPaginada.filasPorPagina + filaSeleccionada;
        if (indiceReal < 0 || indiceReal >= tablaPaginada.datos.size()) {
            JOptionPane.showMessageDialog(this, "Error: índice inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] filaDatos = tablaPaginada.datos.get(indiceReal);
        if (filaDatos[0] == null || !(filaDatos[0] instanceof Integer)) {
            JOptionPane.showMessageDialog(this, "El ID del evento es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idEvento = (int) filaDatos[0];
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que desea eliminar este evento?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = eventoControlador.eliminarEvento(idEvento);

            if (eliminado) {
                tablaPaginada.eliminarFila(indiceReal);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Evento eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: No se pudo eliminar el evento. \nVerifique:\n"
                        + "- Que exista el evento en la base de datos.\n"
                        + "- Que no haya reservas asociadas.\n"
                        + "- Los permisos de la base de datos.",
                        "Error Crítico",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void actualizarEvento() {
        if (tablaPaginada == null || tablaPaginada.tabla == null) {
            JOptionPane.showMessageDialog(this, "Error: la tabla no está inicializada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int filaSeleccionada = tablaPaginada.tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento para modificar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int indiceReal = (tablaPaginada.paginaActual - 1) * tablaPaginada.filasPorPagina + filaSeleccionada;
        if (indiceReal < 0 || indiceReal >= tablaPaginada.datos.size()) {
            JOptionPane.showMessageDialog(this, "Error: índice inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] filaDatos = tablaPaginada.datos.get(indiceReal);
        if (filaDatos[0] == null || !(filaDatos[0] instanceof Integer)) {
            JOptionPane.showMessageDialog(this, "El ID del evento es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idEvento = (int) filaDatos[0];

        JTextField nombreField = new JTextField(filaDatos[1].toString());
        JTextField fechaField = new JTextField(filaDatos[2].toString());
        JTextField ubicacionField = new JTextField(filaDatos[3].toString());
        JTextField categoriaField = new JTextField(filaDatos[4].toString());
        JTextField precioField = new JTextField(filaDatos[5].toString());
        JTextField idOrganizadorField = new JTextField(filaDatos[6] != null ? filaDatos[6].toString() : "");

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Fecha (YYYY-MM-DD HH:MM:SS):"));
        panel.add(fechaField);
        panel.add(new JLabel("Ubicación:"));
        panel.add(ubicacionField);
        panel.add(new JLabel("Categoría:"));
        panel.add(categoriaField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);
        panel.add(new JLabel("ID Organizador:"));
        panel.add(idOrganizadorField);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Modificar Evento", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nombre = nombreField.getText();
                String fechaTexto = fechaField.getText();
                String ubicacion = ubicacionField.getText();
                String categoria = categoriaField.getText();
                float precio = Float.parseFloat(precioField.getText());
                Integer idOrganizador = idOrganizadorField.getText().isEmpty() ? null : Integer.parseInt(idOrganizadorField.getText());

                boolean actualizado = eventoControlador.actualizarEvento(idEvento, nombre, fechaTexto, ubicacion, categoria, precio, idOrganizador);

                if (actualizado) {
                    JOptionPane.showMessageDialog(this, "Evento actualizado con éxito.");
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el evento.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        tablaPaginada.datos = EventoDao.obtenerEventos();
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

        jInternalFrame1 = new javax.swing.JInternalFrame();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
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
            java.util.logging.Logger.getLogger(Eventos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Eventos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Eventos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Eventos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Eventos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame jInternalFrame1;
    // End of variables declaration//GEN-END:variables
}
