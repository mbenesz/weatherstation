package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TemperatureControllerE2ETest {

  @Value(value="${local.server.port}")
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


  @Test
  @DisplayName("Should return temperature")
  public void shouldReturnTemperature() throws Exception {
    ResponseEntity<WeatherPoint> response = restTemplate.getForEntity("http://localhost:" + port + "/temperature", WeatherPoint.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertNotNull(response.getBody().getTemperature());

  }

}