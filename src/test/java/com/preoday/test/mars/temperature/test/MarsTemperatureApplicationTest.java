package com.preoday.test.mars.temperature.test;

import com.preoday.test.mars.temperature.auxiliar.ConvertTemperatures;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MarsTemperatureApplicationTest {

    private final Double AVERAGE_TEMPERATURE = -80.37682857142859;
    private final String SOL_PARAMETER = "414";
    private final Double AVERAGE_TEMPERATURE_SOL = -80.6854;
    private final String SOL_PARAMETER_WRONG = "222";

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testWithoutParameter() throws Exception {
        mockMvc.perform(
                get("/nasa/temperature")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.averageTemperature").value(AVERAGE_TEMPERATURE));
    }

    @Test
    public void testWithParameter() throws Exception {
        mockMvc.perform(
                get("/nasa/temperature")
                        .param("SOL", SOL_PARAMETER)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.averageTemperature").value(AVERAGE_TEMPERATURE_SOL));
    }

    @Test
    public void testWithWrongParameter() throws Exception {
        mockMvc.perform(
                get("/nasa/temperature")
                        .param("SOL", SOL_PARAMETER_WRONG)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void testConversionCelsiusToFahrenheit() {
        Assert.assertEquals(32.0, ConvertTemperatures.celsiusToFahrenheit(0.0), 0.001);
        Assert.assertEquals(50.0, ConvertTemperatures.celsiusToFahrenheit(10.0), 0.001);
        Assert.assertEquals(14.0, ConvertTemperatures.celsiusToFahrenheit(-10.0), 0.001);
    }
}