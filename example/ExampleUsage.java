import com.joshwalters.weather.*;

import java.util.Iterator;

public class ExampleUsage {

	static void printWeatherConditions(WeatherCondition weatherCondition) {
			System.out.println("Location: " + weatherCondition.getLocation());
			System.out.println("General weather condition: " + weatherCondition.getGeneralWeatherCondition());
			System.out.println("Temperature (F): " + weatherCondition.getTempF());
			System.out.println("Temperature (C): " + weatherCondition.getTempC());
			System.out.println("Humitity: " + weatherCondition.getHumidity());
			System.out.println("Wind conditon: " + weatherCondition.getWindCondition());
			System.out.println("Day of week: " + weatherCondition.getDayOfWeek());
			System.out.println("Low temperature: " + weatherCondition.getLowTemperature());
			System.out.println("High temperature: " + weatherCondition.getHighTemperature());
			System.out.println("\n");
	}

	public static void main(String args[]) {
		if(args.length < 1) {
			System.out.println("Pass the zip code to look up weather data for.\n");
			return;
		}
	
		WeatherLookup weather = new WeatherLookup();
		
		try {
			weather.lookup(args[0]); // Use the user supplied zip code
		
			System.out.println("\nCurrent weather:");
			printWeatherConditions(weather.currentWeatherConditions);

			Iterator iterator = weather.futureWeatherConditions.iterator();
			System.out.println("Future weather predictions: \n");
			while(iterator.hasNext()) {
				printWeatherConditions((WeatherCondition)iterator.next());
			}
		
		} catch (WeatherLookupException e) {
			System.out.println("Error!");
		}
	}
}
