package com.example.mariaa.weather;

import android.app.Application;

import com.example.mariaa.weather.network.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static final String ROOT_URL = "http://api.openweathermap.org";

    private static Retrofit gerRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return gerRetrofitInstance().create(ApiService.class);
    }

}
