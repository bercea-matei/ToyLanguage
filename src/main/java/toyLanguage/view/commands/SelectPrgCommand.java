package toyLanguage.view.commands;

import toyLanguage.controller.MyController; 
import java.util.Scanner;

import toyLanguage.domain.myExceptions.UnknownOperatorException;

public class SelectPrgCommand extends Command {
    private final MyController controller;
    private final Scanner keyboard = new Scanner(System.in);
    private final String[] hardcode_options = {
        "int v; v=2; NOP; Print(v)", 
        "int a; int b; a=2+3*5; b=a+1; Print(b)",
        "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)",
        "(String varf ; (varf = test.in ; (openRFile(varf) ; (int varc ; (readFile(varf, varc) ; (print(varc) ; (readFile(varf, varc) ; (print(varc) ; closeRFile(varf)))))))))"
    };

        
    public SelectPrgCommand (String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        this.printOptions();
        String option = keyboard.next();
        
        if(option.equals("1")) {
            this.loadOption1();
            System.out.println("program saved successfully");
        } else if (option.equals("2")) {
            this.loadOption2();
            System.out.println("program saved successfully");
        } else if (option.equals("3")) {
            this.loadOption3();
            System.out.println("program saved successfully");
        } else if (option.equals("4")) {
            this.loadOption4();
            System.out.println("program saved successfully");
        } else {
            System.err.println("unkown option");
        }
    }

    private void printOptions() {
        int ind = 1;
        System.out.println("\n----------- SELECT -----------");
        for (String s : this.hardcode_options) {
            String line = String.format("%3s. %s", Integer.toString(ind), s);
            System.out.println(line);
            ind++;
        }
        System.out.println("  0. Back");
        System.out.println("------------------------------");

    }
    private void loadOption1() {
        this.controller.loadOption1();
    }
    private void loadOption2() {
        try {
            this.controller.loadOption2();
        } catch (UnknownOperatorException e) {
            System.out.println(e.getMessage());
        }
    }
    private void loadOption3() {
        this.controller.loadOption3();
    }
    private void loadOption4() {
        this.controller.loadOption4();
    }

        
}
  
