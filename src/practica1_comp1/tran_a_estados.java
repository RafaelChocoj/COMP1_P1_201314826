/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_comp1;

/**
 *
 * @author RAFAEL
 */
public class tran_a_estados {
    String terminal;
    String Ir_a_Estado;
    String Tipo_ter;
    
    //public tran_a_estados (String terminal, String Ir_a_Estado){
    public tran_a_estados (String terminal, String Ir_a_Estado, String Tipo_ter){
        
        this.terminal = terminal;
        this.Ir_a_Estado = Ir_a_Estado;
        
        this.Tipo_ter = Tipo_ter;
    }
    
}
