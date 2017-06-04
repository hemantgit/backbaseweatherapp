package bartburg.nl.backbaseweather.model;

import bartburg.nl.backbaseweather.WeatherType;

/**
 * Created by Bart on 6/4/2017.
 */

public class WeatherRange{
    private int lowestGroupId;
    private int highestGroupId;
    private WeatherType weatherType;

    public WeatherRange(int lowestGroupId, int highestGroupId, WeatherType weatherType) {
        this.lowestGroupId = lowestGroupId;
        this.highestGroupId = highestGroupId;
        this.weatherType = weatherType;
    }

    public WeatherRange(int singleWeatherGroup, WeatherType weatherType) {
        this.lowestGroupId = singleWeatherGroup;
        this.highestGroupId = singleWeatherGroup;
        this.weatherType = weatherType;
    }

    public int getLowestGroupId() {
        return lowestGroupId;
    }

    public int getHighestGroupId() {
        return highestGroupId;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }
}
