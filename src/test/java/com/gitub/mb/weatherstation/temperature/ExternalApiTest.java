package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.extension.responsetemplating.UrlPath;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class ExternalApiTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9090));

    @Test
    @DisplayName("Should return 200 ok when get on wiremock API")
    public void wiremock_with_junit_test() throws Exception {
        //given
 //       configureFor("localhost", 9090);
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse().withBody("the weather is fine")));
        //when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String response = testRestTemplate.getForObject("http://localhost:9090/some/thing", String.class);
        //then
        assertEquals("the weather is fine", response);
        verify(getRequestedFor(urlEqualTo("/some/thing")));
    }

    @Test
    @DisplayName("Should map json response sample into WeatherPoint")
    public void shouldReturnWeatherPointWhenCallApi() throws Exception {
        //given
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherWebClientService weatherWebClientService = new WeatherWebClientService(objectMapper,restTemplate);

        Path filePath = Path.of("./src/test/resources/response.json");
        String content = Files.readString(filePath);

        configureFor("localhost", 9090);
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse().withBody(content)));
        //when
        WeatherPoint weatherPoint = weatherWebClientService.retrieveWeatherPointFromApi("http://localhost:9090/some/thing");

        //then
        assertNotNull(weatherPoint);
        verify(getRequestedFor(urlEqualTo("/some/thing")));

    }


}
