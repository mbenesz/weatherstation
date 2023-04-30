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
public interface WeatherWebClientService {

    WeatherPoint retrieveWeatherPointFromApi();

    WeatherPoint mapStringToWeatherPoint(String response);

}
