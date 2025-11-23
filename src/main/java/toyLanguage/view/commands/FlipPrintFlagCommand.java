package toyLanguage.view.commands;

import toyLanguage.controller.MyController; 

public class FlipPrintFlagCommand extends Command {
    private final MyController controller;
        
    public FlipPrintFlagCommand (String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        this.controller.flipPrintFlag();    
    }
}
 
