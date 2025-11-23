package toyLanguage.view.commands;

import toyLanguage.controller.MyController;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.prg_state.PrgState;

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
