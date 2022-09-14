package analizador.lexico.tomas.cannatella;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author tomas
 */
public class Token {
    
    private Map resultado = new HashMap ();
    
    public Map cadenaNumerica(String cadena){
        resultado.clear();
        if(cadena.matches("[+-]?[0-9]+")){
           resultado.put("tipo","Cadena Numerica");
           resultado.put("resultado",cadena);
        }
        return resultado;
    }
    
    public Map cadenaAlfaNumerica (String cadena){
        resultado.clear();
        if(cadena.matches("([a-z]|[A-Z])+[0-9]*")){
            resultado.put("tipo","Cadena Alfa Númerica");
            resultado.put("resultado",cadena);
        }
        return this.resultado;
    }
    
    public Map asignacionVariable (String cadena){
        resultado.clear();
        StringTokenizer st = new StringTokenizer(cadena);
        Boolean isAsignacion = true;
        String lvalue="",rvalue="";
       
        if(st.countTokens() == 3){
            while(st.hasMoreTokens()){
                cadena = st.nextToken();
                switch(st.countTokens()){
                    case 2:{//Es el lvalue
                        if(cadena.matches("([a-z]|[A-Z])+[_]*[0-9]*[_]*")){
                           lvalue = cadena;
                        }else{
                          isAsignacion = false;
                        }
                        break;
                    }
                    
                    case 1:{ //Es el sibolo de asignacion '='
                        if(!cadena.matches("[=]")){
                            isAsignacion = false;
                        }
                        break;
                    }
                    
                    case 0:{ //Es el valor de asignacion
                        if(cadena.matches("([0-9])*[_]*([a-z]|[A-Z])*[_]*")){
                            rvalue = cadena;
                        }else{
                           isAsignacion = false;
                        }
                        break;
                    }       
                }
            }

            if(isAsignacion){
                this.resultado.put("tipo","Asignacion de variable");
                this.resultado.put("resultado","lvalue: "+lvalue+"\n rvalue: "+rvalue);
            }
        }
        return this.resultado;
    }
    
    public Map operacionAritmetica(String cadena){
        Boolean isOperacionAritmetica = true;
        String valores = "";
        String operadores = "";
        StringTokenizer valor = new StringTokenizer(cadena,"+ | - | / | *",true);
        
        while(valor.hasMoreTokens()){
            String valor_aux = valor.nextToken();
            if(valor_aux.matches("[0-9]+")){
                valores += "["+valor_aux+"]";
            }else if(valor_aux.matches("[+]||[-]||[/]||[*]")){
                operadores += "["+valor_aux+"]";
            }else{
                isOperacionAritmetica = false;
            }
        }

        resultado.clear();
        if(isOperacionAritmetica){
            resultado.put("tipo","OPERACION");
            resultado.put("resultado","Operaciones: "+cadena+"\n Operadores: "+operadores+"\n Valores:"+valores);
        }
        return this.resultado;
    }

    public Map expresion(String cadena){
        String valores="";
        String expresion = "";
        Boolean isExpresion = false;
        cadena = cadena.replace(" ","");
        
        StringTokenizer valor = new StringTokenizer(cadena,"<>&|",true);
        
        while(valor.hasMoreTokens())
        {
            String valor_aux = valor.nextToken();
            if(valor_aux.matches("[>]||[<]||[&]||[|]") && valor.countTokens() == 1)
            {
                expresion += "["+valor_aux+"]";
                isExpresion = true;
            }
            else
            {
                valores += "["+valor_aux+"]";
            }
        }
        
        if (isExpresion) 
        {
            this.resultado.put("tipo","Expresion");
            this.resultado.put("resultado","Expresiones: "+expresion+"\n Valores: "+valores);
        }
        
        return this.resultado;
    }
    
    //Se contempla que del lado izq de la condicional va a ir una variable.
    public Map condicional(String cadena){
        this.resultado.clear(); 
        StringTokenizer st = new StringTokenizer(cadena);
        String condicional = "";
        boolean isCondicional = false;
        
        while(st.hasMoreElements()){
            String v = st.nextToken();
            
            if(st.countTokens() == 4 && v.matches("if")){
                isCondicional = true;
            }else if(st.countTokens() == 3 && v.matches("([a-z]|[A-Z])+[_]*[0-9]*[_]*")){
                isCondicional = true;
                condicional += v;
            }else if(st.countTokens() == 2 && v.matches("==")){
                isCondicional = true;
                condicional += v;
            }else if(st.countTokens() == 1 && (v.matches("(([a-z]|[A-Z])+[_]*[0-9]*[_]*)") || v.matches("[0-9]?"))){
                isCondicional = true;
                condicional += v;
            }else if(st.countTokens() == 0 && v.matches("then")){
                isCondicional = true;
            }else{
                isCondicional = false;
            }
        }
        
        if(isCondicional){
            this.resultado.put("tipo","Condicional");
            this.resultado.put("resultado",condicional);
        }
        return this.resultado;
    }
    
    public Map funcion(String cadena){
        this.resultado.clear();
        StringTokenizer st = new StringTokenizer(cadena," (),",false);
        int cantTokens = st.countTokens();
        boolean isFuncion = false;
        String functionName = "";
        String paramsList = "";
        String token="";
        String resultado=""; 
        String [] splitAux;
        
        while(st.hasMoreTokens()){
            token = st.nextToken();            
            if(token.matches("def") && st.countTokens() == cantTokens-1){
                isFuncion = true;
            }else if(token.matches("([a-z]|[A-Z])+[_]*[0-9]*[_]*") && st.countTokens() == cantTokens-2){
                isFuncion = true;
                functionName = token;
                splitAux = cadena.split(functionName);//Separo la cadena para obtener los parametros
                paramsList = splitAux[1];//Obtengo todos los parametros de la funcion
                break;
            }else{
                isFuncion = false;
                break;
            }
        }
        
        //Obtengo los parametros que estan el la cadena
        StringTokenizer param = new StringTokenizer(paramsList,"(), ");
        //Si la bandera isFunction esta en true. 
        //Significa que la palabra reserva def y el nombre de la funcion estan bien definidos.
        while(param.hasMoreElements() && isFuncion){
            token = param.nextToken();
            //Compruebo que los parametros esten bien declarados
            if(token.matches("([a-z]|[A-Z])+[_]*[0-9]*[_]*")){
                resultado += token+", ";
            }else{
                isFuncion = false;
                break;
            }
        }

        if(isFuncion){
            this.resultado.put("tipo","FUNCION");
            this.resultado.put("resultado","Nombre: "+functionName+"\n Parametros: "+resultado);
        }
        return this.resultado;
    }
}
