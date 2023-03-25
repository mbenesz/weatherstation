package com.gitub.mb.weatherstation.temperature;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class WeatherPoint {
  @Id
  @GeneratedValue
  private Long id;
  private Double temperature;
  private LocalDateTime created;

  public WeatherPoint(Double temperature, LocalDateTime created) {
    this.temperature = temperature;
    this.created = created;
  }
}