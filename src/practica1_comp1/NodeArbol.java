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
public class NodeArbol {
    
    String lexema;
    int id;
    NodeArbol left;
    NodeArbol right;
    
    String Anulable;
    int identificador;
    String primeros;
    String ultimos;
    
    String tipo;

    int height;

    
    //public NodeArbol (String lexema,int id, String Anulable, int identificador, String primeros,String ultimos /*, String tipo;*/ ){
    public NodeArbol (String lexema,int id, String Anulable, int identificador, String primeros,String ultimos, String tipo ){    
        this.id = id;
        this.lexema = lexema;
        this.left = null;
        this.right = null;
        
        this.Anulable = Anulable;
        this.identificador = identificador;
        this.primeros = primeros;
        this.ultimos = ultimos;
        
        this.tipo = tipo;
                
        this.height = 1;
    }
}
