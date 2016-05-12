package edu.hzuapps.androidworks.homeworks.com1314080901103.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.hzuapps.androidworks.homeworks.com1314080901103.db.WeatherDB;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.City;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.County;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.CountyWeather;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.Province;

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvincesResponse(WeatherDB
                                                                       cWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    // 将解析出来的数据存储到Province表
                    cWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(WeatherDB cWeatherDB,
                                               String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    // 将解析出来的数据存储到City表
                    cWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountiesResponse(WeatherDB cWeatherDB,
                                                 String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    // 将解析出来的数据存储到County表
                    cWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的 JSON 数据，并将解析出的数据存储到本地
     */
    public static String handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
                    weatherDesp);
            return cityName;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将服务器返回的所有天气信息存储到AddCountyWeather表中
     */
    public static void saveWeatherInfo(Context context, String countyName,
                                       String weatherCode, String temp1, String temp2, String weatherDesp) {
        WeatherDB weatherDB = WeatherDB.getInstance(context);
        CountyWeather countyWeather = new CountyWeather();
        Calendar c = Calendar.getInstance();
        String updateTime = c.get(Calendar.HOUR_OF_DAY) + ":";
        if (c.get(Calendar.MINUTE) <= 9) {
            updateTime = updateTime + "0" + c.get(Calendar.MINUTE);
        } else {
            updateTime = updateTime + c.get(Calendar.MINUTE);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 M 月 d 日",
                Locale.CHINA);
        String currentDate = sdf.format(new Date());
        if (!weatherDB.selectAddCountiesWeatherByCountyName(countyName)) {
            countyWeather.setCountyName(countyName);
            countyWeather.setWeatherCode(weatherCode);
            countyWeather.setTemp1(temp1);
            countyWeather.setTemp2(temp2);
            countyWeather.setWeatherDesp(weatherDesp);
            countyWeather.setUpdateTime(updateTime);
            countyWeather.setCurrentDate(currentDate);
            weatherDB.saveAddCountyWeather(countyWeather);
        } else {
            weatherDB.updateAddCountiesWeather(countyName, weatherCode, temp1, temp2, weatherDesp, updateTime, currentDate);
        }

    }

    /**
     * 解析从聚合数据返回的 JSON 数据，并将解析出的数据存储到本地
     */
    public static void handleWeatherResponseFromJuHe(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            //  获取result节点下的位置信息
            JSONObject resultObject = jsonObject.getJSONObject
                    ("result");
            JSONObject JSONObjectToday = resultObject.getJSONObject
                    ("today");
            String cityName = JSONObjectToday.getString("city");
            String[] temp = JSONObjectToday.getString("temperature").split("~");
            String temp1 = temp[0];
            String temp2 = temp[1];
            String weather = JSONObjectToday.getString("weather");
            saveWeatherInfoFromJuHe(context, cityName, temp1, temp2, weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将聚合数据返回的所有天气信息存储到 SharedPreferences 文件中
     */
    public static void saveWeatherInfoFromJuHe(Context context, String cityName,
                                               String temp1, String temp2, String weather) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        Calendar c = Calendar.getInstance();
        String updateTime = c.get(Calendar.HOUR_OF_DAY) + ":";
        if (c.get(Calendar.MINUTE) < 9) {
            updateTime = updateTime + "0" + c.get(Calendar.MINUTE);
        } else {
            updateTime = updateTime + c.get(Calendar.MINUTE);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 M 月 d 日",
                Locale.CHINA);
        String currentDate = sdf.format(new Date());
        editor.putString("city_name_juhe", cityName);
        editor.putString("temp1_juhe", temp1);
        editor.putString("temp2_juhe", temp2);
        editor.putString("weather_juhe", weather);
        editor.putString("update_time_juhe", updateTime);
        editor.putString("current_date_juhe", currentDate);
        editor.commit();
    }
}