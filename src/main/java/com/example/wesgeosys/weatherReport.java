package com.example.wesgeosys;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

/**
 * This class provides functionality to retrieve and parse weather data
 * from an external API and store it in various fields.
 */
public class weatherReport {

    /**
     * Constant representing error message when weather data is unavailable
     */
    public static final String ERROR_MESSAGE = "NA";
    static private String currentTemperature = ERROR_MESSAGE;
    static private String minTemperature = ERROR_MESSAGE;
    static private String maxTemperature = ERROR_MESSAGE;
    static private String feelsLikeTemperature = ERROR_MESSAGE;
    static private final String DEFAULT_LATITUDE = "42.9849";
    static private final String DEFAULT_LONGITUDE = "-81.2453";
    static private JSONObject weatherData = null;

    /**
     * This method sets all weather data including current temperature,
     * minimum temperature, maximum temperature and feels-like temperature.
     */
    public static void setAllWeatherData() {
        setCurrentWeatherData();
        setCurrentTemperature();
        setMaxTemperature();
        setMinTemperature();
        setFeelsLikeTemperature();
    }

    /**
     * This method prints an error message to console in case of an exception.
     *
     * @param e An Exception object.
     */
    private static void printErrorMessage(Exception e) {
        System.out.println("Test did not succeed");
        System.out.println("Error: " + e);
    }

    /**
     * Sets the minimum temperature based on the weather data.
     * If the data is not available, sets the value to ERROR_MESSAGE.
     */
    private static void setMinTemperature() {
        try {
            var main = weatherData.getJSONObject("main");
            float temp = main.getFloat("temp_min") - 273.15f;
            minTemperature = String.valueOf(Math.round(temp));
        } catch (Exception e) {
            printErrorMessage(e);
            minTemperature = ERROR_MESSAGE;
        }
    }

    /**
     * Sets the current temperature based on the weather data.
     * If the data is not available, sets the value to ERROR_MESSAGE.
     */
    private static void setCurrentTemperature() {
        try {
            var main = weatherData.getJSONObject("main");
            float temp = main.getFloat("temp") - 273.15f;
            currentTemperature = String.valueOf(Math.round(temp));
        } catch (Exception e) {
            printErrorMessage(e);
            currentTemperature = ERROR_MESSAGE;
        }
    }

    /**
     * Sets the feels-like temperature based on the weather data.
     * If the data is not available, sets the value to ERROR_MESSAGE.
     */
    private static void setFeelsLikeTemperature() {
        try {
            var innerJSONData = weatherData.getJSONObject("main");
            float temp = innerJSONData.getFloat("feels_like") - 273.15f;
            feelsLikeTemperature = String.valueOf(Math.round(temp));
        } catch (Exception e) {
            printErrorMessage(e);
            feelsLikeTemperature = ERROR_MESSAGE;
        }
    }

    /**
     * Sets the max temperature value.
     * If an exception occurs, prints an error message and sets the max temperature to "NA".
     */
    private static void setMaxTemperature() {
        try {
            var main = weatherData.getJSONObject("main");
            float temp = main.getFloat("temp_max") - 273.15f;
            maxTemperature = String.valueOf(Math.round(temp));
        } catch (Exception e) {
            printErrorMessage(e);
            maxTemperature = ERROR_MESSAGE;
        }
    }

    /**
     * Sets the current weather data by making a HTTP request to the OpenWeatherMap API.
     * If an exception occurs, prints an error message and sets the weather data to null.
     */
    private static void setCurrentWeatherData() {
        try {
            String apiAccessKey = "6ae73b11767febcd9a4c5b850246e0f1";
            String endpointUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s", DEFAULT_LATITUDE, DEFAULT_LONGITUDE, apiAccessKey);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            weatherData = new JSONObject(response.body());
        } catch (Exception e) {
            printErrorMessage(e);
            weatherData = null;
        }
    }

    /**
     * Returns the current temperature.
     *
     * @return The current temperature.
     */
    public static String getCurrentTemperature() {
        return currentTemperature;
    }

    /**
     * Returns the minimum temperature.
     *
     * @return The minimum temperature.
     */
    public static String getMinTemperature() {
        return minTemperature;
    }

    /**
     * Returns the maximum temperature.
     *
     * @return The maximum temperature.
     */
    public static String getMaxTemperature() {
        return maxTemperature;
    }

    /**
     * Returns the temperature that it feels like.
     *
     * @return The temperature that it feels like.
     */
    public static String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    /**
     * Main method for testing purposes.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
    }
}
