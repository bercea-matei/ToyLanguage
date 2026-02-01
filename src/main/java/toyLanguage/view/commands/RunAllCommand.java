package toyLanguage.view.commands;

import toyLanguage.controller.MyController; 
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;

public class RunAllCommand extends Command{
    private final MyController controller;
        
    public RunAllCommand (String key, String description, MyController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.allStep();
        } catch (ToyLanguageExceptions e) {
            System.err.println("Execution failed: " + e.getMessage());
        }
    }
    /*
    @Override
    public void onExecutionStart(PrgState initialState) {
        System.out.println("--- Starting All Run ---");
        System.out.println(initialState.toString());
    }
    @Override
    public void onStepExecuted(PrgState currentState) {
        System.out.println("==>");
        System.out.println(currentState.toString());
    }
    @Override
    public void onExecutionFinish(PrgState finalState) {
        System.out.println("-----------------------------");
        System.out.println("Execution finished successfully.");
    }
    */
}
 
