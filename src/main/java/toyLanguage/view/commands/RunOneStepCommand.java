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
            this.controller.oneStepForAllPrg();;
            if (this.controller.getPrintFlag()) {
                this.controller.logPrgStateExec();
        }
        } catch (ToyLanguageExceptions e) {
            System.out.println(e.getMessage() + "!!!!");
        }
    }

        
}
 
