package bartburg.nl.backbaseweather.provision.remote.controller.forecast;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.HashMap;

import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.remote.annotation.ApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.BaseApiController;

/**
 * Created by Bart on 6/3/2017.
 * This class is used for getting the forecast for the coming 5 days, per 3 hours.
 */
@ApiController(
        relativePath = "forecast/daily"
)
public class ForecastApiController extends BaseApiController {


    public void getForecast(final String cityName, final OnForecastResponseListener onForecastResponseListener, @Nullable final OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("q", cityName);
                onForecastResponse(parameters, onErrorListener, onForecastResponseListener);
            }
        });
    }

    public void getForecast(final Coordinates coordinates, final OnForecastResponseListener onForecastResponseListener, @Nullable final OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("lat", String.valueOf(coordinates.getLat()));
                parameters.put("long", String.valueOf(coordinates.getLon()));
                onForecastResponse(parameters, onErrorListener, onForecastResponseListener);
            }
        });
    }

    private void onForecastResponse(HashMap<String, String> parameters, @Nullable OnErrorListener onErrorListener, OnForecastResponseListener onForecastResponseListener) {
        String resultString = get(parameters, onErrorListener);
        if (resultString != null) {
            ForecastResponse forecastResponse = new Gson().fromJson(resultString, ForecastResponse.class);
            onForecastResponseListener.onSuccess(forecastResponse);
        }
    }

    public interface OnForecastResponseListener {
        void onSuccess(ForecastResponse forecastResponse);
    }
}
