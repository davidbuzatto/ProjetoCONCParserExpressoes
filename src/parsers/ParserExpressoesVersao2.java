package parsers;

/**
 * Parser relativo ao slide 3 da Aula 0.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParserExpressoesVersao2 {
    
    public static int parse( String expressao ) {
        
        String[] tokens = expressao.split( "\\s+" );
        int resultado = 0;
        
        int operando1 = 0;
        int operando2 = 0;
        String operador = "";
            
        for ( int i = 0; i < tokens.length; i += 2 ) {
            if ( i == 0 ) {
                operando1 = Integer.parseInt( tokens[i] );
                operando2 = Integer.parseInt( tokens[i+2] );
                operador = tokens[i+1];
                i++;
            } else {
                operando1 = resultado;
                operando2 = Integer.parseInt( tokens[i+1] );
                operador = tokens[i];
            }
            switch ( operador ) {
                case "+": resultado = operando1 + operando2; break;
                case "-": resultado = operando1 - operando2; break;
                case "*": resultado = operando1 * operando2; break;
                case "/": resultado = operando1 / operando2; break;
            }   
        }
        
        return resultado;
        
    }
    
    public static void main( String[] args ) {
        
        String[] expressoes = {
            "1 + 4 - 5 + 10",
            "2 * 4 / 3 * 5"
        };
        
        for ( String expressao : expressoes ) {
            System.out.printf( "Analisando a expressao \"%s\":\n", expressao );
            System.out.printf( "  %s = %d\n\n", expressao, parse( expressao ) );
        }
        
    }
    
}
