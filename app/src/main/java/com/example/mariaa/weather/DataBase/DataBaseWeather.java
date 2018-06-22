package com.example.mariaa.weather.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {WeatherDB.class}, version = 1)
public abstract class DataBaseWeather extends RoomDatabase {
    public abstract DaoWeather daoWeather();
}
