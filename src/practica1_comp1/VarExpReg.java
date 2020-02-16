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
public class VarExpReg {
    
    String name_exreg;
    LinkedList<ER_unitario> prefijo;
    
    public VarExpReg (String name_exreg, LinkedList<ER_unitario> prefijo){
        this.name_exreg = name_exreg;
        this.prefijo = prefijo;
    }
    
//        public String toString(){
//        return this.prefijo.get(0).er;
//    }
}
