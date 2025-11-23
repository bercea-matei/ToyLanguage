package toyLanguage.view.commands;

import java.util.Scanner;

import toyLanguage.controller.MyController;


public class GetLogCommand extends Command{
    private final MyController controller;
    private final Scanner keyboard = new Scanner(System.in);
        
    public GetLogCommand (String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        String currPath = this.controller.getLogFilePath();
        if (currPath != null)
            System.out.println(currPath);
        else
            System.out.println("No path set.");
        System.out.print("input a path (or 0 to go back)\n>>");
        String newPath = keyboard.next();
        if (! newPath.equals("0"))
            this.controller.setLogFilePath(newPath);
    }
}
 
