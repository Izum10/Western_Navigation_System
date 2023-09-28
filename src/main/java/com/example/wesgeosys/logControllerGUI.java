package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for launching the login GUI.
 * This class extends the Application class and launches the login page.
 */
public class logControllerGUI extends Application {

    /**
     * This method sets up the login GUI by loading the FXML file and creating a new scene with the specified dimensions.
     * It then sets the stage title, sets the scene, and shows the stage.
     *
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(logControllerGUI.class.getResource("logGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);/**/
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }
}