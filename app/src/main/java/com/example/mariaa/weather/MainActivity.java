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
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    TextView text_show_latitude;
    TextView text_show_longitude;
    //TextView response;


    private LocationManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                TextView weather = (TextView) findViewById(R.id.textView2);

      //  manager = (LocationManager) getSystemService(LOCATION_SERVICE);



        try {
            Response response = Client.getApiService().getMyGSON("Ufa").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client.getApiService().getMyGSON("Ufa").enqueue(new Callback<MainWeather>() {
            @Override
            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onResponse (Call <MainWeather> call, Response <MainWeather> response) {
                response.body().getMain().getTemp();
            }
        }

    }/*
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
    };*/

}

