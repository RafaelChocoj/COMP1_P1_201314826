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
public class TTransiciones {
    String name_estado;
    LinkedList<Integer> siguientes;
    String terminal;
    String tipo_estado;
    
    LinkedList<tran_a_estados> ir_a;
    
    public TTransiciones (String name_estado,LinkedList<Integer> siguientes,String terminal,String tipo_estado, LinkedList<tran_a_estados> ir_a ){
        
        this.name_estado = name_estado;
        this.siguientes = siguientes;
        this.tipo_estado = tipo_estado;
        this.terminal = terminal;
        
        this.ir_a = ir_a;
    }
}
