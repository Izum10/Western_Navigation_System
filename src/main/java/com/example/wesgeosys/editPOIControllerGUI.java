package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * A JavaFX controller class for the editPOIGUI.fxml file.
 */
public class editPOIControllerGUI extends Application {
    /**
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * This method sets up the JavaFX stage for the Edit POI GUI.
     *
     * @param stage The primary stage for this JavaFX application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(editPOIControllerGUI.class.getResource("editPOIGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
        stage.setTitle("Edit POI");
        stage.setScene(scene);
        stage.show();
    }
}
