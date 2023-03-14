package comp5111.assignment.cut;

import soot.*;
import soot.jimple.*;
import soot.util.Chain;
import java.util.Iterator;
import java.util.Map;

public class Instrumenter extends BodyTransformer {

    /* some internal fields */
    static SootClass counterClass;
    static SootMethod add_method, add_branch, add_statement;

    static {
        counterClass = Scene.v().loadClassAndSupport("comp5111.assignment.cut.Counter");
        add_branch = counterClass.getMethod("void covered_branch(java.lang.String,java.lang.String)");
        add_statement = counterClass.getMethod("void covered_statement(java.lang.String,java.lang.String,java.lang.String)");
    }

    @Override
    protected void internalTransform(Body body, String phase, Map options) {
        // body's method
        SootMethod method = body.getMethod();

        String signature = method.getSignature();
        int left_ptr = signature.indexOf("<") + 1;
        int mid_ptr = signature.indexOf(":", left_ptr);
        int right_ptr = signature.indexOf(">", mid_ptr);
        // Retrieve the class and method name from signature
        String class_signature = signature.substring(left_ptr, mid_ptr);
        String method_signature = signature.substring(mid_ptr + 2, right_ptr);

        // get body's unit as a chain
        Chain<Unit> units = body.getUnits();

        // get a snapshot iterator of the unit since we are going to
        // mutate the chain when iterating over it.
        Iterator<?> stmtIt = units.snapshotIterator();
        // Skip the name define instruction of methods
        stmtIt.next();
        
        // typical while loop for iterating over each statement
        while (stmtIt.hasNext()) {

            // cast back to a statement.
            Stmt stmt = (Stmt) stmtIt.next();
            
            // Create unique line signature to be recognized
            String line_signature = Integer.toString(stmt.getJavaSourceStartLineNumber());
            // Create unique statement signature to be recognized
            String statement_signature = method_signature + line_signature + stmt.toString();

            // if it is new invoked instruction, or argument define statement of method, skip it
            if (line_signature.charAt(0) == '-') 
                continue;

            // Update the total number of statements and lines
            Counter.update_statement(class_signature, statement_signature, line_signature);
            // Invoke new statement to update Counter class with specific class and statement signature
            InvokeExpr V = Jimple.v().newStaticInvokeExpr(add_statement.makeRef(), 
                StringConstant.v(class_signature),
                StringConstant.v(statement_signature),
                StringConstant.v(line_signature)
            );
            
            Stmt VCounter = Jimple.v().newInvokeStmt(V);
            //Place the new statement right before the considering statement
            units.insertBefore(VCounter, stmt);
        }
        
        // Traverse same method one more time
        stmtIt = units.snapshotIterator();
        Stmt stmt = (Stmt) stmtIt.next();

        // while there is next statement, enter to loop
        while (true) {
            // If statement is branch statement
            if (stmt instanceof IfStmt){
                //update the total number of branches
                Counter.update_branch(class_signature);
                // get the next statement
                Stmt nextstmt = (Stmt) stmtIt.next();   
                /*
                 *     I followed following strategy to count two separate branches
                 *     The Lcounter is invoked in case IfStmt conditions satisfied 
                 *     If not, Rcounter is invoked and statement continues without any affect
                 *     BEFORE:
                 *         |- IfStmt target
                 *         |  nextstmt
                 *         |  ...
                 *         -> target
                 * 
                 *      AFTER:
                 *         |- IfStmt Lcounter
                 *         |  Rcounter
                 *         |  Rgoto   --|
                 *         -> LCounter  | 
                 *         |- Lgoto     |
                 *         |  nextstmt <-
                 *         |  ...
                 *         -> target
                 */
                InvokeExpr L = Jimple.v().newStaticInvokeExpr(add_branch.makeRef(), 
                    StringConstant.v(class_signature),
                    StringConstant.v(Integer.toString(stmt.getJavaSourceStartLineNumber()) + stmt.toString())
                );
                InvokeExpr R = Jimple.v().newStaticInvokeExpr(add_branch.makeRef(), 
                    StringConstant.v(class_signature),
                    // to make difference of two branches add "!" to the end of branch signature
                    StringConstant.v(Integer.toString(stmt.getJavaSourceStartLineNumber()) + stmt.toString() + "!")
                );
                // define the new statements for two branches of if statements
                Stmt LCounter = Jimple.v().newInvokeStmt(L);
                Stmt RCounter = Jimple.v().newInvokeStmt(R);
                // get the target statement of if statement
                Stmt target = ((IfStmt) stmt).getTarget();
                
                // set the new target of if statement (in case condition satisfied) based on our strategy
                ((IfStmt)stmt).setTarget(LCounter);
                // new goto statements introduced based on our strategy
                GotoStmt LGoto = Jimple.v().newGotoStmt(target);
                GotoStmt RGoto = Jimple.v().newGotoStmt(nextstmt);

                // place the newly introduced statements on right places
                units.insertAfter(RCounter, stmt);
                units.insertAfter(RGoto, RCounter);
                units.insertAfter(LCounter, RGoto);
                units.insertAfter(LGoto, LCounter);

                // move to the next statement in old version of method
                stmt = nextstmt;
            }
            // If there is next statement in method, move to the next statement
            else if (stmtIt.hasNext())
                stmt = (Stmt) stmtIt.next();
            // otherwise terminate program
            else
                break;
        }
    }
}
