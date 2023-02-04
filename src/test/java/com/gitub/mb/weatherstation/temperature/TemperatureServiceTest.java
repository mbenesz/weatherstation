package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class TemperatureServiceTest {
    @Autowired
    TemperatureRepository temperatureRepository;
    TemperatureService temperatureService;

    @BeforeEach
    void setup() {
        temperatureService = new TemperatureService(temperatureRepository);
    }

    @Test
    void shouldRetrieveTemperatureFromRepo() {
        WeatherPoint temperature = temperatureService.retrieveTemperature();
        assertNotNull(temperature.getTemperature());

    }
}