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
        StringBuilder measurments = arduinoConnect.getMeasurments();
        String[] split = measurments.toString().split("\n");
        String temperature = split[1];  //[0] measurment is skipped, second is valid

        return new WeatherPoint(Double.valueOf(temperature), Timestamp.valueOf(LocalDateTime.now()));
    }

}
