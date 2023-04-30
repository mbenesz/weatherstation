package com.gitub.mb.weatherstation.temperature;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;
    private WeatherWebClientService weatherService;
    private OpenweathermapImpl openweathermap;

    public TemperatureService(TemperatureRepository temperatureRepository, WeatherWebClientService weatherService, OpenweathermapImpl openweathermap) {
        this.temperatureRepository = temperatureRepository;
        this.weatherService = weatherService;
        this.openweathermap = openweathermap;
    }

    @Cacheable(cacheNames = "retrieveTemperature")
    public WeatherPoint retrieveTemperature()  {
        return temperatureRepository.findTopByOrderByIdDesc()
                .orElse(openweathermap.retrieveWeatherPointFromApi());
    }

    public void addTemperature(WeatherPoint weatherPoint) {
        temperatureRepository.save(weatherPoint);
    }
}
