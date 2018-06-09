package com.example.mariaa.weather;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class App {

  private static Retrofit getRetrofitInstance(){
      return new Retrofit.Builder()
              .baseUrl("http://api.openweathermap.org/data/2.5/weather?q=")
              .addConverterFactory(GsonConverterFactory.create())
              .build();
  }


}
