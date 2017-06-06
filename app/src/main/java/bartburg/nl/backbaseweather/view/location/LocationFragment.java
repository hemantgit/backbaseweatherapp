package bartburg.nl.backbaseweather.view.location;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import bartburg.nl.backbaseweather.MainActivity;
import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.alert.ServerErrorAlert;
import bartburg.nl.backbaseweather.enumeration.MetricUnitSystem;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.local.controller.city.CityDbHandler;
import bartburg.nl.backbaseweather.provision.remote.controller.BaseApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;
import bartburg.nl.backbaseweather.util.MetricUnitSystemUtil;
import bartburg.nl.backbaseweather.util.WeatherDescriptionUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCityBookmarkChangedListener} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements OnCurrentCityLoadedListener {
    private static final String TAG_CITY = "city";

    private City city;
    private MainActivity mainActivityParent;
    public ForecastResponse forecastResponse;
    private boolean cityBookmarked = false;
    private float currentBookmarkIconAlpha = 0.15f;

    private View bookmarkIcon;
    private TextView unitSystemTextView;
    private TextView temperatureTextView;
    private TextView cityNameTextView;
    private ImageView weatherIconImageView;


    public LocationFragment() {
    }

    public static LocationFragment newInstance(City city) {
        LocationFragment fragment = new LocationFragment();
        if (city != null) {
            Bundle args = new Bundle();
            args.putParcelable(TAG_CITY, city);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getParcelable(TAG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = new FrameLayout(getActivity());
        populateViewForOrientation(inflater, frameLayout);
        return frameLayout;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_location, viewGroup);
        getViews(subview);
        setViewValues();
        getWeatherAndForecast();
        if (city != null) {
            cityBookmarked = new CityDbHandler(getContext()).cityInDb(city.getId());
            currentBookmarkIconAlpha = cityBookmarked ? 1f : 0.15f;
        }
        setBookmarkedIcon(subview, false);
        setBookmarkIconClickListener();
    }

    private void getViews(View parent) {
        bookmarkIcon = parent.findViewById(R.id.bookmarked_icon);
        cityNameTextView = (TextView) parent.findViewById(R.id.city_name_textview);
        unitSystemTextView = (TextView) parent.findViewById(R.id.unit_system_textview);
        temperatureTextView = (TextView) parent.findViewById(R.id.temperature_textview);
        weatherIconImageView = (ImageView) parent.findViewById(R.id.weather_imageview);
    }

    private void setViewValues() {
        if (city != null) {
            cityNameTextView.setText(city.getName());
            unitSystemTextView.setText(MetricUnitSystemUtil.getWeatherUnitSystem(getContext()) == MetricUnitSystem.CELSIUS ? "C" : "F");
        }
    }

    public MainActivity getMainActivityParent() {
        if (mainActivityParent == null) {
            mainActivityParent = (MainActivity) getActivity();
        }
        return mainActivityParent;
    }

    private void setBookmarkIconClickListener() {
        bookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBookmarkThisCity();
            }
        });
    }

    private void setBookmarkedIcon(View parent, boolean animate) {
        if (parent != null) {
            AlphaAnimation animation1 = new AlphaAnimation(currentBookmarkIconAlpha, cityBookmarked ? 1f : 0.15f);
            animation1.setDuration(animate ? 150 : 0);
            animation1.setStartOffset(0);
            animation1.setFillAfter(true);
            bookmarkIcon.startAnimation(animation1);
            currentBookmarkIconAlpha = cityBookmarked ? 1f : 0.15f;
        }
    }

    private void getWeatherAndForecast() {
        if (city == null) {
            return;
        }
        getForecast();
        getWeather();
    }

    private void getWeather() {
        new WeatherApiController().getWeather(city.getName(), new WeatherApiController.OnWeatherResponseListener() {
            @Override
            public void onSuccess(final WeatherResponse weatherResponse) {
                View view = getView();
                if (view != null) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            temperatureTextView.setText(WeatherDescriptionUtil.getTemperature(weatherResponse, false, MetricUnitSystemUtil.getWeatherUnitSystem(getContext())));
                            int weatherImage = WeatherDescriptionUtil.getWeatherImage(weatherResponse);
                            if (weatherImage > 0) {
                                weatherIconImageView.setImageResource(weatherImage);
                            }
                        }
                    });
                }
            }
        }, null);
    }

    private void getForecast() {
        new ForecastApiController().getForecast(city.getName(), new ForecastApiController.OnForecastResponseListener() {
            @Override
            public void onSuccess(final ForecastResponse forecastResponse) {
                View view = getView();
                if (view != null) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            if (LocationFragment.this.mainActivityParent == null) {
                                return;
                            }
                            LocationFragment.this.forecastResponse = forecastResponse;
                            updateScreen();
                        }
                    });
                }
            }
        }, new BaseApiController.OnErrorListener() {
            @Override
            public void onError(int responseCode, String responseMessage) {
                if (LocationFragment.this.mainActivityParent == null) {
                    return;
                }
                ServerErrorAlert.show(getContext());
            }
        });
    }

    private void updateScreen() {
        if (forecastResponse != null) {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.location_forecast_container, LocationForecastFragment.newInstance(forecastResponse));
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public void toggleBookmarkThisCity() {
        if (getMainActivityParent() != null && city != null) {
            cityBookmarked = !cityBookmarked;
            city.setBookmarked(cityBookmarked);
            getMainActivityParent().onCityBookmarkChanged(city, cityBookmarked);
            setBookmarkedIcon(getView(), true);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivityParent = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must be attached to MainActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivityParent = null;
    }

    @Override
    public void cityLoaded(City city) {
        this.city = city;
        getWeatherAndForecast();
    }

    public interface OnCityBookmarkChangedListener {
        void onCityBookmarkChanged(City city, boolean bookmark);
    }
}
