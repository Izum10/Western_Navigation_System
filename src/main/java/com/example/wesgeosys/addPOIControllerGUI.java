package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class is responsible for launching the GUI for adding a new point of interest (POI).
 * It extends the JavaFX Application class, and overrides its start() method to create a new window
 * and load a graphical user interface (GUI) from an FXML file.
 */
public class addPOIControllerGUI extends Application {

    /**
     * This method is called when the application is started, and it creates a new window and loads
     * the graphical user interface (GUI) for adding a new POI from an FXML file.
     *
     * @param stage The primary stage for the application
     * @throws IOException If an input/output error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(addPOIControllerGUI.class.getResource("addPOIGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
        stage.setTitle("Add POI");
        stage.setScene(scene);
        stage.show();
    }
}

