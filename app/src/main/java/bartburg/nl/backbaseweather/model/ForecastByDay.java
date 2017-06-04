package bartburg.nl.backbaseweather.model;

import java.util.ArrayList;

/**
 * Created by Bart on 6/4/2017.
 */

public class ForecastByDay {
    private String dayAbbrevation;
    private ArrayList<Forecast> forecastResponses;

    public ForecastByDay(String dayAbbrevation, ArrayList<Forecast> forecastResponses) {
        this.dayAbbrevation = dayAbbrevation;
        this.forecastResponses = forecastResponses;
    }

    public String getDayAbbrevation() {
        return dayAbbrevation;
    }

    public ArrayList<Forecast> getForecastResponses() {
        return forecastResponses;
    }
}
