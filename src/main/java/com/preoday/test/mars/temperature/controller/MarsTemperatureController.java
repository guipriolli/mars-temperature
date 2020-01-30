package com.preoday.test.mars.temperature.controller;

import com.preoday.test.mars.temperature.dto.TemperatureDto;
import com.preoday.test.mars.temperature.service.NasaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.IIOException;
import java.io.IOException;

@RestController
@RequestMapping(path = "/nasa/temperature")
public class MarsTemperatureController {

    @Autowired
    NasaApiService nasaApiService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TemperatureDto> getAverageTemperature(@RequestParam(name = "SOL", required = false) String sol) throws IOException {
        try {
            Double averageTemperature = nasaApiService.getAverageTemperature(sol);
            TemperatureDto temperatureDto = new TemperatureDto(averageTemperature);
            return ResponseEntity.ok(temperatureDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        } catch (IIOException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
