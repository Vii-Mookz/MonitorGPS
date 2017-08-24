package com.example.vipavee.monitorgps;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Vipavee on 23/08/2017.
 */

class LogHelper {
    public static final String LOGTAG = "Monitor Location";
    static final String _timeStampFormat = "yyyy-MM-dd'T'HH:mm:ss";
    static final String _timeStampTimeZoneId = "UTC";



    public static String FormatLocationInfo(String provider, double lat, double lng, float accuracy, long time) {
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat(_timeStampFormat);
        timeStampFormatter.setTimeZone(TimeZone.getTimeZone(_timeStampTimeZoneId));

        String timeStamp = timeStampFormatter.format(time);

        String logMessage = String.format("%s | lat/lng=%f/%f | accuracy=%f | Time=%s",
                provider, lat, lng, accuracy, timeStamp);

        return logMessage;
    }

    public static String FormatLocationInfo(Location location) {

        if (location == null)
            return "<NULL Location Value>";

        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        return LogHelper.FormatLocationInfo(provider, lat, lng, accuracy, time);
    }

    public static String formationLocationProvider(Context context, LocationProvider provider) {
        String name = provider.getName();
        int horizontalAccuracy = provider.getAccuracy();
        int powerRequirements = provider.getPowerRequirement();
        boolean hasMonetaryCost = provider.hasMonetaryCost();
        boolean requiresCell = provider.requiresCell();
        boolean requiresNetwork = provider.requiresNetwork();
        boolean requiresSatellite = provider.requiresSatellite();
        boolean supportsAltitude = provider.supportsAltitude();
        boolean supportsBearing = provider.supportsBearing();
        boolean supportsSpeed = provider.supportsSpeed();

        String enabledMessage = "UNKNOWN";
        if (context != null) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            enabledMessage = yOrN(locationManager.isProviderEnabled(name));
        }

        String horizontalAccuracyDisplay = translateAccuracyFineCourse(horizontalAccuracy);
        String powerRequirementsDisplay = translatePower(powerRequirements);

        String logMessage = String.format("%s | enabled:%s | horizontal accuracy:%s | power:%s | " +
                        "cost:%s | uses cell:%s | uses network:%s | uses satellite:%s | " +
                        "has altitude:%s | has bearing:%s | has speed:%s |",
                name, enabledMessage, horizontalAccuracyDisplay, powerRequirementsDisplay,
                yOrN(hasMonetaryCost), yOrN(requiresCell), yOrN(requiresNetwork), yOrN(requiresSatellite),
                yOrN(supportsAltitude), yOrN(supportsBearing),yOrN(supportsSpeed));

        return logMessage;
    }

    public static String yOrN(boolean value) {
        return value ? "Y" : "N";
    }

    public static String yOrN(int value) {
        return value != 0 ? "Y" : "N";
    }

    public static String translateStatus(int value) {
        String message = "UNDEFINED";
        switch (value) {
            case LocationProvider.AVAILABLE:
                message = "AVAILABLE";
                break;
            case LocationProvider.OUT_OF_SERVICE:
                message = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                message = "TEMPORARILY_UNAVAILABLE";
                break;
        }

        return message;
    }

    public static String translateAccuracyFineCourse(int value) {
        String message = "UNDEFINED";
        switch (value) {
            case Criteria.ACCURACY_COARSE:
                message = "COARSE";
                break;
            case Criteria.ACCURACY_FINE:
                message = "FINE";
                break;

        }
        return message;
    }

    public static String translateAccuracyHighMediumLow(int value) {
        String message = "UNDEFINED";
        switch (value) {
            case Criteria.ACCURACY_HIGH:
                message = "HIGH";
                break;
            case Criteria.ACCURACY_MEDIUM:
                message = "MEDIUM";
                break;
            case Criteria.ACCURACY_LOW:
                message = "LOW";
                break;
        }

        return message;
    }

    public static String translatePower(int value) {
        String message = "NO_REQUIREMENT";
        switch (value) {
            case Criteria.POWER_HIGH:
                message = "HIGH";
                break;
            case Criteria.POWER_MEDIUM:
                message = "MEDIUM";
                break;
            case Criteria.POWER_LOW:
                message = "LOW";
                break;
        }

        return message;
    }

    public static String translateGpsEvent(int value) {
        String message = "UNDEFINED";
        switch (value) {
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                message = "GPS_EVENT_FIRST_FIX";
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                message = "GPS_EVENT_SATELLITE_STATUS";
                break;
            case GpsStatus.GPS_EVENT_STARTED:
                message = "GPS_EVENT_STARTED";
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                message = "GPS_EVENT_STOPPED";
                break;

        }
        return message;
    }
}