package com.gitub.mb.weatherstation.temperature.webClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class WeatherDto {
    private WeatherCurrentWeatherDto current_weather;
}
