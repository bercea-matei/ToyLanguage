package toyLanguage.view;

import toyLanguage.Interpreter; // Assuming this is where the static logicController is
import toyLanguage.controller.MyController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainWindowController {

    private MyController logicController;

    @FXML
    private ListView<String> programStatesListView;

    @FXML
    private Button runOneStepButton;

    @FXML
    public void initialize() {
        this.logicController = Interpreter.controller;

        runOneStepButton.setDisable(true); 
        
        updateProgramStatesList();
    }

    @FXML
    private void handleRunOneStep() {
        if (logicController == null) {
            showError("Controller not initialized.");
            return;
        }

        try {
            logicController.oneStepForAllPrg();
            
            updateAllUIComponents();

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }


    private void updateProgramStatesList() {
        programStatesListView.getItems().clear();
    }

    private void updateAllUIComponents() {
        // This method would be called after any action to refresh everything.
        updateProgramStatesList();
        // updateHeapTableView();
        // updateSymbolTableView();
        // updateOutputListView();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
