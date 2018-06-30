package com.example.mariaa.weather.ui;

import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.example.mariaa.weather.model.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.location.LocationManager;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static android.util.Log.*;


public class MainActivity extends AppCompatActivity {

    TextView text_temp, text_city, text_wind_clouds, textView;
    EditText text_city_enter_user;
    ImageView imageView;
    Button but_database;
    private LocationManager manager;
    Double latitude, longitude;
    Boolean one_response= false;//определение местоположения только один раз (при запуске)
    String City, Wind, Clouds, Temp, Icon;
    public DataBaseWeather dataBaseWeather4;
    Date Now;
    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_temp = findViewById(R.id.text_temp);
        text_city = findViewById(R.id.text_city);
        text_city_enter_user = findViewById(R.id.text_city_enter_user);
        text_wind_clouds = findViewById(R.id.text_wind_clouds);
        but_database = findViewById(R.id.but_database);

        imageView = (ImageView) findViewById(R.id.imageView);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        dataBaseWeather4 = Room.databaseBuilder(getApplicationContext(),DataBaseWeather.class, "WeatherTable")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();

        Now= new Date();

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
               else {
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


    public void ShowTextWeather() {
        text_city.setText(City);
        text_wind_clouds.setText("wind "+ Wind + " m/s. " + "clouds " + Clouds + "%" );
        text_temp.setText(Temp+" °C");
    }

    public void ShowIconWeather (String Icon) {

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

                Date Early = dataBaseWeather4.daoWeather().getAll().get(dataBaseWeather4.daoWeather().getAll().size()-1).getDate();

                if (Early.after(Now)) {
                    //если новый день, то в базу добавляем прошлый запрос
                    AddInDB(Early);
                    //запоминает запрос за сегодня (вдруг последний) и выводим
                    City = city;
                    Wind = Double.toString(response.body().getWind().getSpeed());
                    Clouds = Double.toString(response.body().getClouds().getAll());
                    Temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    Icon = response.body().getWeather().get(0).getIcon();
                    AddPreferences();
                    ShowTextWeather();
                    ShowIconWeather(Icon);
                }
                else {
                    //если день не изменился, то запоминаем
                    City = city;
                    Wind = Double.toString(response.body().getWind().getSpeed());
                    Clouds = Double.toString(response.body().getClouds().getAll());
                    Temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    Icon = response.body().getWeather().get(0).getIcon();
                    AddPreferences();
                    ShowTextWeather();
                    ShowIconWeather(Icon);
                }

            }

            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddInDB (Date date) {

        sPref = getPreferences(MODE_PRIVATE);

        WeatherDB weather = new WeatherDB();
        weather.setCity(sPref.getString("eCity", ""));
        weather.setWind(sPref.getString("eWind", ""));
        weather.setClouds(sPref.getString("eClouds", ""));
        weather.setTemn(sPref.getString("eTemp", ""));
        weather.setIcon(sPref.getString("eIcon", ""));
        weather.setDate(date);

        dataBaseWeather4.daoWeather().insert(weather);
        Toast.makeText(getApplicationContext(),"seccess", Toast.LENGTH_LONG).show();

    }

    private void loadWeatherLocation(final Double lon, final Double lat) {
        App.getApiService().getWeatherByLocation(lon, lat).enqueue(new Callback<MainWeather>() {

            public void onFailure(Call<MainWeather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }

            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {

               Date Early = dataBaseWeather4.daoWeather().getAll().get(dataBaseWeather4.daoWeather().getAll().size()-1).getDate();

                if (Early.after(Now)) {
                    //если новый день, то в базу добавляем прошлый запрос
                    AddInDB(Early);
                    //запоминает запрос за сегодня (вдруг последний) и выводим
                    City = response.body().getName();
                    Wind = Double.toString(response.body().getWind().getSpeed());
                    Clouds = Double.toString(response.body().getClouds().getAll());
                    Temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    Icon = response.body().getWeather().get(0).getIcon();
                    AddPreferences();
                    ShowTextWeather();
                    ShowIconWeather(Icon);
                }
                else {
                    //если день не изменился, то запоминаем
                    City = response.body().getName();
                    Wind = Double.toString(response.body().getWind().getSpeed());
                    Clouds = Double.toString(response.body().getClouds().getAll());
                    Temp = Double.toString( (int) response.body().getMain().getTemp()-273);
                    Icon = response.body().getWeather().get(0).getIcon();
                    AddPreferences();
                    ShowTextWeather();
                    ShowIconWeather(Icon);

                }

            }
        });
    }

    public void AddPreferences(){
        sPref = getPreferences(MODE_PRIVATE);
        Editor eCity = sPref.edit();
        Editor eWind = sPref.edit();
        Editor eClouds = sPref.edit();
        Editor eTemp = sPref.edit();
        Editor eIcon = sPref.edit();
        eCity.putString("eCity", City);
        eWind.putString("eWind", Wind);
        eClouds.putString("eClouds", Clouds);
        eTemp.putString("eTemp", Temp);
        eIcon.putString("eIcon", Icon);
        eCity.commit();
        eWind.commit();
        eClouds.commit();
        eTemp.commit();
        eIcon.commit();
    }



    public void but_show_click(View view) {
        loadWeatherByCity(text_city_enter_user.getText().toString());
        //закрытие клавиатуры
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
    
    public void but_show_database(View view) {
        Intent intent = new Intent(this, DataBaseActivity.class);
        startActivity(intent);
    }



}

