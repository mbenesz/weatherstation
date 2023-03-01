package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@SpringBootTest
class TemperatureServiceTest {

    @Test
    @DisplayName("Should retrieve temperature from repo")       // czy mozna zrezygnowac juz z mocka?
    void shouldRetrieveTemperatureFromRepo() {
        //given
        TemperatureRepository temperatureRepository = mock(TemperatureRepository.class);
        TemperatureService temperatureService = new TemperatureService(temperatureRepository);

        Optional<WeatherPoint> weatherPoint = Optional.of(new WeatherPoint(Long.MAX_VALUE, 5.0));
        given(temperatureRepository.findTopByOrderByIdDesc()).willReturn(weatherPoint);

        //when
        WeatherPoint weatherDataPoint = temperatureService.retrieveTemperature();

        //then
        verify(temperatureRepository,times(1)).findTopByOrderByIdDesc();
        assertNotNull(weatherDataPoint);
        assertNotNull(weatherDataPoint.getTemperature());
    }
}