package toyLanguage.view.commands;

import toyLanguage.controller.Controller; 

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;

public class RunOneStepCommand extends Command {
    private final Controller controller;
        
    public RunOneStepCommand (String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.oneStep(this.controller.getCurrentState());
        } catch (ToyLanguageExceptions e) {
            System.out.println(e.getMessage());
        }
    }

        
}
 
