package com.gitub.mb.weatherstation.temperature;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;
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
  private Timestamp created;

  public WeatherPoint(Double temperature, Timestamp created) {
    this.temperature = temperature;
    this.created = created;
  }
}