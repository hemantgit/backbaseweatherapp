package bartburg.nl.backbaseweather.provision.remote.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import bartburg.nl.backbaseweather.AppConstants;
import bartburg.nl.backbaseweather.provision.remote.annotation.ApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;
import bartburg.nl.backbaseweather.provision.remote.helper.QueryStringHelper;

import static bartburg.nl.backbaseweather.AppConstants.OPEN_WEATHER_MAP_BASE_URL;
import static bartburg.nl.backbaseweather.AppConstants.OPEN_WEATHER_PROTOCOL;

/**
 * Created by Bart on 6/3/2017.
 */

public abstract class BaseApiController {



    /**
     * Do the actual work of requesting data from the server. Note, should not run on main thread.
     *
     * @param parameters      Parameters that will be added to the query string.
     * @param onErrorListener Listener that will get called when status code is not 200
     * @return The result string or *null* when failed.
     */
    @Nullable
    public String get(HashMap<String, String> parameters, @Nullable OnErrorListener onErrorListener) {
        try {
            parameters.put("appid", AppConstants.OPEN_WEATHER_MAP_KEY);
            URL url = new URL(OPEN_WEATHER_PROTOCOL + OPEN_WEATHER_MAP_BASE_URL + getRelativePath() + QueryStringHelper.mapToQueryString(parameters));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();
            if (responseCode != 200 && onErrorListener != null) {
                onErrorListener.onError(responseCode, responseMessage);
            } else {
                return readInputStream(urlConnection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    private String readInputStream(HttpURLConnection urlConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    public interface OnErrorListener {
        void onError(int responseCode, String responseMessage);
    }

    protected String getRelativePath() {
        Class<? extends BaseApiController> aClass = getClass();
        if (aClass.isAnnotationPresent(ApiController.class)) {
            Annotation annotation = aClass.getAnnotation(ApiController.class);
            ApiController apiController = (ApiController) annotation;
            return apiController.relativePath();
        }
        return "";
    }

}
