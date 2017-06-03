package bartburg.nl.backbaseweather.provision.local.controller.city.helper;

import android.util.SparseArray;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.model.City;

/**
 * Created by Bart on 6/3/2017.
 */

public class CitiesListHelper {
    public static SparseArray<City> convertToSparseArray(ArrayList<City> allCitiesFromDatabase) {
        SparseArray<City> citySparseArray = new SparseArray<>();
        for(City city : allCitiesFromDatabase){
            citySparseArray.put(city.getId(), city);
        }
        return citySparseArray;
    }
}
