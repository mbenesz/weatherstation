package com.gitub.mb.weatherstation.temperature;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ArduinoMapper {
    private final ArduinoConnect arduinoConnect;

    public ArduinoMapper(ArduinoConnect arduinoConnect) {
        this.arduinoConnect = arduinoConnect;
    }

    public WeatherPoint retrieveWeatherPointFromArduino() {
        StringBuilder measurments = arduinoConnect.getMeasurment();
        String s = measurments.toString().split("\n")[0];

        Double temperature = Double.valueOf(s);

        return new WeatherPoint(temperature, Timestamp.valueOf(LocalDateTime.now()));
    }

}
