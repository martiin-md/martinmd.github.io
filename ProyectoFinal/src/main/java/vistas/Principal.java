package vistas;


import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import com.toedter.calendar.JCalendar;
import com.mycompany.GestionEventos.dao.BusquedaBD;

import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author martin
 */
public final class Principal extends javax.swing.JFrame {

    private EscritorioConFondo escritorio;
    private JTextField campoBusqueda;
    private String tipoUsuario;
    private JInternalFrame ventanaCalendario;
    private JCalendar calendario;

    BusquedaBD busquedaBD = new BusquedaBD();

    public Principal(String tipoUsuario) {
        super("Ibiza Event Manager - Ventana Principal");
        setTitle("Gestión de Eventos - Ibiza");

        this.tipoUsuario = tipoUsuario;

        escritorio = new EscritorioConFondo("src/main/java/recursos/pachaIbz.png");
        add(escritorio, BorderLayout.CENTER);

        setJMenuBar(crearMenuSuperior());
        add(crearBarraInferior(), BorderLayout.SOUTH);

        // Manejo del cierre de ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSalida();
            }
        });

        // Agregar evento para abrir la ayuda con F1
        getRootPane().registerKeyboardAction(
                e -> metodoAyuda(),
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setFocusable(true);
        requestFocus();
        setSize(2000, 820);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private Principal() {
    }

    public JMenuBar crearMenuSuperior() {
        JMenuBar menuBar = new JMenuBar();

        // Menú de opciones
        JMenu menuGestion = new JMenu("Gestión");

        // Comprobación para poder entrar a la gestion de Usuarios segun si es Admin
        System.out.println("Tipo de usuario: " + tipoUsuario);
        if ("Admin".equals(tipoUsuario)) {
            JMenuItem itemUsuarios = new JMenuItem("Usuarios");
            itemUsuarios.addActionListener(e -> escritorio.abrirVentana(new Usuarios()));
            menuGestion.add(itemUsuarios);
        }
        
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuGestion.add(itemCerrarSesion);


        JMenuItem itemAyuda = new JMenuItem("Ayuda");
        itemAyuda.addActionListener(e -> metodoAyuda());
        menuGestion.add(itemAyuda);

        menuBar.add(menuGestion);

        // Campo de búsqueda
        campoBusqueda = new JTextField(15);
        menuBar.add(campoBusqueda);

        JButton botonBuscar = new JButton("🔍");
        botonBuscar.addActionListener(e -> realizarBusqueda());
        menuBar.add(botonBuscar);

        return menuBar;
    }

    public JToolBar crearBarraInferior() {
        JToolBar barraTareas = new JToolBar();
        barraTareas.setFloatable(false);

        JButton botonEventos = new JButton("Eventos");
        botonEventos.addActionListener(e -> escritorio.abrirVentana(new Eventos()));
        barraTareas.add(botonEventos);

        JButton botonArtistas = new JButton("Artistas");
        botonArtistas.addActionListener(e -> escritorio.abrirVentana(new Artistas()));
        barraTareas.add(botonArtistas);

        JButton botonReservas = new JButton("Reservas");
        botonReservas.addActionListener(e -> escritorio.abrirVentana(new Reservas()));
        barraTareas.add(botonReservas);

        barraTareas.add(Box.createHorizontalGlue());

        ImageIcon iconoSalir = cargarIcono("src/main/java/recursos/iconoSalir.png");
        // Redimensiono el icono a un tamaño adecuado
        Image imgSalir = iconoSalir.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon iconoSalirRedimensionar = new ImageIcon(imgSalir);

        JButton botonSalir = new JButton(iconoSalirRedimensionar);
        botonSalir.addActionListener(e -> confirmarSalida());
        barraTareas.add(botonSalir);

        return barraTareas;
    }

    public class EscritorioConFondo extends JDesktopPane {

        private Image fondo;

        public EscritorioConFondo(String rutaImagen) {
            try {
                fondo = new ImageIcon(rutaImagen).getImage();
            } catch (Exception e) {
                System.out.println("Error cargando imagen de fondo: " + e.getMessage());
            }

            ImageIcon iconoCambiarFondo = cargarIcono("src/main/java/recursos/iconoCarpeta.png");
            // Redimensiono el icono a un tamaño adecuado
            Image img = iconoCambiarFondo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon iconoCambiarFondoRedimensionado = new ImageIcon(img);

            ImageIcon iconoCalendario = cargarIcono("src/main/java/recursos/iconoCalendario.png");
            // Redimensiono el icono a un tamaño adecuado
            Image imgCalendario = iconoCalendario.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon iconoCalendarioRedimensionar = new ImageIcon(imgCalendario);

            // Botón con solo el icono (sin texto)
            JButton botonCambiarFondo = new JButton(iconoCambiarFondoRedimensionado);

            // Ajustar el tamaño del botón y quitar el texto
            botonCambiarFondo.setPreferredSize(new Dimension(40, 40)); // Ajusta el tamaño del botón
            botonCambiarFondo.setText(""); // Eliminar el texto del botón            
            botonCambiarFondo.setBounds(10, 10, 40, 40);
            botonCambiarFondo.addActionListener(e -> abrirVentanaFondo());
            this.add(botonCambiarFondo);

            JButton botonCalendario = new JButton(iconoCalendarioRedimensionar);
            botonCalendario.setBounds(60, 10, 40, 40);
            botonCalendario.addActionListener(e -> mostrarCalendario());
            this.add(botonCalendario);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (fondo != null) {
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        }

        public void abrirVentana(JInternalFrame ventana) {
            for (JInternalFrame frame : getAllFrames()) {
                if (frame.getClass().equals(ventana.getClass())) {
                    frame.toFront();
                    try {
                        frame.setSelected(true);
                    } catch (Exception ignored) {
                    }
                    return;
                }
            }
            add(ventana);
            ventana.setVisible(true);
        }

        public void abrirVentanaFondo() {
            JInternalFrame ventanaFondo = new JInternalFrame("Cambiar Fondo", true, true, true, true);
            ventanaFondo.setLayout(new FlowLayout());
            ventanaFondo.setSize(400, 200);

            JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
            btnSeleccionarImagen.addActionListener(e -> seleccionarFondo());
            ventanaFondo.add(btnSeleccionarImagen);

            ventanaFondo.setVisible(true);
            add(ventanaFondo);
        }

        public void seleccionarFondo() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "gif"));
            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                fondo = new ImageIcon(archivo.getAbsolutePath()).getImage();
                repaint();
            }
        }

        public void mostrarCalendario() {

            // Si la ventana del calendario ya está abierta, no la creamos de nuevo
            if (ventanaCalendario != null && ventanaCalendario.isVisible()) {
                ventanaCalendario.toFront();
                return;
            }

            // Crear la ventana del calendario
            ventanaCalendario = new JInternalFrame("Calendario de Eventos-Reservas", true, true, true, true);
            ventanaCalendario.setSize(450, 350);
            ventanaCalendario.setLayout(new BorderLayout());

            // Crear el componente JCalendar
            calendario = new JCalendar();
            calendario.setPreferredSize(new Dimension(400, 250));

            // Agregar el calendario a la ventana
            ventanaCalendario.add(calendario, BorderLayout.CENTER);

            // Hacer visible la ventana
            ventanaCalendario.setVisible(true);
            add(ventanaCalendario);
        }
    }

    public void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Seguro que quieres cerrar sesión?", "Cerrar sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra la ventana actual

            // Volver a la ventana de Login
            SwingUtilities.invokeLater(() -> {
                new Login().setVisible(true);  // Asegúrate de que `Login` sea tu ventana de inicio de sesión
            });
        }
    }

    public void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(
                this, "¿Quieres salir o solo cerrar sesión?", "Confirmar acción",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // Salir completamente
        } else if (opcion == JOptionPane.NO_OPTION) {
            cerrarSesion(); // Volver al login
        }
    }

    // Método para realizar la búsqueda
    public void realizarBusqueda() {
        String consulta = campoBusqueda.getText().trim();
        if (!consulta.isEmpty()) {
            // Llamo a la clase BusquedaBD para realizar la búsqueda
            busquedaBD.realizarBusqueda(consulta);
        } else {
            JOptionPane.showMessageDialog(this, "Introduce un término de búsqueda.");
        }
    }

    public void metodoAyuda() {
        try {
            URL hsURL = getClass().getClassLoader().getResource("help/help_set.hs");
            if (hsURL == null) {
                throw new FileNotFoundException("No se encontró el archivo helpset.hs en el classpath.");
            }

            HelpSet hs = new HelpSet(null, hsURL);
            HelpBroker hb = hs.createHelpBroker();
            hb.setDisplayed(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo cargar la ayuda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar iconos
    public ImageIcon cargarIcono(String ruta) {
        return new ImageIcon(ruta);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
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
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    // End of variables declaration//GEN-END:variables
}
