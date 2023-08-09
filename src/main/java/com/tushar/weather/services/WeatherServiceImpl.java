package com.tushar.weather.services;

import com.tushar.weather.exceptions.ResourceNotFoundException;
import com.tushar.weather.models.Weather;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherServiceImpl implements WeatherService
{
	public static String fahrenheitToCelsius(String tempInC)
	{
		double celsius     = Double.parseDouble(tempInC);
		double fahrenheit  = (celsius * 9 / 5) + 32;
		String fahrenheitS = String.valueOf(fahrenheit);
		return fahrenheitS;
	}

	public static String kmToMi(String visibilityInKm)
	{

		double kilometers = Double.parseDouble(visibilityInKm);
		double miles      = kilometers * 0.621371;
		String milesS     = String.valueOf(miles);
		return milesS;
	}

	@Override
	public Weather getWeatherFromCities(String cityName) throws IOException, ResourceNotFoundException
	{
		String url = "https://in.search.yahoo.com/search;_ylt=AwrPrM9sk7JkU.sjZzK7HAx.;" +
			     "_ylc=X1MDMjExNDcyMzAwMwRfcgMyBGZyA3NmcARmcjIDc2ItdG9wBGdwcmlkAwRuX3JzbHQDMARuX3N1Z2" +
			     "cDMARvcmlnaW4DaW4uc2VhcmNoLnlhaG9vLmNvbQRwb3MDMARwcXN0cgMEcHFzdHJsA" +
			     "zAEcXN0cmwDMTQEcXVlcnkDd2VhdGhlciUyMG11bWJhaQR0X3N0bXADMTY4OTQzMDcyMw--" +
			     "?p=weather+" + cityName + "&fr2=sb-top&fr=sfp&vm=r";

		Document doc         = Jsoup.connect(url).get();
		Weather  weatherData = new Weather();
		String   city        = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherImage.m-0 > div.cptn > div > p.txt").text();

		if (city == "")
		{
			throw new ResourceNotFoundException("Weather", "city", cityName);
		}
		else
		{
			String currTempC          = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherImage.m-0 > div.cptn-bot > div.cptn-ctnt > div > div > span.currTemp").text();
			String currTempF          = fahrenheitToCelsius(currTempC);
			String stateAndCountry    = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherImage.m-0 > div.cptn > div > p.subTxt").text();
			String condition          = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherImage.m-0 > div.cptn-bot > div.main-cond > span").text();
			String highTempCTemp      = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherImage.m-0 > div.cptn-bot > div.cptn-ctnt > div > span > span.high").text();
			String highTempC          = highTempCTemp.replaceAll("[^0-9.]", "");
			String highTempF          = fahrenheitToCelsius(highTempC);
			String lowTempCTemp       = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherImage.m-0 > div.cptn-bot > div.cptn-ctnt > div > span > span.low").text();
			String lowTempC           = lowTempCTemp.replaceAll("[^0-9.]", "");
			String lowTempF           = fahrenheitToCelsius(lowTempC);
			String feelsLikeCTemp     = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherTextList.bb-1.toggle.plr-10 > ul.wthr-list.gnrl > li:nth-child(1) > span > span").text();
			String feelsLikeC         = feelsLikeCTemp.replaceAll("[^0-9.]", "");
			String feelsLikeF         = fahrenheitToCelsius(feelsLikeC);
			String visibilityInKmTemp = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherTextList.bb-1.toggle.plr-10 > ul.wthr-list.gnrl > li:nth-child(2) > span > span").text();
			String visibilityKm       = visibilityInKmTemp.replaceAll("[^0-9.]", "");
			String visibilityMi       = kmToMi(visibilityKm);
			String humidity           = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherTextList.bb-1.toggle.plr-10 > ul.wthr-list.gnrl > li:nth-child(3) > span > span").text();
			String dewPointCTemp      = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherTextList.bb-1.toggle.plr-10 > ul.wthr-list.gnrl > li:nth-child(5) > span > span").text();
			String dewPointC          = dewPointCTemp.replaceAll("[^0-9.]", "");
			String dewPointF          = fahrenheitToCelsius(dewPointC);
			String uvIndex            = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.compWeatherTextList.bb-1.toggle.plr-10 > ul.wthr-list.gnrl > li:nth-child(4) > span > span").text();
			String moonPhase          = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.layoutCenter > div.compWeatherSunMoon.small.d-ib.toggle.pb-10 > div > div.moon-container > div").text();
			String windSpeedKmphTemp  = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.layoutCenter > div.compWeatherBarometer.small.d-ib.toggle.pb-10 > div.subInfo.wind > span.txt > span").text();
			String windSpeedKmph      = windSpeedKmphTemp.replaceAll("[^0-9.]", "");
			String windSpeedMph       = kmToMi(windSpeedKmph);
			String sunrise            = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.layoutCenter > div.compWeatherSunMoon.small.d-ib.toggle.pb-10 > div > span.sunrise").text();
			String sunset             = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.layoutCenter > div.compWeatherSunMoon.small.d-ib.toggle.pb-10 > div > span.sunset").text();
			String barometer          = doc.select("#web > ol.reg.searchCenterTop > li > div > div > div.layoutCenter > div.compWeatherBarometer.small.d-ib.toggle.pb-10 > div.subInfo.barometer > div.subInfoTxt > span > span").text();

			weatherData.setCity(city);
			weatherData.setCountry(stateAndCountry);
			weatherData.setCondition(condition);
			weatherData.setCurrTempC(currTempC);
			weatherData.setCurrTempF(currTempF);
			weatherData.setHighTempC(highTempC);
			weatherData.setHighTempF(highTempF);
			weatherData.setLowTempC(lowTempC);
			weatherData.setLowTempF(lowTempF);
			weatherData.setFeelsLikeC(feelsLikeC);
			weatherData.setFeelsLikeF(feelsLikeF);
			weatherData.setVisibilityKm(visibilityKm);
			weatherData.setVisibilityMi(visibilityMi);
			weatherData.setHumidity(humidity);
			weatherData.setDewPointC(dewPointC);
			weatherData.setDewPointF(dewPointF);
			weatherData.setUvIndex(uvIndex);
			weatherData.setMoonPhase(moonPhase);
			weatherData.setWindSpeedKmph(windSpeedKmph);
			weatherData.setWindSpeedMph(windSpeedMph);
			weatherData.setSunrise(sunrise);
			weatherData.setSunset(sunset);
			weatherData.setBarometer(barometer);

			return weatherData;
		}


	}
}
