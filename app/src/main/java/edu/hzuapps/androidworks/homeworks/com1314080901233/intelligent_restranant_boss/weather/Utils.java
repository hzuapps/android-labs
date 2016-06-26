package com.example.intelligent_restranant_boss.weather;

import com.example.intelligent_restranant_boss.R;

public class Utils {
	// 获取图片
	public final static int imageResoId(String weather) {
		int resoid = R.drawable.weather_s_2;
		if (weather.indexOf("多云") != -1 || weather.indexOf("晴") != -1) {// 多云转晴，以下类同
																		// indexOf:包含字串
			resoid = R.drawable.weather_s_1;
		} else if (weather.indexOf("多云") != -1 && weather.indexOf("阴") != -1) {
			resoid = R.drawable.weather_s_2;
		} else if (weather.indexOf("阴") != -1 && weather.indexOf("雨") != -1) {
			resoid = R.drawable.weather_s_3;
		} else if (weather.indexOf("晴") != -1 && weather.indexOf("雨") != -1) {
			resoid = R.drawable.weather_s_12;
		} else if (weather.indexOf("晴") != -1 && weather.indexOf("雾") != -1) {
			resoid = R.drawable.weather_s_12;
		} else if (weather.indexOf("晴") != -1) {
			resoid = R.drawable.weather_s_13;
		} else if (weather.indexOf("多云") != -1) {
			resoid = R.drawable.weather_s_2;
		} else if (weather.indexOf("阵雨") != -1) {
			resoid = R.drawable.weather_s_3;
		} else if (weather.indexOf("小雨") != -1) {
			resoid = R.drawable.weather_s_3;
		} else if (weather.indexOf("中雨") != -1) {
			resoid = R.drawable.weather_s_4;
		} else if (weather.indexOf("大雨") != -1) {
			resoid = R.drawable.weather_s_5;
		} else if (weather.indexOf("暴雨") != -1) {
			resoid = R.drawable.weather_s_5;
		} else if (weather.indexOf("冰雹") != -1) {
			resoid = R.drawable.weather_s_6;
		} else if (weather.indexOf("雷阵雨") != -1) {
			resoid = R.drawable.weather_s_7;
		} else if (weather.indexOf("小雪") != -1) {
			resoid = R.drawable.weather_s_8;
		} else if (weather.indexOf("中雪") != -1) {
			resoid = R.drawable.weather_s_9;
		} else if (weather.indexOf("大雪") != -1) {
			resoid = R.drawable.weather_s_10;
		} else if (weather.indexOf("暴雪") != -1) {
			resoid = R.drawable.weather_s_10;
		} else if (weather.indexOf("扬沙") != -1) {
			resoid = R.drawable.weather_s_11;
		} else if (weather.indexOf("沙尘") != -1) {
			resoid = R.drawable.weather_s_11;
		} else if (weather.indexOf("雾") != -1) {
			resoid = R.drawable.weather_s_12;
		}
		return resoid;
	}
}
