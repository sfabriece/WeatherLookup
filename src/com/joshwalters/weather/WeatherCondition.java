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

/**
 * Stores the weather condition data.
 * 
 * @author Josh Walters
 * @version 2.0
 */
public class WeatherCondition {

    /**
     * Stores the location that the lookup was performed on.
     */
    private String location;

    /**
     * Stores the general weather conditions. I.E. Cloudy, Sunny, etc.
     */
    private String generalWeatherCondition;

    /**
     * Stores the current temperature in Fahrenheit. Only applicable for the
     * current day, not forecasts.
     */
    private String tempF;

    /**
     * Stores the current temperature in celsius. Only applicable for the
     * current day, not forecasts.
     */
    private String tempC;

    /**
     * Stores the current humidity. Only applicable for the current day, not
     * forecasts.
     */
    private String humidity;

    /**
     * Stores the current wind conditions. Only applicable for the current day,
     * not forecasts.
     */
    private String windCondition;

    /**
     * Stores the day of week for the forecast.
     */
    private String dayOfWeek;

    /**
     * Stores the predicted low temperature for the forecast.
     */
    private String lowTemperature;

    /**
     *Stores the predicted high temperature for the forecast.
     */
    private String highTemperature;

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the generalWeatherCondition
     */
    public String getGeneralWeatherCondition() {
	return generalWeatherCondition;
    }

    /**
     * @param generalWeatherCondition
     *            the generalWeatherCondition to set
     */
    public void setGeneralWeatherCondition(String generalWeatherCondition) {
	this.generalWeatherCondition = generalWeatherCondition;
    }

    /**
     * @return the temp_f
     */
    public String getTempF() {
	return tempF;
    }

    /**
     * @param tempF
     *            the temp_f to set
     */
    public void setTempF(String tempF) {
	this.tempF = tempF;
    }

    /**
     * @return the temp_c
     */
    public String getTempC() {
	return tempC;
    }

    /**
     * @param tempC
     *            the temp_c to set
     */
    public void setTempC(String tempC) {
	this.tempC = tempC;
    }

    /**
     * @return the humidity
     */
    public String getHumidity() {
	return humidity;
    }

    /**
     * @param humidity
     *            the humidity to set
     */
    public void setHumidity(String humidity) {
	this.humidity = humidity;
    }

    /**
     * @return the windCondition
     */
    public String getWindCondition() {
	return windCondition;
    }

    /**
     * @param windCondition
     *            the windCondition to set
     */
    public void setWindCondition(String windCondition) {
	this.windCondition = windCondition;
    }

    /**
     * @return the dayOfWeek
     */
    public String getDayOfWeek() {
	return dayOfWeek;
    }

    /**
     * @param dayOfWeek
     *            the dayOfWeek to set
     */
    public void setDayOfWeek(String dayOfWeek) {
	this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return the low temperature
     */
    public String getLowTemperature() {
	return lowTemperature;
    }

    /**
     * @param low
     *            temperature the low temperature to set
     */
    public void setLowTemperature(String lowTemperature) {
	this.lowTemperature = lowTemperature;
    }

    /**
     * @return the high temperature
     */
    public String getHighTemperature() {
	return highTemperature;
    }

    /**
     * @param high
     *            the high temperature to set
     */
    public void setHighTemperature(String highTemperature) {
	this.highTemperature = highTemperature;
    }

    /**
     * Checks to see if all fields are null.
     * 
     * @return True if all fields are null, false otherwise.
     */
    public boolean areAllFieldsNull() {

	// Check to see if every field is null.
	if (location == null)
	    if (generalWeatherCondition == null)
		if (tempF == null)
		    if (tempC == null)
			if (humidity == null)
			    if (windCondition == null)
				if (dayOfWeek == null)
				    if (lowTemperature == null)
					if (highTemperature == null)
					    return true;
	
	// Else, there was some field that was not null
	return false;
    }
}