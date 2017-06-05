package bartburg.nl.backbaseweather.view.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;
import bartburg.nl.backbaseweather.view.bookmarks.CityAction;
import bartburg.nl.backbaseweather.view.bookmarks.OnBookmarkInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link City} and makes a call to the
 * specified {@link OnBookmarkInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CitySearchResultViewAdapter extends RecyclerView.Adapter<CitySearchResultViewAdapter.ViewHolder> {

    private final List<String> values;
    private final OnBookmarkInteractionListener listener;

    public CitySearchResultViewAdapter(List<String> items, OnBookmarkInteractionListener listener) {
        values = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cityName = values.get(position);
        holder.cityNameTextView.setText(values.get(position));
        //holder.mContentView.setText(values.get(position).content);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    new WeatherApiController().getWeather(holder.cityName, new WeatherApiController.OnWeatherResponseListener() {
                        @Override
                        public void onSuccess(WeatherResponse weatherResponse) {
                           listener.onBookmarkInteraction(weatherResponse.getCity(), CityAction.LOAD);
                        }
                    }, null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView cityNameTextView;
        public String cityName;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            cityNameTextView = (TextView) view.findViewById(R.id.city_name_textview);
        }

        @Override
        public String toString() {
            return cityName;
        }
    }
}
