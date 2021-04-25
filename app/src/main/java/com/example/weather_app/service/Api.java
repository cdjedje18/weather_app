package com.example.weather_app.service;

import com.example.weather_app.model.WeatherList;
import com.example.weather_app.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("weather")
    Call<WeatherResponse> getLocationWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("appid") String appId
    );

    @GET("group")
    Call<WeatherList> getCitiesWeather(
            @Query("id") String id,
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("appid") String appId
    );
}
