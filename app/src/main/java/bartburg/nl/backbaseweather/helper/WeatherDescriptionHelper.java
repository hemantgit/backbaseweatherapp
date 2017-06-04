package bartburg.nl.backbaseweather.helper;

import java.text.DecimalFormat;
import java.util.List;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.Weather;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;

/**
 * Created by Bart on 6/4/2017.
 */

public class WeatherDescriptionHelper {

    public static DecimalFormat decimalFormat = new DecimalFormat("#.0");

    public static String getShortDescription(WeatherResponse weatherResponse) {
        return getTemperature(weatherResponse) + getWeatherMessage(weatherResponse);
    }

    private static String getWeatherMessage(WeatherResponse weatherResponse) {
        String weatherMessage = " ";
        if (weatherResponse.getWeather() != null && !weatherResponse.getWeather().isEmpty()) {
            String description = weatherResponse.getWeather().get(0).getDescription();
            weatherMessage += description.substring(0, 1).toUpperCase() + description.substring(1);
            return weatherMessage;
        }
        return "";
    }

    private static String getTemperature(WeatherResponse weatherResponse) {
        return TemperatureUnitHelper.getTemperatureString(weatherResponse.getMain().getTemp());
    }

    public static int getWeatherImage(WeatherResponse weatherResponse) {
        List<Weather> weather = weatherResponse.getWeather();
        if (weather != null && !weather.isEmpty()) {
            switch (WeatherTypeHelper.getWeatherTypeFromId(weather.get(0).getId())) {
                case RAIN:
                case DRIZZLE:
                case SNOW:
                    return R.drawable.rainy;
                case THUNDER:
                    return R.drawable.thunder;
                case CLEAR:
                    return R.drawable.sun;
                case CLOUDS:
                    return R.drawable.cloudy;
                case PARTIALLY_CLOUDS:
                    return R.drawable.partially_cloudy;
            }
        }
        return -1;
    }

    public static String getFullCityName(WeatherResponse weatherResponse) {
        if (weatherResponse.getCity() != null) {
            if (weatherResponse.getCity().getCountry() != null) {
                return weatherResponse.getCity().getName() + " - " + weatherResponse.getSys().getCountry();
            } else {
                return weatherResponse.getCity().getName();
            }
        }
        return "";
    }
}
