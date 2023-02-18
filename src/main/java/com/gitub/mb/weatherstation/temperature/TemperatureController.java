package com.gitub.mb.weatherstation.temperature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {

  private final TemperatureService temperatureService;

  public TemperatureController(TemperatureService temperatureService) {
    this.temperatureService = temperatureService;
  }

  @GetMapping("/temperature")
  @ResponseStatus(HttpStatus.OK)
  public WeatherPoint getTemperature() {
    return temperatureService.retrieveTemperature();
  }

  @PostMapping("/temperature")
  @ResponseStatus(HttpStatus.CREATED)
  public WeatherPoint postTemperature() {
    return null;
  }


}
