package toyLanguage.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import toyLanguage.Interpreter;
import toyLanguage.controller.Controller;
import toyLanguage.controller.MyController;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.examples.Examples;
import toyLanguage.examples.ProgramExample;
import toyLanguage.repository.MyRepository;
import toyLanguage.repository.Repository;

import java.io.IOException;
import java.util.List;

public class ProgramSelectorController {
    List<ProgramExample> exList;

    @FXML
    private ListView<ProgramExample> programListView;

    //public Stmt getSelectedProgram() {
    //    return programListView.getSelectionModel().getSelectedItem();
    //}
    
    @FXML
    public void initialize() {
        Examples ex = new Examples();
        this.exList = ex.getAll();
        programListView.getItems().setAll(exList);
        
        programListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    handleNewExecutorEngine();
                }
            }
        );
    }

    private void handleNewExecutorEngine() {
        ProgramExample selectedPrg = programListView.getSelectionModel().getSelectedItem();
        if (selectedPrg == null) {
            return;
        }

        MyController controller;
        MyRepository repo = new Repository();
        controller = new Controller(repo);

        try {
            try {
                selectedPrg.loadIntoController(controller);
            } catch (ToyLanguageExceptions e) {
                showError(e.getMessage());
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();

            MainWindowController uiCtrl = loader.getController();
            uiCtrl.setLogicController(controller);

            Stage newStage = new Stage();
            newStage.setTitle("Execution Engine - " + selectedPrg.toString());
            
            Scene scene = new Scene(root, 1000, 700);
            newStage.setScene(scene);
            
            newStage.initOwner(programListView.getScene().getWindow()); 
            newStage.initModality(Modality.NONE); 

            newStage.show();

        } catch (IOException e) {
            showError("Critical Error: Could not load the execution interface.");
        }
    }

    private void showError(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred!");
            alert.setContentText(message);
            alert.showAndWait();
        }
}




