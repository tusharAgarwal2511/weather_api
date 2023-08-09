package com.tushar.weather.services;

import com.tushar.weather.exceptions.ResourceNotFoundException;
import com.tushar.weather.models.Weather;

import java.io.IOException;

public interface WeatherService
{
	Weather getWeatherFromCities(String cityName) throws IOException, ResourceNotFoundException;
}
