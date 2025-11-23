package toyLanguage.view.commands;

import toyLanguage.controller.MyController;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;

public class GoToNextStateCommand extends Command {
    MyController controller;

    public GoToNextStateCommand(String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.goToNextState();
            System.out.println("Next state was loaded");
        } catch (FinishUnexistentStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
