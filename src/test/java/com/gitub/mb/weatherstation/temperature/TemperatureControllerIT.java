package com.gitub.mb.weatherstation.temperature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TemperatureControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return 200 when get temperature")
    public void shouldReturn200WhenGetTemperature() throws Exception {
        mockMvc.perform(get("/temperature")).andExpect(status().isOk());
    }
}
