package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;


@SpringBootTest
public class OpenweathermapImplTest {
    @Value(value = "${text.api.url}")
    String realApiUrl;

    private int port;
    private WireMockServer wireMockServer;
    private OpenweathermapImpl weatherWebServiceImpl;
    private TemperatureRepository temperatureRepository;

    @BeforeEach
    public void setup() {
        temperatureRepository = mock(TemperatureRepository.class);
        weatherWebServiceImpl = new OpenweathermapImpl(new ObjectMapper(), new RestTemplate(), realApiUrl);
    }

    private void startWireMockServer() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        port = wireMockServer.port();
        configureFor("localhost", port);
    }

    private void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Should map json response into WeatherPoint")
    public void shouldMapJsonResponseToWeatherPoint() throws IOException {
        //given
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);

        //when
        WeatherPoint weatherPoint = weatherWebServiceImpl.mapStringToWeatherPoint(content);

        //then
        assertNotNull(weatherPoint);
        assertEquals(WeatherPoint.class, weatherPoint.getClass());
    }

    @Test
    @DisplayName("Should return 200 ok when call real API")
    public void shouldReturn200OkWhenCallRealApi() throws Exception {
        //given
        String apiUrl = realApiUrl;

        //when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity(apiUrl, String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
