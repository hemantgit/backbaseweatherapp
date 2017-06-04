package bartburg.nl.backbaseweather.helper;

import java.text.DecimalFormat;

import bartburg.nl.backbaseweather.AppConstants;
import bartburg.nl.backbaseweather.enumeration.WeatherUnitSystem;

/**
 * Created by Bart on 6/4/2017.
 */

public class TemperatureUnitHelper {

    public static DecimalFormat decimalFormat = new DecimalFormat("0.0");

    public static String getTemperatureString(double kelvinValue, boolean withUnit) {
        if (AppConstants.WEATHER_UNIT_SYSTEM == WeatherUnitSystem.CELCIUS) {
            double temperatureInCelsius = TemperatureUnitHelper.kelvinToCelsius(kelvinValue);
            return decimalFormat.format(temperatureInCelsius) + (withUnit ? (char) 0x00B0 + "C" : "");
        } else {
            double temperatureInFahrenheit = TemperatureUnitHelper.kelvindToFahrenheit(kelvinValue);
            return decimalFormat.format(temperatureInFahrenheit) + (withUnit ? (char) 0x00B0 + "F" : "");
        }
    }

    public static double kelvinToCelsius(double kelvinValue) {
        return kelvinValue - 273.16;
    }

    public static double kelvindToFahrenheit(double kelvinValue) {
        return (((kelvinValue - 273) * 9 / 5.0) + 32);
    }

}
