/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.VehiculosYPersonasDAO;
import Vista.RegistroVehiculos;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;


/**
 *
 * @author martin
 */
public class PaginadorDeTabla<Vehiculo> {

    private JPanel panelPaginacionBotones;
    private JComboBox<Integer> listaLimiteDeFilas;
    private JTable tablaVehiculos;
    private VehiculosYPersonasDAO vehiculoDAO;
    private int[] numeroDeFilasPermitidas;
    private int filasPermitidasPorDefecto;
    private int paginaActual = 1;
    private RegistroVehiculos modeloDeTabla;
    private int limiteBotonesPaginadores = 9;
    
    

}
