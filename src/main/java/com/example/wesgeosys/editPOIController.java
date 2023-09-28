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
 * The controller class for the edit POI view.
 */
public class editPOIController implements Initializable {

    // FXML fields for the input fields and buttons
    @FXML
    public TextField name;         // name of the point of interest
    @FXML
    public TextField desc;         // description of the point of interest
    @FXML
    public TextField layerType;    // the type of layer the POI is on
    @FXML
    public TextField roomNum;      // the room number the POI is associated with
    @FXML
    public TextField x;            // the x-coordinate of the POI on the map
    @FXML
    public TextField y;            // the y-coordinate of the POI on the map
    @FXML
    public Button submitButton;    // button to submit changes
    @FXML
    public Button cancelButton;    // button to cancel changes

    // static fields to hold the updated POI data
    public static String updateName;    // updated name of the POI
    public static String updatePOIDesc;    // updated description of the POI
    public static String updateRoomNum;    // updated room number of the POI
    public static String player;   // updated layer type of the POI

    /**
     * Method to update the static fields with the new POI data and close the view.
     *
     * @param event The event triggered by clicking the submit button
     */
    private void submit(ActionEvent event) {
        updateName = name.getText();
        updatePOIDesc = desc.getText();
        updateRoomNum = roomNum.getText();
        player = layerType.getText();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method to close the view without updating the POI data.
     *
     * @param event The event triggered by clicking the cancel button
     */
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
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
