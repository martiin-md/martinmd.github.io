package vistas;

import dao.UsuarioDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;

/**
 *
 * @author martin
 */
public class Login extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JCheckBox checkboxMostrarContrasena;

    public Login() {
        super("Login - Ibiza Event Manager");
        setLayout(new BorderLayout());

        // Panel izquierdo con imagen de fondo
        JPanel panelIzquierdo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/main/java/recursos/hii ibz.jpg"); // Ruta de imagen
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelIzquierdo.setPreferredSize(new Dimension(480, 500));
        panelIzquierdo.setLayout(new BorderLayout());

        // Panel derecho con formulario
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Imagen de perfil
        ImageIcon iconPerfil = new ImageIcon("src/main/java/recursos/loginperfil.png"); // Ruta de imagen de perfil
        JLabel labelImagenPerfil = new JLabel(new ImageIcon(iconPerfil.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelDerecho.add(labelImagenPerfil, gbc);

        // Título LOGIN
        JLabel labelTitulo = new JLabel("LOGIN");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setForeground(new Color(0, 128, 128));
        gbc.gridy = 1;
        panelDerecho.add(labelTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        campoUsuario = new JTextField(15);
        panelDerecho.add(campoUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDerecho.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        campoPassword = new JPasswordField(15);
        panelDerecho.add(campoPassword, gbc);

        gbc.gridy = 4;
        checkboxMostrarContrasena = new JCheckBox("Mostrar contraseña");
        checkboxMostrarContrasena.setBackground(Color.WHITE);
        checkboxMostrarContrasena.setForeground(new Color(0, 128, 128));
        panelDerecho.add(checkboxMostrarContrasena, gbc);

        checkboxMostrarContrasena.addItemListener(e -> {
            if (checkboxMostrarContrasena.isSelected()) {
                campoPassword.setEchoChar((char) 0);
            } else {
                campoPassword.setEchoChar('•');
            }
        });

        JButton botonLogin = new JButton("Login");
        botonLogin.setBackground(new Color(255, 102, 0));
        botonLogin.setForeground(Color.WHITE);
        gbc.gridy = 5;
        panelDerecho.add(botonLogin, gbc);

        JButton botonCrearCuenta = new JButton("Crear Cuenta");
        botonCrearCuenta.setForeground(Color.WHITE);
        botonCrearCuenta.setBackground(new Color(30, 144, 255));
        gbc.gridy = 6;
        panelDerecho.add(botonCrearCuenta, gbc);

        JButton botonAyuda = new JButton("Ayuda");
        botonCrearCuenta.setForeground(Color.WHITE);
        botonCrearCuenta.setBackground(new Color(30, 144, 255));
        gbc.gridy = 7;
        panelDerecho.add(botonAyuda, gbc);

        botonLogin.addActionListener((e) -> {
            autenticarUsuario();
        });

        botonCrearCuenta.addActionListener((e) -> {
            crearUsuario();
        });

        botonAyuda.addActionListener(this::mostrarAyuda);

        // Agregar evento para abrir la ayuda con F1
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    mostrarAyuda(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        setFocusable(true);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void autenticarUsuario() {
        String usuario = campoUsuario.getText();
        String password = new String(campoPassword.getPassword());

        UsuarioDao usuarioDAO = new UsuarioDao();

        // Autenticar el usuario y obtener el tipo de Usuario
        if (usuarioDAO.autenticarUsuario(usuario, password)) {
            String tipoUsuario = usuarioDAO.obtenerRolPorUsuario(usuario, password);

            if (tipoUsuario != null) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido, " + usuario + "!", "Acceso permitido", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                Principal p = new Principal(tipoUsuario);
                p.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Rol no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearUsuario() {
        CrearUsuario registro = new CrearUsuario();
        registro.setVisible(true);
        dispose(); // Cierra la ventana actual
    }

    private void mostrarAyuda(ActionEvent e) {
        try {
            // Use the classloader to load the helpset file from the classpath
            ClassLoader cl = PresentancionVista.class.getClassLoader();
            URL hsURL = getClass().getClassLoader().getResource("help/help_set.hs");

            if (hsURL == null) {
                throw new FileNotFoundException("No se encontró el archivo helpset.hs en el classpath.");
            }

            // Create the HelpSet and HelpBroker
            HelpSet hs = new HelpSet(null, getClass().getResource("/help/help_set.hs"));
            HelpBroker hb = hs.createHelpBroker();

            // Display the help
            hb.setDisplayed(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo cargar la ayuda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
