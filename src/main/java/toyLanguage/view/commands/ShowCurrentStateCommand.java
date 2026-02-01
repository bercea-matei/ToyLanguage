package toyLanguage.view.commands;

import toyLanguage.controller.MyController;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.prg_state.PrgState;

public class ShowCurrentStateCommand extends Command {
    private MyController controller;

    public ShowCurrentStateCommand(String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println("\n---------CurrentState---------");
        System.out.println("\n(deprecated)");
        System.out.println("\n(we change nothing)");
        //try {
            //PrgState stateD = this.controller.getCurrentState();
        //    System.out.println(stateD.toString());
        //} catch (NoProgramToRunException e) {
        //    System.out.println(e.getMessage());
        //}
    }
}
