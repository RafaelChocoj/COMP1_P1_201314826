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
            
    NodeArbol root;
    public Arbol (NodeArbol root){
        this.root = root;
        
        tabla_siguientes = new LinkedList<>();
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
        
        return this.graf_arbolavl(graf.toString(), "arbol_a");
  
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
            
//            if (root_ac.lexema.equals("|")){
//                graf.append(" "+ "l" /*+ "\\n"*/);
//            } else {
                graf.append( "\\"+root_ac.lexema + "\\n");
//            }
            
            if (root_ac.right == null && root_ac.left == null)  {
                graf.append("iden: "+ root_ac.identificador+ "\\n");
            }
            
            
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


        graf.append("digraph G { rankdir=LR\n");
        graf.append("node [shape=record];\n");
        graf.append("node0[label=\"{");

        hoja.append("{Hoja");
        index.append("|{Hoja ID ");
        sig.append("|{Siguientes ");
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            hoja.append("|" + tabla_siguientes.get(i).hoja );
            index.append("|" + tabla_siguientes.get(i).id );
            sig.append("|" + tabla_siguientes.get(i).nexts );
        }
        hoja.append("}\n");
        index.append("}\n");
        sig.append("}\n");
        
        graf.append(hoja);
        graf.append(index);
        graf.append(sig);
        
        graf.append("}\"];\n");
        graf.append("}");

        //JOptionPane.showMessageDialog(null,graf);
        return this.graf_arbolavl(graf.toString(),"tab_sig");
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
            ter.append("|" + tab_transiciones.get(i).terminal );
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
        return this.graf_arbolavl(graf.toString(),"tab_tran");
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
                    if (j ==0) {
                        ter.append( /*tab_transiciones.get(i).name_estado+" * " +*/ tab_transiciones.get(i).ir_a.get(j).terminal +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
                    } else{
                        ter.append("|" + /*tab_transiciones.get(i).name_estado+" * " +*/ tab_transiciones.get(i).ir_a.get(j).terminal +" -\\> " +tab_transiciones.get(i).ir_a.get(j).Ir_a_Estado );
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
            tipo.append("|" + tab_transiciones.get(i).tipo_estado );
            
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
        return this.graf_arbolavl(graf.toString(),"tab_tran");
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
        
        JOptionPane.showMessageDialog(null,"recorriendo estado inicial ");  
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
      
        tab_transiciones = new LinkedList<>();
        
        String[] inicial = this.root.left.primeros.split(",");
        LinkedList<Integer> siguientes = new LinkedList<>();
        for (int i = 0; i < inicial.length ;i++) {
                //JOptionPane.showMessageDialog(null, inicial[i].trim());   
                siguientes.add(Integer.parseInt(inicial[i]));
        }
        Collections.sort(siguientes);
        JOptionPane.showMessageDialog(null,"siguientes: " +  siguientes); 
        
        LinkedList<tran_a_estados> ir_a_estados = new LinkedList<>();
        
        String name_es = "S" + tab_transiciones.size();
        TTransiciones transi = new TTransiciones(name_es,siguientes,"","N", ir_a_estados);
        tab_transiciones.add(transi);
        
        ////////////////////////////////////////////////////////////////////////////////
        /*iniciar a reoorrer los estados mientras hay un simbolo disponibe*/
        
        for (int i = 0; i < tab_transiciones.size() ;i++) {
            JOptionPane.showMessageDialog(null,"size(): " +  tab_transiciones.size() +" ** " +i); 
            JOptionPane.showMessageDialog(null,"actual analisis: " +  tab_transiciones.get(i).name_estado ); 
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
                        JOptionPane.showMessageDialog(null,new_con.get(j).terminal +" ---+++--" + new_con.get(j).conjunto_s ); 
        
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
                        JOptionPane.showMessageDialog(null,"sige new lis1: " + sige); 
                        /*si existe en transiciones agregar si no no*/
                        ///si existe
                        JOptionPane.showMessageDialog(null,"existe_Estado(sige): " +  existe_Estado(sige)); 
                        if (existe_Estado(sige)) {
                            ///agrega estado de transicion
                            
                            //JOptionPane.showMessageDialog(null,"exite +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
                            //JOptionPane.showMessageDialog(null, "(" +tab_transiciones.get(i).name_estado+")" + new_con.get(j).terminal +" -> " + name_Estado(sige)); 
                            
                            tab_transiciones.get(i).ir_a.add(new tran_a_estados(new_con.get(j).terminal, name_Estado(sige)));
                            
                        } else // si no existe
                        {
                            /*verificando si es estado final ya no agrega */
                            LinkedList<tran_a_estados> ir_a_estados_tem = new LinkedList<>();
                            String name_es_tem = "S" + tab_transiciones.size();
                            TTransiciones transi_tem = new TTransiciones(name_es_tem,sige,new_con.get(j).terminal,"N", ir_a_estados_tem);
                            tab_transiciones.add(transi_tem);
                            
                            //JOptionPane.showMessageDialog(null,"No existe +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
                            //JOptionPane.showMessageDialog(null, "(" +tab_transiciones.get(i).name_estado+")" + new_con.get(j).terminal +" -> " + name_Estado(sige)); 
                            
                            tab_transiciones.get(i).ir_a.add(new tran_a_estados(new_con.get(j).terminal, name_Estado(sige)));
                            
                            
                           
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
        JOptionPane.showMessageDialog(null,"conj: " +  conj); 
        for (int i = 0; i < conj.size() ;i++) {
            //1. obtengo el indice
            //2. obtengo el terminal
            String ter = Obtego_terminal_enSig(conj.get(i));
            JOptionPane.showMessageDialog(null,"ter: " +  ter); 
            //comparo si existe ya, se agrega en su indice si no solo se agrega
            int index = Busca_terminal(conjunto_actul, ter);
            if(index == -1){
                LinkedList<Integer> new_con = new LinkedList<>();
                new_con.add(conj.get(i));
                conjunto_actul.add(new AnalizarConjunto(ter,new_con ) );
                JOptionPane.showMessageDialog(null,"no existe entonces agrega - " + conj.get(i)); 
            } else
            { /// ya existe terminal encontes solo agrego a los demas
                JOptionPane.showMessageDialog(null,"existe solo add - " + conj.get(i)); 
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
    
    public String Obtego_terminal_enSig(int idter){
        for (int i = 0; i < tabla_siguientes.size() ;i++) {
            
            if (tabla_siguientes.get(i).id == idter) {
                return tabla_siguientes.get(i).hoja;
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
            Siguientes sig = new Siguientes(node.lexema, iden, nsig);
            tabla_siguientes.add(sig);
        }
        
    }
}
