package bartburg.nl.backbaseweather.provision.local.controller.city.util;

import android.util.SparseArray;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.model.City;

/**
 * Created by Bart on 6/3/2017.
 */

public class CitiesListConverter {
    public static SparseArray<City> convertToSparseArray(ArrayList<City> allCitiesFromDatabase) {
        SparseArray<City> citySparseArray = new SparseArray<>();
        for(City city : allCitiesFromDatabase){
            citySparseArray.put(city.getId(), city);
        }
        return citySparseArray;
    }
}
