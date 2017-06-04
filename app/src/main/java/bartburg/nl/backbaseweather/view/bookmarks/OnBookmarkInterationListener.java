package bartburg.nl.backbaseweather.view.bookmarks;

import bartburg.nl.backbaseweather.model.City;

/**
 * Created by Bart on 6/4/2017.
 */

public interface OnBookmarkInterationListener {
    void onBookmarkInteraction(City cityClicked, CityAction action);
}
