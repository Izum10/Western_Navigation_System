package com.example.wesgeosys;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Objects;

/**
 * The searchHelperTool class provides helper methods for searching through JSON data structures containing information about buildings, floors, and points of interest (POI).
 * This class is designed to be used in conjunction with other classes and methods that work with JSON data in a similar format.
 */
public class searchHelperTool {
    JSONObject temporaryObject;
    Boolean adminAccess;
    JSONArray entireStructure;

    /**
     * This method returns the X or Y coordinate of a given point of interest (POI) as an integer.
     *
     * @param XY  The coordinate axis to retrieve. Must be "X" or "Y".
     * @param poi The JSONObject containing information about the POI.
     * @return An integer value representing the coordinate value.
     */
    public int getCoordinates(String XY, JSONObject poi) {
        String key = XY.equals("X") ? "xCord" : "yCord";
        String string = poi.get(key).toString();
        double a = Integer.parseInt(string);
        int i= (int)Math.floor(a);
        return i;
    }

    /**
     * This constructor method initializes a new searchHelperTool object with the given JSONArray containing all of the JSON data being searched, and the specified admin access level.
     *
     * @param allBuildings The JSONArray containing all of the JSON data being searched.
     * @param admin        The admin access level. True if the user has administrative access, false otherwise.
     */
    public searchHelperTool(JSONArray allBuildings, Boolean admin) {
        this.entireStructure = allBuildings;
        this.adminAccess = admin;
    }

    /**
     * This method returns the JSONObject containing information about a specified building.
     *
     * @param buildName The name of the building to retrieve.
     * @return A JSONObject containing information about the specified building, or null if the building is not found.
     */
    public JSONObject getBuildingObject(String buildName) {
        for (int n = 0; n < entireStructure.size(); n++) {
            temporaryObject = (JSONObject) entireStructure.get(n);
            if (temporaryObject.get("Building").equals(buildName)) {
                return temporaryObject;
            }
        }
        return null;
    }

    /**
     * This method returns the name of the building at the specified index value in the entireStructure JSONArray.
     *
     * @param indexValue The index value of the building to retrieve.
     * @return A String value representing the name of the specified building.
     */
    public String getBuildingIndex(int indexValue) {
        return ((JSONObject) entireStructure.get(indexValue)).get("Building").toString();
    }

    /**
     * This method returns the index value of a specified point of interest (POI) within a given JSONArray.
     *
     * @param poiList The JSONArray containing the list of POIs to search.
     * @param name    The name of the POI to search for.
     * @param roomNum The room number of the POI to search for.
     * @return An integer value representing the index of the specified POI within the poiList JSONArray, or -1 if the POI is not found.
     */
    public int getPointOfInterestIndex(JSONArray poiList, String name, String roomNum) {
        for (int n = 0; n < poiList.size(); n++) {
            temporaryObject = (JSONObject) poiList.get(n);
            String poiName = temporaryObject.get("name").toString();
            String poiRoomNum = temporaryObject.get("roomNum").toString();

            if (roomNum==null&& poiName.equals(name))
                return n;
                else
            if ((poiName.equals(name) || ("(User)" + poiName).equals(name)) && poiRoomNum.equals(roomNum)) {
                return n;
            }
        }
        return -1;
    }

    /**
     * Finds all points of interest in the current floor with a specific layer type.
     * @param currFloor the current floor being searched for points of interest
     * @param layerType the type of layer to look for in the points of interest
     * @return a JSONArray containing all points of interest with the specified layer type
     */
    public JSONArray findAllLayerKind(JSONObject currFloor, String layerType) {
        JSONArray floorsArray = (JSONArray) currFloor.get("pointsOfInterest");
        JSONArray returnList = new JSONArray();
        JSONObject temporaryObject;
        String temporaryString = "";
        for (int n = 0; n < floorsArray.size(); n++) {
            temporaryObject = (JSONObject) floorsArray.get(n);
            temporaryString = temporaryObject.get("layerType").toString();
            if (temporaryString.equals(layerType) == true) {
                returnList.add(temporaryObject);
            }
        }
        return returnList;
    }

    /**
     * Searches for the filename of an image for a specific building and floor number.
     * @param buildName the name of the building to search for
     * @param floorNum the number of the floor to search for
     * @return the filename of the image for the specified building and floor number
     */
    public String searchImage(String buildName, int floorNum) {
        temporaryObject = (JSONObject) getBuildingObject(buildName);
        JSONArray floorsArray = (JSONArray) temporaryObject.get("floors");
        temporaryObject = (JSONObject) floorsArray.get(floorNum);
        return temporaryObject.get("imageFileName").toString();
    }
}

