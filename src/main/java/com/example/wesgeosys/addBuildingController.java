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
 * This class controls the functionality for adding a new building to the system.
 * It contains methods for submitting and cancelling the operation, as well as initializing the form.
 */
public class addBuildingController implements Initializable {

    @FXML
    private TextField building;

    @FXML
    public Button submitButton;

    @FXML
    public Button cancelButton;

    public static String buidingName;

    /**
     * Gets the name of the building from the text field and sets it to a static variable for later use.
     * Closes the current window upon completion.
     *
     * @param event The event triggered by clicking the submit button
     */
    private void submit(ActionEvent event) {
        buidingName = building.getText();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the current window without saving any changes.
     *
     * @param event The event triggered by clicking the cancel button
     */
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets up the functionality for the submit and cancel buttons on the form.
     *
     * @param url      The URL to initialize
     * @param rb       The resource bundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitButton.setOnAction(this::submit);
        cancelButton.setOnAction(this::cancel);
    }
}

