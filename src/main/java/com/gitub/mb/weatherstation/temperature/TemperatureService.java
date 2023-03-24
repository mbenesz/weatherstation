package com.gitub.mb.weatherstation.temperature;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;

    }

    public WeatherPoint retrieveTemperature()  {
        return temperatureRepository.findTopByOrderByIdDesc()
                .orElseThrow(NoSuchElementException::new);
    }
}
