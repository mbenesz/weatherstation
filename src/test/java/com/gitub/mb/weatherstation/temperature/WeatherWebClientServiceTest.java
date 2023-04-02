package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import org.junit.jupiter.api.AfterEach;
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
public class WeatherWebClientServiceTest {
    @Value(value = "${text.api.url}")
    String realApiUrl;

    private int port;
    private WireMockServer wireMockServer;
    private WeatherWebClientService weatherWebClientService;
    private TemperatureRepository temperatureRepository;

    @BeforeEach
    public void setup() {
        temperatureRepository = mock(TemperatureRepository.class);
        weatherWebClientService = new WeatherWebClientService(new ObjectMapper(), new RestTemplate(), temperatureRepository);

        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        port = wireMockServer.port();
    }

    @AfterEach
    public void stopServer() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Should map json response into WeatherPoint")
    public void shouldMapJsonResponseToWeatherPoint() throws IOException {
        //given
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);

        //when
        WeatherPoint weatherPoint = weatherWebClientService.mapStringToWeatherPoint(content);

        //then
        assertNotNull(weatherPoint);
        assertEquals(WeatherPoint.class, weatherPoint.getClass());
    }

    @Test
    @DisplayName("Should return WeatherPont when call wiremock API")
    public void shouldReturnWeatherPointWhenCallApi() throws Exception {
        //given
        String apiUrl = "http://localhost:" + port + "/some/thing";
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);

        configureFor("localhost", port);
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse().withBody(content)));

        //when
        WeatherPoint weatherPoint = weatherWebClientService.retrieveWeatherPointFromApi(apiUrl);

        //then
        assertNotNull(weatherPoint);
        verify(getRequestedFor(urlEqualTo("/some/thing")));
    }

    @Test
    @DisplayName("Should save json response from wiremock Api to repository")
    public void shouldSaveResponseFromApiToRepository() throws IOException {
        //given
        String apiUrl = "http://localhost:" + port + "/some/thing";
        Path filePath = Path.of("./src/test/resources/response1.json");
        String content = Files.readString(filePath);

        configureFor("localhost", port);
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse().withBody(content)));

        //when
        weatherWebClientService.addRetrievedWeatherPoint(apiUrl);

        //then
        Mockito.verify(temperatureRepository, times(1)).save(ArgumentMatchers.any());
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

    @Test
    @DisplayName("Should return WeatherPont when call real API")
    public void shouldReturnWeatherPointWhenCallRealApi() throws Exception {
        //given
        String apiUrl = realApiUrl;

        //when
        WeatherPoint weatherPoint = weatherWebClientService.retrieveWeatherPointFromApi(apiUrl);

        //then
        assertNotNull(weatherPoint);
    }
}
