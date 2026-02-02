package toyLanguage.view;

import toyLanguage.controller.MyController;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class MainWindowController {

    private MyController logicController;
    private int currentSelectedProgramId = 1;

    @FXML
    private Label noPrgStates;
    @FXML
    private Label outListLabel;
    @FXML
    private Label fileTableLabel;
    @FXML
    private Label currentPrgStateIdLabel;
    @FXML
    private Button runOneStepButton;
    @FXML
    private ListView<String> exeStkListView;
    @FXML
    private ListView<Integer> prgStatesIdsListView;
    @FXML
    private ListView<String> outListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private TableView<Map.Entry<String, Value>> symTableView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symVarNameCol;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symValueCol;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapAddressCol;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapValueCol;
    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> latchLocationCol;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> latchValueCol;


    public void setLogicController(MyController controller) {
        this.logicController = controller;
        updateAllUIComponents();
    }

    @FXML
    public void initialize() {
        

        prgStatesIdsListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                
                if (newValue != null) {
                    handleProgramStateSelection(newValue);
                }
            }
        );

        symVarNameCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getKey());
        });

        symValueCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getValue().toString());
        });

        heapAddressCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getKey().toString());
        });

        heapValueCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getValue().toString());
        });

        latchLocationCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getKey().toString());
        });

        latchValueCol.setCellValueFactory(p -> {
            return new SimpleStringProperty(p.getValue().getValue().toString());
        });
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
    
        } catch (ToyLanguageExceptions e) {
            this.runOneStepButton.setDisable(true);
            showError(e.getMessage());
        }
    }

    private void handleProgramStateSelection(Integer selectedId) {
        currentSelectedProgramId = selectedId;
        this.currentPrgStateIdLabel.setText("Current Program State Id: " + this.currentSelectedProgramId);
        updateAllUIComponents();
    }

    private void updateNoProgramStates(Integer size) {
        noPrgStates.setText("No. Program States:" + String.valueOf(size));
    }
    private void updatePrgStatesIdsListView(List<Integer> allIds) {
        //allIds = List.of(1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18);
        //prgStatesIdsListView.getItems().setAll(allIds);

        List<Integer> currentItems = prgStatesIdsListView.getItems();

        if (!currentItems.equals(allIds)) {
            Integer selected = prgStatesIdsListView.getSelectionModel().getSelectedItem();
            
            prgStatesIdsListView.getItems().setAll(allIds);
            
            if (selected != null && allIds.contains(selected)) {
                prgStatesIdsListView.getSelectionModel().select(selected);
            } else if (!allIds.isEmpty()) {
                prgStatesIdsListView.getSelectionModel().selectFirst();
            }
        }
    }
    //-------------------------------
    //   OutList
    //-------------------------------
    private void updateOutListView() {
        if(outListView == null)
            return;

        MyList<Value> outList = this.logicController.getOutList();

        if (outList == null || outList.isEmpty()) {
            return; 
        }

        List<Value> allPrints = new ArrayList<>();
        for (Value val : outList) {
            allPrints.add(val);
        }
        

        List<String> strAllPrints = allPrints.stream()
                                    .map(Value::toString)
                                    .collect(Collectors.toList());
        outListView.getItems().setAll(strAllPrints);
    }
    //-------------------------------
    //   FileTable
    //-------------------------------
    private void updateFileTableListView() {
        MyDict<StringValue, BufferedReader> fileTable = this.logicController.getFileTable();
        if (fileTable == null)
            return;
        List<String> allFiles = new ArrayList<>();
        for(Map.Entry<StringValue, BufferedReader> entry : fileTable)
            allFiles.add(entry.getKey().toString() + " --> " + entry.getValue().toString());

        fileTableListView.getItems().setAll(allFiles);
    }
    //-------------------------------
    //---ExeStk update
    //-------------------------------
    private void updateExeStkListView() {
        MyStack<Stmt> exeStk = this.logicController.getExeStkById(this.currentSelectedProgramId);
         if (exeStk == null)
            return;
        List<String> allStmts = new ArrayList<>();
        for(Stmt stmt : exeStk)
            allStmts.add(stmt.toString());
        exeStkListView.getItems().setAll(allStmts);
    }

    //-------------------------------
    //SymTable
    //-------------------------------
    private void updateSymbolTableView() {
        MyDict<String, Value> symTable = this.logicController.getSymTableById(this.currentSelectedProgramId);
        if (symTableView == null) {
            return;
        }

        ObservableList<Map.Entry<String, Value>> items = symTableView.getItems();

        if (symTable == null || symTable.isEmpty()) {
            items.clear();
            return;
        }

        Map<String, Value> map = symTable.getContent();
        List<Map.Entry<String, Value>> symTableEntryList = new ArrayList<>(map.entrySet());
        items.setAll(symTableEntryList);
    }
    //-------------------------------
    //   HeapTable
    //-------------------------------
    private void updateHeapTableView() {
        MyHeap<Integer, Value> heapTable = this.logicController.getHeapTable();
        if (heapTableView == null) {
            return;
        }

        ObservableList<Map.Entry<Integer, Value>> items = heapTableView.getItems();

        if (heapTable == null || heapTable.isEmpty()) {
            //items.clear();
            return;
        }

        Map<Integer, Value> map = heapTable.getContent();
        List<Map.Entry<Integer, Value>> symTableEntryList = new ArrayList<>(map.entrySet());
        items.setAll(symTableEntryList);
    }
    //-------------------------------
    //   LatchTable
    //-------------------------------
    private void updateLatchTableView() {
        MyDict<Integer, Integer> latchTable = this.logicController.getLatchTable();
        if (latchTableView == null) {
            return;
        }

        if (latchTable == null || latchTable.isEmpty()) {
            //items.clear();
            return;
        }

        List<Map.Entry<Integer, Integer>> latchList = new ArrayList<>(latchTable.getContent().entrySet());

        latchTableView.getItems().setAll(latchList);
    }

    //-------------------------------
    //   ALL Update
    //-------------------------------
    private void updateAllUIComponents() {
        List<PrgState> currentStates = logicController.getPrgList();
        if (currentStates.isEmpty()) {
            //outListView.getItems().clear();
            exeStkListView.getItems().clear();
            prgStatesIdsListView.getItems().clear();
            //fileTableListView.getItems().clear();
            symTableView.getItems().clear();
            heapTableView.getItems().clear();
        
            updateOutListView();
            updateFileTableListView();
            updateLatchTableView();

            return;
        }
        //no_prgs
        updateNoProgramStates(currentStates.size());
        //---prgstate id's
        List<Integer> allIds = currentStates.stream()
                                            .map((prg) -> {return prg.getID();})
                                            .collect(Collectors.toList());
        updatePrgStatesIdsListView(allIds);
        

        //gracefull ones
        updateOutListView();
        updateFileTableListView();
        updateExeStkListView();
        updateSymbolTableView();
        updateHeapTableView();
        updateLatchTableView();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred!");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
