package com.example.mariaa.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/data/2.5/weather?q=city&appid=9f218f4c039f56e0dcfbada94c1215a5")
    Call <MainWeather> getMyGSON (@Query("city") String city);

}
