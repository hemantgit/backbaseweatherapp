package bartburg.nl.backbaseweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import bartburg.nl.backbaseweather.model.City;
import bartburg.nl.backbaseweather.model.Coordinates;
import bartburg.nl.backbaseweather.provision.local.controller.city.CityDbHandler;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherApiController;
import bartburg.nl.backbaseweather.provision.remote.controller.weather.WeatherResponse;
import bartburg.nl.backbaseweather.view.bookmarks.BookmarksListFragment;
import bartburg.nl.backbaseweather.view.bookmarks.BookmarksTabHostFragment;
import bartburg.nl.backbaseweather.view.bookmarks.CityAction;
import bartburg.nl.backbaseweather.view.bookmarks.OnBookmarkInteractionListener;
import bartburg.nl.backbaseweather.view.location.LocationFragment;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnBookmarkInteractionListener, LocationFragment.OnCityBookmarkChangedListener {

    public static final int PERMISSION_ACCESS_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager;
    private City currentCity;
    private NavigationView navigationView;

    @Override
    public void onCityBookmarkChanged(City city, boolean bookmark) {
        if (bookmark) {
            new CityDbHandler(this).addCity(city);
        } else {
            new CityDbHandler(this).deleteCity(city);
        }
    }

    @Override
    public void onBookmarkInteraction(City cityInteracted, CityAction action) {
        switch (action) {
            case ADD:
                new CityDbHandler(this).addCity(cityInteracted);
                break;
            case DELETE:
                new CityDbHandler(this).deleteCity(cityInteracted);
                break;
            case LOAD:
                navigationView.getMenu().getItem(1).setChecked(true); //TODO should be more dynamic
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container, LocationFragment.newInstance(cityInteracted));
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }

    public enum FragmentName {
        BOOKMARKS,
        HELP,
        LOCATION,
        SETTINGS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer(toolbar);
        getUserLocation();
        openFirstFragment();
    }

    private void getUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_ACCESS_FINE_LOCATION);
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                getCurrentLocationWeather(location);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation();
                }
            }
        }
    }

    private void getCurrentLocationWeather(Location location) {
        new WeatherApiController().getWeather(new Coordinates(location.getLatitude(), location.getLongitude()), new WeatherApiController.OnWeatherResponseListener() {
            @Override
            public void onSuccess(WeatherResponse weatherResponse) {
                currentCity = weatherResponse.getCity();
            }
        }, null);
    }

    private void openFirstFragment() {
        openFragment(FragmentName.BOOKMARKS);
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmarks) {
            openFragment(FragmentName.BOOKMARKS);
        } else if (id == R.id.nav_location) {
            openFragment(FragmentName.LOCATION);
        } else if (id == R.id.nav_help) {
            openFragment(FragmentName.HELP);
        } else if (id == R.id.nav_settings) {
            openFragment(FragmentName.SETTINGS);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(FragmentName fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragmentName) {
            case BOOKMARKS:
                fragmentTransaction.replace(R.id.main_fragment_container, BookmarksTabHostFragment.newInstance(0));
                break;
            case LOCATION:
                fragmentTransaction.replace(R.id.main_fragment_container, LocationFragment.newInstance(currentCity));
                break;
            case HELP:
                fragmentTransaction.replace(R.id.main_fragment_container, BookmarksListFragment.newInstance(1));
                break;
            case SETTINGS:
                fragmentTransaction.replace(R.id.main_fragment_container, BookmarksListFragment.newInstance(1));
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

}
