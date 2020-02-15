/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_comp1;

import java.util.LinkedList;

/**
 *
 * @author RAFAEL
 */
public class Variables {
    String name_var;
    LinkedList<String> valores;
    String tipo; // C = conjunto, R = rango
    
    
    public Variables (String name_var, LinkedList<String> valores, String tipo){
        this.name_var = name_var;
        this.valores = valores;
        this.tipo = tipo;
    }
    
}
