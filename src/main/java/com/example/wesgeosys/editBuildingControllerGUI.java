package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * A JavaFX controller class for the editBuildingGUI.fxml file.
 */
public class editBuildingControllerGUI extends Application{

    /**
     * The start method of the JavaFX application, which loads and displays the GUI scene.
     * @param stage the primary stage for the application
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(editBuildingControllerGUI.class.getResource("editBuildingGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
        stage.setTitle("Edit Building");
        stage.setScene(scene);
        stage.show();
    }
}

