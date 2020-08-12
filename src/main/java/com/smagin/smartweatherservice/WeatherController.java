package com.smagin.smartweatherservice;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class WeatherController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/weather/{city}")
    public WeatherResponse weatherByCityOnDate(@PathVariable("city") Integer city
                                               /*@PathVariable("date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDate localDate*/){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/weather?id="
                + city + "&appid=1d17116b3ac212464b3dfc2f189f1aa5", String.class);
        System.out.println(forEntity.getBody());
        JSONObject jsonObject  = new JSONObject(forEntity.getBody());
        jsonObject.get("base");
        System.out.println("!!!!!!!!!"+ jsonObject.get("base"));
        return null;
    }

    @Configuration
    public static class WebConfig{
        @Bean
        public RestTemplate restTemplate(){
            return  new RestTemplate();
        }
    }
}
