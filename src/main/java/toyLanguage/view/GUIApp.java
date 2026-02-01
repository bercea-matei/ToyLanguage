package toyLanguage.view;

import toyLanguage.Interpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApp.class.getResource("ProgramSelector.fxml"));
        Parent root = fxmlLoader.load();
        ProgramSelectorController selectorController = fxmlLoader.getController();
        primaryStage.setOnCloseRequest(event -> {
            selectorController.shutdownAll();
            System.exit(0);
        });

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("My Toy Language Interpreter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        
        super.stop();
    }
}
