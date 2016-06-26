package com.example.intelligent_restranant_boss.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.intelligent_restranant_boss.R;

public class WidgetService extends Service {
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener;
	private String cityName;
	private String wd;
	private String tq;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy年MM月dd ,HH:mm");
	private Timer timer;
	private RemoteViews view;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();

		IntentFilter filter = new IntentFilter();
		filter.addAction("SEND_INFOR");
		this.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equalsIgnoreCase("SEND_INFOR")) {
					cityName = intent.getStringExtra("city");
					wd = intent.getStringExtra("wd");
					tq = intent.getStringExtra("tq");
				}
			}

		}, filter);
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

	@Override
	public void onDestroy() {
		timer = null;
		super.onDestroy();
	}

	void setIamgeResource(String weather) {
		view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_2);
		if (weather.indexOf("多云") != -1 || weather.indexOf("晴") != -1) {// 多云转晴，以下类同
																		// indexOf:包含字串
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_1);
		} else if (weather.indexOf("多云") != -1 && weather.indexOf("阴") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_2);
		} else if (weather.indexOf("阴") != -1 && weather.indexOf("雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_3);
		} else if (weather.indexOf("晴") != -1 && weather.indexOf("雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_12);
		} else if (weather.indexOf("晴") != -1 && weather.indexOf("雾") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_12);
		} else if (weather.indexOf("晴") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_13);
		} else if (weather.indexOf("多云") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_2);
		} else if (weather.indexOf("阵雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_3);
		} else if (weather.indexOf("小雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_3);
		} else if (weather.indexOf("中雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_4);
		} else if (weather.indexOf("大雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_5);
		} else if (weather.indexOf("暴雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_5);
		} else if (weather.indexOf("冰雹") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_6);
		} else if (weather.indexOf("雷阵雨") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_7);
		} else if (weather.indexOf("小雪") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_8);
		} else if (weather.indexOf("中雪") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_9);
		} else if (weather.indexOf("大雪") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_10);
		} else if (weather.indexOf("暴雪") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_10);
		} else if (weather.indexOf("扬沙") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_11);
		} else if (weather.indexOf("沙尘") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_11);
		} else if (weather.indexOf("雾") != -1) {
			view.setImageViewResource(R.id.weather_img, R.drawable.weather_s_12);
		}
	}
}
