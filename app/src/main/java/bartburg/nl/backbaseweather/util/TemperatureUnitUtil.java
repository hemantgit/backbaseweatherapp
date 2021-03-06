package bartburg.nl.backbaseweather.util;

import java.text.DecimalFormat;

import bartburg.nl.backbaseweather.enumeration.MetricUnitSystem;

/**
 * Created by Bart on 6/4/2017.
 */

public class TemperatureUnitUtil {

    public static DecimalFormat decimalFormat = new DecimalFormat("0.0");

    public static String getTemperatureString(double kelvinValue, boolean withUnit, MetricUnitSystem metricUnitSystem) {
        if (metricUnitSystem == MetricUnitSystem.CELSIUS) {
            double temperatureInCelsius = TemperatureUnitUtil.kelvinToCelsius(kelvinValue);
            return decimalFormat.format(temperatureInCelsius) + (withUnit ? (char) 0x00B0 + "C" : "");
        } else {
            double temperatureInFahrenheit = TemperatureUnitUtil.kelvindToFahrenheit(kelvinValue);
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
