package com.example.wesgeosys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class provides functionality to load the "signupGUI.fxml" file,
 * create and show a new JavaFX stage that displays the GUI for user sign up.
 */
public class signupControllerGUI extends Application {

    /**
     * Overrides the {@code start} method of {@code Application} to create and show the sign-up window.
     *
     * @param stage the primary {@code Stage} for the application
     * @throws IOException if the FXML file for the sign-up window cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(logController.class.getResource("signupGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
    }
}

