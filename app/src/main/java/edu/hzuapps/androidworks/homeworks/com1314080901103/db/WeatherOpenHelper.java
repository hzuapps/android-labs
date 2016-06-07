package edu.hzuapps.androidworks.homeworks.com1314080901103.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * Province表建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement, "
            + "province_name text, "
            + "province_code text)";
    /**
     * City表建表语句
     */
    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement, "
            + "city_name text, "
            + "city_code text, "
            + "province_id integer)";
    /**
     * County表建表语句
     */
    public static final String CREATE_COUNTY = "create table County ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text, "
            + "city_id integer)";
    /**
     * AddCounty表建表语句
     */
    public static final String CREATE_ADD_COUNTY = "create table AddCounty ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text) ";
    /**
     * AddCounty表建表语句
     */
    public static final String CREATE_ADD_COUNTY_WEATHER = "create table AddCountyWeather ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "weather_code text, "
            + "temp1 text, "
            + "temp2 text, "
            + "weather_desp text, "
            + "update_time text, "
            + "current_date text) ";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);    //  创建Province表
        db.execSQL(CREATE_CITY);    //  创建City表
        db.execSQL(CREATE_COUNTY);      //  创建County表
        db.execSQL(CREATE_ADD_COUNTY);  //  创建AddCounty表
        db.execSQL(CREATE_ADD_COUNTY_WEATHER);  //  创建AddCountyWeather表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}