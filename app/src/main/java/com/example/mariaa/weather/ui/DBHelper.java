package com.example.mariaa.weather.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WeatherDB";
    public static final String TABLE_CONTACTS = "Weather";

    public static final String KEY_ID = "_id";
    public static final String KEY_CITY = "City";
    public static final String KEY_WIND = "Wind";
    public static final String KEY_CLOUDS = "Clouds";
    public static final String KEY_TEMP = "Temp";
    public static final String KEY_ICON = "Icon";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("create table %s(%sinteger primary key, %s text, %s text, %s text, %s text, %s text)", TABLE_CONTACTS, KEY_ID, KEY_CITY, KEY_WIND, KEY_CLOUDS, KEY_TEMP, KEY_ICON));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* сработает при изменении номера версии */
    }
}
