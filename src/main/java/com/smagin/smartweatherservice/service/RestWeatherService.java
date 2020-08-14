package com.smagin.smartweatherservice.service;

import com.smagin.smartweatherservice.exception.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class RestWeatherService implements WeatherService {
    private final RestTemplate restTemplate;
    private final ClothService clothService;
    @Value("${appId}")
    private String appId;

    @Override
    public WeatherResponse getWeather(Integer city) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/weather?id="
                + city + "&appid="+appId, String.class);
        JSONObject jsonObject  = new JSONObject(forEntity.getBody());
        BiFunction<String, String, Double>  bigDecimalValue = (s1, s2) -> jsonObject.getJSONObject(s1).getDouble(s2);
        BiFunction<String, String, Integer>  getInteger = (s1, s2) -> jsonObject.getJSONObject(s1).getInt(s2);
        return clothService.getRecommendation(buildWeatherResponse(jsonObject, bigDecimalValue, getInteger));
    }

    private WeatherResponse buildWeatherResponse(JSONObject jsonObject, BiFunction<String, String, Double> bigDecimalValue, BiFunction<String, String, Integer> getInteger) {
        return WeatherResponse
                .builder()
                .generalDescription(jsonObject.getJSONArray("weather")
                        .getJSONObject(0).getString("description"))
                .temp(bigDecimalValue.apply("main", "temp"))
                .minTemp(bigDecimalValue.apply("main", "temp_min"))
                .maxTemp(bigDecimalValue.apply("main", "temp_max"))
                .windSpeed(getInteger.apply("wind", "speed"))
                .name(jsonObject.getString("name"))
                .build();
    }

    private ModelMap getModelMap(JSONObject jsonObject, BiFunction<String, String, Double> bigDecimalValue, BiFunction<String, String, Integer> getInteger) {
        ModelMap map = new ModelMap();
        map.addAttribute(  "generalDescription", jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
        map.addAttribute(  "temp", jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
        map.addAttribute(  "minTemp", bigDecimalValue.apply("main", "temp_min"));
        map.addAttribute(  "maxTemp", bigDecimalValue.apply("main", "temp_max"));
        map.addAttribute(  "wind", getInteger.apply("wind", "speed"));
        return map;
    }
}
