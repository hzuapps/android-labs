package com.perkin.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.perkin.weather.model.City;
import com.perkin.weather.model.County;
import com.perkin.weather.model.Province;
import com.perkin.weather.service.WeatherDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by perkin on 2016/6/13.
 */
public class Utility {
    /**
     * 解析和处理服务器返回的升级数据
     */
    public synchronized static boolean handleProvinceResponse(WeatherDB weatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvince = response.split(",");
            if(allProvince!=null&&allProvince.length>0)
            {
                for (String p:allProvince){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    //将解析出来的数据存储到Province表中
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(WeatherDB weatherDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities!=null&&allCities.length>0){
                for(String c:allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    city.setProvinceId(provinceId);
                    //将解析出来的数据存储到City表中
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountiesResponse(WeatherDB weatherDB,String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties!=null&& allCounties.length>0){
                for(String c:allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyName(array[1]);
                    county.setCountyCode(array[0]);
                    county.setCityId(cityId);
                    //将解析出来的数据存储到County表中
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地
     */
    public static void handleWeatherResponse(Context context,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    /**
     * 将服务器返回的所有天气信息存储到SharedPreference文件中
     */
    public static void saveWeatherInfo(Context context,String cityName,String weatherCode, String temp1,String temp2,String weatherDesp,String publishTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();

    }
}




