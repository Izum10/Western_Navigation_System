package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is responsible for launching the GUI form used to add a new floor to the system.
 * It uses an FXML file to define the layout and behavior of the form.
 */
public class addFloorControllerGUI extends Application {

    /**
     * Starts the JavaFX application by loading the FXML file and displaying the form in a new window.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set
     * @throws IOException If an I/O error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(addFloorControllerGUI.class.getResource("addFloorGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);;
        stage.setTitle("Add Floor");
        stage.setScene(scene);
        stage.show();
    }
}
