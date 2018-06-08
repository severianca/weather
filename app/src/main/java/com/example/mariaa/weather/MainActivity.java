package com.example.mariaa.weather;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView text_show_latitude;
    TextView text_show_longitude;
        private LocationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_show_latitude = (TextView) findViewById(R.id.text_latitude);
        text_show_longitude = (TextView) findViewById(R.id.text_longitude);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }
        @SuppressLint("MissingPermission")
        @Override
        protected void onResume() {
            super.onResume();
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,listener);
        }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            if (location!=null) {
                text_show_latitude.setText(String.valueOf(location.getLatitude()));
                text_show_longitude.setText(String.valueOf(location.getLongitude()));
            }
            else{
                text_show_latitude.setText("Sorry, location");
                text_show_longitude.setText("unavailable");
            }

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
    };

}

