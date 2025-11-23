package toyLanguage.view;

import toyLanguage.controller.MyController;
import toyLanguage.view.commands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;
    private final Scanner keyboard = new Scanner(System.in);
    private MyController controller;

    public TextMenu(MyController controller) {
        this.commands = new HashMap<>();
        this.controller = controller;
    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu() {
        System.out.println("\n------------ MENU ------------");
        for (Command command : commands.values()) {
            String line = String.format("%3s. %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
        System.out.println("------------------------------");
        System.out.printf(" print flag: %b%n", this.controller.getPrintFlag());
        System.out.println("------------------------------");
    }

    public void show() {
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
