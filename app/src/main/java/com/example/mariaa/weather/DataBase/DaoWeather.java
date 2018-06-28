package com.example.mariaa.weather.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface DaoWeather {

    @Query("SELECT * FROM WeatherDB")
    List<WeatherDB> getAll();

    @Insert
    public void insert (WeatherDB weatherdb);

    @Update
    void update (WeatherDB weatherdb);

    @Delete
    void delete (WeatherDB weatherdb);


}
