/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_comp1;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author RAFAEL
 */
public class Sintactico {
    
    /*psts variables*/
    LinkedList<Variables> lis_var;
    //LinkedList<String> valores;
    String var_tempo;
    
     /*para variables de expresiones regulares*/
    LinkedList<VarExpReg> lis_ex_reg;
    
    LinkedList<Token> listaTokens;
    int numPreanalisis;
    Token preanalisis;
    public Sintactico() {
    }
    
    public void Parsear(LinkedList<Token> list)
    {
        /*inicializando lista de variables*/
        lis_var = new LinkedList<Variables>();
        
        /*inicializando lista de expresiones regulares*/
        lis_ex_reg = new LinkedList<VarExpReg>();
        
        //valores = new LinkedList<String>();
        
//        nodos = 0;

//        can_erroes = 0;
        listaTokens = list;
        listaTokens.add(new Token("", "UltimoToken", 0, 0));
        preanalisis = list.get(0);
        numPreanalisis = 0;
        S0();
    }
    
    void S0()
    {
//            Ins();
        match("llaveIzq"); //}
        
        /*pueden venir conjuntos*/
        if (preanalisis.idToken.equals("Reservada"))
        {
            CONJ();
        }
        else if (preanalisis.idToken.equals("Identificador"))
        {
            ER();
        }
        
        /*psts evaluar las expresines regulares*/
        if (preanalisis.idToken.equals("Delimitador"))
        {
            match("Delimitador"); //%%
            match("Delimitador"); //%%
            EVA_ER();
        }
        /*pueden venir expresiones*/
        match("llaveDer"); //{

    }
    /*evaliuando expresion*/
    public void EVA_ER(){
        if (preanalisis.idToken.equals("Identificador"))
        {
            match("Identificador"); // name var ex regular
            match("Igualdad"); // ->
            match("Cadena"); // cadena
            match("PuntoComa"); // ;
            
            if (preanalisis.idToken.equals("Identificador"))
            {               
                EVA_ER();
            }
//            if (preanalisis.idToken.equals("PuntoComa"))
//            {
//                match("PuntoComa"); // ;
//                EVA_ER();
//            }
            
            
        }
        else {
            JOptionPane.showMessageDialog(null,"Error se Esperaba un Identificador. " + preanalisis.lexema );
        }
    }
    
    /*expresion regular*/
    public void ExReg(LinkedList<ER_unitario> pref_er){
        
        //{
        boolean er_sim = false;
        if (preanalisis.idToken.equals("llaveIzq"))
        {
            match("llaveIzq"); //{
            //pref_er.add(preanalisis.lexema);
            pref_er.add(new ER_unitario(preanalisis.lexema, "CO"));
            match("Identificador"); // conjunto
            match("llaveDer"); //}
        }
        //.
        else if (preanalisis.idToken.equals("Conca_por"))
        {
            //pref_er.add(preanalisis.lexema);
            pref_er.add(new ER_unitario(preanalisis.lexema, "O"));
            match("Conca_por");
        }
        //|
        else if (preanalisis.idToken.equals("Disyun_mas"))
        {
            pref_er.add(new ER_unitario(preanalisis.lexema, "O"));
            match("Disyun_mas");
        }
        //?
        else if (preanalisis.idToken.equals("0oUnavez"))
        {
            pref_er.add(new ER_unitario(preanalisis.lexema, "O"));
            match("0oUnavez");
        }
        //*
        else if (preanalisis.idToken.equals("0oMasvez"))
        {
            pref_er.add(new ER_unitario(preanalisis.lexema, "O"));
            match("0oMasvez");
        }
        //+
        else if (preanalisis.idToken.equals("1oMasvez"))
        {
            pref_er.add(new ER_unitario(preanalisis.lexema, "O"));
            match("1oMasvez");
        }
        //cadena
        else if (preanalisis.idToken.equals("Cadena"))
        {
            pref_er.add(new ER_unitario(preanalisis.lexema.replace("\"", "") , "CA"));
            match("Cadena");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Error se Esperaba un operador ER. " + preanalisis.lexema );
            //error
            er_sim = true;
        }
        
        if (er_sim== false){
            if (preanalisis.idToken.equals("PuntoComa"))
            {
    //            match("PuntoComa"); //;      

            }
            else
            {
                ExReg(pref_er);
            }
        }
        
    }
    
    /*para palabras reservadas*/
    String name_expresion_reg;
    public void ER() {
        
        if (preanalisis.idToken.equals("Identificador"))
        {
            name_expresion_reg = "";
            name_expresion_reg = preanalisis.lexema;
            match("Identificador"); // name variable
            match("Igualdad"); // ->
            
            //LinkedList<String> pref_er = new LinkedList<String>();
            LinkedList<ER_unitario> pref_er = new LinkedList<ER_unitario>();
            ExReg(pref_er);
            
            if (preanalisis.idToken.equals("PuntoComa"))
            {
                match("PuntoComa"); //,
                
                VarExpReg new_er = new VarExpReg(name_expresion_reg, pref_er);
                lis_ex_reg.add(new_er);
                var_tempo ="";
    
                //////ER();
            }
        }
        else
        {
            //error
        }
        
        ////recusivo para conjuntos
        if (preanalisis.idToken.equals("Reservada"))
        {
            CONJ();
        }
        else if (preanalisis.idToken.equals("Identificador"))
        {
            ER();
        }
    }
    
    /*para conjuntos*/
    public void CONJ() {
        if (preanalisis.idToken.equals("Reservada"))
        {
            if (preanalisis.lexema.equals("CONJ"))
            {
                
                match("Reservada"); // CONJ
                match("DosPuntos"); // :
                
                /*guardando variable*/
                if (preanalisis.idToken.equals("Identificador"))
                {
                    //JOptionPane.showMessageDialog(null,"var: " + preanalisis.lexema);
                    var_tempo = preanalisis.lexema;
                } 
                match("Identificador"); // name variable
                
                match("Igualdad"); // ->
                
                /*guardando valores*/
                LinkedList<String> valores = new LinkedList<String>();
                VAL_CON(valores);

            }
            else
            {
                ////error
            }
            
        }
        ////recusivo para conjuntos
        if (preanalisis.idToken.equals("Reservada"))
        {
            CONJ();
        }
        else if (preanalisis.idToken.equals("Identificador"))
        {
            ER();
        }
//        if (preanalisis.idToken.equals("Reservada"))
//        {
//            CONJ();
//        }
    }
    
    /*para valores de los conjuntos*/
    public void VALOR_UNITARIO(LinkedList<String> valores){
        if (preanalisis.idToken.equals("Identificador"))
        {
            valores.add(preanalisis.lexema);
            match("Identificador"); //a
        }
        else if (preanalisis.idToken.equals("Digito"))
        {
            valores.add(preanalisis.lexema);
            match("Digito"); //1
        }
        else {
            JOptionPane.showMessageDialog(null,"Error Sintactico se esperaba un caracter o Dijito lexema: " + preanalisis.lexema );
        }
    }
    public void VAL_CON(LinkedList<String> valores) {

        VALOR_UNITARIO(valores);
        /*verificando si es coma o dos puntos*/
        /*sigue*/
        if (preanalisis.idToken.equals("coma"))
        {
            match("coma"); //,
            VAL_CON(valores);
        }
        /*verificando si es rango*/
        else if (preanalisis.idToken.equals("SeparRango"))
        {
            match("SeparRango"); //,
            VALOR_UNITARIO(valores);//VALOR FINAL
            match("PuntoComa"); //;
            
            Variables n_var = new Variables(var_tempo, valores, "R");
            lis_var.add(n_var);
            var_tempo ="";
        }
        /*termina*/
        else if (preanalisis.idToken.equals("PuntoComa"))
        {
            match("PuntoComa"); //;
            
            Variables n_var = new Variables(var_tempo, valores, "C");
            lis_var.add(n_var);
            var_tempo ="";
        }
        
        
        
    }
    
    /*haciendo mach a las palabras, si son iguales*/
    public void match(String tipo)
    {
        //imprime el actual
//////////////////////       JOptionPane.showMessageDialog(null,"*******Actual= " + tipo + "************ lexema= " + preanalisis.lexema);
        if (!tipo.equals(preanalisis.idToken ))
        {
            JOptionPane.showMessageDialog(null,"Error Sintactico se esperaba un caracter TIPO " + tipo + ", lexema: " + preanalisis.lexema );
//            //lexema, idToken, linea, columna
//            addError(lexe_ac, des_er, preanalisis.getLinea(), preanalisis.getColumna());
//            can_erroes++;
        }
        else
        {
            //JOptionPane.showMessageDialog(null,"paso sintactico");// , lexema: " + preanalisis.lexema);

        }
        if (!preanalisis.idToken.equals("UltimoToken"))
        {
            numPreanalisis++;
            preanalisis = listaTokens.get(numPreanalisis);
        } else {JOptionPane.showMessageDialog(null,"fin token");}

    }
    /*retorna lista de expresiones */
    public LinkedList<Variables> getLista_Conjuntos(){
        return this.lis_var;
    }
    
    public LinkedList<VarExpReg> getLista_ExpRegulares(){
        return this.lis_ex_reg;
    }
    
    /*imprime variables y sus valores*/
    public void Imprime_var(){
        for (int i = 0; i < lis_var.size(); ++i)
        {
            //JOptionPane.showMessageDialog(null,"--- " + lis_var.get(i).name_var);
            
            JOptionPane.showMessageDialog(null, "--- " + lis_var.get(i).name_var + " - "
                    +lis_var.get(i).valores + " - " + lis_var.get(i).tipo);
        }
        
    }
    
    public void Imprime_ER(){
        /*probando armando el arbol*/
        
        for (int i = 0; i < lis_ex_reg.size(); ++i)
        { 
            JOptionPane.showMessageDialog(null,  lis_ex_reg.get(i).name_exreg + " - "
                   /* +lis_ex_reg.get(i).toString()*/ );
            
            
            ///
            Armando_RPN arbol =  new Armando_RPN(lis_ex_reg.get(i).prefijo);
            NodeArbol roor = arbol.leyendo_expresiones();
            Arbol tree = new Arbol(roor);
            tree.Graficando_arbol();
        
        }
        
    }
    
}
