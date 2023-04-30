package com.gitub.mb.weatherstation.temperature;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureService {
    private final TemperatureRepository temperatureRepository;
    private final WeatherWebClientService weatherService;


    public TemperatureService(TemperatureRepository temperatureRepository, WeatherWebClientService weatherService) {
        this.temperatureRepository = temperatureRepository;
        this.weatherService = weatherService;

    }

    @Cacheable(cacheNames = "retrieveTemperature")
    public WeatherPoint retrieveTemperature() {
        Optional<WeatherPoint> topByOrderByIdDesc = temperatureRepository.findTopByOrderByIdDesc();
        if(topByOrderByIdDesc.isPresent())
            return topByOrderByIdDesc.get();
        else
            return weatherService.retrieveWeatherPointFromApi();
    }

    public void addTemperature(WeatherPoint weatherPoint) {
        temperatureRepository.save(weatherPoint);
    }
}
