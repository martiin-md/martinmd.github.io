/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Coches;
import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class VehiculosTabla {

    //ArrayList para crear vehiculos de coches
    public static ArrayList<Coches> coches = new ArrayList<>();

    public static void inicializarCoches() {
        Coches c1 = new Coches("ASD5533", 2022, "PORCHE", "Panamera");
        Coches c2 = new Coches("DFF2255", 2021, "MERCEDES", "AMG GT");
        Coches c3 = new Coches("JJG8221", 2024, "AUDI", "R8");
        Coches c4 = new Coches("RTH9549", 2018, "MINI", "Cooper");

        //Añadimos los coches del array
        coches.add(c1);
        coches.add(c2);
        coches.add(c3);
        coches.add(c4);
    }
}
