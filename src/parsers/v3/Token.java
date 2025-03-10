package parsers.v3;

/**
 * Token da expressão.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Token {
    
    public static enum Tipo {
        MAIS,
        MENOS,
        VEZES,
        DIVIDIR,
        PARENTESES_ESQUERDO,
        PARENTESES_DIREITO,
        NUMERO;
    }
    
    private Tipo tipo;
    private String valor;

    @Override
    public String toString() {
        return "Token{" + "tipo=" + tipo + ", valor=" + valor + '}';
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo( Tipo tipo ) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor( String valor ) {
        this.valor = valor;
    }
    
}
