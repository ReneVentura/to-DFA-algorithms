package lab1.afn.preafn;
import java.io.*;
import java.util.*;
 
public class extensionReg {

   /*
    * 
si contiene un ?, esto se interpreta en un |ε que solo afecta al simbolo
si antes de ? hay ) esto se interpreta como que afecta al parentesis (expresion|ε)

sea a, un simbolo de una regex si hay un + este se interpreta como aa*
sea (ab), una expresion entre parentesis, si hay un + luego de ) este afecta a todo el parentesis siendo (abab*)

ref: https://ccc.inaoep.mx/~emorales/Cursos/Automatas/ExpRegulares.pdf

    */

    public static String transform_Sum(String regex){
        
        for(int i=0; i< regex.length();i++){
            
            if(regex.charAt(i)=='+'){
                if(regex.charAt(i-1)== ')'){
                    
                    for(int j=i-1;j>=0;j--){
                        
                        
                        if(regex.charAt(j)=='(' ){
                            
                            if(regex.contains(")+")){
                            String presube= regex.substring(j+1, i-1);
                            String right= new String();
                            String left= regex.substring(0, j+1);
                            if(i+1>=regex.length()){
                                right= regex.substring(i-1,i);
                            }
                            else{
                            right= regex.substring(i-1,i);
                            right+= regex.substring(i+1);
                        }
                            String sube= presube+presube+'*';
                            regex= left+sube+right;
                            

                        }
                    }
                        
                    }
                }
                else{
                    String exp= Character.toString(regex.charAt(i-1));
                    String left= regex.substring(0, i-1);
                    String right= regex.substring(i+1);
                    String sube= exp+exp+'*';
                    regex= left+sube+right;
                    
                }
        }
        
    }
        
        return regex;
    }
    public static String transform_Question(String regex){
        
        for(int i=0; i< regex.length();i++){
            
            if(regex.charAt(i)=='?'){
                if(regex.charAt(i-1)== ')'){
                    
                    for(int j=i-1;j>=0;j--){
                        
                        
                        if(regex.charAt(j)=='(' ){
                            
                            if(regex.contains(")?")){
                            String presube= regex.substring(j+1, i-1);
                            String right= new String();
                            String left= regex.substring(0, j+1);
                            if(i+1>=regex.length()){
                                right= regex.substring(i-1,i);
                            }
                            else{
                            right= regex.substring(i-1,i);
                            right+= regex.substring(i+1);
                        }
                            String sube= presube+"|ε";
                            regex= left+sube+right;
                            

                        }
                    }
                        
                    }
                }
                else{
                    String exp= Character.toString(regex.charAt(i-1));
                    String left= regex.substring(0, i-1);
                    String right= regex.substring(i+1);
                    String sube= exp+"|ε";
                    regex= left+sube+right;
                    
                }
        }
        
    }
        
        return regex;
    }
    public static String transform_Regex(String regex){
        String sum= transform_Sum(regex);
        String r= transform_Question(sum);
        return r;
    }
    public static void main(String[] args)
    {
       String regex="0?(1?)?0*?";
       String t= transform_Regex(regex);
       System.out.println(t);
    }
}