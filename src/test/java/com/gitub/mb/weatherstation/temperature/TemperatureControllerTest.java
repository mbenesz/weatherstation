package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureControllerTest {

    private TemperatureController temperatureController;

    @BeforeEach
    void setup() {
        temperatureController = new TemperatureController();
    }

    @Test
    void shouldReturnTemperature() {
        WeatherDataPoint temperature = temperatureController.getTemperature();
        assertNotNull(temperature);
    }
}