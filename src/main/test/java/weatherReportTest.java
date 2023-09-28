import com.example.wesgeosys.weatherReport;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * A class for testing the functionality of the {@link weatherReport} class.
 */
public class weatherReportTest {

    /**
     * Tests the {@link weatherReport#getCurrentTemperature()} method.
     * Asserts that the current temperature is valid (not equal to ERROR_MESSAGE).
     * Sets all weather data before running the test.
     */
    @Test
    public void testGetCurrentTemperature() {
        weatherReport.setAllWeatherData();
        assertNotEquals(weatherReport.getCurrentTemperature(), weatherReport.ERROR_MESSAGE);
    }

    /**
     * Tests the {@link weatherReport#getMinTemperature()} method.
     * Asserts that the minimum temperature is valid (not equal to ERROR_MESSAGE).
     * Sets all weather data before running the test.
     */
    @Test
    public void testGetMinTemperature() {
        weatherReport.setAllWeatherData();
        assertNotEquals(weatherReport.getMinTemperature(), weatherReport.ERROR_MESSAGE);
    }

    /**
     * Tests the {@link weatherReport#getMaxTemperature()} method.
     * Asserts that the maximum temperature is valid (not equal to ERROR_MESSAGE).
     * Sets all weather data before running the test.
     */
    @Test
    public void testGetMaxTemperature() {
        weatherReport.setAllWeatherData();
        assertNotEquals(weatherReport.getMaxTemperature(), weatherReport.ERROR_MESSAGE);
    }

    /**
     * Tests the {@link weatherReport#getFeelsLikeTemperature()} method.
     * Asserts that the "feels like" temperature is valid (not equal to ERROR_MESSAGE).
     * Sets all weather data before running the test.
     */
    @Test
    public void testGetFeelsLikeTemperature() {
        weatherReport.setAllWeatherData();
        assertNotEquals(weatherReport.getFeelsLikeTemperature(), weatherReport.ERROR_MESSAGE);
    }
}
