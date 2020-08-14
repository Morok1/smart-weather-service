package com.smagin.smartweatherservice.service;

import com.smagin.smartweatherservice.exception.WeatherResponse;

public interface ClothService {
    WeatherResponse getRecommendation(WeatherResponse response);
}
