/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_comp1;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author RAFAEL
 */
public class Arbol {
    
    NodeArbol root;
    public Arbol (NodeArbol root){
        this.root = root;
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
        
        return this.graf_arbolavl(graf.toString());
  
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
            
            graf.append("ANUL: "+ root_ac.Anulable+ "\\n" );
            
//            if (root_ac.lexema.equals("|")){
//                graf.append(" "+ "l" /*+ "\\n"*/);
//            } else {
                graf.append( "\\"+root_ac.lexema + "\\n");
//            }
            
            //if (root_ac.right == null && root_ac.left == null)  {
                graf.append("iden: "+ root_ac.identificador+ "\\n");
            //}
            
            
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
    
    
    public boolean graf_arbolavl(String grafica){
        //File archivo =new File("hash_user.txt");
        try
            {
            File archivo =new File("arbol_avl.txt");
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
            Process p = rt.exec("dot -Tpng arbol_avl.txt -o arbol_avl.jpg");
            p.waitFor();
            //rt.exec("hash_user.jpg");
            
            Desktop.getDesktop().open(new File("arbol_avl.jpg"));

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex,"NANI",JOptionPane.ERROR_MESSAGE);
                return false;
            } finally {}
        
        return true;
        
    }
    
    //////////////////////////////////fin graficando arbol
    
    
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
            JOptionPane.showMessageDialog(null, node.lexema,"NANI",JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    void SetIndentificador(NodeArbol node){
        if (node.right == null && node.left == null)  {
            iden++;
            node.identificador = iden;
            node.primeros = iden;
            node.ultimos = iden;
            node.Anulable = "F";
        }
        
    }
}
