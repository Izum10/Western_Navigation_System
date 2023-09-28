package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class creates a graphical user interface (GUI) for adding a new building to the system.
 * It uses an FXML file to define the layout and behavior of the form.
 */
public class addBuildingControllerGUI extends Application {

    /**
     * Starts the application and sets up the main window with the addBuildingGUI.fxml file as the content.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set
     * @throws IOException If there is an error loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(addBuildingControllerGUI.class.getResource("addBuildingGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
        stage.setTitle("Add Building");
        stage.setScene(scene);
        stage.show();
    }
}
