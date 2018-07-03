package com.example.mariaa.weather.ui;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mariaa.weather.App;
import com.example.mariaa.weather.DataBase.DataBaseWeather;
import com.example.mariaa.weather.DataBase.WeatherDB;
import com.example.mariaa.weather.DataBaseActivity;
import com.example.mariaa.weather.R;
import com.example.mariaa.weather.model.MainWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.location.LocationManager;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    TextView textTemp, textCity, textWindClouds;
    EditText textCityEnterUser;
    ImageView imageView;
    Button butttonDatabase;
    private LocationManager manager;
    Double latitude, longitude;
    Boolean oneResponse = false;//определение местоположения только один раз (при запуске)
    String city, wind, clouds, temp, icon;
    public DataBaseWeather dataBaseWeather00;
    Date now;
    SimpleDateFormat formatForDateNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTemp = findViewById(R.id.text_temp);
        textCity = findViewById(R.id.text_city);
        textCityEnterUser = findViewById(R.id.text_city_enter_user);
        textWindClouds = findViewById(R.id.text_wind_clouds);
        butttonDatabase = findViewById(R.id.but_database);

        imageView = (ImageView) findViewById(R.id.imageView);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        dataBaseWeather00 = Room.databaseBuilder(getApplicationContext(),DataBaseWeather.class, "WeatherTable")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();

        now = new Date();
        formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

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
               if (!oneResponse) {
                   loadWeatherLocation(longitude, latitude);
                   oneResponse = true;
               }
               } else {
               Toast.makeText(getApplicationContext(), "отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
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


    public void showTextWeather() {
        textCity.setText(city);
        textWindClouds.setText("wind "+ wind + " m/s. " + "clouds " + clouds + "%" );
        textTemp.setText(temp +" °C");
    }

    public void showIconWeather(String Icon) {

        switch (Icon) {

            case "01d" : imageView.setImageResource(R.drawable.ic_01d);
                break;
            case "01n" : imageView.setImageResource(R.drawable.ic_01n);
                break;
            case "02d" : imageView.setImageResource(R.drawable.ic_02d);
                break;
            case "02n" : imageView.setImageResource(R.drawable.ic_02n);
                break;
            case "03d" : imageView.setImageResource(R.drawable.ic_03d);
                break;
            case "03n" : imageView.setImageResource(R.drawable.ic_03n);
                break;
            case "04d" : imageView.setImageResource(R.drawable.ic_04d);
                break;
            case "04n" : imageView.setImageResource(R.drawable.ic_04n);
                break;
            case "09d" : imageView.setImageResource(R.drawable.ic_09d);
                break;
            case "09n" : imageView.setImageResource(R.drawable.ic_09n);
                break;
            case "10d" : imageView.setImageResource(R.drawable.ic_10d);
                break;
            case "10n" : imageView.setImageResource(R.drawable.ic_10n);
                break;
            case "11d" : imageView.setImageResource(R.drawable.ic_11d);
                break;
            case "11n" : imageView.setImageResource(R.drawable.ic_11n);
                break;
            case "13d" : imageView.setImageResource(R.drawable.ic_13d);
                break;
            case "13n" : imageView.setImageResource(R.drawable.ic_13n);
                break;
            case "50d" : imageView.setImageResource(R.drawable.ic_50d);
                break;
            case "50n" : imageView.setImageResource(R.drawable.ic_50n);
                break;
                default: imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

    }

    private void loadWeatherByCity(final String city) {
        App.getApiService().getWeather(city).enqueue(new Callback<MainWeather>() {

            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {

                Date early =  dataBaseWeather00.daoWeather().getAll().get(dataBaseWeather00.daoWeather().getAll().size()-1).getDate();
                String sEarly = formatForDateNow.format(early);
                String sNow = formatForDateNow.format(now);
                if (sEarly.compareTo(sNow) >0){
                    //если новый день
                    MainActivity.this.city = response.body().getName();
                    wind = Double.toString(response.body().getWind().getSpeed());
                    clouds = Double.toString(response.body().getClouds().getAll());
                    temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    icon = response.body().getWeather().get(0).getIcon();
                    showTextWeather();
                    showIconWeather(icon);
                    addInDB(MainActivity.this.city, wind, clouds, temp, icon, now);
                }else {
                    MainActivity.this.city = response.body().getName();
                    wind = Double.toString(response.body().getWind().getSpeed());
                    clouds = Double.toString(response.body().getClouds().getAll());
                    temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    icon = response.body().getWeather().get(0).getIcon();
                    showTextWeather();
                    showIconWeather(icon);
                }

            }

            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addInDB(String aCity, String aWind, String aClouds, String aTemp, String aIcon, Date aNow) {

        WeatherDB weather = new WeatherDB();
        weather.setCity(aCity);
        weather.setWind(aWind);
        weather.setClouds(aClouds);
        weather.setTemn(aTemp);
        weather.setIcon(aIcon);
        weather.setDate(aNow);

        dataBaseWeather00.daoWeather().insert(weather);
    }

    private void loadWeatherLocation(final Double lon, final Double lat) {
        App.getApiService().getWeatherByLocation(lon, lat).enqueue(new Callback<MainWeather>() {

            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }

            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {

                if (dataBaseWeather00.daoWeather().getAll().size()==0){
                    city = response.body().getName();
                    wind = Double.toString(response.body().getWind().getSpeed());
                    clouds = Double.toString(response.body().getClouds().getAll());
                    temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    icon = response.body().getWeather().get(0).getIcon();
                    showTextWeather();
                    showIconWeather(icon);
                    addInDB(city, wind, clouds, temp, icon, now);
                } else {
                    Date Early =  dataBaseWeather00.daoWeather().getAll().get(dataBaseWeather00.daoWeather().getAll().size()-1).getDate();
                    String sEarly = formatForDateNow.format(Early);
                    String sNow = formatForDateNow.format(now);
                    if (sNow.compareTo(sEarly)>0){
                        //если новый день
                        city = response.body().getName();
                        wind = Double.toString(response.body().getWind().getSpeed());
                        clouds = Double.toString(response.body().getClouds().getAll());
                        temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                        icon = response.body().getWeather().get(0).getIcon();
                        showTextWeather();
                        showIconWeather(icon);
                        addInDB(city, wind, clouds, temp, icon, now);
                    }if (sNow.compareTo(sEarly)==0){
                        city = response.body().getName();
                        wind = Double.toString(response.body().getWind().getSpeed());
                        clouds = Double.toString(response.body().getClouds().getAll());
                        temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                        icon = response.body().getWeather().get(0).getIcon();
                        showTextWeather();
                        showIconWeather(icon);
                    }
                }
            }
        });
    }

    public void but_show_click(View view) {
        loadWeatherByCity(textCityEnterUser.getText().toString());
        //закрытие клавиатуры
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
    
    public void but_show_database(View view) {
        Intent intent = new Intent(this, DataBaseActivity.class);
        startActivity(intent);
    }
}

