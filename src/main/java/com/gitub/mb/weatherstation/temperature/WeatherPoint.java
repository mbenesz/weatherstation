package com.gitub.mb.weatherstation.temperature;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Value;

@Value
@Entity
public class WeatherPoint {
  @Id
  @GeneratedValue
  Long id;
  Double temperature;
}