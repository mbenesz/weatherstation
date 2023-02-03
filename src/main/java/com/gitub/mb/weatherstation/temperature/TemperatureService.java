package com.gitub.mb.weatherstation.temperature;

import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    public TemperatureService(TemperatureRepository temperatureRepository) {

    }

    public WeatherPoint retrieveTemperature() {
        return new WeatherPoint(null,null);
    }
}
