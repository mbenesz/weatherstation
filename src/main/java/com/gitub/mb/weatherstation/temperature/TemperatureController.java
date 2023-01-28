package com.gitub.mb.weatherstation.temperature;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {
  @GetMapping("/temperature")
  WeatherDataPoint getTemperature() {
    return new WeatherDataPoint();
  }
}
