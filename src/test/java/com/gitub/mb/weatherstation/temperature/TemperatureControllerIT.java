package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

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


    @Test
    @Sql("/add_single_weather_point.sql")
    @DisplayName("Should return 200 status when get temperature")
    public void shouldReturn200WhenGetTemperature() throws Exception {
        mockMvc.perform(get("/temperature"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return 201 status when post temperature")
    public void shouldReturn201WhenPostTemperature() throws Exception {
        mockMvc.perform(post("/temperature"))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
