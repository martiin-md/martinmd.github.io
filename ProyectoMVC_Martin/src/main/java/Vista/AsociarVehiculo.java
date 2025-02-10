package Vista;

import Controlador.ControladorPersona;
import DAO.ConexionBD;
import Services.Servicios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AsociarVehiculo extends JFrame {

    private JButton asociarVehiculo;
    private JCheckBox mostrarVehiculosAsociados;
    private JTable tablaPersona = new JTable();
    private JTable tablaVehiculo = new JTable();
    private DefaultTableModel modeloTablaPersona, modeloTablaVehiculo;
    private Servicios vehiculoServicio;

    public AsociarVehiculo(ControladorPersona controladorPersona) {
    }

    public AsociarVehiculo() throws SQLException {
        super("Asociar Vehículos");

        ConexionBD conn = new ConexionBD();
        vehiculoServicio = new Servicios(conn);

        inicializarComponentes();
        configurarEventos();
        cargarDatosTablas();
    }

    //Componentes de la ventana
    private void inicializarComponentes() {
        JPanel panelPersona = new JPanel(new BorderLayout()); //Panel para la tabla personas
        String[] columnasP = {"Nombre", "DNI", "Genero"};
        modeloTablaPersona = new DefaultTableModel(null, columnasP);
        tablaPersona.setModel(modeloTablaPersona);
        panelPersona.add(new JScrollPane(tablaPersona), BorderLayout.CENTER);

        JPanel panelVehiculo = new JPanel(new BorderLayout()); //Panel para la tabla vehiculos
        String[] columnasV = {"Matricula", "Año", "Marca", "Modelo"};
        modeloTablaVehiculo = new DefaultTableModel(null, columnasV);
        tablaVehiculo.setModel(modeloTablaVehiculo);
        panelVehiculo.add(new JScrollPane(tablaVehiculo), BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER)); //Panel del boton y del checkbox
        asociarVehiculo = new JButton("Asociar");
        asociarVehiculo.setBackground(Color.RED);
        asociarVehiculo.setForeground(Color.WHITE);
        panelBoton.add(asociarVehiculo);

        mostrarVehiculosAsociados = new JCheckBox("Vehiculos Asociados");
        mostrarVehiculosAsociados.setBackground(Color.YELLOW);
        mostrarVehiculosAsociados.setForeground(Color.BLACK);
        panelBoton.add(mostrarVehiculosAsociados);

        JPanel panelPrincipal = new JPanel(new BorderLayout()); //Panel principal
        panelPrincipal.add(panelPersona, BorderLayout.WEST);
        panelPrincipal.add(panelVehiculo, BorderLayout.EAST);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);
        setSize(1200, 500); //Tamaño de la ventana
        setLocationRelativeTo(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void realizarAsociacion() {
        int personaSeleccionada = tablaPersona.getSelectedRow();
        int vehiculoSeleccionado = tablaVehiculo.getSelectedRow();

        if (personaSeleccionada == -1 || vehiculoSeleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una persona y un vehículo para asociar.");
            return;
        }

        // Datos de la persona
        String nombre = (String) tablaPersona.getValueAt(personaSeleccionada, 0);
        String dni = (String) tablaPersona.getValueAt(personaSeleccionada, 1);
        String genero = (String) tablaPersona.getValueAt(personaSeleccionada, 2);

        // Datos del vehiculo
        String matricula = (String) tablaVehiculo.getValueAt(vehiculoSeleccionado, 0);
        int anio = (int) tablaVehiculo.getValueAt(vehiculoSeleccionado, 1);
        String marca = (String) tablaVehiculo.getValueAt(vehiculoSeleccionado, 2);
        String modelo = (String) tablaVehiculo.getValueAt(vehiculoSeleccionado, 3);

        // Verifica que los valores no sean nulos
        System.out.println("Persona seleccionada: " + nombre + ", Vehiculo seleccionado: " + matricula);

        // Llama al servicio para realizar la asociación
        boolean exito = vehiculoServicio.asociarVehiculoAPersona(nombre, dni, genero, matricula, anio, marca, modelo);
        if (exito) {
            JOptionPane.showMessageDialog(null, "Asociación exitosa.");
            cargarDatosTablas();  // Refresca las tablas para mostrar los cambios
        } else {
            JOptionPane.showMessageDialog(null, "Error al asociar el vehiculo. Intente nuevamente.");
        }
    }

    //=============================================
    //                  EVENTOS
    //=============================================
    private void configurarEventos() {
        asociarVehiculo.addActionListener(e -> realizarAsociacion()); //Función de asociar persona con vehiculo

        mostrarVehiculosAsociados.addActionListener(e -> cargarDatosTablas()); //Función que muestra los vehiculos asociados
    }

    //=============================================
    //                DATOS TABLA
    //=============================================
    private void cargarDatosTablas() {
        Object[][] listaPersonas = vehiculoServicio.obtenerPersonas(); //Llamamos al objeto de obtener personas
        actualizarTabla(listaPersonas);

        Object[][] listaVehiculos = mostrarVehiculosAsociados.isSelected() //Llamamos al objetos de mostrar los vehiculos asociados con personas
                ? vehiculoServicio.obtenerTodos() : vehiculoServicio.obtenerVehiculosLibres();
        actualizarTablaVehiculosLibres(listaVehiculos);

    }

    //==============================================
    //               MODELOS TABLAS
    //==============================================
    //actualizamos el modelo de la tabla persona
    public void actualizarTabla(Object[][] datos) {
        modeloTablaPersona.setDataVector(datos, new String[]{"Nombre", "DNI", "Genero"});
    }

    //actualizamos el modelo de la tabla vehiculos
    public void actualizarTablaVehiculosLibres(Object[][] datos) {
        modeloTablaVehiculo.setDataVector(datos, new String[]{"Matricula/Propietario", "Año/DNI", "Marca/Genero", "Modelo/Matricula"});
        
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
            java.util.logging.Logger.getLogger(AsociarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AsociarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AsociarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsociarVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControladorPersona controladorPersona = new ControladorPersona();
                    new AsociarVehiculo(controladorPersona).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(AsociarVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
