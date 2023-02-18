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
  @DisplayName("Should create and then return temperature")
  public void shouldCreateAndThenReturnTemperature() throws Exception {
    //given
    String temperatureEndPoint = "http://localhost:" + port + "/temperature";
    WeatherPoint weatherPoint = new WeatherPoint(1L, 4.0);

    //when
    ResponseEntity<WeatherPoint> postResponse = restTemplate.postForEntity(temperatureEndPoint, weatherPoint, WeatherPoint.class);
    ResponseEntity<WeatherPoint> getResponse = restTemplate.getForEntity(temperatureEndPoint, WeatherPoint.class);

    //then
    assertEquals(HttpStatus.CREATED,postResponse.getStatusCode());
    assertEquals(HttpStatus.OK,getResponse.getStatusCode());
    assertThat(getResponse.getBody()).isNotNull();
    assertNotNull(getResponse.getBody().getTemperature());
  }



}