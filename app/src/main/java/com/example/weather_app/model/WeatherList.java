package com.example.weather_app.model;

import com.example.weather_app.model.common.Weather;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WeatherList {

    @SerializedName("list")
    private List<WeatherResponse> weatherList;

    public List<WeatherResponse> getWeatherList() {
        return weatherList;
    }
}
