package com.example.wesgeosys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class logController implements Initializable {

    @FXML
    private TextField passwordField;

    @FXML
    public Button loginButton;

    @FXML
    public Button signupButton;

    @FXML
    private TextField usernameField;

    boolean isValidUser = false;
    boolean isValidPassword = false;
    boolean isAdmin = false;
    public accountClass accountNum = new accountClass("src/main/java/com/example/wesgeosys/accountData.json");


    /**
     * Loads the Sign Up GUI and displays it on a new stage when the user clicks the "Sign Up" button.
     * Hides the current stage after displaying the new stage.
     *
     * @param event The ActionEvent triggered by the "Sign Up" button.
     */
    private void signupSelection(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signupGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
            Stage stage = new Stage();
            stage.setTitle("Sign Up Page");
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the controller with the specified URL and ResourceBundle.
     * Sets the actions to be executed when the "Submit" and "Sign Up" buttons are clicked.
     *
     * @param url The URL of the FXML file associated with this controller.
     * @param rb  The ResourceBundle for this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction(this::submitSelection);
        signupButton.setOnAction(this::signupSelection);
    }

    /**
     * Performs necessary actions when the "Submit" button is clicked.
     * Checks if the entered username and password match with the account information.
     * If the user is an admin, loads the "Admin Maps" GUI and displays it on a new stage.
     * If the user is a regular user, loads the "Main User Maps" GUI and displays it on a new stage.
     * If the entered information is invalid, loads the "Login Invalid" GUI and displays it on a new stage.
     *
     * @param event The ActionEvent triggered by the "Submit" button.
     */
    private void submitSelection(ActionEvent event) {
        try {
            if (usernameField.getText().equals(accountNum.getUser(usernameField.getText()))) {
                isValidUser = true;
            }
            if (passwordField.getText().equals(accountNum.getPassword(usernameField.getText()))) {
                isValidPassword = true;
            }
            isAdmin = accountNum.checkAdmin(usernameField.getText());
            if (isValidUser && isValidPassword && isAdmin) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admMapsGUI.fxml"));
                    String username = usernameField.getText();
                    mainMapsController.username = username;
                    mainMapsController.adminAccess = isAdmin;
                    Scene scene = new Scene(fxmlLoader.load(), 805.0, 1398.0);
                    Stage stage = new Stage();
                    stage.setTitle("Adminstration Maps");
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (isValidUser && isValidPassword) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMapsGUI.fxml"));
                    mainMapsController.username = usernameField.getText();
                    mainMapsController.adminAccess = isAdmin;
                    Scene scene = new Scene(fxmlLoader.load(), 1398.0, 805.0);
                    Stage stage = new Stage();
                    stage.setTitle("Main User Maps");
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logInvalidGUI.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
                    Stage stage = new Stage();
                    stage.setTitle("Login Invalid");
                    stage.setScene(scene);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
