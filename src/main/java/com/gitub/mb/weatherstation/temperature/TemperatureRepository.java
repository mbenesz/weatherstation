package com.gitub.mb.weatherstation.temperature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TemperatureRepository extends JpaRepository<WeatherPoint,Long> {
    Optional<WeatherPoint> findTopByOrderByIdDesc();
}
