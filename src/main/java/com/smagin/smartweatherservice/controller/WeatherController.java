package com.smagin.smartweatherservice.controller;

import com.smagin.smartweatherservice.data.WeatherSearchForm;
import com.smagin.smartweatherservice.exception.WeatherResponse;
import com.smagin.smartweatherservice.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@RestController
@CrossOrigin(origins = "http://localhost:3001")
@Slf4j
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class WeatherController {
    private final WeatherService weatherService;

    @RequestMapping(value = "/",  method = RequestMethod.GET)
    public ModelAndView weatherByCity(){
        return new ModelAndView("weather_search");
    }


    @RequestMapping(value = "/weather_search",  method = RequestMethod.GET)
    public ModelAndView weatherByCityOnDate(){
        return new ModelAndView("weather_search", "searchForm", new WeatherSearchForm());
    }

    @RequestMapping(value = "/weather_search", method = RequestMethod.POST)
    @ExceptionHandler(Exception.class)
    public ModelAndView searchWeather(@ModelAttribute("searchForm") WeatherSearchForm form, BindingResult bindingResult) {
        log.debug("Received request to search weather {}, with result={}", form, bindingResult);
        String view = "weather_search";
        ModelMap model = new ModelMap();

        if(form.getCity().trim().length() != 6){
            throw new RuntimeException("Invalid city code!");
        }
        if (!"".equals(form.getCity())) {
            WeatherResponse response =  weatherService.getWeather(Integer.valueOf(form.getCity()));
            if (response != null) {
                view = "weather_summary";
                model.addAttribute("temp", response.getTemp());
                model.addAttribute("minTemp", response.getMinTemp());
                model.addAttribute("maxTemp", response.getMaxTemp());
                model.addAttribute("wind", response.getWindSpeed());
                model.addAttribute("info", response.getGeneralDescription());
                model.addAttribute("name", response.getName());
                model.addAttribute("recommendation", response.getRecommendation());
            }
        }
        return new ModelAndView(view, model);
    }


    private ModelMap getModelMap(WeatherResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute(  "generalDescription", response.getGeneralDescription());
        map.addAttribute(  "temp", response.getTemp());
        map.addAttribute(  "minTemp", response.getMinTemp());
        map.addAttribute(  "maxTemp", response.getMaxTemp());
        map.addAttribute(  "wind", response.getWindSpeed());
        return map;
    }




}

