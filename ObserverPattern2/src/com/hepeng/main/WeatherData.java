package com.hepeng.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2016/8/17.
 */
public class WeatherData  {
    Observable observable;
    private String httpUrl;
    private String httpArg;
    private JSONObject nowRoot;
    private JSONObject cityInfoRoot;
    private JSONObject aqiRoot;
    private JSONObject suggestionRoot;
    private JSONArray  dailyWeatherRoot;
    private JSONArray hourWeatherRoot;

    public WeatherData(){
        httpUrl     = "http://apis.baidu.com/heweather/weather/free";
        httpArg     = "city=qingdao";
        observable  = new Observable();
    }

    public void addObserver(Observer o) {
        observable.addObserver(o);
    }

    public void delObserver(Observer o) {
        observable.delObserver(o);
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
            String status = secondroot.getString("status");
            if(!status.equals("ok")) {
                ErrorHandle(status);
            }
            else {
                UpdateWeatherInfo(secondroot);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void ErrorHandle(String status){
        switch (status) {
            case "invalid key":
                System.out.println("错误的用户 key");
                break;
            case "unknown city":
                System.out.println("未知城市");
                break;
            case "no more requests":
                System.out.println("访问次数超限");
                break;
            case "anr":
                System.out.println("服务器无响应");
                break;
            case "permission deneid":
                System.out.println("没有访问权限");
                break;
        }
    }

    private void UpdateWeatherInfo(JSONObject root) {
        try {
            nowRoot             = root.getJSONObject("now");
            aqiRoot             = root.getJSONObject("aqi");
            cityInfoRoot        = root.getJSONObject("basic");
            hourWeatherRoot     = root.getJSONArray("hourly_forecast");
            suggestionRoot      = root.getJSONObject("suggestion");
            dailyWeatherRoot    = root.getJSONArray("daily_forecast");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        observable.setChanged();
        observable.notifyObservers(this);

    }

    public void GetWeatherInfo() {
        String weatherInfo = request(httpUrl,httpArg);
        ParseWeatherInfo(weatherInfo);
    }

    public String GetCityName() {
        try {
            return cityInfoRoot.getString("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String GetCountryName() {
        try {
            return cityInfoRoot.getString("cnty");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public double GetLongitude() {
        try {
            return cityInfoRoot.getDouble("lon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double GetLatitude() {
        try {
            return cityInfoRoot.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
