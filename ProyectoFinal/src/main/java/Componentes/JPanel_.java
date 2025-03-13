/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author martin
 */
public class JPanel_ extends JPanel implements Serializable {

    private File rutaImagen;
    private float opacidad;
    private JPanel imagenFondo;
    private boolean ratonPresionado = false;
    private Point puntoPresion;
    private ArrastreListener arrastreListener;

    public JPanel_() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (ratonPresionado) {
                    Point posicionActual = e.getPoint();

                    if (Math.abs(puntoPresion.x - posicionActual.x) > 50) {
                        if (arrastreListener != null) {
                            arrastreListener.arrastre(ratonPresionado);
                        }
                        {

                        }

                    }
                    ratonPresionado = false;

                }

            }

        });
    }

    public JPanel setImagenFondo(File get) {

        return imagenFondo;

    }

    public File getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(File rutaImagen) {
        this.rutaImagen = rutaImagen;
        repaint();
    }

    public float getOpacidad() {
        return opacidad;
    }

    public void setOpacidad(float opacidad) {
        this.opacidad = opacidad;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.getRutaImagen() != null && this.getRutaImagen().exists()) {

            ImageIcon imageIcon = new ImageIcon(this.getRutaImagen().getAbsolutePath());
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getOpacidad()));
            g.drawImage(imageIcon.getImage(), 0, 0, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }

    }

    public void addArrastreListener(ArrastreListener arrastreListener) {
        this.arrastreListener = arrastreListener;
    }

    public void removeArrastreListener() {
        this.arrastreListener = null;
    }

}
