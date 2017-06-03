package bartburg.nl.backbaseweather.provision.forecast;

/**
 * Created by Bart on 6/3/2017.
 * This class is used for getting the forecast for the coming 5 days, per 3 hours.
 */

public class ForecastApiController {

    public void getForecast(){

    }

    public interface onForecastResponse{
        void onSuccess(ForecastResponse forecastResponse);
        void onError(ForecastResponse forecastResponse);
    }

}
