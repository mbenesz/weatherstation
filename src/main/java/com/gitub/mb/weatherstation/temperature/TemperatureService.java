package com.gitub.mb.weatherstation.temperature;

import org.springframework.stereotype.Service;

@Service
public class TemperatureService {
    private TemperatureRepository temperatureRepository;

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;

    }

    public WeatherPoint retrieveTemperature()  {
        return new WeatherPoint(null,null);//temperatureRepository.findTopByOrderByIdDesc().orElseThrow(RuntimeException::new);
    }
}
