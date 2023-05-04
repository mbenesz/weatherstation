package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TemperatureServiceTest {

    private TemperatureRepository temperatureRepository;
    private TemperatureService temperatureService;
    private WeatherWebClientService weatherWebClientService;


    @BeforeEach
    void setup() {
        temperatureRepository = mock(TemperatureRepository.class);
        weatherWebClientService = mock(WeatherWebClientService.class);
        temperatureService = new TemperatureService(temperatureRepository, weatherWebClientService);
    }

    @Test
    @DisplayName("Should retrieve temperature from repo")
    void shouldRetrieveTemperatureFromRepo() {
        //given
        Optional<WeatherPoint> weatherPoint = Optional.of(new WeatherPoint(Long.MAX_VALUE, 5.0, Timestamp.valueOf(LocalDateTime.now())));
        when(temperatureRepository.findTopByOrderByIdDesc()).thenReturn(weatherPoint);

        //when
        WeatherPoint weatherDataPoint = temperatureService.retrieveTemperature();

        //then
        verify(temperatureRepository, times(1)).findTopByOrderByIdDesc();
        assertNotNull(weatherDataPoint);
        assertNotNull(weatherDataPoint.getTemperature());
    }

    @Test
    @DisplayName("Should retrieve temperature from external Api when DB is empty")
    void shouldRetrieveTemperatureFromExternalApi() {
        //given
        WeatherPoint expectedWeatherPoint = new WeatherPoint(13.0, Timestamp.valueOf(LocalDateTime.now()));
        when(temperatureRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());
        when(weatherWebClientService.retrieveWeatherPointFromApi()).thenReturn(expectedWeatherPoint);

        //when
        WeatherPoint weatherDataPoint = temperatureService.retrieveTemperature();

        //then
        assertEquals(13.0, weatherDataPoint.getTemperature());
    }

    @Test
    @DisplayName("Should save temperature to repository")
    void shouldSaveTemperatureToRepository() {
        //given
        WeatherPoint weatherPoint = new WeatherPoint(Long.MAX_VALUE, 5.0, Timestamp.valueOf(LocalDateTime.now()));

        //when
        temperatureService.addTemperature(weatherPoint);

        //then
        verify(temperatureRepository, times(1)).save(weatherPoint);
    }

}