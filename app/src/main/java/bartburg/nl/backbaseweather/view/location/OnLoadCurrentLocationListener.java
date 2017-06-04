package bartburg.nl.backbaseweather.view.location;

import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.view.bookmarks.CityAction;

/**
 * Created by Bart on 6/4/2017.
 */

public interface OnLoadCurrentLocationListener {
    void onBookmarkInteraction(OnCurrentCityLoadedListener onCurrentCityLoadedListener);
}
