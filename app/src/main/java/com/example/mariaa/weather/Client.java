package com.example.mariaa.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

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
