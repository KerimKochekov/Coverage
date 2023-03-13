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
        // debugging
        String signature = method.getSignature();
        int left_ptr = signature.indexOf("<") + 1;
        int mid_ptr = signature.indexOf(":", left_ptr);
        int right_ptr = signature.indexOf(">", mid_ptr);
        String class_signature = signature.substring(left_ptr, mid_ptr);
        String method_signature = signature.substring(mid_ptr + 2, right_ptr);
        Chain<Unit> units = body.getUnits();

        // get a snapshot iterator of the unit since we are going to
        // mutate the chain when iterating over it.

        Iterator<?> stmtIt = units.snapshotIterator();
        stmtIt.next();
        
        while (stmtIt.hasNext()) {

            // cast back to a statement.
            Stmt stmt = (Stmt) stmtIt.next(), VCounter;  
            
            String line_signature = Integer.toString(stmt.getJavaSourceStartLineNumber());
            String statement_signature = method_signature  + line_signature + stmt.toString();
            if (line_signature.charAt(0) == '-') continue;
            Counter.update_statement(class_signature, statement_signature, line_signature);
            InvokeExpr V = Jimple.v().newStaticInvokeExpr(add_statement.makeRef(), 
                StringConstant.v(class_signature),
                StringConstant.v(statement_signature),
                StringConstant.v(line_signature)
            );
    
            VCounter = Jimple.v().newInvokeStmt(V);
            units.insertBefore(VCounter, stmt);
        }
        
        stmtIt = units.snapshotIterator();
        Stmt stmt = (Stmt) stmtIt.next();
        while (true) {
            // cast back to a statement. 
            if (stmt instanceof IfStmt){
                Counter.update_branch(class_signature);
                Stmt nextstmt = (Stmt) stmtIt.next();   
                InvokeExpr L = Jimple.v().newStaticInvokeExpr(add_branch.makeRef(), 
                    StringConstant.v(class_signature),
                    StringConstant.v(Integer.toString(stmt.getJavaSourceStartLineNumber()) + stmt.toString())
                );
                InvokeExpr R = Jimple.v().newStaticInvokeExpr(add_branch.makeRef(), 
                    StringConstant.v(class_signature),
                    StringConstant.v(Integer.toString(stmt.getJavaSourceStartLineNumber()) + stmt.toString() + "!")
                );
                Stmt LCounter = Jimple.v().newInvokeStmt(L);
                Stmt RCounter = Jimple.v().newInvokeStmt(R);
                Stmt target = ((IfStmt) stmt).getTarget();

                ((IfStmt)stmt).setTarget(LCounter);
                GotoStmt LGoto = Jimple.v().newGotoStmt(target);
                GotoStmt RGoto = Jimple.v().newGotoStmt(nextstmt);

                units.insertAfter(RCounter, stmt);
                units.insertAfter(RGoto, RCounter);
                units.insertAfter(LCounter, RGoto);
                units.insertAfter(LGoto, LCounter);

                stmt = nextstmt;
            }
            else if (stmtIt.hasNext())
                stmt = (Stmt) stmtIt.next();
            else
                break;
        }
    }
}
