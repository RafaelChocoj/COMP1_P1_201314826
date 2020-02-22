/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_comp1;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author RAFAEL
 */
public class Arbol {
    
    LinkedList<Siguientes> tabla_siguientes;
    
    LinkedList<TTransiciones> tab_transiciones;
            
   LinkedList<Variables> lis_conjuntos;
   String name_expre;
   
   
   LinkedList<String> resul_expresiones;
   
    NodeArbol root;
    public Arbol (NodeArbol root, String name_expre){
        this.root = root;
        this.name_expre = name_expre;
        
        tabla_siguientes = new LinkedList<>();
        
        resul_expresiones = new LinkedList<>();
    }
    
    //////////////////////////////////////inicio graficando arbol
    public boolean Graficando_arbol(){

        StringBuilder graf  = new StringBuilder();
        //graf.append("arbol_avl.txt", "w");
        graf.append("digraph G { \n");
        graf.append("rankdir=TB;\n");
        graf.append("graph [nodesep=0.5 ];\n");
        graf.append("node [shape = record, fillcolor=seashell2];\n");
        this.VerArbol(graf);
        graf.append("\n}\n");
        
        return this.graf_arbolavl(graf.toString(), "arbol_a_" + name_expre);
  
    }
    
    public void VerArbol(StringBuilder graf){ 
        this.VerArbol(this.root, graf);
        //this.VerArbol(this.root, graf);
    }
    
    public void VerArbol(NodeArbol root_ac, StringBuilder graf){ 
        if (root_ac != null){
            this.VerArbol(root_ac.left, graf);
            NodeArbol tempo = root_ac;
            if (tempo.right != null){               
                //graf.append("\"nodo"+ root_ac.lexema+"\"");
                //graf.append(":C1 -> \"nodo"+ tempo.right.lexema + "\"\n");
                
                graf.append("\"nodo"+ root_ac.lexema + root_ac.id + "\"");
                graf.append(":C1 -> \"nodo"+ tempo.right.lexema + tempo.right.id + "\"\n");
            }             
            if (tempo.left != null){
                //graf.append("\"nodo"+ root_ac.lexema+"\"");
                //graf.append(":C0 -> \"nodo"+ tempo.left.lexema + "\"\n");
                
                graf.append("\"nodo"+ root_ac.lexema+root_ac.id +"\"");
                graf.append(":C0 -> \"nodo"+ tempo.left.lexema +tempo.left.id + "\"\n");
            }
            //graf.append("\"nodo"+ root_ac.lexema  +"\" [ label =\"<C0>|");
            graf.append("\"nodo"+ root_ac.lexema +root_ac.id +"\" [ label =\"<C0>"+root_ac.primeros+"|");
            
            //graf.append("ANUL: "+ root_ac.Anulable+ "\\n" );
            graf.append(" "+ root_ac.Anulable+ "\\n" );
            
//            if (root_ac.lexema.equals("|") || 
//                    root_ac.lexema.equals(">") ){
//                graf.append( "\\"+root_ac.lexema + "\\n");
//            } else {
//                graf.append( "\\ "+root_ac.lexema + "\\n");
//            }
            String lex = root_ac.lexema;
            lex = lex.replace("|", "\\|");
            lex = lex.replace(">", "\\>");
            graf.append( lex + "\\n");
        

            if (root_ac.right == null && root_ac.left == null)  {
                graf.append("iden: "+ root_ac.identificador+ "\\n");
            }
            
//////////////            graf.append("-tipo: "+ root_ac.tipo+ "\\n");
            
            //graf.append("Propietario: "+ root_ac.user+ "\\n" );
            //String conte = root_ac.contenido.replace("\"", "\\\"");
            //conte = conte.replace("{", "\\{");
            //conte = conte.replace("}", "\\}");
            
            //if (conte.length() >= 50 ) {
            //    conte = conte.substring(0,48) + "...";
            //}
            //graf.append("Contenido: " + conte );
            
            graf.append("|<C1>"+root_ac.ultimos+"\"]; \n");

            this.VerArbol(root_ac.right, graf);
        }
    }
    
    
    public boolean graf_arbolavl(String grafica, String name_g){
        //File archivo =new File("hash_user.txt");
        try
            {
            //File archivo =new File("arbol_avl.txt");
            File archivo =new File(name_g+".txt");
            FileWriter escribir= new FileWriter(archivo);
            escribir.write(grafica);
            escribir.close();
            }

            catch(Exception e)
            {
            JOptionPane.showMessageDialog(null, "Error al escribir","NANI",JOptionPane.ERROR_MESSAGE);
            return false;
            }
        
        try {

            Runtime rt = Runtime.getRuntime();
            //rt.exec( cmd );
            //Process p = rt.exec("dot -Tpng arbol_avl.txt -o arbol_avl.jpg");
            Process p = rt.exec("dot -Tpng "+name_g+".txt -o "+name_g+".jpg");
            p.waitFor();
            //rt.exec("hash_user.jpg");
            
            //Desktop.getDesktop().open(new File("arbol_avl.jpg"));
            Desktop.getDesktop().open(new File(name_g+".jpg"));

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex,"NANI",JOptionPane.ERROR_MESSAGE);
                return false;
            } finally {}
        
        return true;
        
    }
    
    //////////////////////////////////fin graficando arbol
    public boolean graficando_siguientes(){
        StringBuilder graf  = new StringBuilder(); //grafica total
        
        StringBuilder hoja  = new StringBuilder(); // para nombre hoja
        StringBuilder index  = new StringBuilder(); // para indice
        StringBuilder sig  = new StringBuilder(); // para siguientes
        
        StringBuilder tip  = new StringBuilder(); // para tipo de hoja


        graf.append("digraph G { rankdir=LR\n");
        graf.append("node [shape=record];\n");
        graf.append("node0[label=\"{");

        tip.append("{Tipo "); ///
        hoja.append("|{Hoja"); ///
        
        //hoja.append("{Hoja");
        index.append("|{Hoja ID ");
        sig.append("|{Siguientes ");
        
        //tip.append("|{Tipo "); ///
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            
//            if (tabla_siguientes.get(i).hoja.equals("|") || 
//                    tabla_siguientes.get(i).hoja.equals(">") ){
//                hoja.append("|\\" + tabla_siguientes.get(i).hoja);
//            } else {
//                hoja.append("|" + tabla_siguientes.get(i).hoja);
//            }
            tip.append("|" + tabla_siguientes.get(i).Tipo_hoja );  //
            
            String lex = tabla_siguientes.get(i).hoja;
            lex = lex.replace("|", "\\|");
            lex = lex.replace(">", "\\>");
            hoja.append("|" +lex );
            index.append("|" + tabla_siguientes.get(i).id );
            sig.append("|" + tabla_siguientes.get(i).nexts );
            
            
        }
        
        tip.append("}\n"); //
        
        hoja.append("}\n");
        index.append("}\n");
        sig.append("}\n");
        
        
        
        graf.append(tip); //
        
        graf.append(hoja);
        graf.append(index);
        graf.append(sig);
        
        
        
        graf.append("}\"];\n");
        graf.append("}");

        //JOptionPane.showMessageDialog(null,graf);
        return this.graf_arbolavl(graf.toString(),"tab_sig_"+ name_expre);
    }
    
    public boolean graficando_tabTransiciones_back(){
        StringBuilder graf  = new StringBuilder(); //grafica total
        
        StringBuilder esta  = new StringBuilder(); // para nombre hoja
        StringBuilder sig  = new StringBuilder(); // para indice
        StringBuilder ter  = new StringBuilder(); // para siguientes
        StringBuilder tipo  = new StringBuilder(); // para indice


        graf.append("digraph G { rankdir=LR\n");
        graf.append("node [shape=record];\n");
        graf.append("node0[label=\"{");

        esta.append("{Estado");
        sig.append("|{ siguientes");
        ter.append("|{ terminal");
        tipo.append("|{Tipo ");
        for (int i = 0; i < tab_transiciones.size() ;i++) {
            esta.append("|" + tab_transiciones.get(i).name_estado );
            sig.append("|" + tab_transiciones.get(i).siguientes );
            
            String lex = tab_transiciones.get(i).terminal;
            lex = lex.replace("|", "\\|");
            lex = lex.replace(">", "\\>");
            ter.append("|" +  lex);
            //ter.append("|" + tab_transiciones.get(i).terminal );
            
            tipo.append("|" + tab_transiciones.get(i).tipo_estado );
        }
        esta.append("}\n");
        sig.append("}\n");
        ter.append("}\n");
        tipo.append("}\n");
        
        graf.append(esta);
        graf.append(sig);
        graf.append(ter);
        graf.append(tipo);
        
        graf.append("}\"];\n");
        graf.append("}");

        //JOptionPane.showMessageDialog(null,graf);
        return this.graf_arbolavl(graf.toString(),"tab_tran_"  + name_expre);
    }
    
    public boolean graficando_tabTransiciones(){
        StringBuilder graf  = new StringBuilder(); //grafica total
        
        StringBuilder esta  = new StringBuilder(); // para nombre hoja
        StringBuilder sig  = new StringBuilder(); // para indice
        StringBuilder ter  = new StringBuilder(); // para siguientes
        StringBuilder tipo  = new StringBuilder(); // para indice


        graf.append("digraph G { rankdir=LR\n");
        graf.append("node [shape=record];\n");
        graf.append("node0[label=\"{");

        esta.append("{Estado");
        sig.append("|{ siguientes");
        ter.append("|{ terminal");
//        for (int i = 0; i < all_terminales.size() ;i++) {
//            JOptionPane.showMessageDialog(null, all_terminales.get(i),"NANI",JOptionPane.ERROR_MESSAGE);
//            ter.append("|{" + all_terminales.get(i) /*+ "}\n"*/ );
//        }
        tipo.append("|{Tipo ");
        for (int i = 0; i < tab_transiciones.size() ;i++) {
            esta.append("|" + tab_transiciones.get(i).name_estado );
            sig.append("|" + tab_transiciones.get(i).siguientes );
//            ter.append("|" + i );
            /*recorriendo a estados*/
//            JOptionPane.showMessageDialog(null, i +" * "+ tab_transiciones.get(i).name_estado + " can: " + tab_transiciones.get(i).ir_a.size(),"NANI",JOptionPane.ERROR_MESSAGE);
                ter.append("|{");
                for (int j = 0; j < tab_transiciones.get(i).ir_a.size() ;j++){
                //JOptionPane.showMessageDialog(null,"es: " + tab_transiciones.get(i).name_estado +" - " +tab_transiciones.get(i).ir_a.get(j).terminal,"ah",JOptionPane.ERROR_MESSAGE);
//                    if (tab_transiciones.get(i).ir_a.get(j).terminal.equals(all_terminales.get(te))){
                        ////ter.append("|" + tab_transiciones.get(i).name_estado+" * " + tab_transiciones.get(i).ir_a.get(j).terminal +"-" +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
//                    } else{
//                        ter.append("| -- "  );
//                    }

                    String lex =  tab_transiciones.get(i).ir_a.get(j).terminal;
                    lex = lex.replace("|", "\\|");
                    lex = lex.replace(">", "\\>");
                    
                    //ter.append( /*tab_transiciones.get(i).name_estado+" * " +*/ tab_transiciones.get(i).ir_a.get(j).terminal +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                    
                    if (j ==0) {
                        ter.append(lex +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                        //ter.append(tab_transiciones.get(i).ir_a.get(j).Tipo_ter+")" + lex +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                    } else{
                        ter.append("|" + lex +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                        //ter.append("|"+ tab_transiciones.get(i).ir_a.get(j).Tipo_ter+")" + lex +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                    }
                }
                ter.append("}\n");

//////            for (int te = 0; te < all_terminales.size() ; te++) {
//////                
//////                for (int j = 0; j < tab_transiciones.get(i).ir_a.size() ;j++){
//////                    if (tab_transiciones.get(i).ir_a.get(j).terminal.equals(all_terminales.get(te))){
//////                        ter.append("|" + tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
//////                    } else{
//////                        ter.append("| -- "  );
//////                    }
//////                }
//////            }
            //tipo.append("|" + tab_transiciones.get(i).tipo_estado );
            tipo.append("|" + tab_transiciones.get(i).terminal );
            
//            /*recorriendo a estados*/
//            for (int j = 0; j < tab_transiciones.get(i).ir_a.size() ;j++){
//                
//            }
            
            
        }
        esta.append("}\n");
        sig.append("}\n");
        ter.append("}\n");
        tipo.append("}\n");
        
        graf.append(esta);
        graf.append(sig);
        graf.append(ter);
        graf.append(tipo);
        
        graf.append("}\"];\n");
        graf.append("}");

        //JOptionPane.showMessageDialog(null,graf);
        return this.graf_arbolavl(graf.toString(),"tab_tran_"+ name_expre);
    }
    
    /*graficando automata*/
    public boolean graficando_Automata(){
        StringBuilder graf  = new StringBuilder(); //grafica total
        
        StringBuilder automatas  = new StringBuilder(); // para las transiciones
        StringBuilder finales  = new StringBuilder(); // para los estados finales

        graf.append("digraph finite_state_machine {\n");
        graf.append("rankdir=LR;\n");
        graf.append("size=\"8,5\"");
        graf.append("\n");

        for (int i = 0; i < tab_transiciones.size() ;i++) {
            
            if (tab_transiciones.get(i).terminal.equals("F")) {
                finales.append(" " +tab_transiciones.get(i).name_estado );
            }
        
                for (int j = 0; j < tab_transiciones.get(i).ir_a.size() ;j++){

                    automatas.append(tab_transiciones.get(i).name_estado +" -> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                    automatas.append("[ label = \"" + tab_transiciones.get(i).ir_a.get(j).terminal +"\" ];" );
                    automatas.append("\n");

                }
        }

        graf.append("node [shape = doublecircle]; ");
        graf.append(finales);
        graf.append("\n");
        graf.append("node [shape = circle];\n");
        graf.append(automatas);

        graf.append("\n}");

        //JOptionPane.showMessageDialog(null,graf);
        return this.graf_arbolavl(graf.toString(),"graf_automata_"+ name_expre);
    }
    
    //////////////////////////////////
    /*recortofpd*/
    int iden;
    void preOrder()  
    {  
        iden = 0;
        preOrder(this.root);
    }
    void preOrder(NodeArbol node)  
    {  
        if (node != null)  
        {  
            SetIndentificador(node);
            preOrder(node.left);  
            preOrder(node.right);  
        }  
    }
    
    /*recorrido pos order*/
    void posOrder()  
    {  
        posOrder(this.root);
    }
    void posOrder(NodeArbol node)  
    {  
        if (node != null)  
        {  
            posOrder(node.left);  
            posOrder(node.right);  
            
            if (node.right == null && node.left == null)  {/*JOptionPane.showMessageDialog(null, node.lexema);*/}else{
                //JOptionPane.showMessageDialog(null, node.lexema,"NANI",JOptionPane.ERROR_MESSAGE);
                setAnulPrimUl(node);
            }
        }  
    }
    
    void setAnulPrimUl(NodeArbol node){
        
        /*operador OR*/
        if(node.lexema.equals("|")){
            
            /*set anulable*/
            if (node.left.Anulable.equals("V") || node.right.Anulable.equals("V")) {
                node.Anulable = "V";
            } else {
                node.Anulable = "F";
            }
            /*set primeros, ultmos*/
            node.primeros = node.left.primeros + ", " + node.right.primeros;
            node.ultimos = node.left.ultimos + ", " + node.right.ultimos;
        }
        /*operador AND*/
        if(node.lexema.equals(".")){
            if (node.left.Anulable.equals("V") && node.right.Anulable.equals("V")) {
                node.Anulable = "V";
            } else {
                node.Anulable = "F";
            }
            /*set primero and*/
            /*lrft = c1
            si c1.anulable = V
            c1 u c2
            si no
            ci
            */
            if (node.left.Anulable.equals("V")) {
                node.primeros = node.left.primeros + ", " + node.right.primeros;
            } if (node.left.Anulable.equals("F")) {
                node.primeros = node.left.primeros;
            }
            /*set ultimo*/
            /*right = c2*/
            if (node.right.Anulable.equals("V")) {
                node.ultimos = node.left.ultimos + ", " + node.right.ultimos;
            } if (node.right.Anulable.equals("F")) {
                node.ultimos = node.right.ultimos;
            }
            
        }
        //*
        else if(node.lexema.equals("*")){
            node.Anulable = "V";
            /*suponiendo que el unico nodo que tiene lo 
            tiene a la derecha*/
            node.primeros = node.right.primeros;
            node.ultimos = node.right.ultimos;     
        }      
        //?
        else if(node.lexema.equals("?")){
            node.Anulable = "V";
            /*suponiendo que el unico nodo que tiene lo 
            tiene a la derecha*/
            node.primeros = node.right.primeros;
            node.ultimos = node.right.ultimos;     
        }
        ///falta confirmar si es asi :V
        //+
        else if(node.lexema.equals("+")){
            node.Anulable = node.right.Anulable;
            /*suponiendo que el unico nodo que tiene lo 
            tiene a la derecha*/          
            node.primeros = node.right.primeros;
            node.ultimos = node.right.ultimos;     
        }
    }
    
    /*recodiendo arbol para los siguientes*/
    void posOrder_sig()  
    {  
        posOrder_sig(this.root);
    }
    void posOrder_sig(NodeArbol node)  
    {  
        if (node != null)  
        {  
            posOrder_sig(node.left);  
            posOrder_sig(node.right);  
            
            if (node.right == null && node.left == null)  {}else{
                TabSiguientes(node);
            }
        }  
    }
    
    /*creando tabla de sisguientes*/
    void TabSiguientes(NodeArbol node){
        
        //*
        if(node.lexema.equals("*")){
            /*rigth ultima -: left sguiente*/
            String[] ultimos = node.ultimos.split(",");
            for (int i = 0; i < ultimos.length ;i++) {
                //JOptionPane.showMessageDialog(null, "ultimos: " + ultimos[i].trim());     
                for (Siguientes tab : tabla_siguientes) {
                    if (tab.id == Integer.parseInt(ultimos[i].trim())){
                        //tab.nexts = tab.nexts + ", " + node.primeros;
                        ////////
                        String[] primeros = node.primeros.split(",");
                        for (int pri = 0; pri < primeros.length ;pri++){
                            //JOptionPane.showMessageDialog(null, "primeros: " + primeros[pri].trim());   
                            tab.nexts.add(Integer.parseInt(primeros[pri].trim()));
                        }
                        /////////

                    }
                }
            }   
        }
        //.
        else
            if(node.lexema.equals(".")){
            String[] ultimosC1 = node.left.ultimos.split(",");
            for (int i = 0; i < ultimosC1.length ;i++) {
                //JOptionPane.showMessageDialog(null, ultimosC1[i].trim());     
                for (Siguientes tab : tabla_siguientes) {
                    if (tab.id == Integer.parseInt(ultimosC1[i].trim())){
                        //tab.nexts = tab.nexts + ", " + node.right.primeros;
                        
                        ////////
                        String[] primeros = node.right.primeros.split(",");
                        for (int pri = 0; pri < primeros.length ;pri++){
                            //JOptionPane.showMessageDialog(null, "primeros: " + primeros[pri].trim());   
                            tab.nexts.add(Integer.parseInt(primeros[pri].trim()));
                        }
                        /////////
                    }
                }
            }
            
        }
        /*+*/
        else
            if(node.lexema.equals("+")){
            /*rigth ultima -: left sguiente*/
            String[] ultimos = node.ultimos.split(",");
            for (int i = 0; i < ultimos.length ;i++) {
                //JOptionPane.showMessageDialog(null, ultimos[i].trim());     
                for (Siguientes tab : tabla_siguientes) {
                    if (tab.id == Integer.parseInt(ultimos[i].trim())){
                        //tab.nexts = tab.nexts + ", " + node.primeros;
                        
                        ////////
                        String[] primeros = node.primeros.split(",");
                        for (int pri = 0; pri < primeros.length ;pri++){
                            //JOptionPane.showMessageDialog(null, "primeros: " + primeros[pri].trim());   
                            tab.nexts.add(Integer.parseInt(primeros[pri].trim()));
                        }
                        /////////
                    }
                }
            }   
        }
    }
    
    /*creando tabla de transiciones*/
    void TabTransiciones(){
        tab_transiciones = new LinkedList<>();
        
        /*estado inicial*/
        String name_es;
        String[] inicial = this.root.left.primeros.split(",");
        //JOptionPane.showMessageDialog(null,"estado inicial: " +  inicial);  
        LinkedList<Integer> siguientes = new LinkedList<>();
        for (int i = 0; i < inicial.length ;i++) {
                //JOptionPane.showMessageDialog(null, inicial[i].trim());   
                siguientes.add(Integer.parseInt(inicial[i]));
        }
//        siguientes.add(4);
//        siguientes.add(1);
//        siguientes.add(7);
//        siguientes.add(10);
        Collections.sort(siguientes);
        JOptionPane.showMessageDialog(null,"siguientes: " +  siguientes);  
        ///////////
        name_es = "S0";
        
//        TTransiciones transi = new TTransiciones(name_es, siguientes, name_es);
//        tab_transiciones.add(transi);
        
        /////////////////////////////////////////JOptionPane.showMessageDialog(null,"recorriendo estado inicial ");  
        //LinkedList<Integer> siguientes_1 = new LinkedList<>();
        for (int i = 0; i < siguientes.size() ;i++) {
               JOptionPane.showMessageDialog(null, siguientes.get(i)); 
               Siguientes sig;
               sig = ObtengoSiguientes_ter(siguientes.get(i));
               
               JOptionPane.showMessageDialog(null," id:  " + sig.id + " hoja:  " + sig.hoja + " next:  " + sig.nexts);  
        }
        
        /*armando transciones*/
        graficando_tabTransiciones();
    }
    
    public boolean existe_ter(String ter){
        for (int i = 0; i < all_terminales.size() ;i++) {
            if (all_terminales.get(i).equals(ter)) {
                return true;
            }
        }
        return false;
    }
    
    LinkedList<String> all_terminales;
    void Create_TabTransiciones(){
//        /*guardando todas las terminales*/
//        all_terminales = new LinkedList<>();
//        for (int i = 0; i < tabla_siguientes.size() ;i++) {
//            if(i != tabla_siguientes.size()-1){
//                if (existe_ter(tabla_siguientes.get(i).hoja)==false){
//                    all_terminales.add(tabla_siguientes.get(i).hoja);
//                }
//            }
//        }
//        ///
      
        /*
            I = inicial
            N = normal
            F = estado final
        */

        tab_transiciones = new LinkedList<>();
        
        String[] inicial = this.root.left.primeros.split(",");
        LinkedList<Integer> siguientes = new LinkedList<>();
        for (int i = 0; i < inicial.length ;i++) {
                //JOptionPane.showMessageDialog(null, inicial[i].trim());   
                siguientes.add(Integer.parseInt(inicial[i].trim()));
        }
        Collections.sort(siguientes);
////////////        JOptionPane.showMessageDialog(null,"siguientes: " +  siguientes); 
        
        LinkedList<tran_a_estados> ir_a_estados = new LinkedList<>();
        
        String name_es = "S" + tab_transiciones.size();
        TTransiciones transi = new TTransiciones(name_es,siguientes,"I","N", ir_a_estados);
        tab_transiciones.add(transi);
        
        ////////////////////////////////////////////////////////////////////////////////
        /*iniciar a reoorrer los estados mientras hay un simbolo disponibe*/
        
        for (int i = 0; i < tab_transiciones.size() ;i++) {
////////////////            JOptionPane.showMessageDialog(null,"size(): " +  tab_transiciones.size() +" ** " +i); 
////////////////            JOptionPane.showMessageDialog(null,"actual analisis: " +  tab_transiciones.get(i).name_estado ); 
            if(tab_transiciones.get(i).tipo_estado.equals("N")){
                
                /*agrego en lista los terminaes, si hay vas id con el
                mismo terminal tambien lo agrego*/
//                JOptionPane.showMessageDialog(null,"tab_transiciones.get(i).siguientes: " +  tab_transiciones.get(i).siguientes); 
                LinkedList<AnalizarConjunto> new_con;
                new_con = Con_conjunto_analisis(tab_transiciones.get(i).siguientes);
                //JOptionPane.showMessageDialog(null,"new_con: " +  new_con); 
                
                /*aqui voy verificando los estados*/
                //////////////////////////////verificando siguientes 
                
                /**************************
                ya solo tomando un terminal, por si hay repetidos, solo toma una
                */
                for (int j = 0; j < new_con.size(); j++) {
                    
                    /*aqui para que el estado final ya no lo tiem en cuenta*/
                    if (!new_con.get(j).terminal.equals("#")) {
////////////////////                        JOptionPane.showMessageDialog(null,new_con.get(j).terminal +" ---+++--" + new_con.get(j).conjunto_s ); 
        
                    /*agreando insertar a transicion*/
                    
                        ///
//                    if(new_con.get(j).conjunto_s.size()==1){
//                        
//                        /*obtengo siguientes*/
//                        LinkedList<Integer> sige;                 
//                        sige = Siguientes_terminal(new_con.get(j).conjunto_s.get(0));
//                        Collections.sort(sige);
//                        JOptionPane.showMessageDialog(null,"sige: " +  sige); 
//                        
//                        /*si existe en transiciones agregar si no no*/
//                        ///si existe
//                        JOptionPane.showMessageDialog(null,"existe_Estado(sige): " +  existe_Estado(sige)); 
//                        if (existe_Estado(sige)) {
//                        } else // si no existe
//                        {
//                            String name_es_tem = "S" + tab_transiciones.size();
//                            TTransiciones transi_tem = new TTransiciones(name_es_tem,sige,new_con.get(j).terminal,"N");
//                            tab_transiciones.add(transi_tem);
//                        }
//                    } else
//                    if(new_con.get(j).conjunto_s.size() > 1){
                    /////
                        LinkedList<Integer> sige = null;     
                        for (int un = 0; un < new_con.get(j).conjunto_s.size(); un++){
                            if(un == 0){
                                ///la primera lista
                                sige = Siguientes_terminal(new_con.get(j).conjunto_s.get(un));
                            } else {
                                //concatea las uniones
                                LinkedList<Integer> lis2;
                                lis2 = Siguientes_terminal(new_con.get(j).conjunto_s.get(un));
                                for (int junt = 0; junt < lis2.size(); junt++) {
                                    if (exite(sige,lis2.get(junt)) == false){
                                        sige.add(lis2.get(junt));
                                    }
                                }                     
                            }
                        }
                        
                        Collections.sort(sige);
////////////////////                        JOptionPane.showMessageDialog(null,"sige new lis1: " + sige); 
                        /*si existe en transiciones agregar si no no*/
                        ///si existe
////////////////////                        JOptionPane.showMessageDialog(null,"existe_Estado(sige): " +  existe_Estado(sige)); 
                        if (existe_Estado(sige)) {
                            ///agrega estado de transicion
                            
                            //JOptionPane.showMessageDialog(null,"exite +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
                            //JOptionPane.showMessageDialog(null, "(" +tab_transiciones.get(i).name_estado+")" + new_con.get(j).terminal +" -> " + name_Estado(sige)); 
                            
                            //tab_transiciones.get(i).ir_a.add(new tran_a_estados(new_con.get(j).terminal, name_Estado(sige)));
                            tab_transiciones.get(i).ir_a.add(new tran_a_estados(new_con.get(j).terminal, name_Estado(sige), new_con.get(j).tipo_terminal ));
                        } else // si no existe
                        {
                            /*verificando si es estado final ya no agrega */
                            LinkedList<tran_a_estados> ir_a_estados_tem = new LinkedList<>();
                            String name_es_tem = "S" + tab_transiciones.size();
                            
                            /*verifica si es estado final*/
                            String tipo_estado = TipoEstado(sige);
                            //TTransiciones transi_tem = new TTransiciones(name_es_tem,sige,new_con.get(j).terminal,"N", ir_a_estados_tem);
                            TTransiciones transi_tem = new TTransiciones(name_es_tem,sige,tipo_estado,"N", ir_a_estados_tem);
                            tab_transiciones.add(transi_tem);
                            
                            //JOptionPane.showMessageDialog(null,"No existe +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
                            //JOptionPane.showMessageDialog(null, "(" +tab_transiciones.get(i).name_estado+")" + new_con.get(j).terminal +" -> " + name_Estado(sige)); 
                            
                            tab_transiciones.get(i).ir_a.add(new tran_a_estados(new_con.get(j).terminal, name_Estado(sige), new_con.get(j).tipo_terminal));
                            
                            
                           
                        }

//                    } 
                } /// fin no es estado final
                }
                //////////////////////////
                
            }
            
            
            
//            graficando_tabTransiciones();
//            JOptionPane.showMessageDialog(null,"finaliza una iteracion");
        }
        
        graficando_tabTransiciones();
    }
    
    public String TipoEstado(LinkedList<Integer> tipo){
        
        for (int i = 0; i < tipo.size(); i++) {
            /*verfico si es estado final*/
            int ter = tipo.get(i);
            for (int j = 0; j < tabla_siguientes.size() ;j++) {
                if ((ter == tabla_siguientes.get(j).id)&&(tabla_siguientes.get(j).hoja.equals("#"))){
                    return "F";
                }
            }
            
        }
        return "N";
    }
    


    public boolean exite(LinkedList<Integer> lis1, int v2){
        for (int i = 0; i < lis1.size(); i++) {
            if (lis1.get(i)== v2){
                return true;
            }
        }
        return false;
    }
    
    public boolean existe_Estado(LinkedList<Integer> new_estado){
        
        for (int i = 0; i < tab_transiciones.size() ;i++) {
            if (tab_transiciones.get(i).siguientes.equals(new_estado)) {
                //JOptionPane.showMessageDialog(null," las listas son iguales");
                return true;
            } 
//            else {
//                JOptionPane.showMessageDialog(null," No iguales");
//            }
        }
        return false;
    }
    
    public String name_Estado(LinkedList<Integer> new_estado){
        
        for (int i = 0; i < tab_transiciones.size() ;i++) {
            if (tab_transiciones.get(i).siguientes.equals(new_estado)) {
                return tab_transiciones.get(i).name_estado;
            } 
        }
        return null;
    }
    
    public LinkedList<AnalizarConjunto> Con_conjunto_analisis(LinkedList<Integer> conj){
    //public void Con_conjunto_analisis(LinkedList<Integer> conj){
        LinkedList<AnalizarConjunto> conjunto_actul = new LinkedList<>();
////////////        //JOptionPane.showMessageDialog(null,"conj: " +  conj); 
        for (int i = 0; i < conj.size() ;i++) {
            //1. obtengo el indice
            //2. obtengo el terminal
            String ter = Obtego_terminal_enSig(conj.get(i));
            String tipo_ter = Obtego_terminal_enSig_Tipo(conj.get(i)); // new tipo
////////////////            JOptionPane.showMessageDialog(null,"ter: " +  ter); 
            //comparo si existe ya, se agrega en su indice si no solo se agrega
            int index = Busca_terminal(conjunto_actul, ter);
            if(index == -1){
                LinkedList<Integer> new_con = new LinkedList<>();
                new_con.add(conj.get(i));
                //conjunto_actul.add(new AnalizarConjunto(ter,new_con ) );
                conjunto_actul.add(new AnalizarConjunto(ter,new_con, tipo_ter) ); //
                ///agrega a conjunto no existe entonces agrega
//////////////////////////                JOptionPane.showMessageDialog(null,"no existe entonces agrega - " + conj.get(i)); 
            } else
            { /// ya existe terminal encontes solo agrego a los demas
                 ///agrega a conjunto existe solo add
//////////////////                JOptionPane.showMessageDialog(null,"existe solo add - " + conj.get(i)); 
                conjunto_actul.get(index).conjunto_s.add(conj.get(i));
            }
        }
        return conjunto_actul;
    }
    
    public int Busca_terminal(LinkedList<AnalizarConjunto> conjunto_actul, String terminal){
        for (int i = 0; i < conjunto_actul.size() ;i++) {
            if (terminal.equals(conjunto_actul.get(i).terminal)) {
                return i;
            }
        }
        return -1; /// no esta dentro del listado
    }
    
    /*para obtener el lexama del terminar*/
    public String Obtego_terminal_enSig(int idter){
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            
            if (tabla_siguientes.get(i).id == idter) {
                return tabla_siguientes.get(i).hoja;
            }
        }
        return "";
    }
    
    /*para obtener el tipo de lexema*/
    public String Obtego_terminal_enSig_Tipo(int idter){
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            
            if (tabla_siguientes.get(i).id == idter) {
                return tabla_siguientes.get(i).Tipo_hoja;
            }
        }
        return "";
    }
    
    public Siguientes ObtengoSiguientes_ter(int idter){
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            
            if (tabla_siguientes.get(i).id == idter) {
                return tabla_siguientes.get(i);
            }
        }
        return null;
    }
    
    public LinkedList<Integer> Siguientes_terminal(int idter){
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            
            if (tabla_siguientes.get(i).id == idter) {
                return tabla_siguientes.get(i).nexts;
            }
        }
        return null;
    }
    
    void SetIndentificador(NodeArbol node){
        if (node.right == null && node.left == null)  {
            iden++;
            node.identificador = iden;
            node.primeros = String.valueOf(iden);
            node.ultimos = String.valueOf(iden);
            node.Anulable = "F";
            
            /*insertando en tabla*/
            LinkedList<Integer> nsig = new LinkedList<>();
            //Siguientes sig = new Siguientes(node.lexema, iden, nsig);
            Siguientes sig = new Siguientes(node.lexema, iden, nsig, node.tipo);
            tabla_siguientes.add(sig);
        }
        
    }
    
//    ////////////////////////inicio evaliando lexemas
//    public void EvaluandoLexema(String entrada){
//        TTransiciones transcion;
//        int idEstado = 0;
//        String lexema = "";
//        int estado_interno = 0;
//        transcion = tab_transiciones.get(idEstado);
//        char c;
//        entrada = entrada + " ";
//            
//        JOptionPane.showMessageDialog(null, "#"+entrada +"#");
//        for (int i = 0; i < entrada.length() ; i++){
//            //
//            c = entrada.charAt(i); 
//            //JOptionPane.showMessageDialog(null, c);
//            /*inicializamos con el estado inicial*/
//            switch (estado_interno)
//            {
//                case 0:
//                    
//                    /*para numero*/
//                    if (Character.isDigit(c))
//                    {
//                        estado_interno = 2;
//                        lexema += c;
//                    }
//                    /*para cadena*/
//                    else if (Character.isDigit(c))
//                    {
//                        estado_interno = 1;
//                        lexema += c;
//                    }
//                break;
//                /*para numero*/
//                case 2:
//                        if (Character.isDigit(c))
//                        {
//                            lexema += c;
//                            estado_interno = 2;
//                        }
//
//                        else
//                        {
//
//////                            addToken(lexema, "Digito", fila, columna - lexema.length());
//                            JOptionPane.showMessageDialog(null,"num: " +  lexema);
//                            lexema = "";
//                            i--;
//                            estado_interno = 0;
//                        }
//                        break;
//                        
//            }
//            
//            
//        }
//    }
    /////
//    public void Eva_Conjuntos(){
//        
//    }
//    
//    public void Eva_Cadena(String lexema){
//        
//    }
    
    public int getIndexEstado(String estado){
        for (int i = 0; i < tab_transiciones.size() ; i++){
            if (tab_transiciones.get(i).name_estado.equals(estado)){
                return i;
            }
        }
        return -99;
    }
    
//Eva_Lexema_Estado(entrada, cad_actual, estado_interno, i);
  int indice_continuar;
  public boolean Eva_Lexema_Estado(String entrada, String cad_actual, int estado_interno, int ind ){
//        TTransiciones transcion;
////////////        int idEstado = 0;
        String lexema = "";
////////////        int estado_interno = 0;
//        transcion = tab_transiciones.get(idEstado);
        char c;
////////////        entrada = entrada + " ";          
//////////        JOptionPane.showMessageDialog(null, "22#"+entrada +"#");
        /*
Estados
0 = inicial
1 = conjuntos
2 = cadena

-99 = estado error

        */
//////////        String cad_actual ="";
        int i_cad_actual = 0;
        String Ir_a_estado_si_exito = "";
        
        //for (int i = 0; i < entrada.length() ; i++){
        //ind--;
        for (int i = ind; i < entrada.length() ; i++){
            //
            c = entrada.charAt(i); 
//////////////////            JOptionPane.showMessageDialog(null, "2e"+estado_interno + " cccccccccccccccc: "+c);
            /*inicializamos con el estado inicial*/
            switch (estado_interno)
            {                   
                /*para armar conjuntos*/
                case 1:
                    /*buscando si existe conjunto*/
                    Variables conjunto = existeCon(cad_actual);
                    if (conjunto == null) {
////////////////////                        JOptionPane.showMessageDialog(null, "no existe conjunto - " +c);
                        return false;
                    }
                    
                    /*verificando tipo de conjunto*/
                    if (conjunto.tipo.equals("C")) {
                        boolean existe = existe_valor_enConjunto(String.valueOf(c),conjunto.valores);
                        
                        if (existe) {
                            estado_interno = 0;
                            cad_actual = "";
                            lexema = "";
                            indice_continuar = i;
                            return true;
                        } else {
////////////                            JOptionPane.showMessageDialog(null, "No existe valor "+ c + " en Conjunto " + cad_actual);
                            return false;
                        }
                        
                    }
//                    else if (conjunto.tipo.equals("R")) {
////                        boolean existe = existe_valor_enConjunto(String.valueOf(c),conjunto.valores);
////                        
////                        if (existe) {
////                            estado_interno = 0;
////                            cad_actual = "";
////                            lexema = "";
////                            indice_continuar = i;
////                            return true;
////                        } else {
////                            return false;
////                        }
//                        
//                    }
                    
                break; 
                /*para armar la cadena*/
                case 2:
                    lexema += c;
                    i_cad_actual++;
                    if (i_cad_actual >  cad_actual.length()) {
                        estado_interno = -99;
                        return false;
                    }
                    String conca_cad = cad_actual.substring(0, i_cad_actual);
                    //JOptionPane.showMessageDialog(null, "+++cad_actual: "+ cad_actual + " creando cad: " + conca_cad + " - lex: "+ lexema);                  
//////////////                   JOptionPane.showMessageDialog(null, " creando cad: " + conca_cad + " - lex: "+ lexema);                  
                                       
                    if (conca_cad.equals(lexema)) {
                        //JOptionPane.showMessageDialog(null, "pasa"); 
                        if (i_cad_actual ==  cad_actual.length()) {
                            
                            //acepta estado
                            estado_interno = 0;
                            cad_actual = "";
                            lexema = "";
                            //i--;
                            //idEstado = getIndexEstado(Ir_a_estado_si_exito);
                            indice_continuar = i;
                            //JOptionPane.showMessageDialog(null, "Cadena aceptada"); 
                            return true;
                        } 
                    } else {
                        estado_interno = -99;
                        ////JOptionPane.showMessageDialog(null, "se enconetro error, no pasa lexema");
                        return false;
                    }
                    
                        break;
//                case -99:
//                    i = entrada.length();
//                    JOptionPane.showMessageDialog(null, "No paso Prueba de Lexema"); 
//                break;                   
            }
        }
        return false;
    }
   
  public boolean existe_valor_enConjunto(String val, LinkedList<String> valores){
      for (int i = 0; i < valores.size() ; i++){
          if (val.equals(valores.get(i))) {
              return true;
          }
      }
      return false;
  }
  
//  public boolean existe_Rango_enConjunto(String val, LinkedList<String> valores){
//      for (int i = 0; i < valores.size() ; i++){
//          if (val.equals(valores.get(i))) {
//              return true;
//          }
//      }
//      return false;
//  }
  public Variables existeCon(String name_con){
      
      for (int i = 0; i < lis_conjuntos.size() ; i++){
          if (name_con.equals(lis_conjuntos.get(i).name_var)) {
              return lis_conjuntos.get(i);
          }
      }
      return null;
  }
  public void EvaluandoLexema_final(String entrada, LinkedList<Variables> lis_con){
      /*asignado valor a lista de conjuntos*/
      lis_conjuntos = lis_con;
        TTransiciones transcion;
        int idEstado = 0;
        String lexema = "";
        int estado_interno = 0;
        transcion = tab_transiciones.get(idEstado);
        char c;
////////        entrada = entrada + " ";
                 
////////        JOptionPane.showMessageDialog(null, "#"+entrada +"#");
        
        /*
        Estados
        0 = inicial
        1 = conjuntos
        2 = cadena

        -99 = estado error
        */
        boolean Acepta_lexema = false;
        String cad_actual ="";
        int i_cad_actual = 0;
        String Ir_a_estado_si_exito = "";
        for (int i = 0; i < entrada.length() ; i++){
            //
            c = entrada.charAt(i); 
////////            JOptionPane.showMessageDialog(null, i+ ")) *interno: "+estado_interno + " c: "+c);
            /*inicializamos con el estado inicial*/
            
                    boolean pasa = false;
                    for (int j = 0; j < tab_transiciones.get(idEstado).ir_a.size() ; j++){
//////////////JOptionPane.showMessageDialog(null,j + "jjjjjj pasa: "+pasa);
                        String tipo = tab_transiciones.get(idEstado).ir_a.get(j).Tipo_ter;
                        
                        if (tipo.equals("CO")) {
                            cad_actual = tab_transiciones.get(idEstado).ir_a.get(j).terminal;
                            Ir_a_estado_si_exito = tab_transiciones.get(idEstado).ir_a.get(j).Ir_a_Estado;
                            estado_interno = 1;
                            //i_cad_actual = 0;
                            //i--;
//////////////                            JOptionPane.showMessageDialog(null,"1 S: " + tab_transiciones.get(idEstado).name_estado + " CO: "+ tab_transiciones.get(idEstado).ir_a.get(j).terminal);
                            
                            pasa = Eva_Lexema_Estado(entrada, cad_actual, estado_interno, i);
                            if (pasa) {
                                i = indice_continuar+1;
                                j = -1;
                                idEstado = getIndexEstado(Ir_a_estado_si_exito);
//////////////                                JOptionPane.showMessageDialog(null, "CO pasa: "+ pasa+ " Ir_a_estado_si_exito: " + Ir_a_estado_si_exito);
                                
                            }
                            String Aceptacion = tab_transiciones.get(idEstado).terminal;
                                if (Aceptacion.equals("F") ) {
                                    Acepta_lexema = true;
                                } else {
                                    Acepta_lexema = false;
                                }
         
////////////                            JOptionPane.showMessageDialog(null,"1 vf Aceptacion: "+ Aceptacion + " idEstado = " + idEstado);
                        
                        }
                        else if (tipo.equals("CA"))
                        {
                            cad_actual = tab_transiciones.get(idEstado).ir_a.get(j).terminal;
                            Ir_a_estado_si_exito = tab_transiciones.get(idEstado).ir_a.get(j).Ir_a_Estado;
                            estado_interno = 2;
                            //i_cad_actual = 0;
                            //i--;
////////////////////                            JOptionPane.showMessageDialog(null,"2 S: " + tab_transiciones.get(idEstado).name_estado +  " CA: "+ tab_transiciones.get(idEstado).ir_a.get(j).terminal);
                            pasa = Eva_Lexema_Estado(entrada, cad_actual, estado_interno, i);
                            if (pasa) {
                                i = indice_continuar+1;
                                j = -1;
                                idEstado = getIndexEstado(Ir_a_estado_si_exito); 
////////////////                                JOptionPane.showMessageDialog(null, "CA pasa: "+ pasa+ " Ir_a_estado_si_exito: " + Ir_a_estado_si_exito);
                            }
                            String Aceptacion = tab_transiciones.get(idEstado).terminal;
                            if (Aceptacion.equals("F") ) {
                                Acepta_lexema = true;
                            } else {
                                Acepta_lexema = false;
                            }
                            
                       
////////                            JOptionPane.showMessageDialog(null,"1 vf Aceptacion: "+ Aceptacion + " idEstado = " + idEstado);
                                  
                        }
                    }  
//            if (Acepta_lexema){
//                //JOptionPane.showMessageDialog(null, "*************LEXEMA CORRECTO ");
//            } else {

//////////////                JOptionPane.showMessageDialog(null, " pasa: "+ pasa);
                if (pasa==false) {
                    //Acepta_lexema = false;
                    i = entrada.length();
                    //JOptionPane.showMessageDialog(null, "*************************termino, No ");
                }
//            }
        }
        
        if (Acepta_lexema){
////////                JOptionPane.showMessageDialog(null, "*************LEXEMA CORRECTO ");
                resul_expresiones.add(entrada + "// validacion exitosa del lexema");
            } else {
////////                    JOptionPane.showMessageDialog(null, "*************************termino, No ");
                    resul_expresiones.add(entrada + "// error en la validacion del lexema");
            }
    }
  
  public LinkedList<String> getResul_ex(){
      return resul_expresiones;
  }
  
    public void EvaluandoLexema_f(String entrada){
        TTransiciones transcion;
        int idEstado = 0;
        String lexema = "";
        int estado_interno = 0;
        transcion = tab_transiciones.get(idEstado);
        char c;
        entrada = entrada + " ";
        
            
        JOptionPane.showMessageDialog(null, "#"+entrada +"#");
        
//        for (int i = 0; i < tab_transiciones.get(idEstado).ir_a.size() ; i++){
//            JOptionPane.showMessageDialog(null, tab_transiciones.get(idEstado).ir_a.get(i).terminal );
//        }
        /*
Estados
0 = inicial
1 = conjuntos
2 = cadena

-99 = estado error

        */
        String cad_actual ="";
        int i_cad_actual = 0;
        String Ir_a_estado_si_exito = "";
        for (int i = 0; i < entrada.length() ; i++){
            //
            c = entrada.charAt(i); 
            JOptionPane.showMessageDialog(null, "estado_interno: "+estado_interno + " char: "+c);
            /*inicializamos con el estado inicial*/
            switch (estado_interno)
            {
                /*estado inicial*/
                case 0:
                    
                    for (int j = 0; j < tab_transiciones.get(idEstado).ir_a.size() ; j++){

                        String tipo = tab_transiciones.get(idEstado).ir_a.get(j).Tipo_ter;
                        
                        if (tipo.equals("CO")) {
                            JOptionPane.showMessageDialog(null, "CO: "+ tab_transiciones.get(idEstado).ir_a.get(j).terminal);
                            estado_interno = 1;
                            //i_cad_actual = 0;
                            i--;
                        }
                        else if (tipo.equals("CA"))
                        {
                            cad_actual = tab_transiciones.get(idEstado).ir_a.get(j).terminal;
                            Ir_a_estado_si_exito = tab_transiciones.get(idEstado).ir_a.get(j).Ir_a_Estado;
                            estado_interno = 2;
                            i_cad_actual = 0;
                            i--;
                            JOptionPane.showMessageDialog(null, "CA: "+ tab_transiciones.get(idEstado).ir_a.get(j).terminal);
//                            Eva_Cadena(tab_transiciones.get(idEstado).ir_a.get(j).terminal);
                        }
                    }
                    
//                    /*para numero*/
//                    if (Character.isDigit(c))
//                    {
//                        estado_interno = 2;
//                        lexema += c;
//                    }
//                    /*para cadena*/
//                    else if (Character.isDigit(c))
//                    {
//                        estado_interno = 1;
//                        lexema += c;
//                    }
                break;
                
                /*para armar conjuntos*/
                case 1:
                    
                break; 
                /*para armar la cadena*/
                case 2:
                    lexema += c;
                    i_cad_actual++;
                    if (i_cad_actual >  cad_actual.length()) {
                        estado_interno = -99;
                    }
                    String conca_cad = cad_actual.substring(0, i_cad_actual);
                    //JOptionPane.showMessageDialog(null, "+++cad_actual: "+ cad_actual + " creando cad: " + conca_cad + " - lex: "+ lexema);                  
                    JOptionPane.showMessageDialog(null, " creando cad: " + conca_cad + " - lex: "+ lexema);                  
                                       
                    if (conca_cad.equals(lexema)) {
                        JOptionPane.showMessageDialog(null, "pasa"); 
                        if (i_cad_actual ==  cad_actual.length()) {
                            
                            //acepta estado
                            estado_interno = 0;
                            cad_actual = "";
                            lexema = "";
                            //i--;
                            idEstado = getIndexEstado(Ir_a_estado_si_exito);
                            
                            JOptionPane.showMessageDialog(null, "Cadena aceptada - " + idEstado); 
                        } 
                    } else {
                        estado_interno = -99;
                        JOptionPane.showMessageDialog(null, "se enconetro error, no pasa lexema"); 
                    }
                    
                    
//                        if (Character.isDigit(c))
//                        {
//                            lexema += c;
//                            estado_interno = 2;
//                        }
//
//                        else
//                        {
//
//////                            addToken(lexema, "Digito", fila, columna - lexema.length());
//                            JOptionPane.showMessageDialog(null,"num: " +  lexema);
//                            lexema = "";
//                            i--;
//                            estado_interno = 0;
//                        }
                        break;
                case -99:
                    i = entrada.length();
                    JOptionPane.showMessageDialog(null, "No paso Prueba de Lexema"); 
                break;   
//                /*para numero*/
//                case 2:
//                        if (Character.isDigit(c))
//                        {
//                            lexema += c;
//                            estado_interno = 2;
//                        }
//
//                        else
//                        {
//
//////                            addToken(lexema, "Digito", fila, columna - lexema.length());
//                            JOptionPane.showMessageDialog(null,"num: " +  lexema);
//                            lexema = "";
//                            i--;
//                            estado_interno = 0;
//                        }
//                        break;
//                        
            }
            
            
        }
    }
    
    public void EvaluandoLexema_f_back(String entrada){
        TTransiciones transcion;
        int idEstado = 0;
        String lexema = "";
        int estado_interno = 0;
        transcion = tab_transiciones.get(idEstado);
        char c;
        entrada = entrada + " ";
        
            
        JOptionPane.showMessageDialog(null, "#"+entrada +"#");
        
//        for (int i = 0; i < tab_transiciones.get(idEstado).ir_a.size() ; i++){
//            JOptionPane.showMessageDialog(null, tab_transiciones.get(idEstado).ir_a.get(i).terminal );
//        }
        /*
Estados
0 = inicial
1 = conjuntos
2 = cadena

-99 = estado error

        */
        String cad_actual ="";
        int i_cad_actual = 0;
        String Ir_a_estado_si_exito = "";
        for (int i = 0; i < entrada.length() ; i++){
            //
            c = entrada.charAt(i); 
            JOptionPane.showMessageDialog(null, "estado_interno: "+estado_interno + " char: "+c);
            /*inicializamos con el estado inicial*/
            switch (estado_interno)
            {
                /*estado inicial*/
                case 0:
                    
                    for (int j = 0; j < tab_transiciones.get(idEstado).ir_a.size() ; j++){

                        String tipo = tab_transiciones.get(idEstado).ir_a.get(j).Tipo_ter;
                        
                        if (tipo.equals("CO")) {
                            JOptionPane.showMessageDialog(null, "CO: "+ tab_transiciones.get(idEstado).ir_a.get(j).terminal);
                            estado_interno = 1;
                            //i_cad_actual = 0;
                            i--;
                        }
                        else if (tipo.equals("CA"))
                        {
                            cad_actual = tab_transiciones.get(idEstado).ir_a.get(j).terminal;
                            Ir_a_estado_si_exito = tab_transiciones.get(idEstado).ir_a.get(j).Ir_a_Estado;
                            estado_interno = 2;
                            i_cad_actual = 0;
                            i--;
                            JOptionPane.showMessageDialog(null, "CA: "+ tab_transiciones.get(idEstado).ir_a.get(j).terminal);
//                            Eva_Cadena(tab_transiciones.get(idEstado).ir_a.get(j).terminal);
                        }
                    }
                    
//                    /*para numero*/
//                    if (Character.isDigit(c))
//                    {
//                        estado_interno = 2;
//                        lexema += c;
//                    }
//                    /*para cadena*/
//                    else if (Character.isDigit(c))
//                    {
//                        estado_interno = 1;
//                        lexema += c;
//                    }
                break;
                
                /*para armar conjuntos*/
                case 1:
                    
                break; 
                /*para armar la cadena*/
                case 2:
                    lexema += c;
                    i_cad_actual++;
                    if (i_cad_actual >  cad_actual.length()) {
                        estado_interno = -99;
                    }
                    String conca_cad = cad_actual.substring(0, i_cad_actual);
                    //JOptionPane.showMessageDialog(null, "+++cad_actual: "+ cad_actual + " creando cad: " + conca_cad + " - lex: "+ lexema);                  
                    JOptionPane.showMessageDialog(null, " creando cad: " + conca_cad + " - lex: "+ lexema);                  
                                       
                    if (conca_cad.equals(lexema)) {
                        JOptionPane.showMessageDialog(null, "pasa"); 
                        if (i_cad_actual ==  cad_actual.length()) {
                            
                            //acepta estado
                            estado_interno = 0;
                            cad_actual = "";
                            lexema = "";
                            //i--;
                            idEstado = getIndexEstado(Ir_a_estado_si_exito);
                            
                            JOptionPane.showMessageDialog(null, "Cadena aceptada - " + idEstado); 
                        } 
                    } else {
                        estado_interno = -99;
                        JOptionPane.showMessageDialog(null, "se enconetro error, no pasa lexema"); 
                    }
                    
                    
//                        if (Character.isDigit(c))
//                        {
//                            lexema += c;
//                            estado_interno = 2;
//                        }
//
//                        else
//                        {
//
//////                            addToken(lexema, "Digito", fila, columna - lexema.length());
//                            JOptionPane.showMessageDialog(null,"num: " +  lexema);
//                            lexema = "";
//                            i--;
//                            estado_interno = 0;
//                        }
                        break;
                case -99:
                    i = entrada.length();
                    JOptionPane.showMessageDialog(null, "No paso Prueba de Lexema"); 
                break;   
//                /*para numero*/
//                case 2:
//                        if (Character.isDigit(c))
//                        {
//                            lexema += c;
//                            estado_interno = 2;
//                        }
//
//                        else
//                        {
//
//////                            addToken(lexema, "Digito", fila, columna - lexema.length());
//                            JOptionPane.showMessageDialog(null,"num: " +  lexema);
//                            lexema = "";
//                            i--;
//                            estado_interno = 0;
//                        }
//                        break;
//                        
            }
            
            
        }
    }
}
