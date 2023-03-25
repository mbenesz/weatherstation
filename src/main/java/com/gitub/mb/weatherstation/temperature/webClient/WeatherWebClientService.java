package com.gitub.mb.weatherstation.temperature.webClient;

import com.gitub.mb.weatherstation.temperature.WeatherPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherWebClientService {
    private static final String EXTERNAL_API_URL = "https://api.open-meteo.com/v1/forecast?latitude=52.41&longitude=16.93&hourly=temperature_2m&current_weather=true";

    public WeatherPoint retrieveTemperatureFromExternalApi() {

        RestTemplate restTemplate = new RestTemplate();
        WeatherDto response = restTemplate.getForObject(EXTERNAL_API_URL, WeatherDto.class);

        return new WeatherPoint(response.getCurrent_weather().getTemperature(),response.getCurrent_weather().getTime());
    }
}
