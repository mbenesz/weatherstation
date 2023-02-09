package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TemperatureControllerTest {

    private TemperatureController temperatureController;
    private TemperatureService temperatureService;


    @BeforeEach
    void setup() {
        temperatureService = mock(TemperatureService.class);
        temperatureController = new TemperatureController(temperatureService);
    }

    @Test
    @DisplayName("Should retrieve temperature from underlying service")
    void shouldRetrieveTemperatureFromUnderlyingService() {
        given(temperatureService.retrieveTemperature())
                .willReturn(new WeatherPoint(Long.MAX_VALUE,4.0));
        WeatherPoint weatherDataPoint = temperatureController.getTemperature();
        verify(temperatureService, times(1)).retrieveTemperature();
        assertEquals(4.0,weatherDataPoint.getTemperature());
    }
}