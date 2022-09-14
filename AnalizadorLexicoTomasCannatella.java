package analizador.lexico.tomas.cannatella;

import analizador.lexico.tomas.cannatella.Token;
import java.util.Scanner;


public class AnalizadorLexicoTomasCannatella {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        Token tk = new Token();
        String cadena = "";
        
        while(true){
            System.out.println("Ingrese una cadena");
            cadena = sc.nextLine();
            if(!tk.cadenaNumerica(cadena).isEmpty())
            {
                System.out.println(tk.cadenaNumerica(cadena).get("tipo"));
                System.out.println(tk.cadenaNumerica(cadena).get("resultado"));
            }
            else if(!tk.cadenaAlfaNumerica(cadena).isEmpty())
            {
                System.out.println(tk.cadenaAlfaNumerica(cadena).get("tipo"));
                System.out.println(tk.cadenaAlfaNumerica(cadena).get("resultado"));
            }
            else if(!tk.asignacionVariable(cadena).isEmpty())
            {
                System.out.println(tk.asignacionVariable(cadena).get("tipo"));
                System.out.println(tk.asignacionVariable(cadena).get("resultado"));
            }
            else if(!tk.operacionAritmetica(cadena).isEmpty())
            {
                System.out.println(tk.operacionAritmetica(cadena).get("tipo"));
                System.out.println(tk.operacionAritmetica(cadena).get("resultado"));
            }
            else if(!tk.expresion(cadena).isEmpty())
            {
                System.out.println(tk.expresion(cadena).get("tipo"));
                System.out.println(tk.expresion(cadena).get("resultado"));
            }
            else if(!tk.condicional(cadena).isEmpty())
            {
                System.out.println(tk.condicional(cadena).get("tipo"));
                System.out.println(tk.condicional(cadena).get("resultado"));
            }
            else if(!tk.funcion(cadena).isEmpty())
            {
                System.out.println(tk.funcion(cadena).get("tipo"));
                System.out.println(tk.funcion(cadena).get("resultado"));
            }
            else
            {
                System.out.println("La cadena no coicide con ninguno de los casos");
            }
        }          
    }
    
}
