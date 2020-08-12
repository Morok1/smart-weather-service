package com.smagin.smartweatherservice;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class WeatherController {
    @GetMapping("/{weather/{city}/{date}")
    public WeatherResponse weatherByCityOnDate(@PathVariable("city") String city,
                                               @PathVariable("date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDate localDate){
       return null;
    }
}
