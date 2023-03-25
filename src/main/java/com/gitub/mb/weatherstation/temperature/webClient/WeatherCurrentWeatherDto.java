package com.gitub.mb.weatherstation.temperature.webClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class WeatherCurrentWeatherDto {
    private double temperature;
    private LocalDateTime time;
}
