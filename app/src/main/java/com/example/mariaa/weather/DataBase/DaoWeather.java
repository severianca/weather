package com.example.mariaa.weather.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;


@Dao
public interface DaoWeather {

    @Insert
    public void AddWeather (WeatherDB weatherdb);

}
