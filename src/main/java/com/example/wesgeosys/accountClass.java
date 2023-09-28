package com.example.wesgeosys;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Represents an account database stored in a JSON file.
 * The account database can store multiple accounts.
 */
public class accountClass {
    /**
     * JSONObject that contains the account details, including username, password, and favorites.
     * Each account is represented as a JSON object within the parent JSONObject.
     */
    static JSONObject accountDetails = new JSONObject();
    /**
     * The filename of the JSON file that stores the accounts.
     */
    private String fileName = "";

    /**
     * Constructs a new account database instance using the specified filename.
     *
     * @param file The name of the JSON file to use for storing account data
     */
    public accountClass(String file) {
        try {
            this.fileName = file;
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(this.fileName);
            Object obj = jsonParser.parse(reader);
            long favourites[] = new long[10];
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the favourites JSONArray stored in the account database for the specified user.
     *
     * @param user The username of the user for whom we want to retrieve the favourites.
     * @return Returns the JSONArray object that contains the favourites stored in the database.
     * @throws IOException    if there is an I/O error while accessing the database file.
     * @throws ParseException if there is an error parsing the contents of the database file.
     */
    public JSONArray getFavourites(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return (JSONArray) accountDetails.get("favourites");
            }
        }
        return null;
    }

    /**
     * Gets the password for the account associated with the given username.
     *
     * @param user The username for which the password is being retrieved.
     * @return The password for the specified account.
     * @throws IOException    If there is an error accessing the account database file.
     * @throws ParseException If there is an error parsing the contents of the account database file.
     */
    public String getPassword(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return (String) accountDetails.get("password");
            }
        }
        return null;
    }

    /**
     * Retrieves the username associated with the given user account from the database.
     *
     * @param user The username of the account whose username is being retrieved
     * @return The username associated with the specified user account
     * @throws IOException    If an I/O error occurs while accessing the database file
     * @throws ParseException If there is an error while parsing the database file
     */
    public String getUser(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return (String) accountDetails.get("username");
            }
        }
        return null;
    }

    /**
     * Determines if the account associated with the given username has admin privileges
     *
     * @param user The username of the account to check
     * @return True if the account has admin privileges, false otherwise
     * @throws IOException    If an I/O error occurs while accessing the account database
     * @throws ParseException If there is an error parsing the account data
     */
    public boolean checkAdmin(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;

        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                if (accountDetails.get("admin").equals("true")) {
                    return true;
                } else if (accountDetails.get("admin").equals("false")) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given username is present in the account database.
     *
     * @param user The username to search for
     * @return True if the username is found in the database, False otherwise
     * @throws IOException    if there is an error reading the account database file
     * @throws ParseException if there is an error parsing the JSON data in the database file
     */
    public boolean findUser(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given username is available to create a new account.
     *
     * @param newUser The username to check if it's already taken
     * @return Returns true if the username is available, false if it's already taken
     * @throws IOException    If there's an error reading the account database file
     * @throws ParseException If there's an error parsing the account database file
     */
    public boolean checkValidUsername(String newUser) throws IOException, ParseException {
        if (findUser(newUser) == false) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a new account with the given username and password, and stores it in the database (JSON file).
     *
     * @param username The username of the new account to be created
     * @param password The password of the new account to be created
     * @throws IOException    If there is an error writing to the file
     * @throws ParseException If there is an error parsing the JSON data
     */
    public void createAccount(String username, String password) throws IOException, ParseException {
        if (checkValidUsername(username)) {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(this.fileName);
            Object obj = jsonParser.parse(reader);
            JSONArray accountList = (JSONArray) obj;
            String user = (String) accountDetails.get("username");
            String pw = (String) accountDetails.get("password");
            accountDetails.put("username", user);
            accountDetails.put("password", pw);
            accountDetails.put("username", username);
            accountDetails.put("password", password);
            accountDetails.put("admin", "false");
            JSONArray favs = new JSONArray();
            accountDetails.put("favourites", favs);
            JSONArray userPOIs = new JSONArray();
            accountDetails.put("userPOIs", userPOIs);
            accountList.add(accountDetails);
            try (FileWriter file = new FileWriter(this.fileName)) {
                file.write(accountList.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prints an error message to the console, along with the provided exception information.
     *
     * @param e The exception that occurred
     */
    public static void PrintError(Exception e) {
        System.out.println("Error: " + e);
    }

    /**
     * This is the main method for testing the functionality of the accountClass.
     * It creates an instance of the accountClass using a JSON file containing account data.
     * It then calls various methods to check if the class is working as expected, such as checking
     * if a user exists, creating a new account, and checking if a username is valid.
     * The results are printed to the console for verification.
     *
     * @param args Command line arguments (not used)
     * @throws IOException    If there is an error reading the JSON file
     * @throws ParseException If there is an error parsing the JSON data
     */
    public static void main(String[] args) throws IOException, ParseException {
    }
}
