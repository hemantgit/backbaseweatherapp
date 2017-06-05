package bartburg.nl.backbaseweather.provision.remote.controller.weather;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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

    public void getWeather(final String cityName, final OnWeatherResponseListener onWeatherResponseListener, @Nullable final BaseApiController.OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("q", cityName);
                onWeatherResponse(parameters, onErrorListener, onWeatherResponseListener);

            }
        });
    }


    public void getWeather(final Coordinates coordinates, final OnWeatherResponseListener onWeatherResponseListener, @Nullable final BaseApiController.OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("lat", String.valueOf(coordinates.getLat()));
                parameters.put("lon", String.valueOf(coordinates.getLon()));
                onWeatherResponse(parameters, onErrorListener, onWeatherResponseListener);

            }
        });
    }


    public void getWeatherOfMultipleCities(final Integer[] cityIds, final OnMultipleCitiesWeatherResponseListener onWeathersResponseListener, @Nullable final OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("id", TextUtils.join(",", cityIds));
                onMultipleCitiesWeathersResponse(parameters, onErrorListener, onWeathersResponseListener);
            }
        });
    }


    private void onWeatherResponse(HashMap<String, String> parameters, @Nullable OnErrorListener onErrorListener, OnWeatherResponseListener onWeatherResponseListener) {
        String resultString = get(parameters, onErrorListener);
        if (resultString != null) {
            WeatherResponse weatherResponse = new Gson().fromJson(resultString, WeatherResponse.class);
            onWeatherResponseListener.onSuccess(weatherResponse);
        }
    }

    private void onMultipleCitiesWeathersResponse(HashMap<String, String> parameters, @Nullable OnErrorListener onErrorListener, OnMultipleCitiesWeatherResponseListener onWeathersResponseListener) {
        String resultString = get(parameters, onErrorListener);
        if (resultString != null) {
            MultipleCitiesWeatherResponse forecastsResponse = new Gson().fromJson(resultString, MultipleCitiesWeatherResponse.class);
            onWeathersResponseListener.onSuccess(forecastsResponse);
        }
    }


    public interface OnWeatherResponseListener {
        void onSuccess(WeatherResponse weatherResponse);
    }

    public interface OnMultipleCitiesWeatherResponseListener {
        void onSuccess(MultipleCitiesWeatherResponse multipleCityWeatherResponse);
    }
}
