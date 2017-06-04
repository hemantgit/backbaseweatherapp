package bartburg.nl.backbaseweather.view.location.helper;

import bartburg.nl.backbaseweather.helper.TemperatureUnitHelper;
import bartburg.nl.backbaseweather.model.Forecast;

/**
 * Created by Bart on 6/4/2017.
 */

public class ForecastDataPresenter {

    private static final String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};


    private static String windDirectionConverter(Integer deg) {
        return directions[(int) Math.round(((deg % 360.0) / 45)) % 8];
    }

    public static String getTemperatureText(Forecast forecast) {
        return TemperatureUnitHelper.getTemperatureString(forecast.getTemp().getMax())
                + "\n"
                + TemperatureUnitHelper.getTemperatureString(forecast.getTemp().getMin());
    }

    public static String getHumidityText(Forecast forecast) {
        return String.valueOf(forecast.getHumidity());
    }

    public static String getRainChanceText(Forecast forecast) {
        return "0";
    }

    public static String getWindText(Forecast forecast) {
        return forecast.getSpeed() + " " + windDirectionConverter(forecast.getDeg());
    }
}
