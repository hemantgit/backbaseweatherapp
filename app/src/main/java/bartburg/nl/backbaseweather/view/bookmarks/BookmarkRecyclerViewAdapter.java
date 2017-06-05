package bartburg.nl.backbaseweather.view.bookmarks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;
import bartburg.nl.backbaseweather.util.MetricUnitSystemUtil;
import bartburg.nl.backbaseweather.util.WeatherDescriptionUtil;

/**
 *
 */
public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder> {

    private static final SparseArray<WeatherResponse> weatherResponses = new SparseArray<>();
    private final List<City> values;
    private final OnBookmarkInteractionListener listener;
    private Context context;

    public BookmarkRecyclerViewAdapter(ArrayList<City> items, OnBookmarkInteractionListener listener) {
        values = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        City city = values.get(position);
        holder.city = city;
        holder.cityNameTextView.setText(city.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBookmarkInteraction(holder.city, CityAction.LOAD);
                }
            }
        });
        holder.removeClickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBookmarkInteraction(holder.city, CityAction.DELETE);
                    int indexOfCity = values.indexOf(holder.city);
                    values.remove(indexOfCity);
                    notifyItemRemoved(indexOfCity);
                    notifyItemRangeChanged(indexOfCity, values.size());
                }
            }
        });
        WeatherResponse weatherResponse = weatherResponses.get(city.getId());
        if (weatherResponse != null && !weatherResponse.isExpired()) {
            updateWeatherData(weatherResponse, holder);
        } else {
            requestWeatherData(holder); //TODO should retrieve all weather data
        }
    }

    private void requestWeatherData(final ViewHolder holder) {
        new WeatherApiController().getWeather(holder.city.getName(), new WeatherApiController.OnWeatherResponseListener() {
            @Override
            public void onSuccess(final WeatherResponse weatherResponse) {
                weatherResponses.put(weatherResponse.getCityId(), weatherResponse);
                updateWeatherData(weatherResponse, holder);
            }
        }, null);
    }

    private void updateWeatherData(final WeatherResponse weatherResponse, final ViewHolder holder) {
        holder.view.post(new Runnable() {
            @Override
            public void run() {
                holder.cityNameTextView.setText(WeatherDescriptionUtil.getFullCityName(weatherResponse));
                holder.cityWeatherTextView.setText(WeatherDescriptionUtil.getShortDescription(weatherResponse, MetricUnitSystemUtil.getWeatherUnitSystem(context)));
                int weatherImage = WeatherDescriptionUtil.getWeatherImage(weatherResponse);
                if (weatherImage > 0) {
                    holder.cityWeatherIcon.setImageResource(weatherImage);
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
        public final TextView cityWeatherTextView;
        public final ImageView cityWeatherIcon;
        public final View removeClickArea;
        public City city;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            cityNameTextView = (TextView) view.findViewById(R.id.city_name_textview);
            cityWeatherTextView = (TextView) view.findViewById(R.id.content);
            cityWeatherIcon = (ImageView) view.findViewById(R.id.icon_weather);
            removeClickArea = view.findViewById(R.id.remove_click_area);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + cityWeatherTextView.getText() + "'";
        }
    }
}
