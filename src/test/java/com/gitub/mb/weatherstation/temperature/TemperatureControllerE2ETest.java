package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TemperatureControllerE2ETest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired TemperatureRepository temperatureRepository;
    private WireMockServer wireMockServer;


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
    @DisplayName("Should create and then return created temperature")
    public void shouldCreateAndThenReturnCreatedTemperature() throws Exception {
        //given
        String temperatureEndPoint = "http://localhost:" + port + "/temperature";
        WeatherPoint weatherPoint = new WeatherPoint(4.0, Timestamp.valueOf(LocalDateTime.now()));

        //when
        ResponseEntity<WeatherPoint> postResponse = restTemplate.postForEntity(temperatureEndPoint, weatherPoint, WeatherPoint.class);
        ResponseEntity<WeatherPoint> getResponse = restTemplate.getForEntity(temperatureEndPoint, WeatherPoint.class);

        //then
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertThat(getResponse.getBody()).isNotNull();
        assertEquals(getResponse.getBody().getTemperature(), 4.0);
        assertEquals(getResponse.getBody().getId(), 1L);
    }

    @Test
    @DisplayName("Should return WeatherPont when call wiremock API")
    public void shouldReturnWeatherPointWhenCallApi() throws Exception {
        //given
        startWireMockServer();
        String apiUrl = "http://localhost:" + port + "/temperature";
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);

        OpenweathermapImpl weatherWebServiceImpl = new OpenweathermapImpl(new OpenWeathermapMapper(new ObjectMapper()),
                new RestTemplate(), temperatureRepository, apiUrl);

        stubFor(get(urlEqualTo("/temperature")).willReturn(aResponse().withBody(content)));

        //when
        WeatherPoint weatherPoint = weatherWebServiceImpl.retrieveWeatherPointFromApi();

        //then
        assertNotNull(weatherPoint);
        verify(getRequestedFor(urlEqualTo("/temperature")));

        stopWireMockServer();
    }

}