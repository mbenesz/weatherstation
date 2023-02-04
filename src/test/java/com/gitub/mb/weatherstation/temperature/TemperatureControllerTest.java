package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@SpringBootTest
class TemperatureControllerTest {
    @Autowired
    TemperatureRepository temperatureRepository;

    private TemperatureController temperatureController;


    @BeforeEach
    void setup() {
        TemperatureService temperatureService = new TemperatureService(temperatureRepository);
        temperatureController = new TemperatureController(temperatureService);
    }

    @Test
    void shouldRetrieveTemperatureFromUnderlyingService() {

        WeatherPoint temperature = temperatureController.getTemperature();
        assertNotNull(temperature.getTemperature());

    }
}