package comp5111.assignment;

import comp5111.assignment.cut.Instrumenter;
import comp5111.assignment.cut.Counter;

import java.io.IOException;
import org.junit.runner.JUnitCore;
import java.util.Arrays;
import soot.*;
import soot.options.Options;


public class Assignment1 {
    public static String sourceDirectory = System.getProperty("user.dir");

    private static void runJunitTests(String test_suite) {
        Class<?> testClass = null;
        try {
            // here we programmitically run junit tests
            testClass = Class.forName("comp5111.assignment.cut." + test_suite);
            JUnitCore junit = new JUnitCore();
            System.out.println("Running junit test: " + testClass.getName());
            junit.run(testClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    static String SubjectClass = "comp5111.assignment.cut.Subject$";
    // define sub-classes to be analyzed in array form
    private static String[] classesToBeAnalyzed = new String[]{
        SubjectClass + "NumberTasks",
        SubjectClass + "CharTasks",
        SubjectClass + "GregorianTasks",
        SubjectClass + "FilenameTasks",
        SubjectClass + "ArrayTasks",
        SubjectClass + "StringTasks",
        SubjectClass + "BooleanTasks"
    };

    private static void instrumentWithSoot() {
        // the path to the compiled Subject class file
        String targetPath = "./target/classes";

        /*Set the soot-classpath to include the helper class and class to analyze*/
        Options.v().set_soot_classpath(Scene.v().defaultClassPath() + ":" + targetPath);

        // we set the soot output dir to target/classes so that the instrumented class can override the class file
        Options.v().set_output_dir(targetPath);

        // retain line numbers
        Options.v().set_keep_line_number(true);
        // retain the original variable names
        Options.v().setPhaseOption("jb", "use-original-names:true");

        /* add a phase to transformer pack by call Pack.add */
        Pack jtp = PackManager.v().getPack("jtp");

        //add our custom instrumenter to jtp
        Instrumenter instrumenter = new Instrumenter();
        jtp.add(new Transform("jtp.instrumenter", instrumenter));

        // pass arguments to soot
        soot.Main.main(classesToBeAnalyzed); 
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        
        /* check the arguments */
        if ((args.length != 2 && args.length != 3) || (args[0].compareTo("0") != 0 && args[0].compareTo("1") != 0 && args[0].compareTo("2") != 0)) {
            System.err.println("Usage: java comp5111.assignment.Assignment1 [coverage level] test-suite classname" + 
               " [if you enter classname empty then it will analyze all classes]");
            System.err.println("Usage: [coverage level] = 0 for statement coverage");
            System.err.println("Usage: [coverage level] = 1 for branch coverage");
            System.err.println("Usage: [coverage level] = 2 for line coverage");
            System.exit(0);
        }

        // these args will be passed into soot.
        String[] classNames = Arrays.copyOfRange(args, 1, args.length);
        String mainClass = "";
        if (classNames.length > 1){
            // add prefix extension to specify package name
            mainClass = SubjectClass + classNames[1];
            // verify entered input class takes place in main class
            Boolean found_class = false;
            for (int i = 0; i < classesToBeAnalyzed.length; i++)
                found_class |= classesToBeAnalyzed[i].equals(mainClass);
            // if class not found, raise error
            if (!found_class){
                System.err.println("Searched class not found, please enter one of following classes to be analyzed:");
                System.err.print("\tNumberTasks\n\tCharTasks\n\tGregorianTasks"+
                "\n\tFilenameTasks\n\tArrayTasks\n\tStringTasks\n\tBooleanTasks\n");
                System.exit(0);
            }
        }
        // Instrument main class and configure the soot options
        instrumentWithSoot();
        // Run specified test suit
        runJunitTests(classNames[0]);

        // It calls relevant functions of Counter depending on purpose
        if (args[0].compareTo("0") == 0) {
            
            System.out.printf("Overall:\npercentage: %.1f%%\n\n", Counter.summary_statement(mainClass));
            System.out.println("==============================================================\n");
            // print statement coverage percentage of each class
            for (int i = 0; i < classesToBeAnalyzed.length; i++){
                // if it is stated analyze only specified class
                if (mainClass.length() > 0 && !mainClass.equals(classesToBeAnalyzed[i])) continue;
                System.out.println(classesToBeAnalyzed[i]);
                // print covered statement divided by total statement
                System.out.printf("percentage: %.1f%%\n\n",
                    100.0 * Counter.get_statements(classesToBeAnalyzed[i]) / 
                            Counter.total_statements(classesToBeAnalyzed[i]));
            }

        }
        // Comments are same for the following two conditions as well 
        else if (args[0].compareTo("1") == 0) {
            
            System.out.printf("Overall:\npercentage: %.1f%%\n\n", Counter.summary_branch(mainClass));
            System.out.println("==============================================================\n");
            for (int i = 0; i < classesToBeAnalyzed.length; i++){
                if (mainClass.length() > 0 && !mainClass.equals(classesToBeAnalyzed[i]))
                    continue;
                System.out.println(classesToBeAnalyzed[i]);
                System.out.printf("percentage: %.1f%%\n\n",
                    100.0 * Counter.get_branches(classesToBeAnalyzed[i]) / 
                            Counter.total_branches(classesToBeAnalyzed[i]));
            }

        } else if (args[0].compareTo("2") == 0) {
            
            System.out.printf("Overall:\npercentage: %.1f%%\n\n", Counter.summary_line(mainClass));
            System.out.println("==============================================================\n");
            for (int i = 0; i < classesToBeAnalyzed.length; i++){
                if (mainClass.length() > 0 && !mainClass.equals(classesToBeAnalyzed[i]))
                    continue;
                System.out.println(classesToBeAnalyzed[i]);
                System.out.printf("percentage: %.1f%%\n\n",
                    100.0 * Counter.get_lines(classesToBeAnalyzed[i]) / 
                            Counter.total_lines(classesToBeAnalyzed[i]));
            }

        }
    }
}