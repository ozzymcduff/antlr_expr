import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class Main {
    public static void main(String[] args) {
		try {
			FileInputStream file =new FileInputStream(args[0]);
			ANTLRInputStream input = new ANTLRInputStream(file);
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
        	Eval walker = new Eval(nodes); // create a tree parser
        	walker.prog();                 // launch at start rule prog
		} catch(Exception e) {
	    	System.err.println("exception: "+e);
	    	System.exit(1);
		}
    }
}

