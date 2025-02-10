/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Personas;
import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class PersonasTabla {

    //ArrayList para Personas
    public static ArrayList<Personas> personas = new ArrayList<>();

    public static void inicializarPersonas() {

        Personas p1 = new Personas("Martiin99", "HOMBRE", "X722517K");
        Personas p2 = new Personas("Nerea Niemas", "MUJER", "47878755K");
        Personas p3 = new Personas("Laura Niemas", "MUJER", "45454332K");
        Personas p4 = new Personas("Roberto Pérez", "HOMBRE","48654421K");

        //Añadimos las personas del Array
        personas.add(p1); 
        personas.add(p2);
        personas.add(p3);
        personas.add(p4);

    }

}
