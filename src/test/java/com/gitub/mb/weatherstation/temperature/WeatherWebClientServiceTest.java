package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;


@SpringBootTest
public class WeatherWebClientServiceTest {

    private int port;
    private WeatherWebClientService weatherWebClientService;
    private TemperatureRepository temperatureRepository;

    @Before
    public void setup() {
        temperatureRepository = mock(TemperatureRepository.class);
        weatherWebClientService = new WeatherWebClientService(new ObjectMapper(), new RestTemplate(), temperatureRepository);
        port = wireMockRule.port();
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(port));

    @Test
    @DisplayName("Should return 200 ok when get on wiremock API")
    public void wiremock_with_junit_test() throws Exception {
        //given
        String apiUrl = "http://localhost:" + port + "/some/thing";
        configureFor("localhost", port);
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse().withBody("the weather is fine")));

        //when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String response = testRestTemplate.getForObject(apiUrl, String.class);

        //then
        assertEquals("the weather is fine", response);
        verify(getRequestedFor(urlEqualTo("/some/thing")));
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
    @DisplayName("Should return WeatherPont when call external API")
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
    @DisplayName("Should save json response from external Api to repository")
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
}
