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
 * The controller class for the edit building GUI.
 */
public class editBuildingController implements Initializable {

    @FXML
    public TextField name; // A TextField for the user to input the new name of the building.

    @FXML
    public Button submitButton; // A Button to submit the new building name.

    @FXML
    public Button cancelButton; // A Button to cancel the editing.

    public static String editName; // A static variable to store the new building name.

    /**
     * Called when the submit button is clicked.
     * Stores the new building name in the static variable and closes the window.
     */
    private void submit(ActionEvent event) {
        editName = name.getText();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Called when the cancel button is clicked.
     * Closes the window without making any changes.
     */
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     * Sets the actions for the submit and cancel buttons.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitButton.setOnAction(this::submit);
        cancelButton.setOnAction(this::cancel);
    }
}
