package com.gitub.mb.weatherstation.temperature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class OpenweathermapImpl implements WeatherWebClientService {

    private final OpenWeathermapMapper openWeathermapMapper;
    private final RestTemplate restTemplate;
    private final TemperatureRepository temperatureRepository;
    private final String apiUrl;

    public OpenweathermapImpl(OpenWeathermapMapper openWeathermapMapper, RestTemplate restTemplate,
                              TemperatureRepository temperatureRepository, @Value("${text.api.url}") String apiUrl) {
        this.openWeathermapMapper = openWeathermapMapper;
        this.restTemplate = restTemplate;
        this.temperatureRepository = temperatureRepository;
        this.apiUrl = apiUrl;
    }

    @Override
    public WeatherPoint retrieveWeatherPointFromApi() {
        String response = restTemplate.getForObject(apiUrl, String.class);
        WeatherPoint weatherPoint = openWeathermapMapper.mapStringToWeatherPoint(response);
        return temperatureRepository.save(weatherPoint);
    }

}
