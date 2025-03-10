package parsers.v3;

/**
 * Representação de uma expressão binária (dois operandos).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ExpressaoBinaria extends Expressao {
    
    private Expressao op1;
    private Token operador;
    private Expressao op2;

    public ExpressaoBinaria( Expressao op1, Token operador, Expressao op2 ) {
        this.op1 = op1;
        this.operador = operador;
        this.op2 = op2;
    }

    @Override
    public String toString() {
        return op1 + " " + operador.getTipo() + " " + op2;
    }

    public Expressao getOp1() {
        return op1;
    }

    public void setOp1( Expressao op1 ) {
        this.op1 = op1;
    }

    public Token getOperador() {
        return operador;
    }

    public void setOperador( Token operador ) {
        this.operador = operador;
    }

    public Expressao getOp2() {
        return op2;
    }

    public void setOp2( Expressao op2 ) {
        this.op2 = op2;
    }
        
}
