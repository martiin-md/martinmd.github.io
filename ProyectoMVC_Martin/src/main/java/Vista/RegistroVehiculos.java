/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import java.util.List;
import java.util.ArrayList;
import Controlador.ControladorPersona;
import Controlador.ControladorVehiculo;
import DAO.ConexionBD;
import DTO.PersonaDTO;
import DTO.VehiculoDTO;
import Services.Servicios;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author martin
 */
public class RegistroVehiculos extends JFrame {

    private final DefaultTableModel modeloTabla;
    private final JTable tablaRegistro;
    private final JTextField campoMatricula;
    private final JTextField campoAnioMatricula;
    private final JTextField campoNumeroVehiculos;
    private final JTextField totalPaginasField;
    private final JRadioButton hombre;
    private final JRadioButton mujer;
    private final JButton filtrosAvanzados;
    private final JButton botonAplicar;
    private final JComboBox<String> botonDiseño;
    private JComboBox<String> comboMarca;
    private JComboBox<String> comboModeloFiltro;
    private final JPanel panelPrincipal, panelPaginacion;
    private final Servicios vehiculoServicio;
    private int currentPage = 0;
    private final int pageSize = 10;
    private final JLabel labelPagina;
    private final JButton botonAnterior, botonSiguiente;

    public RegistroVehiculos(ControladorPersona controladorPersona1, ControladorVehiculo controladorPersona) throws SQLException {
        super("Registro de Vehiculos");

        //**************************************
        //       CONEXION BASE DE DATOS
        //**************************************
        ConexionBD conexionBD = new ConexionBD();
        vehiculoServicio = new Servicios(conexionBD);

        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //    COLUMNAS Y DATOS DE LA TABLA
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        String[] columnas = {"Propietario", "Matricula", "Año", "Marca", "Modelo", "Total Propietarios"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tablaRegistro = new JTable(modeloTabla);

        //-------------------------------
        //          PANEL TABLA
        //-------------------------------
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(new JScrollPane(tablaRegistro), BorderLayout.CENTER);
        tablaRegistro.setBackground(new Color(0, 123, 255));
        tablaRegistro.setForeground(Color.WHITE);
        cargarDatos();

        //-------------------------------
        //      PANEL PARA OPCIONES
        //-------------------------------
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS)); // Coloca los componentes verticalmente
        panelOpciones.setBackground(Color.BLUE); // Fondo azul para el panel

        // Configuracion del JComboBox de opciones
        JComboBox<String> comboOpciones = new JComboBox<>(new String[]{"Crear Persona", "Crear Vehiculo", "Editar", "Eliminar", "Asociar Vehiculo"});
        comboOpciones.setPreferredSize(new Dimension(150, 25)); // Tamaño pequeño para el JComboBox
        comboOpciones.setMaximumSize(new Dimension(150, 25)); // Limito el tamaño máximo
        comboOpciones.setBackground(Color.RED); // Fondo rojo para el combobox
        comboOpciones.setForeground(Color.WHITE); // Color de Texto blanco
        comboOpciones.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineo el combobox a la izquierda
        panelOpciones.add(comboOpciones);

        // Espacio entre el JComboBox y el boton
        panelOpciones.add(Box.createRigidArea(new Dimension(0, 5)));

        // Configuracion del botón "Historial Propietarios"
        JButton botonMostrarPropietarios = new JButton("Historial Propietarios");
        botonMostrarPropietarios.setPreferredSize(new Dimension(150, 25)); // Tamaño pequeño para el boton
        botonMostrarPropietarios.setMaximumSize(new Dimension(150, 25)); // Limita el tamaño maximo
        botonMostrarPropietarios.setBackground(Color.DARK_GRAY); // Fondo oscuro para el boton
        botonMostrarPropietarios.setForeground(Color.WHITE); // Color de Texto blanco

        // Cambiar el tamaño de los margenes internos para reducir el espacio dentro del boton
        botonMostrarPropietarios.setMargin(new Insets(2, 5, 2, 5));
        botonMostrarPropietarios.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineo el botón a la izquierda
        panelOpciones.add(botonMostrarPropietarios); // Añade el botón al panel

        // Espacio entre el boton y el ComboBox de diseño
        panelOpciones.add(Box.createRigidArea(new Dimension(0, 5)));

        // Etiqueta para el ComboBox de diseño
        JLabel etiquetaDiseño = new JLabel("Diseño:");
        etiquetaDiseño.setForeground(Color.WHITE); // Color de texto blanco
        etiquetaDiseño.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinea la etiqueta a la izquierda
        panelOpciones.add(etiquetaDiseño);

        // Configuracion del ComboBox de diseño
        botonDiseño = new JComboBox<>(new String[]{"Predeterminado", "Oscuro"});
        botonDiseño.setPreferredSize(new Dimension(150, 25)); // Tamaño pequeño para el JComboBox
        botonDiseño.setMaximumSize(new Dimension(150, 25)); // Limito el tamaño máximo
        botonDiseño.setBackground(Color.BLACK); // Fondo negro
        botonDiseño.setForeground(Color.WHITE); // Texto blanco
        botonDiseño.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinea el combo box a la izquierda
        panelOpciones.add(botonDiseño); // Añade el ComboBox de diseño debajo de la etiqueta

        //==========================================
        //  ACCIONES CREAR,ELININAR,EDITAR,ASOCIAR
        //==========================================
        comboOpciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcionSeleccionada = (String) comboOpciones.getSelectedItem();

                switch (opcionSeleccionada) {
                    case "Crear Persona" -> {
                        JPanel panelCrearPersona = new JPanel(new GridLayout(4, 2));
                        panelCrearPersona.add(new JLabel("Nombre"));
                        campoMatricula.setText("");
                        panelCrearPersona.add(campoMatricula);

                        panelCrearPersona.add(new JLabel("DNI"));
                        JTextField campoDNIpersona = new JTextField(20);
                        panelCrearPersona.add(campoDNIpersona);

                        panelCrearPersona.add(new JLabel("Genero"));
                        JRadioButton radioButonHombre = new JRadioButton("HOMBRE");
                        JRadioButton radioButonMujer = new JRadioButton("MUJER");
                        ButtonGroup grupoGenero = new ButtonGroup();
                        grupoGenero.add(radioButonHombre);
                        grupoGenero.add(radioButonMujer);
                        panelCrearPersona.add(radioButonHombre);
                        panelCrearPersona.add(radioButonMujer);

                        int opcionCrearPersona = JOptionPane.showConfirmDialog(null, panelCrearPersona,
                                "Crear Persona", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (opcionCrearPersona == JOptionPane.OK_OPTION) {
                            String nombre = campoMatricula.getText();
                            String dni = campoDNIpersona.getText();
                            String genero = radioButonHombre.isSelected() ? "Hombre" : radioButonMujer.isSelected() ? "Mujer" : "";

                            if (nombre.isEmpty() || dni.isEmpty() || genero.isEmpty()) {
                                JOptionPane.showMessageDialog(RegistroVehiculos.this,
                                        "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                PersonaDTO personaDTO = new PersonaDTO(nombre, genero, dni);
                                try {
                                    vehiculoServicio.añadirPersona(personaDTO);
                                } catch (SQLException ex) {
                                    Logger.getLogger(RegistroVehiculos.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                cargarDatos();
                            }
                        }
                    }

                    case "Crear Vehiculo" -> {
                        JPanel panelCrearPersona = new JPanel(new GridLayout(4, 2));
                        panelCrearPersona.add(new JLabel("Matricula"));
                        panelCrearPersona.add(campoMatricula);

                        panelCrearPersona.add(new JLabel("Año"));
                        panelCrearPersona.add(campoAnioMatricula);

                        List<String> marcas = vehiculoServicio.obtenerMarcas();
                        List<String> modelos = vehiculoServicio.obtenerModelos();

                        if (marcas.isEmpty() && modelos.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "ERROR: Introduce una marca y modelo", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            marcas.add(0, "Todas");
                            modelos.add(0, "Todos");

                            // FILTRO MARCA
                            panelCrearPersona.add(new JLabel("Marca"));
                            comboMarca = new JComboBox<>(marcas.toArray(new String[0]));
                            panelCrearPersona.add(comboMarca);

                            // FILTRO MODELO
                            panelCrearPersona.add(new JLabel("Modelo"));
                            comboModeloFiltro = new JComboBox<>(modelos.toArray(new String[0]));
                            panelCrearPersona.add(comboModeloFiltro);
                        }

                        int opcionCrearVehiculo = JOptionPane.showConfirmDialog(null, panelCrearPersona, "Crear Vehiculo",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (opcionCrearVehiculo == JOptionPane.OK_OPTION) {
                            String matricula = campoMatricula.getText();
                            String anioTexto = campoAnioMatricula.getText();
                            String marca = (String) comboMarca.getSelectedItem();
                            String modelo = (String) comboModeloFiltro.getSelectedItem();

                            if (matricula.isEmpty() || anioTexto.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
                                JOptionPane.showMessageDialog(RegistroVehiculos.this,
                                        "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                try {
                                    int anio = Integer.parseInt(anioTexto); // Intentamos convertir el año a un número
                                    VehiculoDTO vehiculoDTO = new VehiculoDTO(modelo, marca, anio, matricula, modelo);
                                    vehiculoServicio.añadir(vehiculoDTO);
                                    cargarDatos();
                                } catch (NumberFormatException ea) {
                                    JOptionPane.showMessageDialog(RegistroVehiculos.this,
                                            "El año debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(RegistroVehiculos.this,
                                            "Error al añadir el vehículo a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }

                    case "Editar" -> {
                        int filaSeleccionada = tablaRegistro.getSelectedRow();
                        if (filaSeleccionada == -1) {
                            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        // Obtener valores de la fila seleccionada
                        String matriculaSeleccionada = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
                        int anioSeleccionado = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                        String marcaSeleccionada = (String) modeloTabla.getValueAt(filaSeleccionada, 5);
                        String modeloSeleccionado = (String) modeloTabla.getValueAt(filaSeleccionada, 6);

                        JPanel panelEditar = new JPanel(new GridLayout(4, 2));
                        panelEditar.add(new JLabel("Matrícula:"));
                        JTextField campoMatricula = new JTextField(matriculaSeleccionada, 20);
                        panelEditar.add(campoMatricula);

                        panelEditar.add(new JLabel("Año:"));
                        JTextField txtAnoEditar = new JTextField(String.valueOf(anioSeleccionado), 4);
                        panelEditar.add(txtAnoEditar);

                        // Obtener listas de marcas y modelos desde el servicio
                        List<String> marcas = vehiculoServicio.obtenerMarcas();
                        List<String> modelos = vehiculoServicio.obtenerModelos();
                        marcas.add(0, "Seleccione");
                        modelos.add(0, "Seleccione");

                        panelEditar.add(new JLabel("Marca:"));
                        JComboBox<String> comboMarcaEditar = new JComboBox<>(marcas.toArray(new String[0]));
                        comboMarcaEditar.setSelectedItem(marcaSeleccionada);
                        panelEditar.add(comboMarcaEditar);

                        panelEditar.add(new JLabel("Modelo:"));
                        JComboBox<String> comboModeloEditar = new JComboBox<>(modelos.toArray(new String[0]));
                        comboModeloEditar.setSelectedItem(modeloSeleccionado);
                        panelEditar.add(comboModeloEditar);

                        // Mostrar el cuadro de diálogo para editar
                        int opcionEditar = JOptionPane.showConfirmDialog(null, panelEditar, "Editar Vehículo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (opcionEditar == JOptionPane.OK_OPTION) {
                            // Validación y asignación de nuevos valores
                            String nuevaMatricula = campoMatricula.getText();
                            int nuevoAnio;
                            try {
                                nuevoAnio = Integer.parseInt(txtAnoEditar.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Por favor, ingresa un año válido.");
                                return;
                            }

                            String nuevaMarca = (String) comboMarcaEditar.getSelectedItem();
                            String nuevoModelo = (String) comboModeloEditar.getSelectedItem();

                            // Verificación de que no falten datos
                            if (nuevaMatricula.isEmpty() || nuevaMarca.equals("Seleccione") || nuevoModelo.equals("Seleccione")) {
                                JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Crear el objeto VehiculoDTO usando el constructor que incluya todos los campos necesarios
                            VehiculoDTO vehiculo = new VehiculoDTO(nuevaMatricula, nuevoAnio, nuevaMarca, nuevoModelo);

                            // Actualizar en la base de datos
                            try {
                                vehiculoServicio.actualizar(vehiculo); // Llama al método sin asignarlo a boolean
                                JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente.");
                                cargarDatos(); // Recargar datos en la tabla
                            } catch (SQLException ex) {
                                Logger.getLogger(RegistroVehiculos.class.getName()).log(Level.SEVERE, "Error actualizando vehículo", ex);
                                JOptionPane.showMessageDialog(null, "Error al actualizar el vehículo en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                    case "Eliminar" -> {
                        String matriculaEliminar = JOptionPane.showInputDialog("Ingresa la matrícula para eliminar vehículo:");

                        if (matriculaEliminar != null && !matriculaEliminar.isEmpty()) {
                            // Validación del formato de matrícula (4 dígitos seguidos de 3 letras mayúsculas)
                            String regexMatricula = "^[0-9]{4}[A-Z]{3}$";
                            if (!matriculaEliminar.matches(regexMatricula)) {
                                JOptionPane.showMessageDialog(null, "La matrícula debe tener 4 dígitos y 3 letras mayúsculas.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                int opcionEliminar = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el vehículo con matrícula "
                                        + matriculaEliminar + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                                if (opcionEliminar == JOptionPane.YES_OPTION) {
                                    try {
                                        vehiculoServicio.eliminar(matriculaEliminar);
                                        cargarDatos(); // Recargar datos si la eliminación es exitosa
                                    } catch (SQLException ex) {
                                        Logger.getLogger(RegistroVehiculos.class.getName()).log(Level.SEVERE, "Error al eliminar", ex);
                                        JOptionPane.showMessageDialog(null, "Error al eliminar el vehículo en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        } else if (matriculaEliminar != null) {
                            JOptionPane.showMessageDialog(null, "Por favor, introduce una matrícula válida.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    case "Asociar Vehiculo" -> {
                        try {
                            AsociarVehiculo av = new AsociarVehiculo();
                            av.setVisible(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(RegistroVehiculos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }


                    default -> {
                    }
                }
            }
        });

        //-------------------------------
        //        PANEL PRINCIPAL
        //-------------------------------
        panelPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        campoMatricula = new JTextField(10);

        // Agrego la imagen al panel principal
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\marti\\OneDrive\\Escritorio\\logoCoches.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(130, 80, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        JLabel imageLabel = new JLabel(imageIcon);
        panelPrincipal.add(imageLabel);

        panelPrincipal.add(new JLabel("Nombre:"));
        panelPrincipal.add(campoMatricula);

        hombre = new JRadioButton("Hombre");
        mujer = new JRadioButton("Mujer");
        ButtonGroup grupoGenero = new ButtonGroup();

        grupoGenero.add(hombre);
        grupoGenero.add(mujer);
        panelPrincipal.add(hombre);
        panelPrincipal.add(mujer);

        panelPrincipal.add(new JLabel("Marca:"));
        List<String> marcas = new ArrayList<>();
        List<String> modelos = new ArrayList<>();
        marcas = vehiculoServicio.obtenerMarcas();
        modelos = vehiculoServicio.obtenerModelos();

        marcas.add(0, "Todas");
        modelos.add(0, "Todos");

        // Creo los comboBoxs con los datos obtenidos de la base de datos
        comboMarca = new JComboBox<>(marcas.toArray(new String[0]));
        comboModeloFiltro = new JComboBox<>(modelos.toArray(new String[0]));

        comboMarca.setBackground(Color.DARK_GRAY);
        comboModeloFiltro.setBackground(Color.DARK_GRAY);
        panelPrincipal.add(comboMarca);
        panelPrincipal.add(comboModeloFiltro);

        // Inicializo los  filtros avanzados
        filtrosAvanzados = new JButton("Filtros Avanzados");
        filtrosAvanzados.setBackground(Color.darkGray.brighter());
        filtrosAvanzados.setForeground(Color.white);
        panelPrincipal.add(filtrosAvanzados);

        //--------------------------------------
        //            BOTON APLICAR
        //--------------------------------------
        botonAplicar = new JButton("Aplicar");
        botonAplicar.setBackground(Color.darkGray);
        botonAplicar.setForeground(Color.white);
        panelPrincipal.add(botonAplicar);

        botonAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        }
        );

        //--------------------------------------
        //     BOTON DE REINICIO DE FILTROS
        //--------------------------------------
        JButton botonReiniciarFiltros = new JButton("Reiniciar Filtros");
        botonReiniciarFiltros.setBackground(Color.red);
        botonReiniciarFiltros.setForeground(Color.white);
        panelPrincipal.add(botonReiniciarFiltros);

        botonReiniciarFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarFiltros();
            }
        }
        );

        //+++++++++++++++++++++++++
        // PANEL FILTROS AVANZADOS
        //+++++++++++++++++++++++++
        JPanel filtrosAvanzadosPanel = new JPanel();
        filtrosAvanzadosPanel.setBackground(new Color(0, 123, 255));;
        filtrosAvanzadosPanel.setVisible(false);

        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        //   ACCION DEL BOTON FILTROSAVANZADOS
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        filtrosAvanzados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Alterna la visibilidad del panel de filtros avanzados
                boolean isVisible = filtrosAvanzadosPanel.isVisible();
                filtrosAvanzadosPanel.setVisible(!isVisible);

                // Cambia el color del botón según la visibilidad del panel
                if (!isVisible) {
                    filtrosAvanzados.setBackground(Color.BLUE); // Azul cuando el panel es visible
                } else {
                    filtrosAvanzados.setBackground(Color.DARK_GRAY.brighter()); // Color original cuando el panel está oculto
                }
            }
        });

        // Añado los componentes al panel de filtros avanzados
        filtrosAvanzadosPanel.add(new JLabel("Modelo:"));
        filtrosAvanzadosPanel.add(comboModeloFiltro);

        filtrosAvanzadosPanel.add(new JLabel("Año de Matriculación:"));
        campoAnioMatricula = new JTextField(5);
        filtrosAvanzadosPanel.add(campoAnioMatricula);

        filtrosAvanzadosPanel.add(new JLabel("Nº de Vehiculos:"));
        campoNumeroVehiculos = new JTextField(5);
        campoNumeroVehiculos.setEditable(false);
        filtrosAvanzadosPanel.add(campoNumeroVehiculos);

        panelPrincipal.add(filtrosAvanzadosPanel);

        // Acción para mostrar los propietarios por coche
        botonMostrarPropietarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPropietariosPorCoche();
            }
        }
        );

        //-------------------------------------
        //            FUNCIÓN DISEÑO
        //-------------------------------------
        botonDiseño.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonDiseño.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String diseñoSeleccionado = (String) botonDiseño.getSelectedItem();

                        if ("Predeterminado".equals(diseñoSeleccionado)) {
                            getContentPane().setBackground(new Color(0, 123, 255));
                            panelPrincipal.setBackground(new Color(0, 123, 255));
                            panelOpciones.setBackground(new Color(0, 123, 255));
                            panelTabla.setBackground(new Color(0, 123, 255));
                            panelPaginacion.setBackground(new Color(0, 123, 255));

                            comboOpciones.setBackground(Color.RED);
                            comboOpciones.setForeground(Color.WHITE);
                            botonAplicar.setBackground(Color.DARK_GRAY);
                            botonAplicar.setForeground(Color.WHITE);
                            botonDiseño.setBackground(Color.BLACK);
                            botonDiseño.setForeground(Color.WHITE);
                            botonMostrarPropietarios.setBackground(Color.DARK_GRAY);
                            tablaRegistro.setBackground(new Color(0, 123, 255));
                            tablaRegistro.setForeground(Color.WHITE);
                            filtrosAvanzados.setBackground(Color.darkGray.brighter());
                            filtrosAvanzados.setForeground(Color.WHITE);
                            comboMarca.setBackground(Color.DARK_GRAY);

                        } else if ("Oscuro".equals(diseñoSeleccionado)) {
                            getContentPane().setBackground(Color.DARK_GRAY);
                            panelPrincipal.setBackground(Color.DARK_GRAY);
                            panelOpciones.setBackground(Color.DARK_GRAY);
                            panelTabla.setBackground(Color.DARK_GRAY);
                            panelPaginacion.setBackground(Color.DARK_GRAY);

                            comboOpciones.setBackground(Color.GRAY);
                            comboOpciones.setForeground(Color.WHITE);
                            botonAplicar.setBackground(Color.LIGHT_GRAY);
                            botonAplicar.setForeground(Color.WHITE);
                            botonDiseño.setBackground(Color.WHITE);
                            botonDiseño.setForeground(Color.WHITE);
                            tablaRegistro.setBackground(Color.GRAY);
                            tablaRegistro.setForeground(Color.WHITE);
                            filtrosAvanzados.setBackground(Color.GRAY);
                            filtrosAvanzados.setForeground(Color.WHITE);
                        }
                    }
                });
            }
        }
        );

        //###########################################
        //        PAGINACIÓN DE REGISTROS
        //###########################################
        panelPaginacion = new JPanel();
        botonAnterior = new JButton("Anterior");
        botonSiguiente = new JButton("Siguiente");
        labelPagina = new JLabel("Página: 1");
        totalPaginasField = new JTextField(5); // Campo para mostrar el total de paginas
        totalPaginasField.setEditable(false); // Campo de solo lectura
        totalPaginasField.setHorizontalAlignment(JTextField.RIGHT); // Alineacion centrada
        panelPaginacion.add(botonAnterior);
        panelPaginacion.add(labelPagina);
        panelPaginacion.add(new JLabel("de"));
        panelPaginacion.add(totalPaginasField);
        panelPaginacion.add(botonSiguiente);

        // Agregar acciones para botones de paginacion
        botonAnterior.addActionListener(e -> cambiarPagina(-1));
        botonSiguiente.addActionListener(e -> cambiarPagina(1));

        add(panelPaginacion, BorderLayout.SOUTH);

        // Configuracion inicial de datos
        cargarDatos(currentPage, pageSize);

        //************************************************
        //          COMPONENTAS PARA LA VENTANA
        //************************************************
        // Configuracion de la ventana
        setLayout(new BorderLayout());
        add(panelOpciones, BorderLayout.WEST);
        add(panelPrincipal, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelPaginacion, BorderLayout.SOUTH);

        panelPrincipal.setBackground(new Color(0, 123, 255));
        panelOpciones.setBackground(new Color(0, 123, 255));
        panelPaginacion.setBackground(new Color(0, 123, 255));

        //*************************************************
        //           CONFIGURACION DE LA VENTAN
        //*************************************************
        setSize(1600, 600); //El tamaño que tendra la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this); //Para centrar la ventana
        setVisible(true); //Hacemos visible la ventana
    }

    // Método de cargar de datos con paginacion
    private void cargarDatos(int page, int pageSize) {
        modeloTabla.setRowCount(0);
        int offset = page * pageSize;

        List<VehiculoDTO> vehiculos = null;
        int totalRegistros = 0;

        try {
            vehiculos = vehiculoServicio.obtenerVehiculosConPaginacion(pageSize, offset);
            totalRegistros = vehiculoServicio.contarCantidadRegistros(); // Metodo para obtener el total de registros
        } catch (SQLException ex) {
            Logger.getLogger(RegistroVehiculos.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        // Rellenar la tabla con los registros de la pagina actual
        for (VehiculoDTO vehiculo : vehiculos) {
            modeloTabla.addRow(new Object[]{
                vehiculo.getNombre(),
                vehiculo.getGenero(),
                vehiculo.getDni(),
                vehiculo.getMatricula(),
                vehiculo.getAnio(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getCantidadPropietarios()});
        }

        // Calcular el total de paginas
        int totalPages = (int) Math.ceil((double) totalRegistros / pageSize);
        totalPaginasField.setText(String.valueOf(totalPages));

        actualizarLabelPagina();
    }

    // Metodo para cambiar de pagina
    private void cambiarPagina(int increment) {
        currentPage += increment;
        if (currentPage < 0) {
            currentPage = 0;
        }
        cargarDatos(currentPage, pageSize);
    }

    // Metodo para actualizar la etiqueta de la pagina actual
    private void actualizarLabelPagina() {
        labelPagina.setText("Página: " + (currentPage + 1));
    }

    //¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡
    //           METODO FILTROS
    //¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡
    private void aplicarFiltros() {
        // Obtener valores de los filtros
        String nombre = campoMatricula.getText();
        String genero = hombre.isSelected() ? "HOMBRE" : mujer.isSelected() ? "MUJER" : "";
        String marca = (String) comboMarca.getSelectedItem();
        String modelo = (String) comboModeloFiltro.getSelectedItem();
        String anio = campoAnioMatricula.getText();

        // Llama al servicio para obtener los datos filtrados
        Object[][] datosFiltrados = vehiculoServicio.obtenerVehiculosFiltrados(nombre, genero, marca, modelo, anio);
        // Actualiza la tabla o muestra mensaje si no hay resultados
        if (datosFiltrados != null && datosFiltrados.length > 0) {
            campoNumeroVehiculos.setText(String.valueOf(datosFiltrados.length));
            actualizarTabla(datosFiltrados);
        } else {
            JOptionPane.showMessageDialog(this, "ERROR NO SE ENCONTRARON RESULTADOS.",
                    "SIN RESULTADOS", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla(datosFiltrados);
        }

        //Vaciamos los campos
        campoMatricula.setText("");
        campoAnioMatricula.setText("");
        campoNumeroVehiculos.setText("");

    }

    private void reiniciarFiltros() {
        // Reinicio los campos de texto
        campoMatricula.setText("");
        campoAnioMatricula.setText("");
        campoNumeroVehiculos.setText("");

        // Reinicio la seleccion de los combobox a la primera opcion
        if (comboMarca.getItemCount() > 0) {
            comboMarca.setSelectedIndex(0);
        }
        if (comboModeloFiltro.getItemCount() > 0) {
            comboModeloFiltro.setSelectedIndex(0);
        }

        // Reinicio los radio buttons
        hombre.setSelected(false);
        mujer.setSelected(false);

        //Cargamos los datos sin los filtros
        cargarDatos();
    }

    //================================================
    // Método para cargar datos desde la base de datos
    //================================================
    private void cargarDatos() {
        Object[][] listaVehiculos = vehiculoServicio.obtenerTodos();
        actualizarTabla(listaVehiculos);
    }

    //=================================================
    // Método para actualizar la tabla con nuevos datos
    //=================================================
    public void actualizarTabla(Object[][] datos) {
        modeloTabla.setDataVector(datos, new String[]{"Nombre", "Genero", "DNI", "Matricula", "Año", "Marca", "Modelo", "Total Propietarios"});
    }

    // Metodo para mostrar el historial de propietarios de un coche seleccionado por matricula
    private void mostrarPropietariosPorCoche() {
        int filaSeleccionada = tablaRegistro.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un coche en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener  matricula del coche seleccionado en la tabla
        String matricula = (String) modeloTabla.getValueAt(filaSeleccionada, 3);

        // Llamo al servicio para obtener el historial de propietarios
        Object[][] historialPropietarios = vehiculoServicio.obtenerHistorialPropietarios(matricula);

        // Verifico si se han recuperado datos si no muestra un panel de error
        if (historialPropietarios.length == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron propietarios para este coche.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Creo el modelo de tabla para mostrar los propietarios
        String[] columnasPropietarios = {"ID", "Matricula", "Nombre", "DNi", "Fecha_Compra", "Fecha_Fin", "ID Coche"};
        DefaultTableModel modeloPropietarios = new DefaultTableModel(columnasPropietarios, 0);

        // Lleno el modelo de la tabla con los datos obtenidos
        for (Object[] propietario : historialPropietarios) {
            modeloPropietarios.addRow(propietario);
        }

        // Y ya creo otra una nueva tabla para el historial de propietarios y mostrarla en un JOptionPane
        JTable tablaPropietarios = new JTable(modeloPropietarios);
        JOptionPane.showMessageDialog(this, new JScrollPane(tablaPropietarios),
                "Historial de Propietarios - Matricula: " + matricula,
                JOptionPane.INFORMATION_MESSAGE);
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Inicializa los controladores
                    ControladorPersona controladorPersona = new ControladorPersona();
                    ControladorVehiculo controladorVehiculo = new ControladorVehiculo();
                    
                    // Crea una instancia de RegistroVehiculos y pásale los controladores si es necesario
                    RegistroVehiculos rv = new RegistroVehiculos(controladorPersona, controladorVehiculo);
                    rv.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroVehiculos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
