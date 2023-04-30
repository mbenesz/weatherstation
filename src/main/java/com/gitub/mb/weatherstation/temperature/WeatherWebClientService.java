package com.gitub.mb.weatherstation.temperature;

import org.springframework.stereotype.Service;

@Service
public interface WeatherWebClientService {

    WeatherPoint retrieveWeatherPointFromApi();


}
