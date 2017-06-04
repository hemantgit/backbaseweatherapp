package bartburg.nl.backbaseweather.helper;

import java.text.DecimalFormat;

/**
 * Created by Bart on 6/4/2017.
 */

public class TemperatureUnitHelper {

    public static DecimalFormat decimalFormat = new DecimalFormat("#.0");

    public static String getTemperatureString(double kelvinValue) {
        double temperatureInCelsius = TemperatureUnitHelper.kelvinToCelsius(kelvinValue);
        return decimalFormat.format(temperatureInCelsius) + (char) 0x00B0 + "C";
    }

    public static double kelvinToCelsius(double kelvinValue) {
        return kelvinValue - 273.16;
    }

    public static double kelvindToFahrenheit(double kelvinValue) {
        return (((kelvinValue - 273) * 9 / 5.0) + 32);
    }

}
