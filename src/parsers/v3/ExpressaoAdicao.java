package parsers.v3;

/**
 * Representação de uma expressão binária de adição.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ExpressaoAdicao extends ExpressaoBinaria {

    public ExpressaoAdicao( Expressao op1, Token operador, Expressao op2 ) {
        super( op1, operador, op2 );
    }

}
