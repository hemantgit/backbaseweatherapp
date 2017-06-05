package bartburg.nl.backbaseweather.view.search.data;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Container class with searchable cities (predefined)
 */
public class SearchableCitiesContainer {

    public static final ArrayList<String> CITIES = new ArrayList<>();

    static {
        CITIES.add("London");
        CITIES.add("Paris");
        CITIES.add("Berlin");
        CITIES.add("Amsterdam");
        CITIES.add("Barcelona");
        CITIES.add("Rome");
        CITIES.add("Brussels");
        CITIES.add("Bratislava");
        CITIES.add("Prague");
        CITIES.add("Split");
        CITIES.add("Bled");
        CITIES.add("Antwerp");
        CITIES.add("Porto");
        CITIES.add("Lisbon");
        CITIES.add("Tauplitz");
        CITIES.add("Achensee");
        CITIES.add("Andorra");
        CITIES.add("Budapest");
        CITIES.add("Faro");

        Collections.sort(CITIES);
    }

    public static ArrayList<String> searchCities(String searchString) {
        ArrayList<String> searchResult = new ArrayList<>();
        for (String string : CITIES) {
            if (string.matches("(?i)(" + searchString + ").*")) {
                searchResult.add(string);
            }
        }
        return searchResult;
    }
}
