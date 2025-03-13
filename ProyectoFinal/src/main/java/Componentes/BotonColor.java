package Componentes;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotonColor extends JButton {
    // Lista de colores a los que cambiará el botón
    private List<Color> colores = new ArrayList<>();
    private int indiceColorActual = 0;
    private String texto = "Púlsame"; // Por defecto
    private Color colorTexto = Color.RED; // Por defecto

    // Constructor por defecto
    public BotonColor() {
        this("Púlsame", Color.RED, Color.GREEN, Color.BLUE);
    }

    // Constructor con parámetros
    public BotonColor(String texto, Color... colores) {
        this.texto = texto != null ? texto : "Púlsame";
        for (Color color : colores) {
            this.colores.add(color);
        }

        configuracionBoton();
    }

    // Método para configurar el botón
    private void configuracionBoton() {
        setText(texto);
        setForeground(colorTexto);
        setBackground(colores.get(indiceColorActual));

        // Añadir el MouseListener para cambiar el color al pasar el ratón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cambiarColorBoton();
            }
        });

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarColor();
            }
        });
    }

    // Método para cambiar el color del botón
    private void cambiarColor() {
        // Cambiar al siguiente color de la lista
        indiceColorActual = (indiceColorActual + 1) % colores.size();
        setBackground(colores.get(indiceColorActual));
    }

    // Método para cambiar el color del botón de forma aleatoria
    public void cambiarColorBoton() {
        Random random = new Random();
        Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        setBackground(randomColor);

        // Calcular contraste para el texto
        double luminancia = (0.299 * randomColor.getRed() + 0.587 * randomColor.getGreen() + 0.114 * randomColor.getBlue()) / 255;
        setForeground(luminancia > 0.5 ? Color.BLACK : Color.WHITE);
    }

    // Método para cambiar el texto del botón
    public void setTexto(String texto) {
        this.texto = texto;
        setText(texto);
    }

    // Método para añadir colores al botón
    public void añadirColores(Color... colores) {
        for (Color color : colores) {
            this.colores.add(color);
        }
    }

    // Método para establecer el color del texto
    public void setColorTexto(Color color) {
        this.colorTexto = color;
        setForeground(colorTexto);
    }
}
