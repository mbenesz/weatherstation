package com.gitub.mb.weatherstation.temperature;

import org.springframework.stereotype.Service;

@Service
public class TemperatureService {
    TemperatureRepository temperatureRepository;

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;

    }

    public WeatherPoint retrieveTemperature()  {
        return temperatureRepository.findTopByOrderByIdDesc()
                .orElseThrow(RuntimeException::new);
    }
}
