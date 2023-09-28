import com.example.wesgeosys.editTool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;


/**
 * A class to test the functionality of the {@link editTool} class.
 */
public class editToolTest {

    editTool tool;


    /**
     * Sets up the test environment by loading the initial data from a JSON file.
     */
    @BeforeEach
    void setUp() {
        // Load initial data for testing
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/wesgeosys/buildingData.json")) {
            JSONArray buildings = (JSONArray) jsonParser.parse(reader);
            tool = new editTool(buildings, "test_user", true);
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("ParseException");
        }
    }

    /**
     * Tests the functionality of the {@link editTool#createBuilding(String)} method.
     */
    @Test
    void testCreateBuilding() {
        String buildName = "Test Building";
        int initialSize = tool.buildingData.size();
        tool.createBuilding(buildName);
        int finalSize = tool.buildingData.size();
        Assertions.assertEquals(initialSize + 1, finalSize);
        JSONObject createdBuilding = (JSONObject) tool.buildingData.get(finalSize - 1);
        Assertions.assertEquals(buildName, createdBuilding.get("Building"));
    }

    /**
     * Tests the functionality of the {@link editTool#modifyBuilding(JSONObject, String)} method.
     */
    @Test
    void testModifyBuilding() {
        String oldName = "Test Building";
        String newName = "Modified Building";
        tool.createBuilding(oldName);
        JSONObject currentBuilding = (JSONObject) tool.buildingData.get(tool.buildingData.size() - 1);
        tool.modifyBuilding(currentBuilding, newName);
        JSONObject modifiedBuilding = (JSONObject) tool.buildingData.get(tool.buildingData.size() - 1);
        Assertions.assertEquals(newName, modifiedBuilding.get("Building"));
    }

    /**
     * Tests the functionality of the {@link editTool#deleteBuilding(String)} method.
     */
    @Test
    void testDeleteBuilding() {
        String buildName = "Test Building";
        tool.createBuilding(buildName);
        int initialSize = tool.buildingData.size();
        tool.deleteBuilding(buildName);
        int finalSize = tool.buildingData.size();
        Assertions.assertEquals(initialSize - 1, finalSize);
    }

    /**
     * Tests the functionality of the {@link editTool#createFloor(JSONObject, String)} method.
     */
    @Test
    void testCreateFloor() {
        String imageName = "Test Image.jpg";
        String buildName = "Test Building";
        tool.createBuilding(buildName);
        JSONObject currentBuilding = (JSONObject) tool.buildingData.get(tool.buildingData.size() - 1);
        int initialSize = ((JSONArray) currentBuilding.get("floors")).size();
        tool.createFloor(currentBuilding, imageName);
        int finalSize = ((JSONArray) currentBuilding.get("floors")).size();
        Assertions.assertEquals(initialSize + 1, finalSize);
        JSONObject createdFloor = (JSONObject) ((JSONArray) currentBuilding.get("floors")).get(finalSize - 1);
        Assertions.assertEquals(imageName, createdFloor.get("imageFileName"));
    }

    /**
     * Tests the functionality of the {@link editTool#deleteFloor(JSONArray, int)} Floor(JSONObject, String)} method.
     */
    @Test
    void testDeleteFloor() {
        String imageName = "Test Image.jpg";
        String buildName = "Test Building";
        tool.createBuilding(buildName);
        JSONObject currentBuilding = (JSONObject) tool.buildingData.get(tool.buildingData.size() - 1);
        tool.createFloor(currentBuilding, imageName);
        int initialSize = ((JSONArray) currentBuilding.get("floors")).size();
        tool.deleteFloor((JSONArray) currentBuilding.get("floors"), initialSize - 1);
        int finalSize = ((JSONArray) currentBuilding.get("floors")).size();
        Assertions.assertEquals(initialSize - 1, finalSize);
    }
}


