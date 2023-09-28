import com.example.wesgeosys.accountClass;
import com.example.wesgeosys.logController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * This class contains the JUnit tests for the logController class.
 */
public class logControllerTest {

    private logController controller;

    /**
     * Starts the login GUI for testing.
     * @param stage the stage for the login GUI
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginGUI.fxml"));
        Scene scene = new Scene(loader.load(), 322.0, 391.0);
        stage.setScene(scene);
        stage.show();
        controller = loader.getController();
    }

    /**
     * Sets up the test environment by loading the account data for testing.
     * @throws IOException
     * @throws ParseException
     */
    @BeforeEach
    public void setUp() throws IOException, ParseException {
        controller.accountNum = new accountClass("src/test/resources/accountData.json");
    }

    /**
     * Tests submitting the login form with valid credentials.
     */
    @Test
    public void testSubmitSelectionWithValidCredentials() {
        // test implementation
    }

    /**
     * Tests submitting the login form with invalid credentials.
     */
    @Test
    public void testSubmitSelectionWithInvalidCredentials() {
        // test implementation
    }

    /**
     * Tests signing up for a new account.
     */
    @Test
    public void testSignupSelection() {
        // test implementation
    }
}
