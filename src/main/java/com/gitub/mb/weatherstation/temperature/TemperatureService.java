package com.gitub.mb.weatherstation.temperature;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;
    private WeatherWebClientService weatherService;


    public TemperatureService(TemperatureRepository temperatureRepository, WeatherWebClientService weatherService) {
        this.temperatureRepository = temperatureRepository;
        this.weatherService = weatherService;

    }

    @Cacheable(cacheNames = "retrieveTemperature")
    public WeatherPoint retrieveTemperature()  {
        return temperatureRepository.findTopByOrderByIdDesc()
                .orElse(weatherService.retrieveWeatherPointFromApi());
    }

    public void addTemperature(WeatherPoint weatherPoint) {
        temperatureRepository.save(weatherPoint);
    }
}
