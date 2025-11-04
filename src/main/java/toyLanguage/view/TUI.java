package toyLanguage.view;

import toyLanguage.controller.Controller;
import toyLanguage.view.commands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TUI {
    private final Map<String, Command> commands;
    private final Scanner keyboard = new Scanner(System.in);
    private final String flipId = "7";

    public TUI(Controller controller) {
        this.commands = new HashMap<>();
        addCommand(new ExitCommand("0", "Exit"));
        addCommand(new SelectPrgCommand("1", "Select a program", controller));
        addCommand(new RunAllCommand("2", "Run the entire program", controller));
        addCommand(new RunOneStepCommand("3", "Run One Step", controller));
        addCommand(new ShowCurrentStateCommand("4", "Show current state", controller));
        addCommand(new ShowOriginalStateCommand("5", "Show Original state", controller));
        addCommand(new GoToNextStateCommand("6", "Go to next state", controller));
        addCommand(new FlipPrintFlagCommand(flipId, "Flip the print flag", controller));
    }

    private void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu() {
        System.out.println("\n------------ MENU ------------");
        for (Command command : commands.values()) {
            String line = String.format("%3s. %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
        System.out.println("------------------------------");
        System.out.printf(" print flag: %b%n", ((FlipPrintFlagCommand)this.commands.get(this.flipId)).getPrintFlag());
        System.out.println("------------------------------");
    }

    public void start() {
        while (true) {
            printMenu();
            System.out.print("Enter option: ");
            String key = keyboard.next();
            
            Command command = commands.get(key);

            if (command == null) {
                System.out.println("Invalid option. Please try again.");
                continue; 
            }

            command.execute();
        }
    }
}
