package com.smagin.smartweatherservice.service;

import com.smagin.smartweatherservice.exception.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(Integer city);
}
