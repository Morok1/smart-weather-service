package com.smagin.smartweatherservice;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3002")
public class WeatherController {
    @Value("${appId}")
    private String appId;

    @GetMapping("/weather/{city}")
    public WeatherResponse weatherByCityOnDate(@PathVariable("city") Integer city
                                               /*@PathVariable("date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDate localDate*/){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/weather?id="
                + city + "&appid="+appId, String.class);
        JSONObject jsonObject  = new JSONObject(forEntity.getBody());
        BiFunction<String, String, Double>  bigDecimalValue = (s1, s2) -> jsonObject.getJSONObject(s1).getDouble(s2);
        BiFunction<String, String, Integer>  getInteger = (s1, s2) -> jsonObject.getJSONObject(s1).getInt(s2);
        return new WeatherResponse(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"),
                bigDecimalValue.apply("main", "temp"),
                bigDecimalValue.apply("main", "temp_min"),
                bigDecimalValue.apply("main", "temp_max"),
                getInteger.apply("wind", "speed"));
    }
    @Autowired
    private RestTemplate restTemplate;


    @Configuration
    public static class WebConfig{
        @Bean
        public RestTemplate restTemplate(){
            return  new RestTemplate();
        }
    }
}
