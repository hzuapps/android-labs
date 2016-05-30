package edu.hzuapps.androidworks.homeworks.com1314080901103.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidworks.homeworks.com1314080901103.model.City;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.County;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.CountyWeather;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.Province;

public class WeatherDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "my_weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static WeatherDB weatherDB;
    private SQLiteDatabase db;
    /**
     * AddCounty表建表语句
     */
    public static final String CREATE_ADD_COUNTY = "create table AddCounty ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text) ";
    /**
     * AddCounty表删除语句
     */
    public static final String DROP_ADD_COUNTY = "DROP TABLE AddCounty";

    /**
     * 将构造方法私有化
     */
    private WeatherDB(Context context) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取WeatherDB的实例
     */
    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    /**
     * 将Province实例存储到数据库
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<>();
        Cursor cursor = db
                .query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor
                        .getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor
                        .getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 将City实例存储到数据库
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 从数据库读取某省下所有的城市信息
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("City", null, "province_id = ?",
                new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor
                        .getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor
                        .getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 将County实例存储到数据库
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 从数据库读取某城市下所有的县信息
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("County", null, "city_id = ?",
                new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor
                        .getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 将AddCounty实例存储到数据库
     */
    public void saveAddCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            db.insert("AddCounty", null, values);
        }
    }

    /**
     * 查询AddCounty表的城市数
     */
    public int selectAddCountySize() {
        Cursor cursor = db.query("AddCounty", null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * 从数据库读取某县的信息
     */
    public County loadAddCounties(int cityId) {
        Cursor cursor = db.query("AddCounty", null, "id = ?",
                new String[]{String.valueOf(cityId)}, null, null, null);
        County county = new County();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor
                        .getColumnIndex("county_code")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return county;
    }

    /**
     * 从数据库读取某县的Id信息
     */
    public int loadAddCountiesId(String countyCode) {
        Cursor cursor = db.query("AddCounty", null, "county_code = ?",
                new String[]{countyCode}, null, null, null);
        int id = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id;
    }

    /**
     * 从数据库读取所有的添加城市信息
     *
     * @return
     */
    public List<County> loadAddCounties() {
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("AddCounty", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor
                        .getColumnIndex("county_code")));
                list.add(county);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 删除添加城市表中的县
     *
     * @param countyName
     */
    public void deleteFromAddCountiesByCountyName(String countyName) {
        db.delete("AddCounty", "county_name = ?", new String[]{countyName});
    }

    /**
     * 删除AddCounty表
     */
    public void deleteAddCounties() {
        db.execSQL(DROP_ADD_COUNTY);
    }

    /**
     * 重新创建AddCounty表
     */
    public void createAddCounties() {
        db.execSQL(CREATE_ADD_COUNTY);
    }

    /**
     * 将AddCounty实例存储到数据库
     */
    public void saveAddCountyWeather(CountyWeather countyWeather) {
        if (countyWeather != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", countyWeather.getCountyName());
            values.put("weather_code", countyWeather.getWeatherCode());
            values.put("temp1", countyWeather.getTemp1());
            values.put("temp2", countyWeather.getTemp2());
            values.put("weather_desp", countyWeather.getWeatherDesp());
            values.put("update_time", countyWeather.getUpdateTime());
            values.put("current_date", countyWeather.getCurrentDate());
            db.insert("AddCountyWeather", null, values);
        }
    }

    /**
     * 根据城市名查找对应天气的城市信息
     *
     * @return
     */
    public CountyWeather loadAddCountiesWeatherByCountyName(String countyName) {
        Cursor cursor = db.query("AddCountyWeather", null, "county_name = ?",
                new String[]{countyName}, null, null, null);
        CountyWeather countyWeather = new CountyWeather();
        if (cursor.moveToFirst()) {
            do {
                countyWeather.setId(cursor.getInt(cursor.getColumnIndex("id")));
                countyWeather.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                countyWeather.setWeatherCode(cursor.getString(cursor
                        .getColumnIndex("weather_code")));
                countyWeather.setTemp1(cursor.getString(cursor
                        .getColumnIndex("temp1")));
                countyWeather.setTemp2(cursor.getString(cursor
                        .getColumnIndex("temp2")));
                countyWeather.setWeatherDesp(cursor.getString(cursor
                        .getColumnIndex("weather_desp")));
                countyWeather.setUpdateTime(cursor.getString(cursor
                        .getColumnIndex("update_time")));
                countyWeather.setCurrentDate(cursor.getString(cursor
                        .getColumnIndex("current_date")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return countyWeather;
    }

    /**
     * 删除添加城市天气表中的县
     *
     * @param countyName
     */
    public void deleteFromAddCountiesWeatherByCountyName(String countyName) {
        db.delete("AddCountyWeather", "county_name = ?", new String[]{countyName});
    }

    public void updateAddCountiesWeather(String countyName,
                                         String weatherCode, String temp1, String temp2, String weatherDesp, String updateTime, String currentDate) {
        String sql = "UPDATE AddCountyWeather SET weather_code = \' " + weatherCode
                + "\' , temp1 = \'" + temp1 + "\' , temp2 = \'" + temp2 + "\' , weather_desp = \'" + weatherDesp
                + "\' , update_time = \'" + updateTime + "\' , current_date = \'" + currentDate + "\' WHERE county_name = \'"
                + countyName + "\'";
        db.execSQL(sql);
    }

    public boolean selectAddCountiesWeatherByCountyName(String countyName) {
        Cursor cursor = db.rawQuery("SELECT * FROM AddCountyWeather WHERE county_name = ?", new String[]{countyName});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}