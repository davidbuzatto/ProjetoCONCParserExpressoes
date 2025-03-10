package parsers;

/**
 * Parser relativo ao slide 2 da Aula 0.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParserExpressoesVersao1 {
    
    public static int parse( String expressao ) {
        
        String[] tokens = expressao.split( "\\s+" );
        
        int operando1 = Integer.parseInt( tokens[0] );
        int operando2 = Integer.parseInt( tokens[2] );
        String operador = tokens[1];
        
        int resultado = 0;
        
        switch ( operador ) {
            case "+": resultado = operando1 + operando2; break;
            case "-": resultado = operando1 - operando2; break;
            case "*": resultado = operando1 * operando2; break;
            case "/": resultado = operando1 / operando2; break;
        }
        
        return resultado;
        
    }
    
    public static void main( String[] args ) {
        
        String[] expressoes = {
            "1 + 3",
            "2 - 4",
            "8 * 2",
            "10 / 3"
        };
        
        for ( String expressao : expressoes ) {
            System.out.printf( "Analisando a expressao \"%s\":\n", expressao );
            System.out.printf( "  %s = %d\n\n", expressao, parse( expressao ) );
        }
        
    }
    
}
