package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
@Service
public class OpenWeathermapMapper {
    private ObjectMapper objectMapper;

    public OpenWeathermapMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
}
