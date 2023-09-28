package com.example.wesgeosys;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


public class editTool {


    protected String username;

    protected boolean adminPerms;

    public JSONArray buildingData;

    protected JSONArray accountData;

    public editTool(JSONArray buildings, String username, Boolean admin) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/wesgeosys/accountData.json")) {
            this.accountData = (JSONArray) jsonParser.parse(reader);
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (ParseException e) {
            System.out.println("ParseException");
        }
        this.adminPerms = admin;
        this.buildingData = buildings;
        this.username = username;
    }

    public void persistData() {
        try {
            if (adminPerms) {
                BufferedWriter buffWriter = new BufferedWriter(new FileWriter("src/main/java/com/example/wesgeosys/defaultPOI.json"));
                JSONObject tmpObj = null;
                JSONArray tmpArray = new JSONArray();
                buffWriter.write("{\"buildings\":[");
                for (int n = 0; n < buildingData.size(); n++) {
                    tmpObj = (JSONObject) buildingData.get(n);
                    tmpArray = (JSONArray) tmpObj.get("floors");
                    buffWriter.write("{\"Building\":\"" + (String) tmpObj.get("Building") + "\",\"floors\":[");
                    for (int f = 0; f < tmpArray.size(); f++) {
                        JSONObject tmpObject = (JSONObject) tmpArray.get(f);
                        JSONArray poiList = (JSONArray) tmpObject.get("pointsOfInterest");
                        for (int k = 0; k < poiList.size(); k++) {
                            JSONObject checkObj = (JSONObject) poiList.get(k);
                            if ((Boolean) checkObj.get("builtInPOI") == true) {
                                buffWriter.write(poiList.get(k).toString());
                                if (f + 1 != tmpArray.size() || k + 1 != poiList.size()) {
                                    buffWriter.write(",");
                                }
                            }
                        }
                        if (f + 1 != tmpArray.size()) {
                            buffWriter.write("\n");
                        } else {
                            buffWriter.flush();
                            if (n + 1 != buildingData.size()) {
                                buffWriter.write("]},\n");
                            } else {
                                buffWriter.write("]}]}\n");
                            }
                        }
                    }
                    buffWriter.write("\n");
                    buffWriter.flush();
                }
            } else {
                BufferedWriter userBuffWriter = new BufferedWriter(new FileWriter("src/main/java/com/example/wesgeosys/accountData.json"));
                userBuffWriter.write("[");
                for (int n = 0; n < accountData.size(); n++) {
                    userBuffWriter.write(accountData.get(n).toString());
                    if (n + 1 != accountData.size()) {
                        userBuffWriter.write(",\n");
                    } else {
                        userBuffWriter.write("]");
                    }
                    userBuffWriter.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }


    public void createFloor(JSONObject currentBuilding, String imageName) {
        if (adminPerms) {
            JSONObject newFloor = new JSONObject();
            JSONArray poiList = createPOI();
            newFloor.put("imageFileName", imageName);
            newFloor.put("pointsOfInterest", poiList);
            JSONArray tmpArray = (JSONArray) currentBuilding.get("floors");
            tmpArray.add(tmpArray.size(), newFloor);
            persistData();
        } else {
            System.out.println("INVALID PERMISSIONS TO CREATE FLOOR");
        }
    }


    public void deleteFloor(JSONArray buildingCurrent, int numberFloor) {
        if (adminPerms) {
            buildingCurrent.remove(numberFloor);
            persistData();
        } else {
            System.out.println("INVALID PERMISSIONS TO DELETE FLOOR");
        }
    }

    public void createPOI(JSONArray poiList,double x, double y, String name) {
       x= Math.floor(x);
       y= Math.floor(y);
       x= (int)x;
       y= (int)y;
        JSONObject defaultPOI = new JSONObject();
        defaultPOI.put("layerType", "Default");
        defaultPOI.put("visibility", true);
        defaultPOI.put("favourite", false);
        defaultPOI.put("description", "DefaultDescription");
        defaultPOI.put("name", name);
        defaultPOI.put("xCord", x);
        defaultPOI.put("yCord", y);
        defaultPOI.put("roomNum", "DefaultRoomNum");
        if (adminPerms) {
            defaultPOI.put("builtInPOI", true);
        } else {
            defaultPOI.put("builtInPOI", false);
        }
        poiList.add(0, defaultPOI);
        persistData();
    }
    public void createPOI(JSONArray poiList) {
        JSONObject defaultPOI = new JSONObject();
        defaultPOI.put("layerType", "Default");
        defaultPOI.put("visibility", true);
        defaultPOI.put("favourite", false);
        defaultPOI.put("description", "DefaultDescription");
        defaultPOI.put("name", "name");
        defaultPOI.put("xCord", 0);
        defaultPOI.put("yCord", 0);
        defaultPOI.put("roomNum", "DefaultRoomNum");
        if (adminPerms) {
            defaultPOI.put("builtInPOI", true);
        } else {
            defaultPOI.put("builtInPOI", false);
        }
        poiList.add(0, defaultPOI);
        persistData();
    }

    public void modifyPOI(JSONObject poi, String newName, String newDescription, int newX, int newY, String newRoomNum, String newLayerType) {
        if (poi.get("builtInPOI").equals(true)) {
            if (adminPerms) {
                poi.replace("name", newName);
                poi.replace("description", newDescription);
                poi.replace("xCord", newX);
                poi.replace("yCord", newY);
                poi.replace("roomNum", newRoomNum);
                poi.replace("layerType", newLayerType);
                persistData();
            } else {
                System.out.println("INVALID PERMISSIONS");
            }
        } else {
            JSONObject userObj;
            for (int n = 0; n < accountData.size(); n++) {
                userObj = (JSONObject) accountData.get(n);
                String string = userObj.get("username").toString();
                if (string.equals(username)) {
                    JSONArray poiList = (JSONArray) userObj.get("userPOIs");
                    JSONObject tmpObj;
                    for (int k = 0; k < poiList.size(); k++) {
                        tmpObj = (JSONObject) poiList.get(k);
                        JSONObject newObj = new JSONObject();
                        if (tmpObj.get("name").equals(poi.get("name")) && tmpObj.get("roomNum").equals(poi.get("roomNum"))) {
                            poiList.remove(k);
                            poi.replace("building", poi.get("building"));
                            poi.replace("floorNum", poi.get("floorNum"));
                            poi.replace("name", newName);
                            poi.replace("description", newDescription);
                            poi.replace("xCord", newX);
                            poi.replace("yCord", newY);
                            poi.replace("roomNum", newRoomNum);
                            poi.replace("layerType", newLayerType);
                            poi.replace("builtInPOI", false);
                            poi.replace("favourite", poi.get("favourite"));
                            poiList.add(poi);
                            persistData();
                        }
                    }
                }
            }
        }
    }


    public void deletePOI(JSONArray poiList, JSONObject currentPOI) {
        JSONObject tmpObj;
        for (int n = 0; n < poiList.size(); n++) {
            tmpObj = (JSONObject) poiList.get(n);
            if (tmpObj.get("name").equals(currentPOI.get("name")) && tmpObj.get("roomNum").equals(currentPOI.get("roomNum"))) {
                poiList.remove(n);
                persistData();
            }
        }
    }


    public void addUserPOI(String buildName, int floorNum,double x, double y, String name) {
        JSONObject userObj;
        for (int n = 0; n < accountData.size(); n++) {
            userObj = (JSONObject) accountData.get(n);
            if (userObj.get("username") == null) {
            } else {
                String string = userObj.get("username").toString();
                if (string.equals(username)) {
                    JSONArray poiList = (JSONArray) userObj.get("userPOIs");
                    JSONObject defaultPOI = new JSONObject();
                    defaultPOI.put("building", buildName);
                    defaultPOI.put("floorNum", floorNum);
                    defaultPOI.put("name", (name+": newPOI"));
                    defaultPOI.put("description", "DefaultDescrip");
                    defaultPOI.put("roomNum", "DefaultRoomNum");
                    defaultPOI.put("layerType", "Default");
                    defaultPOI.put("visibility", true);
                    defaultPOI.put("favourite", false);
                    defaultPOI.put("builtInPOI", false);
                    defaultPOI.put("xCord", x);
                    defaultPOI.put("yCord", y);
                    poiList.add(0,defaultPOI);

                    persistData();
                }
            }
        }
    }

    public JSONArray createFloor() {
        JSONArray floors = new JSONArray();
        JSONArray poiList = createPOI();
        JSONObject defaultFloorObj = new JSONObject();
        defaultFloorObj.put("imageFileName", "DefaultFileName");
        defaultFloorObj.put("pointsOfInterest", poiList);
        floors.add(0, defaultFloorObj);
        return floors;
    }


    public JSONArray createPOI() {
        JSONArray poiList = new JSONArray();
        JSONObject defaultPOI = new JSONObject();
        defaultPOI.put("layerType", "Default");
        defaultPOI.put("visibility", true);
        defaultPOI.put("description", "Default");
        defaultPOI.put("name", "Unnamed Point of Interest");
        defaultPOI.put("xCord", 0);
        defaultPOI.put("yCord", 0);
        defaultPOI.put("roomNum", "");
        if (adminPerms) {
            defaultPOI.put("builtInPOI", true);
        } else {
            defaultPOI.put("builtInPOI", false);
        }
        poiList.add(0, defaultPOI);
        return poiList;
    }

    public int findIndex(String nameBuilding) {
        for (int g = 0; g < buildingData.size(); g++) {
            JSONObject buildings = (JSONObject) buildingData.get(g);
            String buildingname = (String) buildings.get("Building");
            if (nameBuilding.equals(buildingname)) {
                return g;
            }
        }
        System.out.println("BUILDING DOES NOT EXIST.");
        return -1;
    }

    public void createBuilding(String buildName) {
        if (!adminPerms) {
            System.out.println("INVALID PERMISSIONS TO CREATE BUILDING");
            return;
        }

        JSONObject newBuild = new JSONObject();
        JSONArray newFloor = createFloor();
        newBuild.put("Building", buildName);
        newBuild.put("floors", newFloor);
        buildingData.add(newBuild);
        persistData();
    }


    public void modifyBuilding(JSONObject currentBuilding, String newBuildName) {
        if (!adminPerms) {
            System.out.println("INVALID PERMISSIONS TO EDIT BUILDING");
            return;
        }
        int num = findIndex(currentBuilding.get("Building").toString());
        currentBuilding.replace("Building", newBuildName);
        buildingData.set(num, currentBuilding);
        persistData();
    }


    public void deleteBuilding(String buildingName) {
        if (!adminPerms) {
            System.out.println("INVALID PERMISSIONS TO DELETE BUILDING");
            return;
        }
        int index = findIndex(buildingName);
        if (index == -1) {
            System.out.println("BUILDING NOT FOUND");
            return;
        }
        buildingData.remove(index);
        persistData();
    }


    public void toggleFavourite(String buildCurrent, int floorNum, JSONObject currentPOI, Boolean favouritesBool) {
        for (Object account : accountData) {
            JSONObject userObj = (JSONObject) account;
            String username = (String) userObj.get("username");
            if (username != null && username.equals(this.username)) {
                JSONArray favourites = (JSONArray) userObj.get("favourites");
                int indexToRemove = -1;
                for (int i = 0; i < favourites.size(); i++) {
                    JSONObject poi = (JSONObject) favourites.get(i);
                    if (poi.get("name").equals(currentPOI.get("name"))) {
                        indexToRemove = i;
                        break;
                    }
                }
                if (indexToRemove != -1) {
                    favourites.remove(indexToRemove);
                }
                currentPOI.put("building", buildCurrent);
                currentPOI.put("floorNum", floorNum);
                currentPOI.replace("favourite", favouritesBool);
                favourites.add(currentPOI);
                persistData();
                break;
            }
        }
    }


    public void deleteUserPOI(JSONObject pointofInterest) {
        for (int f = 0; f < accountData.size(); f++) {
            JSONObject userObj = (JSONObject) accountData.get(f);
            if (userObj.get("username").toString().equals(username)) {
                JSONArray poiList = (JSONArray) userObj.get("userPOIs");
                for (int q = 0; q < poiList.size(); q++) {
                    JSONObject tmpObj = (JSONObject) poiList.get(q);
                    if (tmpObj.get("name").toString().equals(pointofInterest.get("name").toString())) {
                        poiList.remove(q);
                        persistData();
                        return;
                    }
                }
            }
        }
    }


}
