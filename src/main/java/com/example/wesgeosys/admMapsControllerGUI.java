package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class is responsible for launching the GUI for admin main maps.
 * It extends the JavaFX Application class, and overrides its start() method to create a new window
 * and load a graphical user interface (GUI) from an FXML file.
 */
public class admMapsControllerGUI extends Application {

    /**
     * This method is called when the admin application is started, and it creates a new window and loads
     * the graphical user interface (GUI) for management.
     *
     * @param stage The primary stage for the application
     * @throws IOException If an input/output error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(addFloorControllerGUI.class.getResource("admMapsGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 805.0, 1398.0);
        stage.setTitle("Admin Maps");
        stage.setScene(scene);
        stage.show();
    }
}