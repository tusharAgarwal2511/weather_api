package com.tushar.weather.models;

import lombok.Data;

@Data
public class Weather
{
	private String city;
	private String country;
	private String condition;
	private String currTempC;
	private String currTempF;
	private String highTempC;
	private String lowTempC;
	private String highTempF;
	private String lowTempF;
	private String feelsLikeC;
	private String feelsLikeF;
	private String visibilityKm;
	private String visibilityMi;
	private String humidity;
	private String dewPointC;
	private String dewPointF;
	private String uvIndex;
	private String moonPhase;
	private String windSpeedKmph;
	private String windSpeedMph;
	private String sunrise;
	private String sunset;
	private String barometer;
}
