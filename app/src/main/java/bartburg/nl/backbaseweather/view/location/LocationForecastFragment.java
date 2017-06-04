package bartburg.nl.backbaseweather.view.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bartburg.nl.backbaseweather.MainActivity;
import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.remote.controller.BaseApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationForecastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationForecastFragment extends Fragment {
    private static final String TAG_CITY = "city";

    private City city;
    private MainActivity parent;
    public ForecastResponse forecastResponse;

    public LocationForecastFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param city City to show forecast.
     * @return A new instance of fragment LocationForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationForecastFragment newInstance(City city) {
        LocationForecastFragment fragment = new LocationForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable(TAG_CITY, city);
        fragment.setArguments(args);
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
        final View parent =  inflater.inflate(R.layout.fragment_location_forecast, container, false);
        getForecast();
        return parent;
    }

    private void getForecast() {
        if(city == null){
            return;
        }
        new ForecastApiController().getForecast(city.getName(), new ForecastApiController.OnForecastResponseListener() {
            @Override
            public void onSuccess(ForecastResponse forecastResponse) {
                if(LocationForecastFragment.this.parent == null){
                    return;
                }
                LocationForecastFragment.this.forecastResponse = forecastResponse;
                updateScreen();
            }
        }, new BaseApiController.OnErrorListener() {
            @Override
            public void onError(int responseCode, String responseMessage) {
                if(LocationForecastFragment.this.parent == null){
                    return;
                }
                new AlertDialog.Builder(LocationForecastFragment.this.parent)
                        .setTitle("Oops!")
                        .setMessage("Something went wrong, couldn't retrieve data from server.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void updateScreen() {
        if(forecastResponse != null){

        }
    }

    public void toggleBookmarkThisCity() {
        if (parent != null) {
            city.setBookmarked(!city.isBookmarked());
            parent.onFragmentInteraction(city);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            parent = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must be attached to MainActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    /**
     *
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(City city);
    }
}
