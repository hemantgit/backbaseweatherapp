package bartburg.nl.backbaseweather.enumeration;

/**
 * Created by Bart on 6/4/2017.
 */

public enum WeatherUnitSystem {

    CELCIUS(0),
    FAHRENHEIT(1);

    private final int value;

    WeatherUnitSystem(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
