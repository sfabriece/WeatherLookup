/*
 *  WeatherLookup - Performs a weather lookup.
 *      
 *  Copyright (C) 2010 Josh Walters
 *  URL: http://joshwalters.com
 *  
 *  This file is part of WeatherLookup.
 *
 *  WeatherLookup is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WeatherLookup is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WeatherLookup.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.joshwalters.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Obtains current weather data, as well as forecasts.
 * 
 * @author Josh Walters
 * @version 2.0
 * 
 */
public class WeatherLookup {

    /**
     * Stores the current weather conditions.
     */
    public WeatherCondition currentWeatherConditions;

    /**
     * Stores weather forecasts.
     */
    public Vector<WeatherCondition> futureWeatherConditions;

    /**
     * Stores the lookup service to use.
     */
    private WeatherLookupService lookupService;

    /**
     * The default lookup service to use.
     */
    private static WeatherLookupService defaultLookupService = WeatherLookupService.GOOGLE;

    /**
     * Default constructor. Sets the lookup service to default.
     */
    public WeatherLookup() {
	// Uses the default lookup service.
	this(defaultLookupService);
    }

    /**
     * Default constructor.
     */
    public WeatherLookup(WeatherLookupService lookupService) {
	currentWeatherConditions = new WeatherCondition();
	futureWeatherConditions = new Vector<WeatherCondition>();
	this.lookupService = lookupService;
    }

    /**
     * Gets the lookup service we are using.
     * 
     * @return
     */
    WeatherLookupService getLookupService() {
	return lookupService;
    }

    /**
     * Sets the lookup service.
     * 
     * @param lookupService
     */
    void setLookupService(WeatherLookupService lookupService) {
	this.lookupService = lookupService;
    }

    /**
     * Performs a weather lookup with a given zip code.
     * 
     * @param lookupKey
     * @throws WeatherLookupException
     */
    public void lookup(String zipCode) throws WeatherLookupException {
	try {
	    switch (lookupService) {
	    case GOOGLE:
		// Perform a lookup with Google weather service
		lookupGoogleWeather(zipCode);
		break;
	    case YAHOO:
		// Perform a lookup with MSN weather service
		lookupYahooWeather(zipCode);
		break;
	    default:
		// Throw if the service is not supported.
		throw new WeatherLookupException("Lookup service "
			+ lookupService + " not supported.");
	    }
	} catch (WeatherLookupException e) {
	    throw e;
	}
    }

    /**
     * Performs a weather lookup with a given zip code. Uses the Google weather
     * service.
     * 
     * @param lookupKey
     *            Zip code
     */
    private void lookupGoogleWeather(String zipCode)
	    throws WeatherLookupException {
	try {
	    // Open the Google URL that will have the weather data.
	    URL url = new URL("http://www.google.com/ig/api?weather=" + zipCode);

	    // Make the document builder for the file using the URL.
	    DocumentBuilder builder = DocumentBuilderFactory.newInstance()
		    .newDocumentBuilder();
	    Document doc = builder.parse(url.openStream());

	    // Check to see that we got a valid report result
	    NodeList nodes = doc.getElementsByTagName("problem_cause");
	    if (nodes.getLength() > 0) {
		// There was an error
		throw new WeatherLookupException(
			"Lookup failed. Possible bad key.");
	    }

	    // Check to see if we can get the city that the zip code is from
	    nodes = doc.getElementsByTagName("city");
	    if (nodes.getLength() > 0) {
		Element element = (Element) nodes.item(0);

		// Get the location
		String tokens[] = element.getAttribute("data").split(",");
		currentWeatherConditions.setLocation(tokens[0]);
	    }

	    // Get the current conditions.
	    nodes = doc.getElementsByTagName("current_conditions");
	    for (int i = 0; i < nodes.getLength(); i++) {
		Element element = (Element) nodes.item(i);

		// Get the current general weather condition
		NodeList condition = element.getElementsByTagName("condition");
		Element line = (Element) condition.item(0);
		currentWeatherConditions.setGeneralWeatherCondition(line
			.getAttribute("data"));

		// Get the temperature in F
		NodeList temp_f = element.getElementsByTagName("temp_f");
		line = (Element) temp_f.item(0);
		currentWeatherConditions.setTempF(line.getAttribute("data"));

		// Get the temperature in C
		NodeList temp_c = element.getElementsByTagName("temp_c");
		line = (Element) temp_c.item(0);
		currentWeatherConditions.setTempC(line.getAttribute("data"));

		// Get the humidity
		NodeList humidity = element.getElementsByTagName("humidity");
		line = (Element) humidity.item(0);
		String tempHumidity = line.getAttribute("data");
		String humidityTokens[] = tempHumidity.split(" ", 2);
		currentWeatherConditions.setHumidity(humidityTokens[1]);

		// Get the wind condition
		NodeList wind_condition = element
			.getElementsByTagName("wind_condition");
		line = (Element) wind_condition.item(0);
		String tempWindConditions = line.getAttribute("data");
		String windConditionsTokens[] = tempWindConditions
			.split(" ", 2);
		currentWeatherConditions
			.setWindCondition(windConditionsTokens[1]);
	    }

	    // Get the forecast conditions.
	    nodes = doc.getElementsByTagName("forecast_conditions");
	    for (int i = 0; i < nodes.getLength(); i++) {
		Element element = (Element) nodes.item(i);

		if (i == 0) {
		    // Get the predicted low temperature
		    NodeList low = element.getElementsByTagName("low");
		    Element line = (Element) low.item(0);
		    currentWeatherConditions.setLowTemperature(line
			    .getAttribute("data"));

		    // Get the predicted high temperature
		    NodeList high = element.getElementsByTagName("high");
		    line = (Element) high.item(0);
		    currentWeatherConditions.setHighTemperature(line
			    .getAttribute("data"));

		    // Get the day
		    NodeList day_of_week = element
			    .getElementsByTagName("day_of_week");
		    line = (Element) day_of_week.item(0);
		    currentWeatherConditions.setDayOfWeek(line
			    .getAttribute("data"));
		} else {
		    // Stores the predicted weather condition
		    WeatherCondition tempWeatherCondition = new WeatherCondition();

		    // Get the predicted low temperature
		    NodeList low = element.getElementsByTagName("low");
		    Element line = (Element) low.item(0);
		    tempWeatherCondition.setLowTemperature(line
			    .getAttribute("data"));

		    // Get the predicted high temperature
		    NodeList high = element.getElementsByTagName("high");
		    line = (Element) high.item(0);
		    tempWeatherCondition.setHighTemperature(line
			    .getAttribute("data"));

		    // Get the day
		    NodeList day_of_week = element
			    .getElementsByTagName("day_of_week");
		    line = (Element) day_of_week.item(0);
		    tempWeatherCondition
			    .setDayOfWeek(line.getAttribute("data"));

		    // Get the current general weather condition
		    NodeList condition = element
			    .getElementsByTagName("condition");
		    line = (Element) condition.item(0);
		    tempWeatherCondition.setGeneralWeatherCondition(line
			    .getAttribute("data"));

		    // Add to the vector
		    futureWeatherConditions.add(tempWeatherCondition);
		}
	    }

	    // Test to see if there was an error (all fields would be null)
	    if (currentWeatherConditions.areAllFieldsNull()) {
		throw new WeatherLookupException(
			"There was an error when obtaining the data.");
	    }
	} catch (MalformedURLException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (IOException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (ParserConfigurationException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (SAXException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (NullPointerException e) {
	    throw new WeatherLookupException(e.toString());
	}
    }

    /**
     * Performs a weather lookup with a given zip code. Uses the Yahoo weather
     * service.
     * 
     * @param lookupKey
     *            Zip code
     */
    private void lookupYahooWeather(String zipCode)
	    throws WeatherLookupException {
	try {
	    // Open the Yahoo URL that will have the weather data.
	    URL url = new URL("http://weather.yahooapis.com/forecastrss?p="
		    + zipCode + "&u=f");

	    // Make the document builder for the file using the URL.
	    DocumentBuilder builder = DocumentBuilderFactory.newInstance()
		    .newDocumentBuilder();
	    Document doc = builder.parse(url.openStream());

	    // Get the current weather

	    // Check to see if we can get the city that the zip code is from
	    NodeList nodes = doc.getElementsByTagName("yweather:location");
	    if (nodes.getLength() > 0) {
		Element element = (Element) nodes.item(0);

		// Get the location
		currentWeatherConditions.setLocation(element
			.getAttribute("city"));
	    }

	    // Check to see if we can get humidity
	    nodes = doc.getElementsByTagName("yweather:atmosphere");
	    if (nodes.getLength() > 0) {
		Element element = (Element) nodes.item(0);

		// Get the humidity
		currentWeatherConditions.setHumidity(element
			.getAttribute("humidity")
			+ "%");
	    }

	    // Check to see if we can get wind data
	    nodes = doc.getElementsByTagName("yweather:wind");
	    if (nodes.getLength() > 0) {
		Element element = (Element) nodes.item(0);

		double directionDegree = new Integer(element.getAttribute("direction"));
		int speed = new Integer(element.getAttribute("speed"));
		String wind = null;
		
		// Convert the direction from degrees to N/S/E/W
		if(directionDegree >= 337.5 && directionDegree < 22.5)
		    wind = "E";
		else if(directionDegree >= 22.5 && directionDegree < 67.5)
		    wind = "NE";
		else if(directionDegree >= 67.5 && directionDegree < 112.5)
		    wind = "N";
		else if(directionDegree >= 112.5 && directionDegree < 157.5)
		    wind = "NW";
		else if(directionDegree >= 157.5 && directionDegree < 202.5)
		    wind = "W";
		else if(directionDegree >= 202.5 && directionDegree < 247.5)
		    wind = "SW";
		else if(directionDegree >= 247.5 && directionDegree < 292.5)
		    wind = "S";
		else if(directionDegree >= 292.5 && directionDegree < 337.5)
		    wind = "SE";
		
		// If we have a direction, finish the string
		if(wind != null)
		    wind += " at " + speed + " mph";
		
		currentWeatherConditions.setWindCondition(wind);
	    }

	    // Get the temperature, day, and conditions
	    nodes = doc.getElementsByTagName("yweather:condition");
	    if (nodes.getLength() > 0) {
		Element element = (Element) nodes.item(0);

		// Get the general weather condition
		currentWeatherConditions.setGeneralWeatherCondition(element
			.getAttribute("text"));

		// Get the temperature
		currentWeatherConditions.setTempF(element.getAttribute("temp"));

		// Convert to Celcius
		int temperatureC = (int) ((Integer
			.parseInt(currentWeatherConditions.getTempF()) - 32) / 1.8);
		currentWeatherConditions.setTempC(String.valueOf(temperatureC));

		// Get the date
		String temp[] = element.getAttribute("date").split(",");
		System.err.println(element.getAttribute("here : " + temp[0]));
		currentWeatherConditions.setDayOfWeek(temp[0]);
	    }

	    // Get the forecasts
	    nodes = doc.getElementsByTagName("yweather:forecast");
	    for (int i = 0; i < nodes.getLength(); i++) {
		Element element = (Element) nodes.item(i);

		// If first forecast, it is about todays forecast
		if (i == 0) {
		    // Get the low temperature
		    currentWeatherConditions.setLowTemperature(element
			    .getAttribute("low"));

		    // Get the high temperature
		    currentWeatherConditions.setHighTemperature(element
			    .getAttribute("high"));
		} else {
		    // Stores the predicted weather condition
		    WeatherCondition tempWeatherCondition = new WeatherCondition();

		    // Get the date
		    tempWeatherCondition.setDayOfWeek(element
			    .getAttribute("day"));

		    // Get the low temperature
		    tempWeatherCondition.setLowTemperature(element
			    .getAttribute("low"));

		    // Get the high temperature
		    tempWeatherCondition.setHighTemperature(element
			    .getAttribute("high"));

		    // Get the general weather condition
		    tempWeatherCondition.setGeneralWeatherCondition(element
			    .getAttribute("text"));

		    // Add to the vector
		    futureWeatherConditions.add(tempWeatherCondition);
		}
	    }

	    // Test to see if there was an error (all fields would be null)
	    if (currentWeatherConditions.areAllFieldsNull()) {
		throw new WeatherLookupException(
			"There was an error when obtaining the data.");
	    }
	} catch (MalformedURLException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (IOException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (ParserConfigurationException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (SAXException e) {
	    throw new WeatherLookupException(e.toString());
	} catch (NullPointerException e) {
	    throw new WeatherLookupException(e.toString());
	}
    }
}