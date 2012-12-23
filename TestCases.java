/***
 * Excerpted from "The Definitive ANTLR Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr for more book information.
***/
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
public class TestCases {
    private class TestEval extends Eval{
        public TestEval(TreeNodeStream input){
            super(input);
        }
        public ArrayList expr = new ArrayList(); 
        @Override public void onExpr(int value){
            expr.add(value);
            //System.out.println(value);
        }   
    }
    @Test public void multiple_expressions_should_be_evaluated()
        throws IOException, RecognitionException{
        // Create an input character stream from standard in
        ANTLRInputStream input = new ANTLRInputStream(new StringBufferInputStream(
            "1+23\na=34\nb=23\na+b\n"));
        // Create an ExprLexer that feeds from that stream
        ExprLexer lexer = new ExprLexer(input);
        // Create a stream of tokens fed by the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Create a parser that feeds off the token stream
        ExprParser parser = new ExprParser(tokens);
        // Begin parsing at rule prog, get return value structure
        ExprParser.prog_return r = parser.prog();

        // WALK RESULTING TREE
        CommonTree t = (CommonTree)r.getTree(); // get tree from parser
        // Create a tree node stream from resulting tree
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
        TestEval walker = new TestEval(nodes); // create a tree parser
        walker.prog();                 // launch at start rule prog
        int[] expected = {24, 57};

        for (int i=0;i< expected.length ; i++) {
            assertEquals(walker.expr.get(i),expected[i]);
            //System.out.println(i); 
        }
    }
}
