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

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TemperatureControllerE2ETest {

  @Value(value="${local.server.port}")
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


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
    assertEquals(HttpStatus.CREATED,postResponse.getStatusCode());
    assertEquals(HttpStatus.OK,getResponse.getStatusCode());
    assertThat(getResponse.getBody()).isNotNull();
    assertEquals(getResponse.getBody().getTemperature(),4.0);
    assertEquals(getResponse.getBody().getId(),1L);
  }

}