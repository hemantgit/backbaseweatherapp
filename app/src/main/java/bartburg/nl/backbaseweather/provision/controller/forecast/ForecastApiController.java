package bartburg.nl.backbaseweather.provision.controller.forecast;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.controller.BaseApiController;
import bartburg.nl.backbaseweather.provision.annotation.ApiController;

/**
 * Created by Bart on 6/3/2017.
 * This class is used for getting the forecast for the coming 5 days, per 3 hours.
 */
@ApiController(
        relativePath = "forecast"
)
public class ForecastApiController extends BaseApiController {

    //TODO DRY methods
    public void getForecast(final String cityName, final OnForecastResponseListener onForecastResponseListener, final OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("q", cityName);
                String resultString = get(parameters, onErrorListener);
                if (resultString != null) {
                    ForecastResponse forecastResponse = new Gson().fromJson(resultString, ForecastResponse.class);
                    onForecastResponseListener.onSuccess(forecastResponse);
                }
            }
        });
    }

    //TODO DRY methods
    public void getForecast(final Coordinates coordinates, final OnForecastResponseListener onForecastResponseListener, final OnErrorListener onErrorListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("lat", String.valueOf(coordinates.getLat()));
                parameters.put("long", String.valueOf(coordinates.getLon()));
                String resultString = get(parameters, onErrorListener);
                if (resultString != null) {
                    ForecastResponse forecastResponse = new Gson().fromJson(resultString, ForecastResponse.class);
                    onForecastResponseListener.onSuccess(forecastResponse);
                }
            }
        });
    }

    public static interface OnForecastResponseListener {
        void onSuccess(ForecastResponse forecastResponse);
    }

}
