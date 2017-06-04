package bartburg.nl.backbaseweather.provision.local;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.SparseArray;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.local.controller.city.helper.CitiesListConverter;
import bartburg.nl.backbaseweather.provision.local.controller.city.CityDbHandler;

/**
 * Created by Bart on 6/3/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CityDbHandlerTest {

    private Context appContext;
    private CityDbHandler cityDbHandler;
    private SparseArray<City> uniqueCities;

    @Before
    public void init(){
        appContext = InstrumentationRegistry.getTargetContext();
        cityDbHandler = new CityDbHandler(appContext);

        Coordinates coordinates = new Coordinates(12.444, -23.453);

        uniqueCities = new SparseArray<>();
        uniqueCities.put(1, new City(1, "Den Bosch",coordinates ));
        uniqueCities.put(2, new City(2, "Eindhoven", coordinates));
        uniqueCities.put(3, new City(3, "Amsterdam", coordinates));
        uniqueCities.put(4, new City(4, "Den Haag", coordinates));
        uniqueCities.put(5, new City(5, "Rotterdam", coordinates));
    }

    @Test
    public void testCrud() throws Exception {
        deleteAllCitiesByCity(); //Ensure empty database
        testAddNewCities();
        checkReadCities(uniqueCities);
        deleteAllCitiesByCity();
        testAddNewCities();
        deleteAllCitiesById();
    }

    private void testAddNewCities(){
        for(int i = 0; i < uniqueCities.size(); i++) {
            int key = uniqueCities.keyAt(i);
            City uniqueCity = uniqueCities.get(key);
            boolean success = cityDbHandler.addCity(uniqueCity);
            Assert.assertTrue(success);
        }

        // Should fail because the cities need to be unique
        for(int i = 0; i < uniqueCities.size(); i++) {
            int key = uniqueCities.keyAt(i);
            City uniqueCity = uniqueCities.get(key);
            boolean success = cityDbHandler.addCity(uniqueCity);
            Assert.assertFalse(success);
        }

    }

    private void deleteAllCitiesByCity(){
        for(int i = 0; i < uniqueCities.size(); i++) {
            int key = uniqueCities.keyAt(i);
            City cityToDelete = uniqueCities.get(key);
            cityDbHandler.deleteCity(cityToDelete);
            uniqueCities.remove(0);
        }
        checkReadCities(new SparseArray<City>());
    }

    private void deleteAllCitiesById(){
        for(int i = 0; i < uniqueCities.size(); i++) {
            int key = uniqueCities.keyAt(i);
            City cityToDelete = uniqueCities.get(key);
            cityDbHandler.deleteCity(cityToDelete.getId());
            uniqueCities.remove(0);
        }
        checkReadCities(new SparseArray<City>());
    }

    /**
     * Checks if result from database is the same size as the local uniqueCities
     */
    private void checkReadCities(SparseArray<City> expectedCities){
        ArrayList<City> allCitiesFromDatabase = cityDbHandler.getAllCities();
        SparseArray<City> allCitiesFromDatabaseMap = CitiesListConverter.convertToSparseArray(allCitiesFromDatabase);
        Assert.assertEquals(expectedCities.size(), allCitiesFromDatabase.size());
        for(int i = 0; i < expectedCities.size(); i++) {
            int key = expectedCities.keyAt(i);
            City expectedCity = expectedCities.get(key);
            Assert.assertTrue(allCitiesFromDatabaseMap.get(expectedCity.getId()) != null);
        }
    }

}
