package toyLanguage.view.commands;

import toyLanguage.controller.MyController; 
import java.util.Scanner;

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.UnknownOperatorException;

public class SelectPrgCommand extends Command {
    private final MyController controller;
    private final Scanner keyboard = new Scanner(System.in);
    private final String[] hardcode_options = {
        "int v; v=2; NOP; Print(v)", 
        "int a; int b; a=2+3*5; b=a+1; Print(b)",
        "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)",
        //log
        "(String varf ; (varf = test.in ; (openRFile(varf) ; (int varc ; (readFile(varf, varc) ; (print(varc) ; (readFile(varf, varc) ; (print(varc) ; closeRFile(varf)))))))))",
        //while
        "int v; v=4; (while (v>0) print(v);v=v-1);print(v)",
        //heap_stuff
        "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)",
        "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)",
        "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)",
        //for garbage collector
        "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))"
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
        } else if (option.equals("2")) {
            this.loadOption2();
        } else if (option.equals("3")) {
            this.loadOption3();
        } else if (option.equals("4")) {
            this.loadOption4();
        } else if (option.equals("5")) {
            this.loadOption5();
        } else if (option.equals("6")) {
            this.loadOption6();
        } else if (option.equals("7")) {
            this.loadOption7();
        } else if (option.equals("8")) {
            this.loadOption8();
        } else if (option.equals("9")) {
            this.loadOption9();
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
        System.out.print(">>");

    }
    private void loadOption1() {
        try {
            this.controller.loadOption1();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption2() {
        try {
            this.controller.loadOption2();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption3() {
        try {
            this.controller.loadOption3();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption4() {
        try {
            this.controller.loadOption4();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption5() {
        try {
            this.controller.loadOption5();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption6() {
        try {
            this.controller.loadOption6();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption7() {
        try {
            this.controller.loadOption7();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }

    }
    private void loadOption8() {
        try {
            this.controller.loadOption8();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
    private void loadOption9() {
        try {
            this.controller.loadOption9();
            System.out.println("program saved successfully");
        } catch (ToyLanguageExceptions e) {
            System.out.println(e);
        }
    }
        
}
  
