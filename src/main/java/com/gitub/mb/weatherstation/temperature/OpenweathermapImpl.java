package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
@Service
public class OpenweathermapImpl implements WeatherWebClientService {
    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;
    private TemperatureRepository temperatureRepository;

    public OpenweathermapImpl(ObjectMapper objectMapper, RestTemplate restTemplate, TemperatureRepository temperatureRepository) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public WeatherPoint retrieveWeatherPointFromApi(@Value("${text.api.url}") String apiUrl) {
        String response = restTemplate.getForObject(apiUrl, String.class);
        return mapStringToWeatherPoint(response);
    }
    @Override
    public WeatherPoint mapStringToWeatherPoint(String response) {

        Object temperature = null;
        try {
            Map<String, Map<String, Object>> map = objectMapper.readValue(response, Map.class);
            temperature = map.get("main").get("temp");

        } catch (JsonProcessingException e) {
            e.getMessage();
        }

        return new WeatherPoint((Double) temperature, Timestamp.valueOf(LocalDateTime.now()));
    }
    @Override
    public void addRetrievedWeatherPoint(@Value("${text.api.url}") String apiUrl) {
        String response = restTemplate.getForObject(apiUrl, String.class);
        WeatherPoint weatherPoint = mapStringToWeatherPoint(response);
        temperatureRepository.save(weatherPoint);
    }
}
