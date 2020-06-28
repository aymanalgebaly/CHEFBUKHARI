package com.compubase.chefbukhari.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.compubase.chefbukhari.ui.activities.HomeActivity;

public class SingleShotLocationProvider {


    public interface LocationCallback {
        void onNewLocationAvailable(GPSCoordinates location);
    }


    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {

        Log.i("sdsdsds", "requestSingleUpdate: ");

        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ((HomeActivity)context).requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
            return;
        }


        locationManager.requestSingleUpdate(criteria, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("onLocationChanged: ", location.toString());
                callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        }, null);


//            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            Log.i( "onLocationChanged: ",isGPSEnabled+"");
//
//            if (isGPSEnabled) {
//
//
//                Criteria criteria = new Criteria();
//                criteria.setAccuracy(Criteria.ACCURACY_FINE);
//
//                locationManager.requestSingleUpdate(criteria, new LocationListener() {
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        Log.i( "onLocationChanged: ",location.toString());
//                        callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
//                    }
//
//                    @Override
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String provider) {
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String provider) {
//                    }
//                }, null);
//
//
//            }

    }


    // consider returning Location instead of this dummy wrapper class
    public static class GPSCoordinates {

        public double longitude;
        public double latitude;


        GPSCoordinates(double theLatitude, double theLongitude) {
            longitude = theLongitude;
            latitude = theLatitude;
        }

    }

}
