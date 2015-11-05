package fr.rtone.meteo.model;

import java.util.List;

/**
 * Created by sylvain on 05/11/15.
 */
public class OpenWeatherMap {
    public String name, cod, message;
    public OpenWeatherMain main;
    public List<OpenWeatherWeather> weather;
}
