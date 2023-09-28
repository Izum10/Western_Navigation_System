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

/**
 * This class handles user account creation and GUI transitions for the sign-up process.
 * Upon submission of the form, the provided username and password are validated and a new account is created.
 * If the username is invalid, the user is notified with an error message.
 * If the username is valid and the account is successfully created, the main maps GUI is displayed in full-screen mode.
 * If any exceptions are thrown during the process, they are printed to the console.
 */
public class signupController implements Initializable {

    @FXML
    private TextField passwordField;

    @FXML
    public Button buttonLogin;

    @FXML
    private TextField usernameField;

    @FXML
    Button buttonRegister;

    accountClass initialAccount = new accountClass("src/main/java/com/example/wesgeosys/accountData.json");
    boolean isValidUser = false;

    /**
     * Initializes the controller class and sets the action events for the buttons.
     *
     * @param url The URL to initialize the controller.
     * @param rb  The resource bundle to initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonRegister.setOnAction(this::submit);
        buttonLogin.setOnAction(this::login);
    }


    /**
     * Displays the login GUI window to allow users to enter their login credentials
     * and access the main maps system. If the user is already logged in, they are redirected
     * to the main maps system.
     *
     * @param event the ActionEvent that triggered the method call
     * @throws RuntimeException if an error occurs while loading the login GUI window
     */
    private void login(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
            Stage stage = new Stage();
            stage.setTitle("Login Page");
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the user clicks the submit button on the sign-up page.
     * It checks whether the entered username is valid by calling the checkValidUsername method of the initialAccount object.
     * If the username is valid, it creates a new user account by calling the createAccount method of the initialAccount object
     * with the entered username and password as arguments. It then loads the mainMapsGUI.fxml file and displays it in a new window.
     * If the username is not valid, it loads the signupInvalidGUI.fxml file and displays it in a new window.
     * If an IO or parse exception occurs, it prints the stack trace.
     *
     * @param event the event that triggered this method (submit button clicked)
     */
    private void submit(ActionEvent event) {
        try {
            if (initialAccount.checkValidUsername(usernameField.getText())) {
                isValidUser = true;
            }
            if (!isValidUser) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signupInvalidGUI.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
                    Stage stage = new Stage();
                    stage.setTitle("Sign Up Page");
                    stage.setScene(scene);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            try {
                initialAccount.createAccount(usernameField.getText(), passwordField.getText());
                mainMapsController.username = usernameField.getText();
                mainMapsController.adminAccess = false;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMapsGUI.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
                Stage stage = new Stage();
                stage.setTitle("Main Maps System");
                stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
