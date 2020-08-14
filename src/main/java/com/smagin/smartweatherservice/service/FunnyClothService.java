package com.smagin.smartweatherservice.service;

import com.smagin.smartweatherservice.exception.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FunnyClothService implements ClothService {
    public static final int WIND_MAGIC_NUMBER = 7;
    public static final int TEMP_MAGIC_NUMBER = 285;

    @Override
    public WeatherResponse getRecommendation(WeatherResponse response) {
        String recommendation =  "According current weather conditions please wear:" +getRecommendationForTemp(response) +
                " and " + getRecommendationForWind(response);
        response.setRecommendation(recommendation);
        return response;
    }

    private String getRecommendationForWind(WeatherResponse response){
        final double windSpeed = response.getWindSpeed();
        return windSpeed > WIND_MAGIC_NUMBER ? "SWEATSHIRT" : "T-SHIRT";
    }
    private String getRecommendationForTemp(WeatherResponse response){
        final double temp = response.getTemp();
        return temp > TEMP_MAGIC_NUMBER ? "T-SHIRT" : "JACKET";
    }
}
