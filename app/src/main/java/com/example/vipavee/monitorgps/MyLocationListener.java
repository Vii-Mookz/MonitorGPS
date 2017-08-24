package com.example.vipavee.monitorgps;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Vipavee on 23/08/2017.
 */

class MyLocationListener implements android.location.LocationListener {
    final String _logTag = "Monitor Location";
    @Override
    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();


        String logMessage = LogHelper.FormatLocationInfo(provider, lat, lng, accuracy, time);

        Log.d(_logTag, "Monitor Location:" + logMessage);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(_logTag, "Monitor Location - Provider Enabled:" + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(_logTag, "Monitor Location - Provider DISabled:" + provider);
    }
}
