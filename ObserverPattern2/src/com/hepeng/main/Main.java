package com.hepeng.main;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WeatherData weatherData = new WeatherData();

        CityInfoDisplay cityInfoDisplay = new CityInfoDisplay(weatherData);

        weatherData.GetWeatherInfo();


    }
}
