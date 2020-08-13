package com.smagin.smartweatherservice;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class WeatherResponse {
    private final String generalDescription;
    private final double temp;
    private final double minTemp;
    private final double maxTemp;
    private final double windSpeed;
}
