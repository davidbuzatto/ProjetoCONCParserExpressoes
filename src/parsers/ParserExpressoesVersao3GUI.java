package parsers;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;

/**
 * Exibição gráfica da árvore sintática abstrata resultante do processo
 * de análise de uma expressão aritimática.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ParserExpressoesVersao3GUI extends EngineFrame {
    
    private ParserExpressoesVersao3 ep;
    private String expressao;
    private int resultado;
    
    private GuiInputDialog inputDialog;
    
    public ParserExpressoesVersao3GUI() {
        super ( 800, 450, "Parser Expressões V3 (visualização)", 60, true, true, false, false, false );
    }
    
    @Override
    public void create() {
        
        //expressao = "1 + 2 - 2 * 4 - ( 5 / 10 )";
        expressao = "( 1 + 4 ) * ( 5 - 10 )";
        ep = new ParserExpressoesVersao3( expressao );
        resultado = ep.getValor();
        
        inputDialog = new GuiInputDialog( "Nova Expressão", "Formato:\n  A = +\n  S = -\n  M = *\n  D = /\n  L = (\n  R = )", true, this );
        
    }
    
    @Override
    public void update( double delta ) {
        
        inputDialog.update( delta );
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_RIGHT ) ) {
            if ( !inputDialog.isVisible() ) {
                inputDialog.show();
            }
        }
        
        if ( inputDialog.isOkButtonPressed() || inputDialog.isEnterKeyPressed() ) {
            expressao = inputDialog.getValue();
            if ( expressao != null ) {
                expressao = substituir( expressao );
                ep = new ParserExpressoesVersao3( expressao );
                resultado = ep.getValor();
            }
            inputDialog.hide();
        }
        
        if ( inputDialog.isCancelButtonPressed() || inputDialog.isCloseButtonPressed() ) {
            inputDialog.hide();
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        ep.desenhar( this, 100, 100, 60, 20 );
        drawText( String.format( "%s = %d", expressao, resultado ), 10, 10, 20, BLACK );
        
        inputDialog.draw();
        
    }
    
    private String substituir( String expressao ) {
        return expressao.replace( "A", "+" ).replace( "S", "-" ).replace( "M", "*" ).replace( "D", "/" ).replace( "L", "(" ).replace( "R", ")" );
    }
    
    public static void main( String[] args ) {
        new ParserExpressoesVersao3GUI();
    }
    
}
