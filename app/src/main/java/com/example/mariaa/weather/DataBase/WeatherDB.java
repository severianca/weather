package com.example.mariaa.weather.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "WeatherTable")
public class WeatherDB {

    @PrimaryKey
    public long id;
    @ColumnInfo(name = "City")
    private String City;
    @ColumnInfo(name = "Wind")
    public String Wind;
    @ColumnInfo(name = "Clouds")
    public String Clouds;
    @ColumnInfo(name = "Temp")
    public String Temn;
    @ColumnInfo(name = "Icon")
    public String Icon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getClouds() {
        return Clouds;
    }

    public void setClouds(String clouds) {
        Clouds = clouds;
    }

    public String getTemn() {
        return Temn;
    }

    public void setTemn(String temn) {
        Temn = temn;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}