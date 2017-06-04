package bartburg.nl.backbaseweather.view.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import bartburg.nl.backbaseweather.MainActivity;
import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.provision.local.controller.city.CityDbHandler;
import bartburg.nl.backbaseweather.provision.remote.controller.BaseApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements OnCurrentCityLoadedListener {
    private static final String TAG_CITY = "city";

    private City city;
    private MainActivity parent;
    public ForecastResponse forecastResponse;
    private boolean cityBookmarked = false;
    private float currentBookmarkIconAlpha = 0.15f;
    private View bookmarkIcon;


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
        final View parent = inflater.inflate(R.layout.fragment_location, container, false);
        getForecast();
        if (city != null) {
            cityBookmarked = new CityDbHandler(getContext()).cityInDb(city.getId());
        }
        bookmarkIcon = parent.findViewById(R.id.bookmarked_icon);
        setBookmarkedIcon(parent, false);
        setBookmarkIconClickListener(parent);
        return parent;
    }

    private void setBookmarkIconClickListener(View parent) {
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

    private void getForecast() {
        if (city == null) {
            return;
        }
        new ForecastApiController().getForecast(city.getName(), new ForecastApiController.OnForecastResponseListener() {
            @Override
            public void onSuccess(ForecastResponse forecastResponse) {
                if (LocationFragment.this.parent == null) {
                    return;
                }
                LocationFragment.this.forecastResponse = forecastResponse;
                updateScreen();
            }
        }, new BaseApiController.OnErrorListener() {
            @Override
            public void onError(int responseCode, String responseMessage) {
                if (LocationFragment.this.parent == null) {
                    return;
                }
                new AlertDialog.Builder(LocationFragment.this.parent)
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
        if (forecastResponse != null) {
            Log.d("LFF", "Ready to update screen");
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.location_forecast_container, LocationForecastFragment.newInstance(forecastResponse));
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public void toggleBookmarkThisCity() {
        if (parent != null && city != null) {
            cityBookmarked = !cityBookmarked;
            city.setBookmarked(cityBookmarked);
            parent.onFragmentInteraction(city);
            setBookmarkedIcon(getView(), true);
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

    @Override
    public void cityLoaded(City city) {
        this.city = city;
        getForecast();
    }

    /**
     *
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(City city);
    }
}
