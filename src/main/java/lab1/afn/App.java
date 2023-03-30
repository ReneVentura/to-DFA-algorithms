package lab1.afn;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.io.IOUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import lab1.afn.dfa.ToDfa;
import lab1.afn.preafn.extensionReg;
import lab1.afn.preafn.infpos;
//import lab1.afn.thompson.DfaMinimizer;
import lab1.afn.thompson.Edges;
import lab1.afn.thompson.NfaToDfaConverter;
import lab1.afn.thompson.Thompson;
import lab1.afn.preafn.infpos;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import lab1.afn.thompson.State;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javafx.application.Platform;
/*
 * 
 * copiar en terminal   1 para interpretar epsilon
    (a|b)*(b|a)*abb
    ((ε|a)b*)*
    (.|;)*-/.(.|;)*
    (x|t)+((a|m)?)+
    ("(.(;(.;(.|;)+)*)*)*

 */
public class App {
    
  

    public static void main(String[] args) throws IOException, InterruptedException {
      //  System.setProperty("org.graphstream.ui", "javafx");
        //Platform.startup(() -> {}); 
      
        /*List<Edges> edges = thompson.getTransitionList();
        
        Graph graph = new SingleGraph("Thompson NFA");
        
        // Add nodes
        for (int i = 0; i <= thompson.stateCount; i++) {
            Node node = graph.addNode(Integer.toString(i));
            if (i == thompson.stateCount) {
                node.setAttribute("ui.class", "final");
            }
            if (thompson.initialState.states.contains(i)) {
                node.setAttribute("ui.class", "initial");
            }
            node.setAttribute("ui.label", Integer.toString(i));
        }
        
        // Add edges
        int c=15;
        for (Edges edge : edges) {
            c++;
            graph.addEdge(Integer.toString(c) , Integer.toString(edge.getSource()), Integer.toString(edge.getTarget())).setAttribute("ui.label", edge.getLabel());
        }
        
        // Set style
        graph.setAttribute("ui.stylesheet", "graph { fill-color: white; } node { shape: circle; size: 30px, 30px; fill-color: white; stroke-mode: plain; stroke-color: black; text-size: 20px; text-color: black; text-style: bold; } node.final { fill-color: #D9E5FF; stroke-color: blue; } node.initial { fill-color: #D9E5FF; stroke-mode: double; stroke-color: green; } edge { fill-color: black; size: 2px; text-size: 20px; text-color: black; text-style: bold; }");
        
        // Display graph
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);*/
        // Create an instance of Thompson representing the NFA
         // create a new Thompson NFA
         // ejercicio 1
        System.out.print("*************** EJERCICIO 1 (a|b)*.(b|a)*.a.b.b *************************\n");
        System.out.print("---------- THOMPSON SIMULATION-----------\n");
        String regex = "(a|b)*.(b|a)*.a.b.b";
        String input="bbabb" ;
        String input2="babb" ;
        String input3="aaaaaaaaaabbbbbbabababababababababababababbb" ;
        String input4="abb" ;
        Thompson thompson = new Thompson(regex);
        System.out.println("Acepta "+ input+" ? "+thompson.accepts(input)+"\n"); 
        System.out.println("Acepta "+ input2+" ? "+thompson.accepts(input2)+"\n"); 
        System.out.println("Acepta "+ input3+" ? "+thompson.accepts(input3)+"\n"); 
        System.out.println("Acepta "+ input4+" ? "+thompson.accepts(input4)+"\n"); 
        System.out.print("---------- DFA SUBSET ALGORITHM AND SIMULATION-----------\n");
        Thompson nfa = new Thompson(regex);
        NfaToDfaConverter converter = new NfaToDfaConverter(nfa);
        converter.toGraph("nfadfa1");
        System.out.println("Acepta "+input+" ? "+converter.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+converter.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+converter.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+converter.accepts(input4)+"\n");
        System.out.print("---------- DFA DIRECT CONVERSION ALGORITHM AND SIMULATION-----------\n");
        ToDfa dfa= new ToDfa(infpos.infixToPostfix(regex));
        dfa.toGraph("direcdfa1");
        System.out.println("Acepta "+input+" ? "+dfa.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+dfa.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+dfa.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+dfa.accepts(input4)+"\n");
        // ejercicio 2
        System.out.print("*************** EJERCICIO 2 ((ε|a).b*)* *************************\n");
        System.out.print("---------- THOMPSON SIMULATION-----------\n");
        regex = "((ε|a).b*)*";
        input=" " ;
        input2="a" ;
        input3="aba" ;
        input4="abba" ;
        thompson = new Thompson(regex);
        System.out.println("Acepta "+ input+" ? "+thompson.accepts(input)+"\n"); 
        System.out.println("Acepta "+ input2+" ? "+thompson.accepts(input2)+"\n"); 
        System.out.println("Acepta "+ input3+" ? "+thompson.accepts(input3)+"\n"); 
        System.out.println("Acepta "+ input4+" ? "+thompson.accepts(input4)+"\n"); 
        System.out.print("---------- DFA SUBSET ALGORITHM AND SIMULATION-----------\n");
        nfa = new Thompson(regex);
        converter = new NfaToDfaConverter(nfa);
        converter.toGraph("nfadfa2");
        System.out.println("Acepta "+input+" ? "+converter.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+converter.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+converter.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+converter.accepts(input4)+"\n");
        System.out.print("---------- DFA DIRECT CONVERSION ALGORITHM AND SIMULATION-----------\n");
        dfa= new ToDfa(infpos.infixToPostfix(regex));
        dfa.toGraph("direcdfa2");
        System.out.println("Acepta "+input+" ? "+dfa.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+dfa.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+dfa.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+dfa.accepts(input4)+"\n");
        
        // ejercicio 3
        try{
        System.out.print("*************** EJERCICIO 3 (.|;)*-/.(.|;)* *************************\n");
        System.out.print("---------- THOMPSON SIMULATION-----------\n");
        regex = "(.|;)*.-/.(.|;)*";
        input=".;-/." ;
        input2="-/..;" ;
        input3="-/." ;
        input4=";;;;;;;......;.;.;.;.;.;.;./.;.;.;.;.;" ;
        thompson = new Thompson(regex);
        System.out.println("Acepta "+ input+" ? "+thompson.accepts(input)+"\n"); 
        System.out.println("Acepta "+ input2+" ? "+thompson.accepts(input2)+"\n"); 
        System.out.println("Acepta "+ input3+" ? "+thompson.accepts(input3)+"\n"); 
        System.out.println("Acepta "+ input4+" ? "+thompson.accepts(input4)+"\n"); 
        System.out.print("---------- DFA SUBSET ALGORITHM AND SIMULATION-----------\n");
        nfa = new Thompson(regex);
        converter = new NfaToDfaConverter(nfa);
        converter.toGraph("nfadfa3");
        System.out.println("Acepta "+input+" ? "+converter.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+converter.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+converter.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+converter.accepts(input4)+"\n");
        System.out.print("---------- DFA DIRECT CONVERSION ALGORITHM AND SIMULATION-----------\n");
        dfa= new ToDfa(infpos.infixToPostfix(regex));
        dfa.toGraph("direcdfa3");
        System.out.println("Acepta "+input+" ? "+dfa.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+dfa.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+dfa.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+dfa.accepts(input4)+"\n");
        
    }
    catch(Exception e){
        System.out.println("ERROR EN LA GENERACION \n");
    }
      // ejercicio 4
      try{
        System.out.print("*************** EJERCICIO 4 (x|t).+((a|m).?).+ *************************\n");
        System.out.print("---------- THOMPSON SIMULATION-----------\n");
        regex = "(x|t).+((a|m).?).+";
        input="x" ;
        input2="txm" ;
        input3="ma" ;
        input4="a" ;
        thompson = new Thompson(regex);
        System.out.println("Acepta "+ input+" ? "+thompson.accepts(input)+"\n"); 
        System.out.println("Acepta "+ input2+" ? "+thompson.accepts(input2)+"\n"); 
        System.out.println("Acepta "+ input3+" ? "+thompson.accepts(input3)+"\n"); 
        System.out.println("Acepta "+ input4+" ? "+thompson.accepts(input4)+"\n"); 
        System.out.print("---------- DFA SUBSET ALGORITHM AND SIMULATION-----------\n");
        nfa = new Thompson(regex);
        converter = new NfaToDfaConverter(nfa);
        converter.toGraph("nfadfa4");
        System.out.println("Acepta "+input+" ? "+converter.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+converter.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+converter.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+converter.accepts(input4)+"\n");
        System.out.print("---------- DFA DIRECT CONVERSION ALGORITHM AND SIMULATION-----------\n");
        dfa= new ToDfa(infpos.infixToPostfix(regex));
        dfa.toGraph("direcdfa4");
        System.out.println("Acepta "+input+" ? "+dfa.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+dfa.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+dfa.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+dfa.accepts(input4)+"\n");
        
    }
    catch(Exception e){
        System.out.println("ERROR EN LA GENERACION \n");
    }
     // ejercicio 5
     try{
        System.out.print("*************** EJERCICIO 5 ('(.(;(.;(.|;)+)*)*)*)  *************************\n");
        System.out.print("---------- THOMPSON SIMULATION-----------\n");
        regex = "('(.(;(.;(.|;).+).*).*).*)";
        input="'.;.;." ;
        input2="'.;.;;." ;
        input3="'.;.;" ;
        input4=" " ;
        String input5= "'.;;.";
        thompson = new Thompson(regex);
        System.out.println("Acepta "+ input+" ? "+thompson.accepts(input)+"\n"); 
        System.out.println("Acepta "+ input2+" ? "+thompson.accepts(input2)+"\n"); 
        System.out.println("Acepta "+ input3+" ? "+thompson.accepts(input3)+"\n"); 
        System.out.println("Acepta "+ input4+" ? "+thompson.accepts(input4)+"\n");
        System.out.println("Acepta "+ input5+" ? "+thompson.accepts(input5)+"\n"); 
        System.out.print("---------- DFA SUBSET ALGORITHM AND SIMULATION-----------\n");
        nfa = new Thompson(regex);
        converter = new NfaToDfaConverter(nfa);
        converter.toGraph("nfadfa5");
        System.out.println("Acepta "+input+" ? "+converter.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+converter.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+converter.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+converter.accepts(input4)+"\n");
        System.out.println("Acepta "+input5+" ? "+converter.accepts(input5)+"\n");
        System.out.print("---------- DFA DIRECT CONVERSION ALGORITHM AND SIMULATION-----------\n");
        dfa= new ToDfa(infpos.infixToPostfix(regex));
        dfa.toGraph("direcdfa5");
        System.out.println("Acepta "+input+" ? "+dfa.accepts(input)+"\n");
        System.out.println("Acepta "+input2+" ? "+dfa.accepts(input2)+"\n");
        System.out.println("Acepta "+input3+" ? "+dfa.accepts(input3)+"\n");
        System.out.println("Acepta "+input4+" ? "+dfa.accepts(input4)+"\n");
        System.out.println("Acepta "+input5+" ? "+dfa.accepts(input5)+"\n");
        
    }
    catch(Exception e){
        System.out.println("ERROR EN LA GENERACION \n");
    }



}

  

    
}
