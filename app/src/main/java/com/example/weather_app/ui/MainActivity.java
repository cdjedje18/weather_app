package com.example.weather_app.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weather_app.R;
import com.example.weather_app.adapter.CityAdapter;
import com.example.weather_app.model.City;
import com.example.weather_app.model.WeatherList;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.model.common.Weather;
import com.example.weather_app.service.Api;
import com.example.weather_app.utils.Constants;
import com.example.weather_app.utils.LocationTracker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CardView currentLocation;
    LinearLayout infoTextWrapper;
    TextView infoText, locationBtnTxt;
    List<City> cities;
    List<String> codes;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.cities_rcv);
        currentLocation = (CardView) findViewById(R.id.current_location);
        infoTextWrapper = (LinearLayout) findViewById(R.id.info_text_wrapper);
        infoText = (TextView) findViewById(R.id.info_text);
        locationBtnTxt = (TextView) findViewById(R.id.current_location_btn);


        cities = new ArrayList<>();
        codes = new ArrayList<>();

        cities.add(new City(2267057, "Lisboa"));
        cities.add(new City(3117735, "Madrid"));
        cities.add(new City(2988507, "Paris"));
        cities.add(new City(2950159, "Berlim"));
        cities.add(new City(2618425, "Copenhaga"));
        cities.add(new City(5134295, "Roma"));
        cities.add(new City(2643743, "Londres"));
        cities.add(new City(5344157, "Dublin"));
        cities.add(new City(3067696, "Praga"));
        cities.add(new City(2761369, "Viena"));

        for (City city : cities) {
            codes.add(String.valueOf(city.getId()));
        }

        String codesParams = codes.stream().collect(Collectors.joining(","));
//        Log.i("params",codesParams);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api mApi = retrofit.create(Api.class);

        Call<WeatherList> call = mApi.getCitiesWeather(codesParams, Constants.LANG, Constants.UNITS, Constants.API_KEY);

        call.enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {
                Log.i("Response", response.body().getWeatherList().get(0).getName());

                infoTextWrapper.setVisibility(View.GONE);
                currentLocation.setVisibility(View.VISIBLE);

                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                recyclerView.setAdapter(new CityAdapter(MainActivity.this, cities, response.body()));
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                infoText.setText("Erro ao Carregar");
            }
        });

    }

    public void seeCurrentLocation(View view){
        LocationTracker locationTracker = new LocationTracker(MainActivity.this);
        if(locationTracker.canGetLocation()){

            locationBtnTxt.setText("A carregar localização...");

            double latitude = locationTracker.getLatitude();
            double longitude = locationTracker.getLongitude();
            Log.i("Current", "Lat: "+latitude+" "+"Long: "+longitude);

            if((int)latitude != 0 && (int)longitude!=0){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api mApi = retrofit.create(Api.class);

                Call<WeatherResponse> call = mApi.getLocationWeather(String.valueOf(latitude),String.valueOf(longitude), Constants.LANG, Constants.UNITS, Constants.API_KEY);

                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
//                    Log.i("Response", response.body().getName());
                        locationBtnTxt.setText("Minha Localização");

                        Intent intent = new Intent(MainActivity.this, CityWeather.class);
                        intent.putExtra("weather", response.body());
                        (MainActivity.this).startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        infoText.setText("Erro ao Carregar");
                    }
                });
            }else{
                locationBtnTxt.setText("Minha Localização");
            }

        }else{
            locationBtnTxt.setText("Minha Localização");
//            locationTracker.showSettingsAlert();
        }
    }
}