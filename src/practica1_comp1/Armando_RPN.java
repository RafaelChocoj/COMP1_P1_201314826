/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_comp1;

import java.util.LinkedList;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author RAFAEL
 */
public class Armando_RPN {
    
    //LinkedList<String> pref_expresiones;
    LinkedList<ER_unitario> pref_expresiones;
    //Stack<String> pila;
    Stack<NodeArbol> pila;
    
    public Armando_RPN(LinkedList<ER_unitario> pref_expresiones){
        this.pref_expresiones = pref_expresiones;
        this.pila = new Stack<NodeArbol>();
        
    }
    
    //public double leyendo_expresiones(){
    public NodeArbol leyendo_expresiones(){
        //String eleDer, eleIzq;
        NodeArbol eleDer, eleIzq;
        
        /*recorriendo listado de expresiores regulares*/
        //for (String expre : pref_expresiones) {
        
        //for (int i = 0; i < pref_expresiones.size(); ++i){
        for (int i = pref_expresiones.size() -1; i >= 0; i--){

////////////            JOptionPane.showMessageDialog(null,pref_expresiones.get(i).er);
            
            /*verificando si es operador*/
            
            //if (IsOperador(expre)) {
            
            //if (IsOperador(pref_expresiones.get(i))) {
            if (pref_expresiones.get(i).tipo.equals("O")) {
                
                int tipo = TipoOperador(pref_expresiones.get(i).er);
                
                if (tipo == 2){
                    //eleDer = pila.pop();
                    eleIzq = pila.pop();
                    if (pila.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Faltan Elementos para armar arbol","NANI", JOptionPane.ERROR_MESSAGE);
                        i = pref_expresiones.size();
                    }
                    //eleIzq = pila.pop();
                    eleDer = pila.pop();
                    NodeArbol resultado = operar(eleIzq, pref_expresiones.get(i).er, eleDer, i );
                    pila.push(resultado);
                }
                else if (tipo == 1){
                    
//                    //eleDer = pila.pop();
//                    eleIzq = pila.pop();
                    if (pila.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Faltan Elementos para armar arbol","NANI", JOptionPane.ERROR_MESSAGE);
                        i = pref_expresiones.size();
                    }
                    //eleIzq = pila.pop();
                    eleDer = pila.pop();
                    NodeArbol resultado = operar_1(pref_expresiones.get(i).er, eleDer, i );
                    pila.push(resultado);
                }
                
            }
            else {
                //pila.push(pref_expresiones.get(i));
                //this.root = new NodeAVL(nombre, contenido, user, fecha_creacion);
                
                //NodeArbol n_nodo = new NodeArbol(pref_expresiones.get(i).er, i, "F", 0, 0, 0);
                
                //NodeArbol n_nodo = new NodeArbol(pref_expresiones.get(i).er, i, "A", 0, "-", "-");
                NodeArbol n_nodo = new NodeArbol(pref_expresiones.get(i).er, i, "A", 0, "-", "-", pref_expresiones.get(i).tipo);
                pila.push(n_nodo);
                //////JOptionPane.showMessageDialog(null,"("+i+") "+pref_expresiones.get(i));
            }
        }
        //nodo final
        ////return pila.pop();
        return Final_tree(pila.pop());

    }
    
//    //public double leyendo_expresiones(){
//    public NodeArbol leyendo_expresiones(){
//        //String eleDer, eleIzq;
//        NodeArbol eleDer, eleIzq;
//        
//        /*recorriendo listado de expresiores regulares*/
//        //for (String expre : pref_expresiones) {
//        
//        //for (int i = 0; i < pref_expresiones.size(); ++i){
//        for (int i = pref_expresiones.size() -1; i >= 0; i--){
//
//            //JOptionPane.showMessageDialog(null,expre);
//            //JOptionPane.showMessageDialog(null,pref_expresiones.get(i));
//            
//            /*verificando si es operador*/
//            //if (IsOperador(expre)) {
//            if (IsOperador(pref_expresiones.get(i))) {
//                //eleDer = pila.pop();
//                eleIzq = pila.pop();
//                if (pila.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Faltan Elementos para armar arbol","NANI", JOptionPane.ERROR_MESSAGE);
//                    i = pref_expresiones.size();
//                }
//                //eleIzq = pila.pop();
//                eleDer = pila.pop();
//                NodeArbol resultado = operar(eleIzq, pref_expresiones.get(i), eleDer, i );
//                
//                pila.push(resultado);
//            }
//            else {
//                //pila.push(pref_expresiones.get(i));
//                //this.root = new NodeAVL(nombre, contenido, user, fecha_creacion);
//                NodeArbol n_nodo = new NodeArbol(pref_expresiones.get(i), i, "F", 0, 0, 0);
//                pila.push(n_nodo);
//                JOptionPane.showMessageDialog(null,"("+i+") "+pref_expresiones.get(i));
//            }
//        }
//        return pila.pop();
//
//    }
    
    /*Agregando status Final*/
    public NodeArbol Final_tree(NodeArbol node_fin){
        //concatenacion
        //NodeArbol n_nodox = new NodeArbol(".", -88, "A", 0, "-", "-");
        NodeArbol n_nodox = new NodeArbol(".", -88, "A", 0, "-", "-", "R");
        //nodo acpetacion
        //NodeArbol n_nodo_acep = new NodeArbol("#", -99, "A", 0, "-", "-");
        NodeArbol n_nodo_acep = new NodeArbol("#", -99, "A", 0, "-", "-" , "FIN");
        n_nodox.right = n_nodo_acep;
        n_nodox.left = node_fin;
        return n_nodox;

    }
    
    /*armando arbol*/
    public NodeArbol operar(NodeArbol eleIzq, String oper, NodeArbol eleDer, int i){

        //////JOptionPane.showMessageDialog(null,"2 ("+i+") "+pref_expresiones.get(i));
        //NodeArbol n_nodo = new NodeArbol(oper, i, "A",0, "-", "-");
        NodeArbol n_nodo = new NodeArbol(oper, i, "A",0, "-", "-" , "O" );
        n_nodo.left = eleIzq;
        n_nodo.right = eleDer;
        return n_nodo;
//        switch (oper){
//            case ".": 
//                NodeArbol n_nodo = new NodeArbol(oper, i, "F", 0, 0, 0);
//                n_nodo.left = eleIzq;
//                n_nodo.right = eleDer;
//                return n_nodo;
//            case "/": 
//                NodeArbol n_nodo2 = new NodeArbol(oper, i, "F", 0, 0, 0);
//                n_nodo2.left = eleIzq;
//                n_nodo2.right = eleDer;
//                return n_nodo2;
//            default: return null;
//        }
    }
    
    public NodeArbol operar_1(String oper, NodeArbol eleDer, int i){

        ///////////////JOptionPane.showMessageDialog(null,"1 ("+i+") "+pref_expresiones.get(i));
        //NodeArbol n_nodo = new NodeArbol(oper, i, "A", 0, "-", "-");
        NodeArbol n_nodo = new NodeArbol(oper, i, "A", 0, "-", "-", "O");
        n_nodo.right = eleDer;
        return n_nodo;
//        switch (oper){
//            case ".": 
//                NodeArbol n_nodo = new NodeArbol(oper, i, "F", 0, 0, 0);
//                n_nodo.left = eleIzq;
//                n_nodo.right = eleDer;
//                return n_nodo;
//            case "/": 
//                NodeArbol n_nodo2 = new NodeArbol(oper, i, "F", 0, 0, 0);
//                n_nodo2.left = eleIzq;
//                n_nodo2.right = eleDer;
//                return n_nodo2;
//            default: return null;
//        }
    }
    
//    /*armando arbol*/
//    public NodeArbol operar(NodeArbol eleIzq, String oper, NodeArbol eleDer, int i){
//        //double a = Double.parseDouble(eleIzq);
//        //double b = Double.parseDouble(eleDer);
//        JOptionPane.showMessageDialog(null,"("+i+") "+pref_expresiones.get(i));
//        switch (oper){
//            case ".": 
//                NodeArbol n_nodo = new NodeArbol(oper, i, "F", 0, 0, 0);
//                n_nodo.left = eleIzq;
//                n_nodo.right = eleDer;
//                return n_nodo;
//            case "/": 
//                NodeArbol n_nodo2 = new NodeArbol(oper, i, "F", 0, 0, 0);
//                n_nodo2.left = eleIzq;
//                n_nodo2.right = eleDer;
//                return n_nodo2;
////            case "-": return a-b;
////            case "*": return a*b;
////            case "/": return a/b;
////            case "%": return a%b;
//
//            default: return null;
//        }
//    }
    
//    public double operar(String eleIzq, String oper, String eleDer ){
//        double a = Double.parseDouble(eleIzq);
//        double b = Double.parseDouble(eleDer);
//        
//        switch (oper){
//            case "+": return a+b;
//            case "-": return a-b;
//            case "*": return a*b;
//            case "/": return a/b;
//            case "%": return a%b;
//
//            default: return -1;
//        }
//    }
    
    private boolean IsOperador(String oper){
        //return oper.equals(".") || oper.equals("-") || oper.equals("*") || oper.equals("/") || oper.equals("%");
        return oper.equals(".") || oper.equals("|") || oper.equals("?") || oper.equals("*") || oper.equals("+");
    }
    
    private int TipoOperador(String oper){   
        if (oper.equals(".") || oper.equals("|")) {
            return 2; } else{ return 1; }
    }
    
}
