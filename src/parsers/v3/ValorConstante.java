package parsers.v3;

/**
 * Representação de um valor constante (constante numérica inteira não negativa).
 * @author Prof. Dr. David Buzatto
 */
public class ValorConstante extends Expressao {

    private String value;

    public ValorConstante( String value ) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

}
