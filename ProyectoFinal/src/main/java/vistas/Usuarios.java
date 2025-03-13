/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import Componentes.TablaPaginada;
import dao.UsuarioDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author martin
 */
public class Usuarios extends JInternalFrame {

    TablaPaginada tablaPaginada;

    /**
     * Creates new form Usuarios
     */
    public Usuarios() {
        setTitle("Gestion Usuarios");
        setSize(700, 500);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setBackground(new Color(200, 230, 255)); // Azul claro
        setLayout(new BorderLayout());

        // Título
        JLabel etiquetaTitulo = new JLabel("Gestión de Usuarios", JLabel.CENTER);
        etiquetaTitulo.setForeground(Color.RED);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(etiquetaTitulo, BorderLayout.NORTH);

        // Obtener datos de la base de datos
        String[] columnas = {"id", "nombre", "email", "password", "tipo", "fecha_registro"};
        List<Object[]> datos = UsuarioDao.obtenerUsuarios();

        // Integrar la tabla con paginación
        tablaPaginada = new TablaPaginada(columnas, datos); // Usamos la variable de instancia
        add(tablaPaginada, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBotones.setBackground(new Color(200, 230, 255)); // Azul claro
        JButton botonAñadir = new JButton("Añadir Usuario");
        JButton botonActualizar = new JButton("Modificar Usuario");
        JButton botonEliminar = new JButton("Eliminar Usuario");

        estilizarBoton(botonAñadir);
        estilizarBoton(botonActualizar);
        estilizarBoton(botonEliminar);

        panelBotones.add(botonAñadir);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de los botones
        botonAñadir.addActionListener((e) -> {
            añadirUsuario();
            JOptionPane.showMessageDialog(this, "Abriendo Ventana Para la Creación de Reservas");

        });

        botonActualizar.addActionListener((e) -> {
            actualizar();
            JOptionPane.showMessageDialog(this, "Abriendo Ventana Para la Modificación de Reservas");
        });

        botonEliminar.addActionListener((e) -> {
            eliminar();
            JOptionPane.showMessageDialog(this, "¿Estas seguro de la Eliminación", "Elininar Reserva", JOptionPane.OK_CANCEL_OPTION);
        });

    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Evento para añadir un nuevo usuario
    private void añadirUsuario() {
        // Crear un formulario o cuadro de diálogo para capturar los datos del usuario
        JPanel panelFormulario = new JPanel(new GridLayout(2, 3));

        JTextField nombreField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Admin", "Usuario"});

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(nombreField);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(emailField);
        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(passwordField);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(tipoCombo);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Añadir Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String tipo = (String) tipoCombo.getSelectedItem();

            // Llamar al método de la DAO para insertar el nuevo usuario en la base de datos
            boolean exito = UsuarioDao.añadirUsuario(nombre, email, password, tipo);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario añadido correctamente");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al añadir usuario");
            }
        }
    }

    private void actualizar() {
        int filaSeleccionada = tablaPaginada.tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para actualizar", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario = (int) tablaPaginada.modelo.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) tablaPaginada.modelo.getValueAt(filaSeleccionada, 1);
        String emailActual = (String) tablaPaginada.modelo.getValueAt(filaSeleccionada, 2);
        String passwordActual = (String) tablaPaginada.modelo.getValueAt(filaSeleccionada, 3);
        String tipoActual = (String) tablaPaginada.modelo.getValueAt(filaSeleccionada, 4);

        // Formulario de edición
        JTextField nombreField = new JTextField(nombreActual);
        JTextField emailField = new JTextField(emailActual);
        JTextField passwordField = new JTextField(passwordActual);
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Admin", "Usuario"});
        tipoCombo.setSelectedItem(tipoActual);

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(nombreField);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(emailField);
        panelFormulario.add(new JLabel("Password:"));
        panelFormulario.add(passwordField);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(tipoCombo);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Actualizar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = nombreField.getText();
            String nuevoEmail = emailField.getText();
            String nuevoPassword = passwordField.getText(); 
            String nuevoTipo = (String) tipoCombo.getSelectedItem();

            boolean exito = UsuarioDao.actualizarUsuario(idUsuario, nuevoNombre, nuevoEmail, nuevoPassword, nuevoTipo); 

            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario");
            }
        }
    }

    private void eliminar() {
        int filaSeleccionada = tablaPaginada.tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario = (int) tablaPaginada.modelo.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este usuario?", "Eliminar Usuario", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = UsuarioDao.eliminarUsuario(idUsuario);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario");
            }
        }
    }

    private void actualizarTabla() {
        tablaPaginada.datos = UsuarioDao.obtenerUsuarios();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
