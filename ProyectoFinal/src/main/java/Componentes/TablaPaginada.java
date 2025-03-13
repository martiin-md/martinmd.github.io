package Componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class TablaPaginada extends JPanel {

    public JTable tabla;
    public DefaultTableModel modelo;
    public JButton btnPrimero, btnAnterior, btnSiguiente, btnUltimo;
    public JLabel lblPagina;
    public int paginaActual = 1;
    public int filasPorPagina = 5;
    public List<Object[]> datos;
    public String[] columnas;

    public TablaPaginada(String[] columnas, List<Object[]> datos) {
        this.columnas = columnas;
        this.datos = datos;
        setBackground(new Color(200, 230, 255));
        setOpaque(true);
        setLayout(new BorderLayout(10, 10));

        // Crear modelo de tabla
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        // Estilizar tabla
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(200, 230, 255)); // Fondo azul claro
        tabla.getTableHeader().setForeground(Color.BLACK); // Texto negro para mejor contraste

        tabla.setSelectionBackground(new Color(46, 204, 113));

        tabla.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel header = new JLabel(value.toString());
                header.setOpaque(true);
                header.setBackground(new Color(46, 204, 113)); // Fondo azul claro
                header.setForeground(Color.WHITE); // Texto negro para mejor contraste
                header.setFont(new Font("SansSerif", Font.BOLD, 14));
                header.setHorizontalAlignment(SwingConstants.CENTER);
                return header;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.getViewport().setBackground(new Color(200, 230, 255));
        scrollPane.setBackground(new Color(200, 230, 255));

        // Panel de paginación estilizado
        JPanel panelPaginacion = new JPanel();
        panelPaginacion.setBackground(new Color(200, 230, 255));
        panelPaginacion.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelPaginacion.setBorder(new EmptyBorder(5, 5, 5, 5));

        btnPrimero = new JButton("<<");
        btnAnterior = new JButton("<");
        lblPagina = new JLabel("Página 1");
        lblPagina.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSiguiente = new JButton(">");
        btnUltimo = new JButton(">>");

        estilizarBoton(btnPrimero);
        estilizarBoton(btnAnterior);
        estilizarBoton(btnSiguiente);
        estilizarBoton(btnUltimo);

        btnPrimero.addActionListener(e -> irPrimeraPagina());
        btnAnterior.addActionListener(e -> irPaginaAnterior());
        btnSiguiente.addActionListener(e -> irPaginaSiguiente());
        btnUltimo.addActionListener(e -> irUltimaPagina());

        panelPaginacion.add(btnPrimero);
        panelPaginacion.add(btnAnterior);
        panelPaginacion.add(lblPagina);
        panelPaginacion.add(btnSiguiente);
        panelPaginacion.add(btnUltimo);

        add(panelPaginacion, BorderLayout.SOUTH);

        actualizarTabla();
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);  // Limpiar tabla
        int totalPaginas = getTotalPaginas();

        if (paginaActual > totalPaginas && totalPaginas > 0) {
            paginaActual = totalPaginas;
        }

        int inicio = (paginaActual - 1) * filasPorPagina;
        int fin = Math.min(inicio + filasPorPagina, datos.size());

        for (int i = inicio; i < fin; i++) {
            modelo.addRow(datos.get(i));
        }

        lblPagina.setText("Página " + paginaActual + " de " + Math.max(totalPaginas, 1));
        actualizarBotones();
    }

    public int getTotalPaginas() {
        return (int) Math.ceil((double) datos.size() / filasPorPagina);
    }

    public void irPrimeraPagina() {
        paginaActual = 1;
        actualizarTabla();
    }

    public void irPaginaAnterior() {
        if (paginaActual > 1) {
            paginaActual--;
            actualizarTabla();
        }
    }

    public void irPaginaSiguiente() {
        if (paginaActual < getTotalPaginas()) {
            paginaActual++;
            actualizarTabla();
        }
    }

    public void irUltimaPagina() {
        paginaActual = getTotalPaginas();
        actualizarTabla();
    }

    public void actualizarBotones() {
        btnPrimero.setEnabled(paginaActual > 1);
        btnAnterior.setEnabled(paginaActual > 1);
        btnSiguiente.setEnabled(paginaActual < getTotalPaginas());
        btnUltimo.setEnabled(paginaActual < getTotalPaginas());
    }

    public void eliminarFila(int indiceReal) {
        datos.remove(indiceReal);
        actualizarTabla();
    }
}
