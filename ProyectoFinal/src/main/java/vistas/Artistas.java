/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import Componentes.TablaPaginada;
import dao.ArtistasDao;
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
public class Artistas extends JInternalFrame {

    GeneradorInformes generadorInformes = new GeneradorInformes();

    /**
     * Creates new form AsistenteVista
     */
    public Artistas() {
        super("Gestion Artistas");
        setSize(700, 500);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setBackground(new Color(200, 230, 255)); 
        setLayout(new BorderLayout());

        // Título
        JLabel etiquetaTitulo = new JLabel("Gestión de Artistas", JLabel.CENTER);
        etiquetaTitulo.setForeground(Color.RED);

        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(etiquetaTitulo, BorderLayout.NORTH);

        // Obtener datos de la base de datos
        String[] columnas = {"id", "nombre", "categoria", "evento", "hora", "id_evento"};
        List<Object[]> datos = ArtistasDao.obtenerArtistas();

        // Integrar la tabla con paginación
        TablaPaginada tablaPaginada = new TablaPaginada(columnas, datos);
        add(tablaPaginada, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBotones.setBackground(new Color(200, 230, 255));

        JButton botonListaEvento = new JButton("Exportar Lista Artistas");
        JButton botonGraficoVentas = new JButton("Exportar Gráfico Artistas");
        JButton botonListaCategoria = new JButton("Exportar Artistas por Categoria");

        //Dar el estilos a los botones
        estilizarBoton(botonListaEvento);
        estilizarBoton(botonGraficoVentas);
        estilizarBoton(botonListaCategoria);

        panelBotones.add(botonListaEvento);
        panelBotones.add(botonGraficoVentas);
        panelBotones.add(botonListaCategoria);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de los botones
        botonListaEvento.addActionListener(e -> {
            generadorInformes.reporteArtistas();
            JOptionPane.showMessageDialog(this, "Exportando lista de Artistas...");
        });

        botonGraficoVentas.addActionListener(e -> {
            generadorInformes.reporteGraficoArtistasEventos();
            JOptionPane.showMessageDialog(this, "Exportando gráfico de Artistas de Eventos...");
        });

        botonListaCategoria.addActionListener(e -> {
            generadorInformes.reporteListaArtistasCategorias();
            JOptionPane.showMessageDialog(this, "Exportando Artistas por Categoria...");
        });
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
            java.util.logging.Logger.getLogger(Artistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Artistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Artistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Artistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Artistas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame jInternalFrame1;
    // End of variables declaration//GEN-END:variables
}
