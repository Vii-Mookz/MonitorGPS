package com.example.vipavee.monitorgps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String _logTag = "Monitor Location";

    LocationListener _networkListener;
    LocationListener _gpsListener;
    private LocationProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onAccurateProvider(MenuItem item) {
        Criteria criteria = new Criteria();


        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> matchingProviderNames = locationManager.getProviders(criteria, false);
        for (String providerName : matchingProviderNames) {
            LocationProvider locationProvider = locationManager.getProvider(providerName);
            String logMessage = LogHelper.formationLocationProvider(this, locationProvider);
            Log.d(LogHelper.LOGTAG, "Monitor Location Provider:" + logMessage);
        }
    }

    public void onLowPowerProvider(MenuItem item) {
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> matchingProviderNames = locationManager.getProviders(criteria, true);
        for (String providerName : matchingProviderNames) {
            LocationProvider provider = locationManager.getProvider(providerName);
            String logMessage = LogHelper.formationLocationProvider(this, provider);
            Log.d(LogHelper.LOGTAG, "Monitor Location Provider:" + logMessage);
        }
    }
    public void onStartListening(MenuItem item) {
        Log.d(_logTag, "Monitor Location - Start Listening");
        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            _networkListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, _networkListener);
            _gpsListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, _gpsListener);
        } catch (Exception e) {
            e.printStackTrace();//To change body of catch statment use File : Setting | File Templete
        }
    }

    public void onStopListening(MenuItem item) {
        Log.d(_logTag, "Monitor Location - Stop Listening");

        doStopListening();
    }

    public void onRecentLocation(MenuItem item) {
        Log.d(_logTag, "Monitor - Recent Location");

        Location networkLocation;
        Location gpsLocation;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        String networkLogMessage = LogHelper.FormatLocationInfo(networkLocation);
        String gpsLogMessage = LogHelper.FormatLocationInfo(gpsLocation);

        Log.d(_logTag, "Monitor Location" + networkLogMessage);
        Log.d(_logTag, "Monitor Location" + gpsLogMessage);
    }

    public void onSingleLocation(MenuItem item) {
        Log.d(_logTag, "Monitor - Single Location");

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        _networkListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, _networkListener, null);

        _gpsListener = new MyLocationListener();
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, _gpsListener, null);

    }

    public void onExit(MenuItem item) {
        Log.d(_logTag,"Monitor Location Exit");
        doStopListening();
        finish();
    }
    public void doStopListening() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (_networkListener != null) {
            locationManager.removeUpdates(_networkListener);
            _networkListener = null;
        }
        if (_gpsListener != null) {
            locationManager.removeUpdates(_gpsListener);
            _gpsListener = null;
        }
    }

}
