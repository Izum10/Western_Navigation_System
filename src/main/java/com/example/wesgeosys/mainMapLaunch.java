package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for launching the application.
 * This class extends the Application class and launches the user map view.
 */
public class mainMapLaunch extends Application {

    /**
     * This method sets up the main map GUI by loading the FXML file and creating a new scene.
     * It then sets the stage title, maximizes the window, and shows the scene.
     *
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(mainMapLaunch.class.getResource("admMapsGUI.fxml"));
        Scene scene = new Scene(root.load());
        stage.setTitle("User Map View");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * The main method for launching the application.
     * This method launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}