import com.example.wesgeosys.signupController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class contains JUnit tests for the SignupController class.
 */
public class signupControllerTest {

    /**
     * Tests the submit method of SignupController.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void testSubmit() throws IOException {
        signupController controller = new signupController();

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signupGUI.fxml"));
        Parent root = loader.load();

        // Set the controller manually
        loader.setController(controller);

        // Create a new scene and set it on a stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Find the username and password fields, and the submit button
        TextField usernameField = (TextField) scene.lookup("#usernameField");
        TextField passwordField = (TextField) scene.lookup("#passwordField");
        Button submitButton = (Button) scene.lookup("#buttonRegister");

        // Set some text in the fields
        usernameField.setText("testuser");
        passwordField.setText("testpassword");

        // Click the submit button
        submitButton.fire();

        // Check that the account was created and the mainMapsGUI was loaded

    }

    /**
     * Tests the login method of SignupController.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void testLogin() throws IOException {
        signupController controller = new signupController();

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signupGUI.fxml"));
        Parent root = loader.load();

        // Set the controller manually
        loader.setController(controller);

        // Create a new scene and set it on a stage
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Find the login button
        Button loginButton = (Button) scene.lookup("#buttonLogin");

        // Click the login button
        loginButton.fire();

        // Check that the login GUI was loaded
        Scene loginScene = stage.getScene();
        assertNotNull(loginScene);
        assertEquals("Login Page", stage.getTitle());
    }
}
