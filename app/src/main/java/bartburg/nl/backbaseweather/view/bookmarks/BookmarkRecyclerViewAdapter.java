package bartburg.nl.backbaseweather.view.bookmarks;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;
import bartburg.nl.backbaseweather.view.bookmarks.BookmarksListFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link City} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder> {

    private final List<City> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public BookmarkRecyclerViewAdapter(ArrayList<City> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        City city = mValues.get(position);
        holder.mCity = city;
        holder.cityNameTextView.setText(city.getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onListFragmentInteraction(holder.mCity, CityAction.LOAD);
                    Log.d("Bookmark", "Load bookmark");
                }
            }
        });
        holder.mRemoveClickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onListFragmentInteraction(holder.mCity, CityAction.DELETE);
                    Log.d("Bookmark", "Delete bookmark");
                }
            }
        });
        //TODO caching of weather
        new WeatherApiController().getWeather(holder.mCity.getName(), new WeatherApiController.OnWeatherResponseListener() {
            @Override
            public void onSuccess(final WeatherResponse weatherResponse) {
               holder.mView.post(new Runnable() {
                   @Override
                   public void run() {
                       holder.cityWeatherTextView.setText("weer ontvangen");
                       holder.cityWeatherIcon.setImageResource(R.drawable.cloudy);
                   }
               });
            }
        }, null);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView cityNameTextView;
        public final TextView cityWeatherTextView;
        public final ImageView cityWeatherIcon;
        public final View mRemoveClickArea;
        public City mCity;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cityNameTextView = (TextView) view.findViewById(R.id.id);
            cityWeatherTextView = (TextView) view.findViewById(R.id.content);
            cityWeatherIcon = (ImageView) view.findViewById(R.id.icon_weather);
            mRemoveClickArea = view.findViewById(R.id.remove_click_area);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + cityWeatherTextView.getText() + "'";
        }
    }
}
