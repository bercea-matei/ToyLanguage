package toyLanguage;

import toyLanguage.controller.Controller;
import toyLanguage.controller.MyController;
import toyLanguage.view.TextMenu;
import toyLanguage.view.GUIApp;
import toyLanguage.repository.Repository;
import toyLanguage.repository.MyRepository;
import toyLanguage.view.commands.*;

public class Interpreter {
    public static MyController controller;

    public static void main(String[] args) {
        
        boolean ifGui = true;

        MyRepository repo = new Repository();
        controller = new Controller(repo);
        
        if (ifGui) {
            GUIApp.main(args);
        } else {
            TextMenu tui = new TextMenu(controller);

            tui.addCommand(new ExitCommand("0", "Exit"));
            tui.addCommand(new SelectPrgCommand("1", "Select a program", controller));
            tui.addCommand(new RunAllCommand("2", "Run the entire program", controller));
            tui.addCommand(new RunOneStepCommand("3", "Run One Step", controller));
            tui.addCommand(new ShowCurrentStateCommand("4", "Show current state", controller));
            tui.addCommand(new ShowOriginalStateCommand("5", "Show Original state", controller));
            tui.addCommand(new GoToNextStateCommand("6", "Go to next state", controller));
            tui.addCommand(new FlipPrintFlagCommand("7", "Flip the print flag", controller));
            tui.addCommand(new GetLogCommand("8", "Change Log Path", controller));

            tui.show();
        }
    }
}
