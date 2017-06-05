package bartburg.nl.backbaseweather.provision.remote.controller.weather;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.HashMap;

import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.remote.annotation.ApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.BaseApiController;

/**
 * Created by Bart on 6/3/2017.
 */

@ApiController(
        relativePath = "weather"
)
public class WeatherApiController extends BaseApiController {

    //TODO DRY methods
    public void getWeather(final String cityName, final OnWeatherResponseListener onWeatherResponseListener, @Nullable final BaseApiController.OnErrorListener onErrorListener) {
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
    public void getWeather(final Coordinates coordinates, final OnWeatherResponseListener onWeatherResponseListener, @Nullable final BaseApiController.OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("lat", String.valueOf(coordinates.getLat()));
                parameters.put("lon", String.valueOf(coordinates.getLon()));
                String resultString = get(parameters, onErrorListener);
                if (resultString != null) {
                    WeatherResponse weatherResponse = new Gson().fromJson(resultString, WeatherResponse.class);
                    onWeatherResponseListener.onSuccess(weatherResponse);
                }

            }
        });
    }


    public interface OnWeatherResponseListener {
        void onSuccess(WeatherResponse weatherResponse);
    }
}
