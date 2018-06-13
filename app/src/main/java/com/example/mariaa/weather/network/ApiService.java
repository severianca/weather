package com.example.mariaa.weather.network;

import com.example.mariaa.weather.model.MainWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    @GET("/data/2.5/weather?appid=9f218f4c039f56e0dcfbada94c1215a5")
    Call<MainWeather> getWeather(@Query("q") String city);
    @GET("/data/2.5/weather?appid=9f218f4c039f56e0dcfbada94c1215a5")
    Call<MainWeather> getWeatherByLocation(@Query("lon") Double longitude, @Query("lat") Double latitude);
}
