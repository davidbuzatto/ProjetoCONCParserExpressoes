package parsers;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.util.ArrayList;
import java.util.List;
import parsers.v3.Expressao;
import parsers.v3.ExpressaoAdicao;
import parsers.v3.ExpressaoMultiplicacao;
import parsers.v3.Token;
import parsers.v3.ValorConstante;

/**
 * Parser relativo ao slide 4 da Aula 0.
 * 
 * Parser LL(1) para expressões aritméticas.
 * 
 * Esse parser assume que a expressão é sintaticamente correta.
 * 
 * Gramática (EBNF):
 * 
 *     expressao -> termo ( opAdicao termo )* .
 *     opAdicao -> "+" | "-" .
 * 
 *     termo -> fator ( opMultiplicacao fator )* . 
 *     opMultiplicacao -> "*" | "/" .
 * 
 *     fator -> valorConstante | "(" expressao ")" .
 *     valorConstante -> [0..1]+ .
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParserExpressoesVersao3 {
    
    private final List<Token> tokens;
    private int pos;
    private Expressao expressaoResultante;
    
    // para resolução de espaçamento do desenho
    private int ranqueAtual;
    
    public static int parse( String expressao ) {
        return new ParserExpressoesVersao3( expressao ).getValor();
    }
    
    public static String toString( String expressao ) {
        return new ParserExpressoesVersao3( expressao ).toString();
    }
    
    public ParserExpressoesVersao3( String expressao ) {
        this.tokens = getTokens( expressao );
        expressaoResultante = parseExpressao();
    }
    
    private Token proximoToken() {
        if ( pos < tokens.size() ) {
            return tokens.get( pos++ );
        }
        return null;
    }
    
    private Token tokenAtual() {
        if ( pos < tokens.size() ) {
            return tokens.get( pos );
        }
        return null;
    }
    
    // expressao -> termo ( opAdicao termo )* .
    // opAdicao -> "+" | "-" .
    private Expressao parseExpressao() {
        
        Expressao termo = null;
        Expressao termo2 = null;
        Token operador = null;
        
        termo = parseTermo();
        
        while ( tokenAtual() != null && (
                tokenAtual().getTipo() == Token.Tipo.MAIS ||
                tokenAtual().getTipo() == Token.Tipo.MENOS ) ) {
            operador = proximoToken();
            termo2 = parseTermo();
            termo = new ExpressaoAdicao( termo, operador, termo2 );
        }
        
        return termo;
        
    }
    
    // termo -> fator ( opMultiplicacao fator )* . 
    // opMultiplicacao -> "*" | "/" .
    private Expressao parseTermo() {
        
        Expressao fator = null;
        Expressao fator2 = null;
        Token operador = null;
        
        fator = parseFator();
        
        while ( tokenAtual() != null && (
                tokenAtual().getTipo() == Token.Tipo.VEZES ||
                tokenAtual().getTipo() == Token.Tipo.DIVIDIR ) ) {
            operador = proximoToken();
            fator2 = parseFator();
            fator = new ExpressaoMultiplicacao( fator, operador, fator2 );
        }
        
        return fator;
        
    }
    
    // fator -> valorConstante | "(" expressao ")" .
    // valorConstante -> [0..1]+ .
    private Expressao parseFator() {
        
        Expressao expr = null;
        
        Token t = proximoToken();
        
        if ( t.getTipo() == Token.Tipo.NUMERO ) {
            expr = new ValorConstante( t.getValor() );
        } else if ( t.getTipo() == Token.Tipo.PARENTESES_ESQUERDO ) {
            expr = parseExpressao();
            proximoToken();
        }
        
        return expr;
        
    }
    
    // todos os tokens devem estar separados por pelo menos um espaço
    private List<Token> getTokens( String expressao ) {
        
        List<Token> tokens = new ArrayList<>();
        
        for ( String s : expressao.split( "\\s+" ) ) {
            
            Token t = new Token();
            t.setValor( s );
            
            switch ( s ) {
                case "+": 
                    t.setTipo( Token.Tipo.MAIS );
                    break;
                case "-": 
                    t.setTipo( Token.Tipo.MENOS );
                    break;
                case "*": 
                    t.setTipo( Token.Tipo.VEZES );
                    break;
                case "/": 
                    t.setTipo( Token.Tipo.DIVIDIR );
                    break;
                case "(": 
                    t.setTipo( Token.Tipo.PARENTESES_ESQUERDO );
                    break;
                case ")": 
                    t.setTipo( Token.Tipo.PARENTESES_DIREITO );
                    break;
                default:
                    t.setTipo( Token.Tipo.NUMERO );
                    t.setValor( s );
                    break;
            }
            
            tokens.add( t );
            
        }
        
        return tokens;
        
    }
    
    public int getValor() {
        return getValor( expressaoResultante );
    }
    
    // visitor
    private int getValor( Expressao e ) {
        
        if ( e instanceof ValorConstante c ) {
            return Integer.parseInt( c.getValue() );
        } else if ( e instanceof ExpressaoAdicao a ) {
            int op1 = getValor( a.getOp1() );
            int op2 = getValor( a.getOp2() );
            if ( a.getOperador().getTipo() == Token.Tipo.MAIS ) {
                return op1 + op2;
            } else {
                return op1 - op2;
            }
        } else if ( e instanceof ExpressaoMultiplicacao m ) {
            int op1 = getValor( m.getOp1() );
            int op2 = getValor( m.getOp2() );
            if ( m.getOperador().getTipo() == Token.Tipo.VEZES ) {
                return op1 * op2;
            } else {
                return op1 / op2;
            }
        }
        
        return 0;
        
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        imprimir( expressaoResultante, sb, 0 );
        return sb.toString().trim();
    }
    
    // visitor
    private void imprimir( Expressao e, StringBuilder sb, int level ) {
        
        String spacing = level > 0 ? " |   ".repeat( level - 1 ) : "";
        String line = level > 0 ? " |___" : "";
        
        if ( e instanceof ValorConstante ) {
            sb.append( "\n" ).append( spacing ).append( line ).append( e );
        } else if ( e instanceof ExpressaoAdicao a ) {
            sb.append( "\n" ).append( spacing ).append( line ).append( "(" ).append( a.getOperador().getValor() ).append( ")" );
            imprimir( a.getOp1(), sb, level + 1 );
            imprimir( a.getOp2(), sb, level + 1 );
        } else if ( e instanceof ExpressaoMultiplicacao m ) {
            sb.append( "\n" ).append( spacing ).append( line ).append( "(" ).append( m.getOperador().getValor() ).append( ")" );
            imprimir( m.getOp1(), sb, level + 1 );
            imprimir( m.getOp2(), sb, level + 1 );
        }
        
    }
    
    public void desenhar( EngineFrame ef, int x, int y, int spacing, int radius ) {
        ranqueAtual = 0;
        calcularRanquesNiveis( expressaoResultante, 0 );
        desenharArestas( ef, expressaoResultante, x, y, spacing, radius );
        desenharNos( ef, expressaoResultante, x, y, spacing, radius );
    }
    
    private void calcularRanquesNiveis( Expressao e, int level ) {
        
        if ( e instanceof ValorConstante c ) {
            c.setRanque( ranqueAtual++ );
            c.setNivel( level );
        } else if ( e instanceof ExpressaoAdicao a ) {
            calcularRanquesNiveis( a.getOp1(), level + 1 );
            a.setRanque( ranqueAtual++ );
            a.setNivel( level );
            calcularRanquesNiveis( a.getOp2(), level + 1 );
        } else if ( e instanceof ExpressaoMultiplicacao m ) {
            calcularRanquesNiveis( m.getOp1(), level + 1 );
            m.setRanque( ranqueAtual++ );
            m.setNivel( level );
            calcularRanquesNiveis( m.getOp2(), level + 1 );
        }
        
    }
    
    private void desenharNos( EngineFrame ef, Expressao e, int x, int y, int espacamento, int raio ) {
        
        ef.fillCircle( x + e.getRanque() * espacamento, y + e.getNivel() * espacamento, raio, EngineFrame.WHITE );
        ef.drawCircle( x + e.getRanque() * espacamento, y + e.getNivel() * espacamento, raio, EngineFrame.BLACK );
        
        if ( e instanceof ValorConstante c ) {
            int w = ef.measureText( c.getValue(), 20 );
            ef.drawText( c.getValue(), x + c.getRanque() * espacamento - w / 2 + 2, y + c.getNivel() * espacamento - 5, 20, EngineFrame.BLACK );
        } else if ( e instanceof ExpressaoAdicao a ) {
            int w = ef.measureText( a.getOperador().getValor(), 20 );
            ef.drawText( a.getOperador().getValor(), x + a.getRanque() * espacamento - w / 2 + 2, y + a.getNivel() * espacamento - 5, 20, EngineFrame.BLACK );
            desenharNos( ef, a.getOp1(), x, y, espacamento, raio );
            desenharNos( ef, a.getOp2(), x, y, espacamento, raio );
        } else if ( e instanceof ExpressaoMultiplicacao m ) {
            int w = ef.measureText( m.getOperador().getValor(), 20 );
            ef.drawText( m.getOperador().getValor(), x + m.getRanque() * espacamento - w / 2 + 2, y + m.getNivel() * espacamento - 5, 20, EngineFrame.BLACK );
            desenharNos( ef, m.getOp1(), x, y, espacamento, raio );
            desenharNos( ef, m.getOp2(), x, y, espacamento, raio );
        }
        
    }
    
    private void desenharArestas( EngineFrame ef, Expressao e, int x, int y, int espacamento, int raio ) {
        
        if ( e instanceof ExpressaoAdicao a ) {
            double x1 = x + a.getRanque() * espacamento;
            double y1 = y + a.getNivel() * espacamento;
            double x2 = x + a.getOp1().getRanque() * espacamento;
            double y2 = y + a.getOp1().getNivel() * espacamento;
            double x3 = x + a.getOp2().getRanque() * espacamento;
            double y3 = y + a.getOp2().getNivel() * espacamento;
            ef.drawLine( x1, y1, x2, y2, EngineFrame.BLACK );
            ef.drawLine( x1, y1, x3, y3, EngineFrame.BLACK );
            desenharArestas( ef, a.getOp1(), x, y, espacamento, raio );
            desenharArestas( ef, a.getOp2(), x, y, espacamento, raio );
        } else if ( e instanceof ExpressaoMultiplicacao m ) {
            double x1 = x + m.getRanque() * espacamento;
            double y1 = y + m.getNivel() * espacamento;
            double x2 = x + m.getOp1().getRanque() * espacamento;
            double y2 = y + m.getOp1().getNivel() * espacamento;
            double x3 = x + m.getOp2().getRanque() * espacamento;
            double y3 = y + m.getOp2().getNivel() * espacamento;
            ef.drawLine( x1, y1, x2, y2, EngineFrame.BLACK );
            ef.drawLine( x1, y1, x3, y3, EngineFrame.BLACK );
            desenharArestas( ef, m.getOp1(), x, y, espacamento, raio );
            desenharArestas( ef, m.getOp2(), x, y, espacamento, raio );
        }
        
    }
    
    public static void main( String[] args ) {
        
        String[] expressoes = {
            "1 - 4",
            "1 + 2 - 3",
            "1 - 2 + 3",
            "2 * 2 - 3",
            "2 - 2 * 3",
            "( 4 - 2 ) * 3",
            "2 / 2 * 3",
            "3 * 3 + 4 * 4",
            "( 1 + 4 ) * ( 5 - 10 )"
        };
        
        for ( String expressao : expressoes ) {
            System.out.printf( "Analisando a expressao \"%s\":\n", expressao );
            System.out.printf( "  %s = %d\n\n", expressao, parse( expressao ) );
            System.out.println( ParserExpressoesVersao3.toString( expressao ) );
            System.out.println( "\n" );
        }
        
    }
    
}
