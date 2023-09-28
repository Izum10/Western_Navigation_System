package com.example.wesgeosys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for handling user input when adding a new floor to the system.
 * It uses an FXML file to define the layout and behavior of the form.
 */
public class addFloorController implements Initializable {

    @FXML
    private TextField floor;
    @FXML
    public Button submitButton;
    @FXML
    public Button cancelButton;

    /**
     * Handles the submission of the form data by printing the floor name to the console and closing the window.
     *
     * @param event The event that triggered this method (button click)
     */
    private void submit(ActionEvent event) {
        System.out.println(floor.getText());
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the cancellation of the form by simply closing the window.
     *
     * @param event The event that triggered this method (button click)
     */
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller by setting up the button event handlers.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb  The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitButton.setOnAction(this::submit);
        cancelButton.setOnAction(this::cancel);
    }
}

