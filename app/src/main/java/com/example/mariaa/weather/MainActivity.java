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

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    TextView text_show_latitude;
    TextView text_show_longitude;
    TextView text_show_city;

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;

    private LocationManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* text_show_latitude = (TextView) findViewById(R.id.text_latitude);
        text_show_longitude = (TextView) findViewById(R.id.text_longitude);
        text_show_city = (TextView) findViewById(R.id.text_city);
        */

        cityField = (TextView)findViewById(R.id.city_field);
        updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView)findViewById(R.id.current_temperature_field);
        humidity_field = (TextView)findViewById(R.id.humidity_field);
        pressure_field = (TextView)findViewById(R.id.pressure_field);


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

