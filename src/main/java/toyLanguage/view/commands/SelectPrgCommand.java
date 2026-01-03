package toyLanguage.view.commands;

import toyLanguage.controller.MyController; 
import java.util.Scanner;

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.UnknownOperatorException;
import toyLanguage.examples.Examples;

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
            try {
                Examples.loadOption1(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("2")) {
            try {
                Examples.loadOption2(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("3")) {
            try {
                Examples.loadOption3(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("4")) {
            try {
                Examples.loadOption4(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("5")) {
            try {
                Examples.loadOption5(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            };
        } else if (option.equals("6")) {
            try {
                Examples.loadOption6(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            };
        } else if (option.equals("7")) {
            try {
                Examples.loadOption7(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("8")) {
            try {
                Examples.loadOption8(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("9")) {
            try {
                Examples.loadOption9(this.controller);;
                System.out.println("program saved successfully");
            } catch (ToyLanguageExceptions e) {
                System.out.println(e.getMessage());
            }
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
} 



