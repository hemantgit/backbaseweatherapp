package bartburg.nl.backbaseweather.helper;

import bartburg.nl.backbaseweather.WeatherType;
import bartburg.nl.backbaseweather.model.WeatherRange;

/**
 * Created by Bart on 6/4/2017.
 */

public class WeatherTypeHelper {

    private static final WeatherRange[] weatherRanges = new WeatherRange[]{
            new WeatherRange(200, 232, WeatherType.THUNDER),
            new WeatherRange(300, 321, WeatherType.DRIZZLE),
            new WeatherRange(500, 531,WeatherType.RAIN),
            new WeatherRange(600, 622, WeatherType.SNOW),
            new WeatherRange(800, WeatherType.CLEAR),
            new WeatherRange(801, 804, WeatherType.PARTIALLY_CLOUDS)
    };

    public static WeatherType getWeatherTypeFromId(int groupId){
        for(WeatherRange weatherRange : weatherRanges){
            if(weatherInRange(groupId, weatherRange)){
                return weatherRange.getWeatherType();
            }
        }
        return WeatherType.CLOUDS;
    }

    private static boolean weatherInRange(int groupId, WeatherRange weatherRange){
        return groupId <= weatherRange.getHighestGroupId() && groupId >= weatherRange.getLowestGroupId();
    }


}
