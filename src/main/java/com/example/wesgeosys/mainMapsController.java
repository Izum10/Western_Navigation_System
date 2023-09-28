package com.example.wesgeosys;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.wesgeosys.addBuildingController.buidingName;
import static com.example.wesgeosys.addPOIController.newName;
import static com.example.wesgeosys.editPOIController.*;

/**
 * The controller for the main maps view.
 */
public class mainMapsController {
    /**
     * A list of all image icons used on the map.
     */
    private static List<ImageView> imageIcons = new ArrayList<>();

    @FXML
    private AnchorPane adminPanel;
    @FXML
    private ComboBox favDropdown;
    @FXML
    private ComboBox floorsDropdown;
    @FXML
    private ImageView mapDisplay;
    @FXML
    private ComboBox layersDropdown;
    @FXML
    private ComboBox mapsDropdown;
    @FXML
    private ComboBox poiDropdown;

    /**
     * The file path to the map images.
     */
    private String mapFilePath = "src/main/java/com/example/wesgeosys/mapImages/";
    /**
     * The index of the current floor.
     */
    private int currentFloorIndex;

    public accountClass user;
    public JSONArray buildingDataFile;
    public JSONObject currentBuildingData;
    public JSONObject currentFloor;
    public JSONArray currentPOIList;
    public JSONObject currentPOI;
    public editTool editHelper;
    public static Boolean adminAccess;
    public static String username;
    public JSONArray userFileData;
    public JSONObject userInstance;
    public searchHelperTool searchUtility;

    @FXML
    private Label currentTemperature;
    @FXML
    private Label descriptionText;
    @FXML
    private Label feelsLikeTemperature;
    @FXML
    private Label highTemperature;
    @FXML
    private Label lowTemperature;

    EventHandler<ActionEvent> favDropdownHandler;
    EventHandler<ActionEvent> floorsDropdownHandler;
    EventHandler<ActionEvent> layerDropdownHandler;
    EventHandler<ActionEvent> poiDropdownHandler;

    private Stage popupPane;

    /**
     * Whether or not an add POI icon is active.
     */
    boolean addPOIIcon = false;
    /**
     * Whether or not a placeable icon is active.
     */
    boolean placeableIcon = false;
    /**
     * The currently placed icon.
     */
    ImageView placedIcon;

    double mapOffsetX = 140;
    double mapOffsetY = 10;
    double mapSizeX = 1398;
    double mapSizeY = 805;
    double xCoordinate;
    double yCoordinate;


    /**
     * Displays the help pop-up window with information on how to use the application.
     */
    @FXML
    protected void handleOpenHelp() {
        try {
            popupPane = new Stage();
            Parent root = new FXMLLoader(mainMapsController.class.getResource("helpPopGUI.fxml")).load();
            Scene scene = new Scene(root, 625, 500);
            popupPane.setTitle("Help");
            popupPane.setScene(scene);
            popupPane.show();
        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * Handles the action triggered when a point of interest (POI) is selected.
     * Clears all icons, disables the layers and favorites dropdown, sets their values to empty, and re-enables them.
     * Finds the selected POI's index in the current POI list and sets the current POI to the one at that index.
     * Loads and displays the icon image corresponding to the selected POI's layer type, and adds it to the admin panel.
     * If the selected POI has a description, sets the description text to it.     *
     *
     * @throws FileNotFoundException if the icon image file for the selected POI cannot be found
     */
    @FXML
    protected void handlePOIAction() {
        clearAllIcons();
        layersDropdown.setOnAction(null);
        favDropdown.setOnAction(null);
        layersDropdown.setValue("");
        favDropdown.setValue("");
        layersDropdown.setOnAction(layerDropdownHandler);
        favDropdown.setOnAction(favDropdownHandler);
        int  indexVal;
        if (poiDropdown.getValue() != null) {
            String string = poiDropdown.getValue().toString();
            if (string.contains(":")){
            String[] poiData = string.split(":");
             indexVal = searchUtility.getPointOfInterestIndex(currentPOIList, poiData[0], poiData[1]);}
            else
                indexVal = searchUtility.getPointOfInterestIndex(currentPOIList,newName,null);
            System.out.println(indexVal);
            currentPOI = (JSONObject) currentPOIList.get(indexVal);
            System.out.println(currentPOI.toString());
            String layerType = currentPOI.get("layerType").toString();
            try {
                String imageName = "Icon Image - " + layerType + ".png";
                String imagePath = "src/main/java/com/example/wesgeosys/iconImages/" + imageName;
                ImageView displayImage = new ImageView(new Image(new FileInputStream(imagePath)));
                displayImage.setPreserveRatio(true);
                displayImage.setFitWidth(30);
                displayImage.setX(((searchUtility.getCoordinates("X", (JSONObject) currentPOI) / 3400.0) * mapSizeX) + mapOffsetX - 15.0);
                displayImage.setY(((searchUtility.getCoordinates("Y", (JSONObject) currentPOI) / 2200.0) * mapSizeY) + mapOffsetY - 15.0);
                adminPanel.getChildren().add(displayImage);
                imageIcons.add(displayImage);
                Object desc = currentPOI.get("description");
                if (desc != null) {
                    descriptionText.setText(desc.toString());
                }
            } catch (FileNotFoundException e) {
                displayError(e);
            }
        }
    }

    /**
     * Handles the event when a favorite point of interest is selected.
     * Clears all the existing icons and sets the event handlers for the layers and poi dropdowns.
     * Gets the current point of interest based on the selected favorite and its index in the current point of interest list.
     * Sets the icon image based on the layer type of the current point of interest and displays it on the admin panel.
     *
     * @throws FileNotFoundException if the icon image file is not found
     */
    @FXML
    protected void handleFavAction() {
        clearAllIcons();
        layersDropdown.setOnAction(null);
        poiDropdown.setOnAction(null);
        layersDropdown.setValue("");
        poiDropdown.setValue("");
        layersDropdown.setOnAction(layerDropdownHandler);
        poiDropdown.setOnAction(poiDropdownHandler);
        if (favDropdown.getValue() != null) {
            String string = favDropdown.getValue().toString();
            String[] poiData = string.split(":");
            int valueIndex = searchUtility.getPointOfInterestIndex(currentPOIList, poiData[0], poiData[1]);
            currentPOI = (JSONObject) currentPOIList.get(valueIndex);
            String layerType = currentPOI.get("layerType").toString();
            try {
                String imageName = "Icon Image - " + layerType + ".png";
                String imagePath = "src/main/java/com/example/wesgeosys/iconImages/" + imageName;
                ImageView displayImage = new ImageView(new Image(new FileInputStream(imagePath)));
                displayImage.setPreserveRatio(true);
                displayImage.setFitWidth(30);
                displayImage.setX(((searchUtility.getCoordinates("X", (JSONObject) currentPOI) / 3400.0) * mapSizeX) + mapOffsetX - 15.0);
                displayImage.setY(((searchUtility.getCoordinates("Y", (JSONObject) currentPOI) / 2200.0) * mapSizeY) + mapOffsetY - 15.0);
                adminPanel.getChildren().add(displayImage);
                imageIcons.add(displayImage);
            } catch (FileNotFoundException e) {
                displayError(e);
            }
        }
    }

    /**
     * Handles the action when the user selects a layer from the layers dropdown menu.
     * Clears all icons and resets dropdown menus for poi and fav.
     * Sets the value of the poi and fav dropdown menus to empty strings.
     * Resets the event handlers for poi and fav dropdown menus.
     * Determines the layer type based on the selected value from the layers dropdown menu.
     * Retrieves the list of layers for the current floor and the selected layer type.
     * Adds the corresponding icon images to the admin panel based on the coordinates of each layer.
     *
     * @throws FileNotFoundException if the icon image file for the selected layer type cannot be found
     */
    @FXML
    protected void handleLayersAction() {
        clearAllIcons();
        poiDropdown.setOnAction(null);
        favDropdown.setOnAction(null);
        poiDropdown.setValue("");
        favDropdown.setValue("");
        poiDropdown.setOnAction(poiDropdownHandler);
        favDropdown.setOnAction(favDropdownHandler);
        String layerType = layersDropdown.getValue() != null ? layersDropdown.getValue().toString() : "Default";
        JSONArray layerList = searchUtility.findAllLayerKind(currentFloor, layerType);
        try {
            for (Object obj : layerList) {
                JSONObject layer = (JSONObject) obj;
                String imageName = "Icon Image - " + layerType + ".png";
                String imagePath = "src/main/java/com/example/wesgeosys/iconImages/" + imageName;
                ImageView displayView = new ImageView(new Image(new FileInputStream(imagePath)));
                displayView.setPreserveRatio(true);
                displayView.setFitWidth(30);
                displayView.setX(((searchUtility.getCoordinates("X", layer) / 3400.0) * mapSizeX) + mapOffsetX - 15.0);
                displayView.setY(((searchUtility.getCoordinates("Y", layer) / 2200.0) * mapSizeY) + mapOffsetY - 15.0);
                adminPanel.getChildren().add(displayView);
                imageIcons.add(displayView);
            }
        } catch (FileNotFoundException e) {
            displayError(e);
        }
    }

    /**
     * Handles the action when the user selects a new floor from the dropdown list.
     * Updates the current floor index, loads the corresponding floor map image, resets the POI dropdown list,
     * updates the list with POIs on the selected floor, and triggers the layers dropdown action handler.
     *
     * @throws FileNotFoundException if the map image file for the selected layer type cannot be found
     */
    @FXML
    protected void handleFloorsAction() {
        this.currentFloorIndex = Integer.parseInt(floorsDropdown.getValue().toString()) - 1;
        JSONArray temporaryArray = (JSONArray) currentBuildingData.get("floors");
        currentFloor = (JSONObject) temporaryArray.get(currentFloorIndex);
        String imageName = searchUtility.searchImage(currentBuildingData.get("Building").toString(), Integer.parseInt(floorsDropdown.getValue().toString()) - 1);
        try {
            mapDisplay.setImage(new Image(new FileInputStream(mapFilePath + imageName)));
            resetComboBox(poiDropdown);
            JSONObject temporaryObject = (JSONObject) temporaryArray.get(currentFloorIndex);
            currentPOIList = (JSONArray) temporaryObject.get("pointsOfInterest");
            for (int n = 0; n < currentPOIList.size(); n++) {
                temporaryObject = (JSONObject) currentPOIList.get(n);
                poiDropdown.getItems().add(temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
            }
            handleLayersAction();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: FILE NOT FOUND");
        }
    }

    /**
     * Handles the toggle favourites action. If a point of interest is currently selected and it is marked as a favourite,
     * it will be unmarked as a favourite and removed from the favourites dropdown. If a point of interest is currently selected
     * and it is not marked as a favourite, it will be marked as a favourite and added to the favourites dropdown. If no point of
     * interest is currently selected, it will print a message indicating that a point of interest is not selected.
     */
    @FXML
    protected void handleToggleFavourites() {
        if (currentPOI == null) {
            System.out.println("POI NOT SELECTED");
            return;
        }
        boolean isFavourite = currentPOI.get("favourite").equals(true);
        editHelper.toggleFavourite(currentBuildingData.get("Building").toString(), currentFloorIndex, currentPOI, !isFavourite);
        if (isFavourite) {
            favDropdown.getItems().remove(currentPOI);
        } else {
            String favDropdownItem = (currentPOI.get("builtInPOI").equals(true))
                    ? currentPOI.get("name") + ":" + currentPOI.get("roomNum")
                    : "(User)" + currentPOI.get("name") + ":" + currentPOI.get("roomNum");
            favDropdown.getItems().add(favDropdownItem);
        }
    }

    /**
     * Handles the action when the user selects a map from the maps dropdown menu.
     * Clears all icons and resets dropdown menus for layers, poi and fav.
     * Sets the value of the layers, poi, and fav dropdown menus to empty strings.
     * Resets the event handlers for layers, poi, and fav dropdown menus.
     * Retrieves the image for the selected map and displays it on the map display.
     * Retrieves the current building data and the current floor data for the selected map.
     * Populates the floors dropdown menu with the number of floors available for the selected map.
     * Populates the poi dropdown menu with the points of interest for the first floor of the selected map.
     * Calls the handleLayersAction method to display the appropriate icons for the selected floor.
     *
     * @throws FileNotFoundException if the image for the selected map cannot be found
     */
    @FXML
    protected void handleMapsAction() {
        clearAllIcons();
        layersDropdown.setOnAction(null);
        poiDropdown.setOnAction(null);
        favDropdown.setOnAction(null);
        layersDropdown.setValue("");
        poiDropdown.setValue("");
        favDropdown.setValue("");
        layersDropdown.setOnAction(floorsDropdownHandler);
        poiDropdown.setOnAction(poiDropdownHandler);
        favDropdown.setOnAction(favDropdownHandler);
        String imageName = searchUtility.searchImage(mapsDropdown.getValue().toString(), 0);
        this.currentBuildingData = searchUtility.getBuildingObject(mapsDropdown.getValue().toString());
        JSONArray temporaryArray = (JSONArray) currentBuildingData.get("floors");
        this.currentFloor = (JSONObject) temporaryArray.get(0);
        try {
            mapDisplay.setImage(new Image(new FileInputStream(mapFilePath + imageName)));
            int val = poiDropdown.getItems().size();
            for (int initialVal = 0; initialVal < val; initialVal++) {
                poiDropdown.getItems().remove(0);
            }
            floorsDropdown.getItems().clear();
            for (int initialVal = 1; initialVal <= temporaryArray.size(); initialVal++) {
                floorsDropdown.getItems().add(initialVal);
            }
            floorsDropdown.setValue("1");
            JSONObject temporaryObject = (JSONObject) temporaryArray.get(0);
            currentPOIList = (JSONArray) temporaryObject.get("pointsOfInterest");
            poiDropdown.getItems().clear();
            for (int initialVal2 = 0; initialVal2 < currentPOIList.size(); initialVal2++) {
                temporaryObject = (JSONObject) currentPOIList.get(initialVal2);
                if ((Boolean) temporaryObject.get("builtInPOI")) {
                    poiDropdown.getItems().add(temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
                } else {
                    poiDropdown.getItems().add("(User)" + temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
                }
            }
            handleLayersAction();
        } catch (FileNotFoundException e) {
            displayError(e);
        } finally {
            floorsDropdown.setOnAction(floorsDropdownHandler);
        }
    }

    /**
     * Merges the user's points of interest and favourite list with the building data file.
     * It loops through each user point of interest and favourite, and checks if the building name and
     * floor number match the building data file. If a match is found, it adds the point of interest
     * to the corresponding floor's points of interest list. If a match is found in the favourite list,
     * it calls the favouriteToggle method in the editHelper object with the building name, floor number,
     * and point of interest to mark it as a favourite.
     *
     * @throws ClassCastException    if userPOIs or favourites cannot be cast to JSONArray
     * @throws NumberFormatException if floorNum cannot be parsed to an integer
     */
    protected void mergeJSON() {
        JSONArray userPOIs = (JSONArray) userInstance.get("userPOIs");
        JSONArray favouriteList = (JSONArray) userInstance.get("favourites");
        for (Object obj : userPOIs) {
            JSONObject userPoi = (JSONObject) obj;
            for (Object buildingObj : buildingDataFile) {
                JSONObject building = (JSONObject) buildingObj;
                if (userPoi.get("building").equals(building.get("Building").toString())) {
                    JSONArray floors = (JSONArray) building.get("floors");
                    JSONObject floor = (JSONObject) floors.get(Integer.parseInt(userPoi.get("floorNum").toString()));
                    JSONArray poiList = (JSONArray) floor.get("pointsOfInterest");
                    editHelper.createPOI(poiList);
                }
            }
        }
        for (Object obj : favouriteList) {
            JSONObject fav = (JSONObject) obj;
            for (Object buildingObj : buildingDataFile) {
                JSONObject building = (JSONObject) buildingObj;
                if (fav.get("building").equals(building.get("Building").toString())) {
                    JSONArray floors = (JSONArray) building.get("floors");
                    JSONObject floor = (JSONObject) floors.get(Integer.parseInt(fav.get("floorNum").toString()));
                    JSONArray poiList = (JSONArray) floor.get("pointsOfInterest");
                    for (Object poiObj : poiList) {
                        JSONObject poi = (JSONObject) poiObj;
                        if (poi.get("name").equals(fav.get("name")) && poi.get("roomNum").equals(fav.get("roomNum"))) {
                            editHelper.toggleFavourite(building.get("Building").toString(), floors.indexOf(floor), poi, true);
                        }
                    }
                }
            }
        }
    }




    /**
     * Launches the "edit building" GUI, allowing the user to modify the details of a building.
     * This method loads the "editBuildingGUI.fxml" file using an FXMLLoader object and sets the
     * loaded scene on a new Stage object. The new stage is then displayed in a modal fashion using
     * the showAndWait() method. Once the user has finished editing the building details, the
     * editBuilding() method of the editHelper object is called with the currentBuildingData and
     * buildingName parameters to update the building data.
     *
     * @throws RuntimeException if an IOException occurs when loading the "editBuildingGUI.fxml"
     */
    @FXML
    protected void modifyBuilding() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editBuildingGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
            Stage stage = new Stage();
            stage.setTitle("Edit Building");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editHelper.modifyBuilding(currentBuildingData, buidingName);
    }

    /**
     * Inserts a new point of interest (POI) into the application's data model. This method first
     * displays an alert message to the user using the displayAlert() method. It then sets the addPOIIcon
     * flag to true to indicate that a new POI icon should be added to the map. The method then adds the
     * new POI to the appropriate data structures depending on the user's access level. If the user has
     * admin access, the POI is added only to the currentPOIList, and its name and room number are added
     * to the poiDropdown menu for selection. If the user does not have admin access, the POI is added
     * to both the currentPOIList and the user's personal list of POIs (userPOIs), and the user's
     * personal list is updated to include the new POI. The POI is also added to the poiDropdown menu
     * with "(User)" prefix added to indicate that it is a personal POI.
     */
    @FXML
    protected void insertPOI() {
        displayAlert();
        addPOIIcon = true;
    }

    /**
     * Adds a new floor to the current building data by calling the addFloor() method from the
     * editHelper object. The new floor is assigned a default file name of "DefaultFile.png". The method
     * then adds a new item to the floorsDropdown menu with the number of items in the menu plus one as
     * the label, to represent the new floor that was added.
     */
    @FXML
    protected void insertFloor() {
        editHelper.createFloor(currentBuildingData, "DefaultFile.png");
        floorsDropdown.getItems().add(floorsDropdown.getItems().size() + 1);
    }

    /**
     * Deletes the current floor from the current building data by calling the removeFloor() method
     * from the editHelper object. The method then removes the last item from the floorsDropdown menu,
     * as this item represents the deleted floor. The current floor is then updated to the first floor
     * in the building, and the image for this floor is loaded and displayed in the mapDisplay ImageView.
     * If the image file for the floor cannot be found, the method prints an error message to the console.
     */
    @FXML
    protected void deleteFloor() {
        JSONArray temporaryArray = (JSONArray) currentBuildingData.get("floors");
        editHelper.deleteFloor(temporaryArray, currentFloorIndex);
        floorsDropdown.getItems().remove(floorsDropdown.getItems().size() - 1);
        temporaryArray = (JSONArray) currentBuildingData.get("floors");
        currentFloor = (JSONObject) temporaryArray.get(0);
        try {
            mapDisplay.setImage(new Image(new FileInputStream(mapFilePath + currentFloor.get("imageFileName").toString())));
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /**
     * Prompts the user to modify the current point of interest (POI) by calling the clearPOI() method.
     * If this method returns true, the method exits without modifying the POI. Otherwise, the method
     * displays an alert to the user and sets the placeableIcon variable to true to indicate that a new
     * icon can be placed on the map. This allows the user to modify the current POI by clicking on the
     * map at a new location.
     *
     * @throws IOException if there is an error reading the editPOIGUI.fxml file
     */
    @FXML
    protected void modifyPOI() throws IOException {
        if (clearPOI()) {
            return;
        }
        displayAlert();
        placeableIcon = true;
    }

    /**
     * Displays an alert page to the user by loading the alertPageGUI.fxml file using the FXMLLoader
     * class. The method creates a new stage with the title "Alert Page" and sets the scene to the loaded
     * FXML scene. The alert page is displayed to the user using the showAndWait() method of the Stage class.
     * If there is an error loading the FXML file, the method throws a RuntimeException with the error message.
     */
    private void displayAlert() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alertPageGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            Stage stage = new Stage();
            stage.setTitle("Alert Page");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the add building GUI and adds the building name to the list of buildings.
     *
     * @throws RuntimeException if an IOException occurs while loading the addBuildingGUI.fxml file
     */
    @FXML
    protected void insertBuilding() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBuildingGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
            Stage stage = new Stage();
            stage.setTitle("Add Building");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editHelper.createBuilding(buidingName);
        mapsDropdown.getItems().add(buidingName);
    }

    /**
     * Displays a pop-up window for editing a Point of Interest (POI).
     * Retrieves user input and updates the POI accordingly.
     * Resets the POI dropdown menu with updated information.
     * Removes the placed POI icon from the map.
     * If there is no current POI selected, it prints an error message to the console.
     */
    private void modifyPOIPopout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editPOIGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
            Stage stage = new Stage();
            stage.setTitle("Edit POI");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (currentPOI != null) {
            String nameInput = currentPOI.get("name").toString();
            String descriptionInput = currentPOI.get("description").toString();
            String roomNumInput = currentPOI.get("roomNum").toString();
            String layerTypeInput = currentPOI.get("layerType").toString();
            int newXCoordinate = (int) xCoordinate;
            int newYCoordinate = (int) yCoordinate;
            int xInput = Integer.parseInt(currentPOI.get("xCord").toString());
            int yInput = Integer.parseInt(currentPOI.get("yCord").toString());
            System.out.println(newXCoordinate);
            System.out.println(newYCoordinate);
            System.out.println(updatePOIDesc);
            System.out.println(updateRoomNum);
            System.out.println(updateName);
            System.out.println(player);
            editHelper.modifyPOI(currentPOI, updateName, updatePOIDesc, newXCoordinate, newYCoordinate, updateRoomNum, player);
            resetComboBox(poiDropdown);
            JSONObject temporaryObject;
            for (int n = 0; n < currentPOIList.size(); n++) {
                temporaryObject = (JSONObject) currentPOIList.get(n);
                poiDropdown.getItems().add(temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
            }
        } else {
            System.out.println("NO POI SELECTED");
        }
        adminPanel.getChildren().remove(placedIcon);
        placeableIcon = false;
    }

    /**
     * Removes the currently selected building from the application.
     * Removes building from editHelper and removes the building from the map dropdown.
     * Sets the currentBuildingData to the first building in the list and updates the currentFloor.
     * Sets the map display to the image of the first floor in the building.
     */
    @FXML
    protected void deleteBuilding() {
        editHelper.deleteBuilding((String) currentBuildingData.get("Building"));
        mapsDropdown.getItems().remove(currentBuildingData.get("Building"));
        currentBuildingData = (JSONObject) buildingDataFile.get(0);
        JSONArray temporaryArray = (JSONArray) currentBuildingData.get("floors");
        currentFloor = (JSONObject) temporaryArray.get(0);
        String imageName = currentFloor.get("imageFileName").toString();
        try {
            mapDisplay.setImage(new Image(new FileInputStream(mapFilePath + imageName)));
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        }
    }

    /**
     * Removes the currently selected point of interest from the application.
     * If the point of interest is a user-created POI, also removes it from the user's list of POIs.
     * Removes the POI from editHelper and from the poiDropdown.
     * If no POI is selected, prints a message to the console.
     */
    @FXML
    protected void deletePOI() {
        if (clearPOI()) {
        } else {
            if (currentPOI != null) {
                clearAllIcons();
                if (adminAccess) {
                    editHelper.deletePOI(currentPOIList, currentPOI);
                    poiDropdown.getItems().remove(currentPOI.get("name") + ":" + currentPOI.get("roomNum"));
                    currentPOI = null;
                } else {
                    editHelper.deletePOI(currentPOIList, currentPOI);
                    editHelper.deletePOI((JSONArray) userInstance.get("userPOIs"), currentPOI);
                    editHelper.deleteUserPOI(currentPOI);
                    poiDropdown.getItems().remove("(User)" + currentPOI.get("name") + ":" + currentPOI.get("roomNum"));
                    currentPOI = null;
                }
            } else {
                System.out.println("NO POI SELECTED");
            }
        }

    }

    /**
     * Clears all the icons on the admin panel.
     */
    private void clearAllIcons() {
        for (ImageView icon : imageIcons) {
            adminPanel.getChildren().remove(icon);
        }
    }

    /**
     * Resets the given ComboBox by removing all items from it.
     *
     * @param cBox the ComboBox to be reset
     */
    private void resetComboBox(ComboBox cBox) {
        int valNum = cBox.getItems().size();
        for (int initialVal = 0; initialVal < valNum; initialVal++) {
            cBox.getItems().remove(0);
        }
    }

    /**
     * Checks if the POI dropdown menu has a selected value.
     *
     * @return true if the POI dropdown menu has no selected value, false otherwise
     */
    private boolean clearPOI() {
        return poiDropdown.getValue() == null;
    }


    /**
     * Initializes the JavaFX GUI elements and sets up event handlers. Also loads data from JSON files.
     *
     * @throws FileNotFoundException if any of the JSON files cannot be found
     */
    @FXML
    public void initialize() throws FileNotFoundException {
        floorsDropdownHandler = floorsDropdown.getOnAction();
        poiDropdownHandler = poiDropdown.getOnAction();
        favDropdownHandler = favDropdown.getOnAction();
        layerDropdownHandler = layersDropdown.getOnAction();
        floorsDropdown.setValue("1");
        mapDisplay.setOnMouseClicked(e -> {
            try {
                handleMouseDown(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.currentFloorIndex = 0;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/wesgeosys/defaultPOIArchive.json")) {
            try (FileReader accountReader = new FileReader("src/main/java/com/example/wesgeosys/accountData.json")) {
                JSONObject currentBuilding = (JSONObject) jsonParser.parse(reader);
                searchUtility = new searchHelperTool((JSONArray) currentBuilding.get("buildings"), adminAccess);
                this.buildingDataFile = (JSONArray) currentBuilding.get("buildings");
                this.currentBuildingData = (JSONObject) buildingDataFile.get(0);
                JSONArray temporaryArray = (JSONArray) currentBuildingData.get("floors");
                this.currentFloor = (JSONObject) temporaryArray.get(currentFloorIndex);
                searchHelperTool search = new searchHelperTool(buildingDataFile, adminAccess);
                this.editHelper = new editTool(buildingDataFile, username, adminAccess);
                this.userFileData = (JSONArray) jsonParser.parse(accountReader);
                for (int q = 0; q < userFileData.size(); q++) {
                    JSONObject temporaryObject = (JSONObject) userFileData.get(q);
                    if (temporaryObject.get("username") == null) {
                    } else if (temporaryObject.get("username").toString().equals(username)) {
                        this.userInstance = temporaryObject;
                    }
                }
                mergeJSON();
                this.currentPOIList = (JSONArray) currentFloor.get("pointsOfInterest");
                for (int n = 0; n < buildingDataFile.size(); n++) {
                    mapsDropdown.getItems().add(search.getBuildingIndex(n));
                }
                for (int n = 0; n < currentPOIList.size(); n++) {
                    JSONObject tmp = (JSONObject) currentPOIList.get(n);
                    if ((Boolean) tmp.get("builtInPOI")) {
                        poiDropdown.getItems().add(tmp.get("name") + ":" + tmp.get("roomNum"));
                    } else {
                        poiDropdown.getItems().add("(User)" + tmp.get("name") + ":" + tmp.get("roomNum"));
                    }
                }
                temporaryArray = (JSONArray) userInstance.get("favourites");
                JSONObject temporaryObject;
                for (int n = 0; n < temporaryArray.size(); n++) {
                    temporaryObject = (JSONObject) temporaryArray.get(n);
                    if (temporaryObject.get("builtInPOI").equals(true)) {
                        favDropdown.getItems().add(temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
                    } else {
                        favDropdown.getItems().add("(User)" + temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
                    }
                }
                layersDropdown.getItems().addAll("Classroom", "CollaborationSpace", "Elevator", "Lab", "Navigation", "Washroom");
                floorsDropdown.getItems().addAll("1", "2", "3", "4", "5");
                mapsDropdown.setValue(search.getBuildingIndex(0));
            } catch (ParseException e) {
                System.out.println("PARSE EXCEPTION");
            }
        } catch (IOException e) {

        }
        weatherReport.setAllWeatherData();
        currentTemperature.setText(weatherReport.getCurrentTemperature() + " °C");
        feelsLikeTemperature.setText(weatherReport.getFeelsLikeTemperature() + " °C");
        lowTemperature.setText(weatherReport.getMinTemperature() + " °C");
        highTemperature.setText(weatherReport.getMaxTemperature() + " °C");
    }

//    backUpBuiltInPOI.json
//    builtInPOI.json
    /**
     * Event handler for mouse clicks on the map. Places an icon at the clicked location if either the placeable icon or
     * add POI icon is currently selected.
     *
     * @param e the MouseEvent representing the mouse click
     * @throws FileNotFoundException if the image for the icon cannot be found
     */
    @FXML
    private void handleMouseDown(MouseEvent e) throws FileNotFoundException {
        xCoordinate = e.getX() / mapSizeX * 3400;
        yCoordinate = e.getY() / mapSizeY * 2200;
        if (!placeableIcon && !addPOIIcon) {
            return;
        }
        try {
            ImageView imgView = new ImageView(new Image(new FileInputStream("src/main/java/com/example/wesgeosys/iconImages/Icon Image - Placeholder.png")));
            imgView.setPreserveRatio(true);
            imgView.setX(e.getX() + mapOffsetX - 15);
            imgView.setY(e.getY() + mapOffsetY - 15);
            imgView.setFitWidth(30);
            placedIcon = imgView;
            adminPanel.getChildren().add(imgView);
            imageIcons.add(imgView);
            if (placeableIcon) {
                modifyPOIPopout();
            }
            if (addPOIIcon) {
                insertPOIPopout();
            }
        } catch (Exception error) {
            displayError(error);
        }
    }

    /**
     * Creates a pop-up window for adding a new POI and adds it to the admin panel.
     */
    private void insertPOIPopout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addPOIGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 322.0, 391.0);
            Stage stage = new Stage();
            stage.setTitle("Add POI");
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (adminAccess) {
            editHelper.createPOI(currentPOIList);
            JSONObject temporaryObject;
            temporaryObject = (JSONObject) currentPOIList.get(0);
            poiDropdown.getItems().add(temporaryObject.get("name") + ":" + temporaryObject.get("roomNum"));
        } else {
            JSONObject temporaryObject;
            editHelper.createPOI(currentPOIList,xCoordinate,yCoordinate,newName);
            editHelper.createPOI((JSONArray) userInstance.get("userPOIs"));
            editHelper.addUserPOI(currentBuildingData.get("Building").toString(), currentFloorIndex,xCoordinate,yCoordinate,newName);
            JSONArray temporaryArray = (JSONArray) userInstance.get("userPOIs");
            temporaryObject = (JSONObject) temporaryArray.get(0);
            poiDropdown.getItems().add(newName);
        }
        adminPanel.getChildren().remove(placedIcon);
        addPOIIcon = false;
    }

    /**
     * Displays an error message with the given exception.
     *
     * @param e the exception to be displayed
     */
    private void displayError(Exception e) {
        System.out.println("ERROR OCCURRED: " + e);
    }
}
