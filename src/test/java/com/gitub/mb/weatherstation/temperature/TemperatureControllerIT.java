package com.gitub.mb.weatherstation.temperature;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TemperatureControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/add_single_weather_point.sql")
    @DisplayName("Should return 200 status when get temperature")
    public void shouldReturn200WhenGetTemperature() throws Exception {
        mockMvc.perform(get("/temperature"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(4.0))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 201 status when post temperature")
    public void shouldReturn201WhenPostTemperature() throws Exception {
        WeatherPoint weatherPoint = new WeatherPoint(7.44, Timestamp.valueOf(LocalDateTime.now()));

        mockMvc.perform(post("/temperature")
                        .content(objectMapper.writeValueAsString(weatherPoint))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
