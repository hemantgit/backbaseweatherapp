package bartburg.nl.backbaseweather.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import bartburg.nl.backbaseweather.AppConstants;
import bartburg.nl.backbaseweather.provision.remote.controller.forecast.ForecastResponse;

/**
 * Created by Bart on 6/3/2017.
 */

public class WeatherResponseReceivedBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.putExtra(AppConstants.FORECAST_RESPONSE_TAG, new ForecastResponse());
    }
}
