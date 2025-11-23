package toyLanguage.view.commands;

import toyLanguage.controller.MyController; 

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;

public class RunOneStepCommand extends Command {
    private final MyController controller;
        
    public RunOneStepCommand (String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.oneStep(this.controller.getCurrentState());
            if (this.controller.getPrintFlag())
                System.out.println(this.controller.getCurrentState().toString());
        } catch (ToyLanguageExceptions e) {
            System.out.println(e.getMessage());
        }
    }

        
}
 
