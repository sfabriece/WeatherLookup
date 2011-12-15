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
 * The exception class for WeatherLookup.
 * 
 * @author Josh Walters
 * @version 2.0
 */
public class WeatherLookupException extends Exception {
    /**
     * Gets rid of warning.
     */
    private static final long serialVersionUID = 415108420433004977L;

    public WeatherLookupException() {
    }

    public WeatherLookupException(String msg) {
	super(msg);
    }
}