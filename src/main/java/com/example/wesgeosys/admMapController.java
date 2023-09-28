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
 * This is the controller class for the administrative map view.
 * It contains methods for handling user interactions with the view, such as clicking submit and cancel buttons.
 */
public class admMapController implements Initializable {

    @FXML
    public Button submitButton;

    @FXML
    public Button cancelButton;

    /**
     * Handles the user clicking the submit button by closing the current window.
     *
     * @param event The event that occurred (the button click)
     */
    private void submit(ActionEvent event) {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the user clicking the cancel button by closing the current window.
     *
     * @param event The event that occurred (the button click)
     */
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is called by the FXMLLoader when the view is being loaded.
     * It sets up event handlers for the submit and cancel buttons.
     *
     * @param url            The URL of the FXML file
     * @param resourceBundle The ResourceBundle for the FXML file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setOnAction(this::submit);
        cancelButton.setOnAction(this::cancel);
    }
}
