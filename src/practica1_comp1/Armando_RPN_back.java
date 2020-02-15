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
public class Armando_RPN_back {
    
    LinkedList<String> pref_expresiones;
    Stack<String> pila;
    
    public Armando_RPN_back(LinkedList<String> pref_expresiones){
        this.pref_expresiones = pref_expresiones;
        this.pila = new Stack<>();
        
    }
    
    public double leyendo_expresiones(){
        String eleDer, eleIzq;
        /*recorriendo listado de expresiores regulares*/
        //for (String expre : pref_expresiones) {
        
        //for (int i = 0; i < pref_expresiones.size(); ++i){
        for (int i = pref_expresiones.size() -1; i >= 0; i--){

            //JOptionPane.showMessageDialog(null,expre);
            JOptionPane.showMessageDialog(null,pref_expresiones.get(i));
            
            /*verificando si es operador*/
            //if (IsOperador(expre)) {
            if (IsOperador(pref_expresiones.get(i))) {
                eleDer = pila.pop();
                if (pila.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltan Elementos para armar arbol","NANI", JOptionPane.ERROR_MESSAGE);
                    i = pref_expresiones.size();
                }
                eleIzq = pila.pop();
                double resultado = operar(eleIzq, pref_expresiones.get(i), eleDer );
                pila.push(""+resultado);
            }
            else {
                pila.push(pref_expresiones.get(i));
            }
        }
        return Double.parseDouble(pila.pop());


    }
    
    public double operar(String eleIzq, String oper, String eleDer ){
        double a = Double.parseDouble(eleIzq);
        double b = Double.parseDouble(eleDer);
        
        switch (oper){
            case "+": return a+b;
            case "-": return a-b;
            case "*": return a*b;
            case "/": return a/b;
            case "%": return a%b;

            default: return -1;
        }
    }
    
    private boolean IsOperador(String oper){
        return oper.equals("+") || oper.equals("-") || oper.equals("*") || oper.equals("/") || oper.equals("%");
        //return oper.equals(".") || oper.equals("|") || oper.equals("?") || oper.equals("*") || oper.equals("+");
    }
    
}
