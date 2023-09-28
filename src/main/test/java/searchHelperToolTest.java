import com.example.wesgeosys.searchHelperTool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A JUnit test class to test the methods of the searchHelperTool class.
 */
public class searchHelperToolTest {

    private searchHelperTool helperTool;

    /**
     * Sets up the test fixture. This method is called before each test method is executed.
     * It creates an example JSON structure to use for testing and creates a new instance of the
     * searchHelperTool class for each test.
     */
    @BeforeEach
    void setUp() {
        // Create an example JSON structure to use for testing
        JSONObject building1 = new JSONObject();
        building1.put("Building", "Building 1");
        JSONArray floors1 = new JSONArray();
        JSONObject floor1 = new JSONObject();
        floor1.put("floor", "1st floor");
        floor1.put("imageFileName", "floor1.jpg");
        JSONArray pointsOfInterest1 = new JSONArray();
        JSONObject poi1 = new JSONObject();
        poi1.put("name", "Room 101");
        poi1.put("roomNum", "101");
        poi1.put("layerType", "room");
        poi1.put("xCord", "10");
        poi1.put("yCord", "20");
        pointsOfInterest1.add(poi1);
        floor1.put("pointsOfInterest", pointsOfInterest1);
        floors1.add(floor1);
        building1.put("floors", floors1);

        JSONObject building2 = new JSONObject();
        building2.put("Building", "Building 2");
        JSONArray floors2 = new JSONArray();
        JSONObject floor2 = new JSONObject();
        floor2.put("floor", "1st floor");
        floor2.put("imageFileName", "floor1.jpg");
        JSONArray pointsOfInterest2 = new JSONArray();
        JSONObject poi2 = new JSONObject();
        poi2.put("name", "Room 201");
        poi2.put("roomNum", "201");
        poi2.put("layerType", "room");
        poi2.put("xCord", "30");
        poi2.put("yCord", "40");
        pointsOfInterest2.add(poi2);
        floor2.put("pointsOfInterest", pointsOfInterest2);
        floors2.add(floor2);
        building2.put("floors", floors2);

        JSONArray allBuildings = new JSONArray();
        allBuildings.add(building1);
        allBuildings.add(building2);

        // Create a new instance of the searchHelperTool class for each test
        helperTool = new searchHelperTool(allBuildings, true);
    }

    /**
     * Tests the getCoordinates() method of the searchHelperTool class.
     * Creates a JSONObject representing a point of interest and verifies that the x and y coordinates
     * can be retrieved correctly using the getCoordinates() method.
     */
    @Test
    void testGetCoordinates() {
        JSONObject poi = new JSONObject();
        poi.put("xCord", "10");
        poi.put("yCord", "20");
        int x = helperTool.getCoordinates("X", poi);
        int y = helperTool.getCoordinates("Y", poi);
        Assertions.assertEquals(10, x);
        Assertions.assertEquals(20, y);
    }

    /**
     * Tests the getBuildingObject() method of the searchHelperTool class.
     * Verifies that the JSONObject for a building can be retrieved correctly using the getBuildingObject()
     * method.
     */
    @Test
    void testGetBuildingObject() {
        JSONObject building1 = helperTool.getBuildingObject("Building 1");
        JSONObject building2 = helperTool.getBuildingObject("Building 2");
        Assertions.assertNotNull(building1);
        Assertions.assertNotNull(building2);
        Assertions.assertEquals("Building 1", building1.get("Building"));
        Assertions.assertEquals("Building 2", building2.get("Building"));
    }

    /**
     * Tests the testGetBuildingIndex() method of the searchHelperTool class.
     * Verifies that the JSONObject for a building can be retrieved correctly using the getBuildingObject()
     * method.
     */
    @Test
    void testGetBuildingIndex() {
        String building1 = helperTool.getBuildingIndex(0);
        String building2 = helperTool.getBuildingIndex(1);
        Assertions.assertEquals("Building 1", building1);
        Assertions.assertEquals("Building 2", building2);
    }
}