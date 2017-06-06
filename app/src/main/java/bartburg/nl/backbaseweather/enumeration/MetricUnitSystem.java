package bartburg.nl.backbaseweather.enumeration;

/**
 * Created by Bart on 6/4/2017.
 */

public enum MetricUnitSystem {

    CELSIUS(0),
    FAHRENHEIT(1);

    private final int value;

    MetricUnitSystem(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
