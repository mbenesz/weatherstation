package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class WeatherWebClientService {
    // this filed to be use as a parameter in Controller
    private static final String EXTERNAL_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=Poznan&units=metric&appid=2dc1c049667bc96025ea0f43e05ef8db";


    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;



    public WeatherWebClientService(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public WeatherPoint retrieveWeatherPointFromApi(String url) {
        String response = restTemplate.getForObject(url, String.class);
        return mapStringToWeatherPoint(response);
    }

    private WeatherPoint mapStringToWeatherPoint(String response) {

        Object temperature = null;
        try {
            Map<String, Map<String, Object>> map = objectMapper.readValue(response, Map.class);
            temperature = map.get("main").get("temp");

        } catch (JsonProcessingException e) {
            e.getMessage();
        }

        return new WeatherPoint((Double) temperature, LocalDateTime.now());

    }
}
