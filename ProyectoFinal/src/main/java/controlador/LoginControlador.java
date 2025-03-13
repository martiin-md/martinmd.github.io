/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author martin
 */

public class LoginControlador {

    public static boolean validarCredenciales(String usuario, String password) {
        // Validar con datos de ejemplo
        return "admin".equals(usuario) && "1234".equals(password);
    }
}
