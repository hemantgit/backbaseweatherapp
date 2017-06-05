package bartburg.nl.backbaseweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import bartburg.nl.backbaseweather.enumeration.MetricUnitSystem;

/**
 * Created by Bart on 6/5/2017.
 */

public class MetricUnitSystemUtil {
    public static MetricUnitSystem getWeatherUnitSystem(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String metricName = prefs.getString("metric", "Celsius");
        switch (metricName){
            case "Fahrenheit":
                return MetricUnitSystem.FAHRENHEIT;
            default:
                return MetricUnitSystem.CELCIUS;
        }
    }
}
