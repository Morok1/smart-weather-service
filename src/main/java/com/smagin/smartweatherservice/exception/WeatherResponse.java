package com.smagin.smartweatherservice.exception;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponse {
    private  String generalDescription;
    private  double temp;
    private  double minTemp;
    private  double maxTemp;
    private  double windSpeed;
    private  String name;
    private  String recommendation;
}
