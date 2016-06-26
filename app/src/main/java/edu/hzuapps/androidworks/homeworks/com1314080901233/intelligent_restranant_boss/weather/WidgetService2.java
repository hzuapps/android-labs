package com.example.intelligent_restranant_boss.weather;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.intelligent_restranant_boss.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WidgetService2 extends Service {
	private LocationClient mLocationClient = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy年MM月dd ,HH:mm");
	private BDLocationListener myListener;
	private String cityName;
	private String wd = "";
	private String tq = "";
	private String fx = "";
	private Timer timer;
	private RemoteViews view;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this); // 声明LocationClient类
		initListener();
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		initLocation();
		mLocationClient.start();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				updateView();
			}

		}, 0, 1000);
	}

	protected void updateView() {
		String str = dateFormat.format(new Date());
		String dateInfor[] = str.split(",");
		view = new RemoteViews(getPackageName(), R.layout.weather_weiget);
		view.setTextViewText(R.id.city, cityName);
		view.setTextViewText(R.id.showtime, dateInfor[1]);
		view.setTextViewText(R.id.showyear, dateInfor[0]);
		view.setTextViewText(R.id.weather, wd);
		view.setTextViewText(R.id.weather_detail, tq);
		// setIamgeResource(tq);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName cn = new ComponentName(this, MyWidgetProviderInfo.class);
		manager.updateAppWidget(cn, view);

	}

	private void initListener() {
		myListener = new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				String citys = location.getCity();
				cityName = citys.substring(0, citys.length() - 1);
				if (cityName != null) {
					QueryAsyncTask asyncTask = new QueryAsyncTask();
					asyncTask.execute();
				}
			}
		};

	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	// 使用异步通信AsynTask对天气进行查询
	private class QueryAsyncTask extends AsyncTask {
		@Override
		protected void onPostExecute(Object result) {

			if (result != null) {
				String weatherResult = (String) result;
				if (weatherResult.split(";").length > 1) {
					String a = weatherResult.split(";")[1];
					if (a.split("=").length > 1) {
						String b = a.split("=")[1];
						String c = b.substring(1, b.length() - 1);
						String[] resultArr = c.split("\\}");
						if (resultArr.length > 0) {
							todayParse(resultArr[0]);

						}
					}
				}
			}

		}

		@Override
		protected Object doInBackground(Object... params) {
			// 查询天气
			//return WeatherService.getWeather(cityName);
		return null;
			}

	}

	public void todayParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");

		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

		}

	}
}
