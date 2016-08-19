package com.hepeng.main;

/**
 * Created by admin on 2016/8/17.
 */
public class CityInfoDisplay implements Observer,DisplayElement{
    private WeatherData weatherData;
    private String cityName;
    private String countryName;
    private double lat;
    private double lon;

    public CityInfoDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.addObserver(this);
    }

    @Override
    public void update(Object obs, Object arg) {
        if(obs instanceof WeatherData){
            WeatherData weatherData = (WeatherData)obs;
            cityName = weatherData.GetCityName();
            countryName = weatherData.GetCountryName();
            lat= weatherData.GetLatitude();
            lon = weatherData.GetLongitude();
            display();
        }
    }

    @Override
    public void display() {
        System.out.println("City Name is "+cityName+" Country Name is " + countryName+" Lat:"+lat+" lon:"+lon);
    }
}
