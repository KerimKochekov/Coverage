package comp5111.assignment.cut;
import java.util.HashMap;

public class Counter {
    // private static HashMap<String, Integer> branches = new HashMap<String, Integer>();

    //------------------------- BRANCH COVERAGE -------------------------------
    private static HashMap<String, HashMap<String, Integer> > branch = new HashMap<String, HashMap<String, Integer>>();
    private static HashMap<String, Integer> total_branch = new HashMap<String, Integer>();
    
    public static void covered_branch(String className, String instruction) {
        if (branch.get(className) == null)
            branch.put(className, new HashMap<String, Integer>());
        
        if (branch.get(className).get(instruction) == null) 
            branch.get(className).put(instruction, 1);
    }

    public static void update_branch(String className){
        if (total_branch.get(className) == null)
            total_branch.put(className, 0);
        total_branch.put(className, total_branch.get(className) + 2);
    }

    public static int total_branches(String className){
        if (total_branch.get(className) == null) return 0;
        return total_branch.get(className);
    }

    public static int get_branches(String className){
        if (branch.get(className) == null) return 0;
        int covered_branches = 0;
        for (String key: branch.get(className).keySet())
            covered_branches += branch.get(className).get(key);
        return covered_branches;
    }

    public static float summary_branch(String intended_class){
        int covered_branches = 0, all_branches = 0;
        for (String className: branch.keySet()){
            if (intended_class.length() > 0 && !intended_class.equals(className))
                continue;
            for (String instruction: branch.get(className).keySet())
                covered_branches += branch.get(className).get(instruction);
        }
        for (String className: total_branch.keySet()){
            if (intended_class.length() > 0 && !intended_class.equals(className))
                continue;
            all_branches += total_branch.get(className);
        }
        return (float)100.0 * covered_branches / all_branches;
    }
    //------------------------- STATEMENT and LINE COVERAGE -------------------------------
    private static HashMap<String, HashMap<String, Integer> > statement = new HashMap<String, HashMap<String, Integer>>();
    private static HashMap<String, HashMap<String, Integer> > total_statement = new HashMap<String, HashMap<String, Integer>>();
    private static HashMap<String, HashMap<String, Integer> > line = new HashMap<String, HashMap<String, Integer>>();
    private static HashMap<String, HashMap<String, Integer> > total_line = new HashMap<String, HashMap<String, Integer>>();

    
    public static void update_statement(String className, String statement_signature, String line_signature){
        if (total_statement.get(className) == null){
            total_line.put(className, new HashMap<String, Integer>());
            total_statement.put(className, new HashMap<String, Integer>());
        }
        
        if (total_statement.get(className).get(statement_signature) == null) 
            total_statement.get(className).put(statement_signature, 1);
        if (total_line.get(className).get(line_signature) == null) 
            total_line.get(className).put(line_signature, 1);
    }

    public static void covered_statement(String className, String statement_signature, String line_signature) {
        if (statement.get(className) == null){
            line.put(className, new HashMap<String, Integer>());
            statement.put(className, new HashMap<String, Integer>());
        }
        
        if (statement.get(className).get(statement_signature) == null) 
            statement.get(className).put(statement_signature, 1);
        if (line.get(className).get(line_signature) == null) 
            line.get(className).put(line_signature, 1);
    }

    public static int total_statements(String className){
        if (total_statement.get(className) == null) return 0;
        int covered_statement = 0;
        for (String key: total_statement.get(className).keySet())
            covered_statement += total_statement.get(className).get(key);
        return covered_statement;
    }
    
    public static int total_lines(String className){
        if (total_line.get(className) == null) return 0;
        int covered_line = 0;
        for (String key: total_line.get(className).keySet())
            covered_line += total_line.get(className).get(key);
        return covered_line;
    }

    public static int get_statements(String className){
        if (statement.get(className) == null) return 0;
        int covered_statement = 0;
        for (String key: statement.get(className).keySet())
            covered_statement += statement.get(className).get(key);
        return covered_statement;
    }

    public static int get_lines(String className){
        if (line.get(className) == null) return 0;
        int covered_line = 0;
        for (String key: line.get(className).keySet())
            covered_line += line.get(className).get(key);
        return covered_line;
    }
    
    public static float summary_statement(String intended_class){
        int covered_statements = 0, all_statements = 0;
        for (String className: statement.keySet()){
            if (intended_class.length() > 0 && !intended_class.equals(className))
                continue;
            for (String instruction: statement.get(className).keySet())
                covered_statements += statement.get(className).get(instruction);
        }
        for (String className: total_statement.keySet()){
            if (intended_class.length() > 0 && !intended_class.equals(className))
                continue;
            all_statements += total_statements(className);
        }
        return (float)100.0 * covered_statements / all_statements;
    }

    public static float summary_line(String intended_class){
        int covered_lines = 0, all_lines = 0;
        for (String className: line.keySet()){
            if (intended_class.length() > 0 && !intended_class.equals(className))
                continue;
            for (String instruction: line.get(className).keySet())
                covered_lines += line.get(className).get(instruction);
        }
        for (String className: total_line.keySet()){
            if (intended_class.length() > 0 && !intended_class.equals(className))
                continue;
                all_lines += total_lines(className);
        }
        return (float)100.0 * covered_lines / all_lines;
    }
    //------------------------- AUXILIARY -------------------------------
    
    
    public static void reset(){
        //Total clearing
        total_branch.clear();
        total_statement.clear();
        total_line.clear();

        //Specific clearing
        branch.clear();
        statement.clear();
        line.clear();
    }
}
