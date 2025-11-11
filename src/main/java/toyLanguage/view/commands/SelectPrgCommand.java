package toyLanguage.view.commands;

import toyLanguage.controller.Controller; 
import java.util.Scanner;

import toyLanguage.domain.statements.*;
import toyLanguage.domain.types.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.dictionary.SymbolTable;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.list.OutList;
import toyLanguage.domain.adts.stack.ExeStk;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.expressions.*;
import toyLanguage.domain.myExceptions.UnknownOperatorException;
import toyLanguage.domain.prg_state.PrgState;

public class SelectPrgCommand extends Command {
    private final Controller controller;
    private final Scanner keyboard = new Scanner(System.in);
    private final String[] hardcode_options = {
        "int v; v=2; NOP; Print(v)", 
        "int a; int b; a=2+3*5; b=a+1; Print(b)",
        "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)" 
    };

        
    public SelectPrgCommand (String key, String description, Controller controller) {
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

        
}
  
