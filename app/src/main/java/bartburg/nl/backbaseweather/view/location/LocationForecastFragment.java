package bartburg.nl.backbaseweather.view.location;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;

/**
 * TODO DESCRIPTIVE TEXT
 */
public class LocationForecastFragment extends Fragment {
    private static final String TAG_FORECASTRESPONSE = "param1";
    private ForecastResponse forecastResponse;

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
}
