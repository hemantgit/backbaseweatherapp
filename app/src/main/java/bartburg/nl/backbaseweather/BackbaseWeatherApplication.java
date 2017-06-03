package bartburg.nl.backbaseweather;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.local.controller.city.CityDbHandler;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;

/**
 * Created by Bart on 6/3/2017.
 */

public class BackbaseWeatherApplication extends Application {

    private SparseArray<ForecastResponse> forecastResponses = new SparseArray<>();
    private SparseArray<WeatherResponse> weatherResponses = new SparseArray<>();

    @Nullable
    public ForecastResponse getForecastResponse(int cityId){
        return forecastResponses.get(cityId);
    }

    public void addForecastResponse(ForecastResponse forecastResponse){
        forecastResponses.put(forecastResponse.getCityId(), forecastResponse);
    }

    public void removeForecastResponse(int cityId){
        forecastResponses.remove(cityId);
    }

    @Nullable
    public WeatherResponse getWeatherResponse(int cityId){
        return weatherResponses.get(cityId);
    }

    public void addForecastResponse(WeatherResponse weatherResponse){
        weatherResponses.put(weatherResponse.getCityId(), weatherResponse);
    }

    public void removeWeatherResponse(int cityId){
        weatherResponses.remove(cityId);
    }
    
    public void updateWeatherAllBookmarkedCities(){
        ArrayList<City> allCities = new CityDbHandler(this).getAllCities();
        for(City city : allCities){
            
        }
    }

}
