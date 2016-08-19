package com.hepeng.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by admin on 2016/8/17.
 */
public class WeatherData implements Subject {
    private ArrayList observers;
    private float temp;
    private float humidity;
    private float pressure;
    private String httpUrl;
    private String httpArg;

    public WeatherData(){
        observers = new ArrayList();
        httpUrl = "http://apis.baidu.com/heweather/weather/free";
        httpArg = "city=qingdao";
    }


    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i < 0){
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for(int i = 0;i<observers.size();i++)
        {
            Observer observer = (Observer)observers.get(i);
            observer.update(temp,humidity,pressure);
        }
    }

    public void measurementsChanged(){
        notifyObservers();
    }

    private void setMeasurements(float temperature,float humidity,float pressure) {
        this.temp = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    private static String request(String httpUrl,String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "2f8eda52b93bdc8b72c08f5c60528f6f");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void ParseWeatherInfo(String weatherInfo){
        try {
            JSONObject firstroot = new JSONObject(weatherInfo);
            JSONArray array = firstroot.getJSONArray("HeWeather data service 3.0");
            JSONObject secondroot = array.getJSONObject(0);
            JSONObject nowroot = secondroot.getJSONObject("now");
            float tmp  = (float)nowroot.getDouble("tmp");
            float hum  = (float)nowroot.getDouble("hum");
            float pres = (float)nowroot.getDouble("pres");
            setMeasurements(tmp,hum,pres);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void GetWeatherInfo() {
        String weatherInfo = request(httpUrl,httpArg);
        ParseWeatherInfo(weatherInfo);
    }

}
