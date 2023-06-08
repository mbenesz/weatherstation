package com.gitub.mb.weatherstation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
@EnableCaching
public class WeatherstationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherstationApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("retrieveTemperature");
	}
	@Bean
	public StringBuilder stringBuilder() {
		return new StringBuilder();
	}
	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

}
