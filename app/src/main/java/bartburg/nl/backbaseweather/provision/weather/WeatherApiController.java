package bartburg.nl.backbaseweather.provision.weather;

/**
 * Created by Bart on 6/3/2017.
 */

public class WeatherApiController {

    public void getForecast(){

    }

    public interface onForecastResponse{
        void onSuccess(WeatherResponse weatherResponse);
        void onError(WeatherResponse weatherResponse);
    }
}
