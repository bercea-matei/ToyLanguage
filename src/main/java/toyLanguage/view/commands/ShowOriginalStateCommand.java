package toyLanguage.view.commands;

import toyLanguage.controller.Controller; 
import toyLanguage.domain.myExceptions.NoProgramToRunException;

public class ShowOriginalStateCommand extends Command {
    private final Controller controller;
        
    public ShowOriginalStateCommand (String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.println("\n--------Original-State--------");
            System.out.println(this.controller.getOriginalState());    
        } catch (NoProgramToRunException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getPrintFlag() {
        return this.controller.getPrintFlag();
    }
}
 
