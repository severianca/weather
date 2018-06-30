package com.example.mariaa.weather.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {WeatherDB.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class DataBaseWeather extends RoomDatabase {
    public abstract DaoWeather daoWeather();
}


