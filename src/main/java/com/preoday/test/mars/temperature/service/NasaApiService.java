package com.preoday.test.mars.temperature.service;

import java.io.IOException;

public interface NasaApiService {

    Double getAverageTemperature(String sol) throws IOException;

}
