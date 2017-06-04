package bartburg.nl.backbaseweather.view.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.Forecast;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;
import bartburg.nl.backbaseweather.view.location.helper.ForecastDataPresenter;
import bartburg.nl.backbaseweather.view.location.helper.FirstDayCharacterProvider;

/**
 * TODO DESCRIPTIVE TEXT
 */
public class LocationForecastFragment extends Fragment {
    private static final String TAG_FORECASTRESPONSE = "param1";
    private ForecastResponse forecastResponse;
    private ArrayList<ForecastDayViewHolder> forecastDayViewHolders = new ArrayList<>();

    public LocationForecastFragment() {
    }

    /**
     *
     */
    public static LocationForecastFragment newInstance(ForecastResponse forecastResponse) {
        LocationForecastFragment fragment = new LocationForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable(TAG_FORECASTRESPONSE, forecastResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forecastResponse = getArguments().getParcelable(TAG_FORECASTRESPONSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_forecast, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFields();
    }

    private void initFields() {
        View parent = getView();
        if (parent == null) {
            return;
        }

        if (forecastResponse.getForecasts().size() > 4) {
            forecastDayViewHolders.add(new ForecastDayViewHolder(parent, forecastResponse.getForecasts().get(0), R.id.day1, R.id.temperature_day1, R.id.humidity_day1, R.id.rain_day1, R.id.wind_day1));
            forecastDayViewHolders.add(new ForecastDayViewHolder(parent, forecastResponse.getForecasts().get(1), R.id.day2, R.id.temperature_day2, R.id.humidity_day2, R.id.rain_day2, R.id.wind_day2));
            forecastDayViewHolders.add(new ForecastDayViewHolder(parent, forecastResponse.getForecasts().get(2), R.id.day3, R.id.temperature_day3, R.id.humidity_day3, R.id.rain_day3, R.id.wind_day3));
            forecastDayViewHolders.add(new ForecastDayViewHolder(parent, forecastResponse.getForecasts().get(3), R.id.day4, R.id.temperature_day4, R.id.humidity_day4, R.id.rain_day4, R.id.wind_day4));
            forecastDayViewHolders.add(new ForecastDayViewHolder(parent, forecastResponse.getForecasts().get(4), R.id.day5, R.id.temperature_day5, R.id.humidity_day5, R.id.rain_day5, R.id.wind_day5));
        }
        setFieldValues();
    }

    private void setFieldValues() {
        for (int i = 0; i < forecastDayViewHolders.size(); i++) {
            ForecastDayViewHolder forecastDayViewHolder = forecastDayViewHolders.get(i);
            Forecast forecast = forecastDayViewHolder.getForecast();
            forecastDayViewHolder.getLabelTextView().setText(FirstDayCharacterProvider.getFirstDayCharacter(i + 1));
            forecastDayViewHolder.getTemperatureTextView().setText(ForecastDataPresenter.getTemperatureText(forecast));
            forecastDayViewHolder.getHumidityTextView().setText(ForecastDataPresenter.getHumidityText(forecast));
            forecastDayViewHolder.getRainChanceTextView().setText(ForecastDataPresenter.getRainChanceText(forecast));
            forecastDayViewHolder.getWindTextView().setText(ForecastDataPresenter.getWindText(forecast));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TAG_FORECASTRESPONSE, forecastResponse);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            forecastResponse = savedInstanceState.getParcelable(TAG_FORECASTRESPONSE);
            initFields();
        }
    }

    public class ForecastDayViewHolder {

        private Forecast forecast;
        private TextView labelTextView;
        private TextView temperatureTextView;
        private TextView humidityTextView;
        private TextView rainChanceTextView;
        private TextView windTextView;

        public ForecastDayViewHolder(View parent, Forecast forecast, int labelViewId, int temperatureViewId, int humidityViewId, int rainChanceViewId, int windViewId) {
            this.forecast = forecast;
            labelTextView = (TextView) parent.findViewById(labelViewId);
            temperatureTextView = (TextView) parent.findViewById(temperatureViewId);
            humidityTextView = (TextView) parent.findViewById(humidityViewId);
            rainChanceTextView = (TextView) parent.findViewById(rainChanceViewId);
            windTextView = (TextView) parent.findViewById(windViewId);
        }

        public Forecast getForecast() {
            return forecast;
        }

        public TextView getLabelTextView() {
            return labelTextView;
        }

        public TextView getTemperatureTextView() {
            return temperatureTextView;
        }

        public TextView getHumidityTextView() {
            return humidityTextView;
        }

        public TextView getRainChanceTextView() {
            return rainChanceTextView;
        }

        public TextView getWindTextView() {
            return windTextView;
        }
    }
}
