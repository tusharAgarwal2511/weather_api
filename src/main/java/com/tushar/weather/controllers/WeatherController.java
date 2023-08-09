package com.tushar.weather.controllers;

import com.tushar.weather.exceptions.ResourceNotFoundException;
import com.tushar.weather.models.Weather;
import com.tushar.weather.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/weather/cities")
@Tag(name = "Weather")
public class WeatherController
{
	@Autowired
	private WeatherService weatherService;

	@CrossOrigin
	@GetMapping("/{cityName}")
	@Operation(description = "Get endpoint for Weather", summary = "Get the weather details of any city")
	public ResponseEntity<Weather> getWeatherFromCities(@PathVariable("cityName") String cityName) throws IOException, ResourceNotFoundException
	{
		Weather weather = weatherService.getWeatherFromCities(cityName);
		return new ResponseEntity<Weather>(weather, HttpStatus.OK);
	}
}
