package com.example.weather_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_app.model.City;
import com.example.weather_app.R;
import com.example.weather_app.model.WeatherList;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.ui.CityWeather;
import com.example.weather_app.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<City> cityList;
    private WeatherList weatherList;


    public CityAdapter(Context context, List<City> cityList, WeatherList weatherList) {
        this.context = context;
        this.cityList = cityList;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card_item, parent, false);
        return new CityHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WeatherResponse weatherResponse = null;

        for (WeatherResponse weatherResponse1 : weatherList.getWeatherList()) {
            if (weatherResponse1.getId() == cityList.get(position).getId()) {
                weatherResponse = weatherResponse1;
                break;
            }
        }
        Picasso.get().load(Constants.IMG_URL + weatherResponse.getWeather().get(0).getIcon() + "@4x.png").into(((CityHolder) holder).tempImg);
//        Log.i("img", Constants.IMG_URL+weatherResponse.getWeather().get(0).getIcon()+".png");

        City city = cityList.get(position);
        ((CityHolder) holder).temp.setText((int) weatherResponse.getMain().getTemp() + "");
//
        ((CityHolder) holder).city.setText(city.getName());
        ((CityHolder) holder).countryCode.setText(weatherResponse.getSys().getCountry());

       final WeatherResponse wr = weatherResponse;
        ((CityHolder) holder).cityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("clicked", cityList.get(position).getId() + "");

                Intent intent = new Intent(context, CityWeather.class);
                intent.putExtra("weather", wr);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}

class CityHolder extends RecyclerView.ViewHolder {
    TextView temp, city, countryCode;
    ImageView tempImg;
    CardView cityItem;

    CityHolder(View itemView) {
        super(itemView);
        temp = itemView.findViewById(R.id.temp);
        city = itemView.findViewById(R.id.city);
        countryCode = itemView.findViewById(R.id.country_code);
        tempImg = itemView.findViewById(R.id.temp_img);
        cityItem = itemView.findViewById(R.id.city_card);
    }
}




