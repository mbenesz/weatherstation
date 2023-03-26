package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TemperatureServiceTest {

    @Test
    @DisplayName("Should retrieve temperature from repo")
    void shouldRetrieveTemperatureFromRepo() {
        //given
        TemperatureRepository temperatureRepository = mock(TemperatureRepository.class);
        TemperatureService temperatureService = new TemperatureService(temperatureRepository);

        Optional<WeatherPoint> weatherPoint = Optional.of(new WeatherPoint(Long.MAX_VALUE, 5.0, LocalDateTime.now()));
        given(temperatureRepository.findTopByOrderByIdDesc()).willReturn(weatherPoint);

        //when
        WeatherPoint weatherDataPoint = temperatureService.retrieveTemperature();

        //then
        verify(temperatureRepository,times(1)).findTopByOrderByIdDesc();
        assertNotNull(weatherDataPoint);
        assertNotNull(weatherDataPoint.getTemperature());
    }
}