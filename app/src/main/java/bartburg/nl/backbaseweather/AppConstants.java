package bartburg.nl.backbaseweather;

import bartburg.nl.backbaseweather.enumeration.MetricUnitSystem;

/**
 * Created by Bart on 6/3/2017.
 * Class to keep all the static app global constants.
 */

public class AppConstants {

    // Open Weather Map
    public static final String OPEN_WEATHER_PROTOCOL = "http://";
    public static final String OPEN_WEATHER_MAP_BASE_URL = "api.openweathermap.org/data/2.5/";
    public static final String OPEN_WEATHER_MAP_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";

    // Broadcast
    public static final String FORECAST_RESPONSE_TAG = "forecastResponse";
    public static final String WEATHER_RESPONSE_TAG = "weatherResponse";

    public static final long FORECAST_EXPIRED_AFTER = 1000 * 60 * 5;

    public static final MetricUnitSystem WEATHER_UNIT_SYSTEM = MetricUnitSystem.CELCIUS;
}
