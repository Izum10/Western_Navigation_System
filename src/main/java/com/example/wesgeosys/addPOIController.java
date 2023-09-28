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
 * This class is responsible for controlling the GUI form used to add a new point of interest (POI) to the system.
 * It contains methods for handling user input and storing the new POI data.
 */
public class addPOIController implements Initializable {

    @FXML
    public TextField poi;

    @FXML
    public Button submitButton;

    @FXML
    public Button cancelButton;

    public static String newName;



    /**
     * Stores the user input from the name and description fields and closes the window when the submit button is clicked.
     *
     * @param event The user's action event
     */
    private void submit(ActionEvent event) {
        newName = poi.getText();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Closes the window when the cancel button is clicked.
     *
     * @param event The user's action event
     */
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Initializes the controller by setting the event handlers for the submit and cancel buttons.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitButton.setOnAction(this::submit);
        cancelButton.setOnAction(this::cancel);
    }
}

