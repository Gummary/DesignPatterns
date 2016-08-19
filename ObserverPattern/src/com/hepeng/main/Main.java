package com.hepeng.main;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentConditionsDisplay   = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay                 = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay                     = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay                   = new HeatIndexDisplay(weatherData);

        weatherData.GetWeatherInfo();
        weatherData.GetWeatherInfo();
        weatherData.GetWeatherInfo();
    }
}
