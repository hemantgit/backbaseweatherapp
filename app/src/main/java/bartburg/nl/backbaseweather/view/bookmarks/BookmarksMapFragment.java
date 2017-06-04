package bartburg.nl.backbaseweather.view.bookmarks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import bartburg.nl.backbaseweather.R;
import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.local.controller.city.CityDbHandler;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;

/**
 * created by Bart Burg
 */
public class BookmarksMapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG_LATITUDE = "param1";
    private static final String TAG_LONGITUDE = "param2";
    private static final String TAG_ZOOM = "param2";

    private double latitude = 52.1588484;
    private double longitude = 5.0566821;
    private float zoom = 8f;
    private GoogleMap googleMap;
    private MapView mapView;
    private OnBookmarkInterationListener mListener;
    HashMap<Marker, City> markerCityMap = new HashMap<>();


    public BookmarksMapFragment() {
    }

    /**
     * @param latitude  latitude position of where the map should start.
     * @param longitude longitude position of where the map should start.
     * @param zoom      zoom of the map at start.
     * @return A new instance of fragment BookmarksMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarksMapFragment newInstance(double latitude, double longitude, float zoom) {
        BookmarksMapFragment fragment = new BookmarksMapFragment();
        Bundle args = new Bundle();
        args.putDouble(TAG_LATITUDE, latitude);
        args.putDouble(TAG_LONGITUDE, longitude);
        args.putFloat(TAG_ZOOM, zoom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble(TAG_LATITUDE);
            longitude = getArguments().getDouble(TAG_LONGITUDE);
            zoom = getArguments().getFloat(TAG_ZOOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_bookmarks_map, container, false);
        initMap(parent, savedInstanceState);
        return parent;
    }

    private void initMap(View parent, Bundle savedInstanceState) {
        mapView = (MapView) parent.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng startPosition = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPosition, zoom));
        placeMarkers(new CityDbHandler(getContext()).getAllCities());
        googleMap.setOnMapLongClickListener(getOnMapLongclickListener());
    }


    private void placeMarker(City city) {
        if (googleMap != null) {
            LatLng cityPosition = new LatLng(city.getCoordinates().getLat(), city.getCoordinates().getLon());
            Marker marker = googleMap.addMarker(new MarkerOptions().position(cityPosition)
                    .title(city.getName()));
            markerCityMap.put(marker, city);
            googleMap.setOnMarkerClickListener(getOnMarkerClickListener());
        }
    }


    private void placeMarkers(ArrayList<City> cities) {
        for (City city : cities) {
            placeMarker(city);
        }
    }

    private GoogleMap.OnMapLongClickListener getOnMapLongclickListener() {
        return new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                new WeatherApiController().getWeather(new Coordinates(latLng), new WeatherApiController.OnWeatherResponseListener() {
                    @Override
                    public void onSuccess(final WeatherResponse weatherResponse) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                City city = weatherResponse.getCity();
                                mListener.onBookmarkInteraction(city, CityAction.ADD);
                                placeMarker(city);
                            }
                        });
                    }
                }, null);
            }
        };
    }

    @NonNull
    private GoogleMap.OnMarkerClickListener getOnMarkerClickListener() {
        return new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                City city = markerCityMap.get(marker);
                if (city != null) {
                    mListener.onBookmarkInteraction(city, CityAction.LOAD);
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookmarkInterationListener) {
            mListener = (OnBookmarkInterationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookmarkInterationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
