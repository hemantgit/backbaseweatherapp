package bartburg.nl.backbaseweather.helper;

/**
 * Created by Bart on 6/4/2017.
 */

public class TemperatureUnitConverter {

    public static double kelvinToCelcius(double kelvinValue){
        return kelvinValue - 273.16;
    }

    public static double kelvindToFahrenheit(double kelvinValue){
        return (((kelvinValue - 273) * 9/5.0) + 32);
    }

}
