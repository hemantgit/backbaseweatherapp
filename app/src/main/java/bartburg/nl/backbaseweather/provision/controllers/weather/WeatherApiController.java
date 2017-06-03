package bartburg.nl.backbaseweather.provision.controllers.weather;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.controllers.BaseApiController;
import bartburg.nl.backbaseweather.provision.annotation.ApiController;

/**
 * Created by Bart on 6/3/2017.
 */

@ApiController(
        relativePath = "forecast"
)
public class WeatherApiController extends BaseApiController {

    //TODO DRY methods
    public void getWeather(final String cityName, final OnWeatherResponseListener onWeatherResponseListener, final BaseApiController.OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("q", cityName);
                String resultString = get(parameters, onErrorListener);
                if (resultString != null) {
                    WeatherResponse weatherResponse = new Gson().fromJson(resultString, WeatherResponse.class);
                    onWeatherResponseListener.onSuccess(weatherResponse);
                }

            }
        });
    }

    //TODO DRY methods
    public void getWeather(final Coordinates coordinates, final OnWeatherResponseListener onWeatherResponseListener, final BaseApiController.OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("lat", String.valueOf(coordinates.getLat()));
                parameters.put("long", String.valueOf(coordinates.getLon()));
                String resultString = get(parameters, onErrorListener);
                if (resultString != null) {
                    WeatherResponse weatherResponse = new Gson().fromJson(resultString, WeatherResponse.class);
                    onWeatherResponseListener.onSuccess(weatherResponse);
                }

            }
        });
    }

    static interface OnWeatherResponseListener {
        void onSuccess(WeatherResponse weatherResponse);
    }
}
