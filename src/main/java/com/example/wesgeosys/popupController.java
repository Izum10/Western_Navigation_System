package com.example.wesgeosys;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class popupController {

    /**
     * The closePopup button in the popup window.
     */
    @FXML private javafx.scene.control.Button closePopup;

    /**
     * This method is called when the ClosePopup button is clicked.
     * It closes the popup window.
     */
    @FXML
    protected void popupClose(){
        Stage stage = (Stage) closePopup.getScene().getWindow();
        stage.close();
    }
}
