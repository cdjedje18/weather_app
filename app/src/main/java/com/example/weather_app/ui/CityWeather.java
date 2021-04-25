package com.example.weather_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_app.R;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.utils.Constants;
import com.squareup.picasso.Picasso;

public class CityWeather extends AppCompatActivity {

    private WeatherResponse weatherResponse;
    TextView city, temp, tempDescription, tempMax, tempMin, windSpeed, humidity;
    ImageView tempIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        city = (TextView) findViewById(R.id.city_country_txt);
        temp = (TextView) findViewById(R.id.temp_txt);
        tempDescription = (TextView) findViewById(R.id.tem_description_txt);
        tempMax = (TextView) findViewById(R.id.temp_max_txt);
        tempMin = (TextView) findViewById(R.id.temp_min_txt);
        windSpeed = (TextView) findViewById(R.id.wind_speed_txt);
        humidity = (TextView) findViewById(R.id.humidity_txt);
        tempIcon = (ImageView) findViewById(R.id.temp_img_details);


        weatherResponse = (WeatherResponse) getIntent().getSerializableExtra("weather");

        city.setText(weatherResponse.getName() + "," + weatherResponse.getSys().getCountry());
        temp.setText((int) weatherResponse.getMain().getTemp() + " C");
        tempDescription.setText(weatherResponse.getWeather().get(0).getDescription());
        tempMax.setText((int) weatherResponse.getMain().getTempMax() + " C");
        tempMin.setText((int) weatherResponse.getMain().getTempMin() + " C");
        windSpeed.setText((int) weatherResponse.getWind().getSpeed() + "m/s");
        humidity.setText((int) weatherResponse.getMain().getHumidity() + " %");

        Log.i("loimg", Constants.IMG_URL + weatherResponse.getWeather().get(0).getIcon() + "@4x.png");

        Picasso.get().load(Constants.IMG_URL + weatherResponse.getWeather().get(0).getIcon() + "@4x.png").into(tempIcon);

    }
}