package bartburg.nl.backbaseweather.view.bookmarks.util;

import java.util.List;

import bartburg.nl.backbaseweather.model.City;

/**
 * Created by Bart on 6/5/2017.
 */

public class CitiesListConverter {

    public static Integer[] toArray(List<City> cities) {
        Integer[] returnValue = new Integer[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            returnValue[i] = cities.get(i).getId();
        }
        return returnValue;
    }

}
