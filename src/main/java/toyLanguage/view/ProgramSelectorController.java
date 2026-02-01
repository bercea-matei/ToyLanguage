package toyLanguage.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import toyLanguage.Interpreter;
import toyLanguage.controller.MyController;
import toyLanguage.domain.statements.Stmt;
import java.util.List;

public class ProgramSelectorController {

    private MyController logicController;

    @FXML
    private ListView<Stmt> programListView;

    public void setPrograms(List<Stmt> programs) {
        programListView.getItems().setAll(programs);
    }

    public Stmt getSelectedProgram() {
        return programListView.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    public void initialize() {
        this.logicController = Interpreter.controller;

        programListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Stmt item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        
        programListView.getSelectionModel().selectFirst();
    }
}
