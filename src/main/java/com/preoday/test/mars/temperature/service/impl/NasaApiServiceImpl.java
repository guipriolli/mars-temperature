package com.preoday.test.mars.temperature.service.impl;

import com.preoday.test.mars.temperature.auxiliar.ConvertTemperatures;
import com.preoday.test.mars.temperature.auxiliar.JsonReader;
import com.preoday.test.mars.temperature.service.NasaApiService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("nasaApiService")
public class NasaApiServiceImpl implements NasaApiService {

    @Value("${nasa.apikey}")
    private String key;

    @Override
    public Double getAverageTemperature(String sol) throws IOException {

        Double averageTemperature = 0.0;

        //Get data from Nasa
        JSONObject jsonNasa = connectNasaAPI();

        //Get SOL Keys
        JSONArray solKeys = jsonNasa.getJSONArray("sol_keys");

        //If paremeter "sol" is null or empty,
        if (sol == null || sol.isEmpty()) {

            //Total of keys
            Integer total = solKeys.length();

            //For each key
            for (int i = 0; i < total; i++) {
                String solKey = solKeys.getString(i);
                averageTemperature += jsonNasa.getJSONObject(solKey).getJSONObject("AT").getDouble("av");
            }

            averageTemperature /= solKeys.length();

            //If parameter "sol" has any value and contains in jsonObject
        } else if (jsonNasa.has(sol)) {
            averageTemperature = jsonNasa.getJSONObject(sol).getJSONObject("AT").getDouble("av");
        } else {
            throw new RuntimeException("SOL not found.");
        }

        return ConvertTemperatures.celsiusToFahrenheit(averageTemperature);
    }

    private JSONObject connectNasaAPI() throws IOException {

        String targetUrl = "https://api.nasa.gov/insight_weather/"
                + "?feedtype=json"
                + "&ver=1.0"
                + "&api_key=" + key;

        return JsonReader.readJsonFromUrl(targetUrl);
    }
}
