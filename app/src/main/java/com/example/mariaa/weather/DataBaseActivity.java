package com.example.mariaa.weather;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mariaa.weather.DataBase.DataBaseWeather;
import com.example.mariaa.weather.DataBase.WeatherDB;
import com.example.mariaa.weather.model.Weather;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DataBaseActivity extends AppCompatActivity {

    TextView text_database;
    public DataBaseWeather dataBaseWeather00;
    String info;
    SimpleDateFormat formatForDateNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        text_database = findViewById(R.id.text_database);

        dataBaseWeather00 = Room.databaseBuilder(getApplicationContext(),DataBaseWeather.class, "WeatherTable")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        List<WeatherDB> weatherDBList = dataBaseWeather00.daoWeather().getAll();

        info = "";

        for (int i=1; i<weatherDBList.size(); i++) {

            String city = weatherDBList.get(i).getCity();
            String temp = weatherDBList.get(i).getTemn();
            String wind = weatherDBList.get(i).getWind();
            String clouds = weatherDBList.get(i).getClouds();
            Date date = weatherDBList.get(i).getDate();
            formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

            info = info + " " + formatForDateNow.format(date) + " " + city + " " + temp + " °C" + " wind: " + wind + "m/c. " + "clods: " + clouds + "%" + "\n";
        }

        text_database.setText(info);


    }
}
