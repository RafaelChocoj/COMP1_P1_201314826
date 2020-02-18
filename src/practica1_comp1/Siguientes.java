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
public class Siguientes {
    String hoja;
    int id;
    //String nexts;
    LinkedList<Integer> nexts;
    
    //public Siguientes (String hoja,int id, String nexts ){
    public Siguientes (String hoja,int id, LinkedList<Integer> nexts ){
        
        this.hoja = hoja;
        this.id = id;
        this.nexts = nexts;
    }
}
