package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class OpenweathermapTest {
    @Value(value = "${text.api.url}")
    String fakeApiUrl;

    private int port;
    private WireMockServer wireMockServer;
    private OpenweathermapImpl weatherWebServiceImpl;
    private TemperatureRepository temperatureRepository;
    private RestTemplate restTemplate;
    private OpenWeathermapMapper openWeathermapMapper;

    @BeforeEach
    public void setup() {
        temperatureRepository = mock(TemperatureRepository.class);
        restTemplate = mock(RestTemplate.class);
        openWeathermapMapper = mock(OpenWeathermapMapper.class);
        weatherWebServiceImpl = new OpenweathermapImpl(openWeathermapMapper, restTemplate, temperatureRepository, fakeApiUrl);
    }

    @Test
    @DisplayName("Should map json response into WeatherPoint")
    public void shouldMapJsonResponseToWeatherPoint() throws IOException {
        //given
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);

        OpenWeathermapMapper openWeathermapMapper = new OpenWeathermapMapper(new ObjectMapper());

        //when
        WeatherPoint weatherPoint = openWeathermapMapper.mapStringToWeatherPoint(content);

        //then
        assertNotNull(weatherPoint);
        assertEquals(WeatherPoint.class, weatherPoint.getClass());
    }

    @Test
    @DisplayName("Sould retrieve and add new WeatherPoint when call Api")
    public void shouldRetrieveAndAddWeatherPointWhenCallExternalApi() throws IOException {
        //given
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);
        WeatherPoint expectedWeatherPoint = new WeatherPoint(4.0, Timestamp.valueOf(LocalDateTime.now()));
        when(restTemplate.getForObject(fakeApiUrl, String.class)).thenReturn(content);
        when(openWeathermapMapper.mapStringToWeatherPoint(content)).thenReturn(expectedWeatherPoint);
        when(temperatureRepository.save(expectedWeatherPoint)).thenReturn(expectedWeatherPoint);


        //when
        WeatherPoint weatherPoint = weatherWebServiceImpl.retrieveWeatherPointFromApi();

        //then
        assertNotNull(weatherPoint);
        verify(temperatureRepository, times(1)).save(expectedWeatherPoint);

    }

}
