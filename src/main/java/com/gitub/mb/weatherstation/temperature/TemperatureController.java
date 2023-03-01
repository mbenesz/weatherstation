package com.gitub.mb.weatherstation.temperature;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
// ******** opcja 1 *************
//  @GetMapping("/temperature")
//  public ResponseEntity<WeatherPoint> getTemperature() {
//    WeatherPoint weatherPoint = temperatureService.retrieveTemperature();
//    if(weatherPoint == null)
//      return ResponseEntity.notFound().build();
//
//    return ResponseEntity.ok(weatherPoint);
//
//  }

//    ********** opcja 2 ************
//    @GetMapping("/temperature")                             // gdyby service zwracał Optionala to można tak:
//  public ResponseEntity<WeatherPoint> getTemperature() {
//      return temperatureService.retrieveTemperature()
//              .map(weatherPoint->ResponseEntity.ok(weatherPoint))
//              .orElse(ResponseEntity.notFound().build());
//  }

  // ************ opcja 3 ************
//  stworzyc klase DataNotFoundExc impl EntityNotFoundException
//  stworzyc advise i rzucic wyjatkiem orElseThrow w metodzie klasy Service
  // ta metoda bedzie nalepsze bo nie trzeba byedzie obslugowac tego wjatku w kontrolerze


  @PostMapping("/temperature")
  @ResponseStatus(HttpStatus.CREATED)
  public void postTemperature() {

  }
  
}
