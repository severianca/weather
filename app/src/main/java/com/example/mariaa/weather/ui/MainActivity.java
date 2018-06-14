package com.example.mariaa.weather.ui;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariaa.weather.App;
import com.example.mariaa.weather.R;
import com.example.mariaa.weather.model.MainWeather;
import com.example.mariaa.weather.model.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.location.LocationManager;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView text_temp, text_city, text_wind_clouds;
    EditText text_city_enter_user;
    private LocationManager manager;
    Double latitude, longitude;
    Boolean one_response= false;//определение местоположения только один раз (при запуске)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_temp = findViewById(R.id.text_temp);
        text_city = findViewById(R.id.text_city);
        text_city_enter_user = findViewById(R.id.text_city_enter_user);
        text_wind_clouds = findViewById(R.id.text_wind_clouds);

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
               longitude = location.getLongitude();
               latitude = location.getLatitude();
               if (!one_response) {
                   loadWeatherLocation(longitude, latitude);
                   one_response= true;
               }
               }
               else
               Toast.makeText(getApplicationContext(), "отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
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

    private void loadWeatherByCity(final String city) {
        App.getApiService().getWeather(city).enqueue(new Callback<MainWeather>() {

            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                text_city.setText(city);
                text_wind_clouds.setText("wind "+ response.body().getWind().getSpeed() + " m/s. " + "clouds " + response.body().getClouds().getAll() + "%" );
                text_temp.setText(Double.toString((int) response.body().getMain().getTemp()-273)+" °C");
                response.body().getWeather().get(0).getIcon();
            }

            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadWeatherLocation(final Double lon, final Double lat) {
        App.getApiService().getWeatherByLocation(lon, lat).enqueue(new Callback<MainWeather>() {

            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }

            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                text_city.setText(response.body().getName());
                text_wind_clouds.setText("wind "+ response.body().getWind().getSpeed() + " m/s. " + "clouds " + response.body().getClouds().getAll() + "%" );
                text_temp.setText(Double.toString((int) response.body().getMain().getTemp()-273)+" °C");
            }
        });
    }

    public void but_show_click(View view) {
        loadWeatherByCity(text_city_enter_user.getText().toString());
        //закрытие клавиатуры
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);



    }

}

