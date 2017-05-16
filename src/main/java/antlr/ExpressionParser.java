package antlr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Locale;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import antlr.SimpleParser.ExprContext;
import ar.edu.utn.dds.modelo.Operando;
import ar.edu.utn.dds.modelo.Cuenta;
import ar.edu.utn.dds.modelo.Multiplicacion;
import ar.edu.utn.dds.modelo.NodoCuenta;
import ar.edu.utn.dds.modelo.NodoIndicador;
import ar.edu.utn.dds.modelo.NodoNumero;
import ar.edu.utn.dds.modelo.Division;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Resta;
import ar.edu.utn.dds.modelo.Suma;



public class ExpressionParser {
    private final ANTLRErrorListener _listener = createErrorListener();

    /**
     * Parses an expression into an integer result.
     * @param expression the expression to part
     * @return and integer result
     */
    public Operando parse(final String expression,ArrayList<Indicador>indicadores) {
        /*
         * Create a lexer that reads from our expression string
         */
        final SimpleLexer lexer = new SimpleLexer(new ANTLRInputStream(expression));

        /*
         * By default Antlr4 lexers / parsers have an attached error listener
         * that prints errors to stderr. I prefer them to throw an exception
         * instead so I implemented my own error listener which is attached
         * here. I also remove any existing error listeners.
         */
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);

        /*
         * The lexer reads characters and lexes them into token stream. The
         * tokens are consumed by the parser that then builds an Abstract
         * Syntax Tree.
         */
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final SimpleParser parser = new SimpleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);

        /*
         * The ExprContext is the root of our Abstract Syntax Tree
         */
        final ExprContext context = parser.expr();

        /*
         * 'Visit' all the branches of the tree to get our expression result.
         * 
         */
      
      return visit(context,indicadores);
       
      
        
    }
    
    
    
    
    
    
    
    
    
    
    

    /*
     * Visits the branches in the expression tree recursively until we hit a
     * leaf.
     */
   /* private int visit(final ExprContext context) {
    	
    
        if (context.number() != null) { //Just a number
            return Integer.valueOf((context.number().getText()));
        }
        else if (context.BR_CLOSE() != null) { //Expression between brackets
            return visit(context.expr(0));
        }
        else if (context.TIMES() != null) { //Expression * expression
            return (visit(context.expr(0)) * visit(context.expr(1)));
        }
        else if (context.DIV() != null) { //Expression / expression
            return visit(context.expr(0)) / visit(context.expr(1));
        }
        else if (context.PLUS() != null) { //Expression + expression
            return visit(context.expr(0)) + visit(context.expr(1));
        }
        else if (context.MINUS() != null) { //Expression - expression
            return visit(context.expr(0)) - visit(context.expr(1));
        }
        else {
            throw new IllegalStateException();
        }
    }*/
    private Operando visit(final ExprContext context, ArrayList<Indicador> indicadores) {
        if (context.number() != null) { //Just a number
            return new NodoNumero(Integer.parseInt(context.number().getText()));
        }
        if (context.indicador() != null) { //
        	return new NodoIndicador((context.indicador().getText()),indicadores);
        }
        if (context.cuenta() != null) {
        	return new NodoCuenta(context.cuenta().getText());
        }
        else if (context.BR_CLOSE() != null) { //Expression between brackets
            return visit(context.expr(0), indicadores);
        }
        else if (context.TIMES() != null) { //Expression * expression
            return new NodoIndicador(visit(context.expr(0),indicadores),new Multiplicacion(),visit(context.expr(1),indicadores));
        }
        else if (context.DIV() != null) { //Expression / expression
            return new NodoIndicador(visit(context.expr(0),indicadores), new Division(),visit(context.expr(1),indicadores));
        }
        else if (context.PLUS() != null) { //Expression + expression
           // return new Indicador(visit(context.expr(0)),new Suma(),visit(context.expr(1)));
        	return new NodoIndicador(visit(context.expr(0),indicadores), new Suma(),visit(context.expr(1),indicadores));
        }
        else if (context.MINUS() != null) { //Expression - expression
        	return new NodoIndicador(visit(context.expr(0),indicadores), new Resta(),visit(context.expr(1),indicadores));
        }
        else {
            throw new IllegalStateException();
        }
    }
    /*
     * Helper method to create an ANTLRErrorListener. We're only interested in
     * the syntaxError method.
     */
    private static ANTLRErrorListener createErrorListener() {
        return new ANTLRErrorListener() {
            public void syntaxError(final Recognizer<?, ?> arg0, final Object obj, final int line, final int position, final String message, final RecognitionException ex) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Exception parsing expression: '%s' on line %s, position %s", message, line, position));
            }

            public void reportContextSensitivity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final int arg4, final ATNConfigSet arg5) {
            }

            public void reportAttemptingFullContext(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final BitSet arg4, final ATNConfigSet arg5) {
            }

            public void reportAmbiguity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final boolean arg4, final BitSet arg5, final ATNConfigSet arg6) {
            }
        };
    }
    public static void main(String[] args) throws IOException {
    	ExpressionParser p=new ExpressionParser();
    	//System.out.println(p.parse("5+9*9"));
    }

}
