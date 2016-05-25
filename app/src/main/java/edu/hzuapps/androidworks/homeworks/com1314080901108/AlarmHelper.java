package com.example.czg.com1314080901108;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHelper {

	private Context c;
	private AlarmManager mAlarmManager;

	public AlarmHelper(Context c) {
		this.c = c;
		mAlarmManager = (AlarmManager) c
				.getSystemService(Context.ALARM_SERVICE);
	}

	public void openAlarm(int id, String title, String content, long time) {
		Intent intent = new Intent();
		intent.putExtra("_id", id);
		intent.putExtra("title", title);
		intent.putExtra("content", content);
		intent.setClass(c, CallAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(c, id, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);
	}

	public void closeAlarm(int id, String title, String content) {
		Intent intent = new Intent();
		intent.putExtra("_id", id);
		intent.putExtra("title", title);
		intent.putExtra("content", content);
		intent.setClass(c, CallAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(c, id, intent, 0);
		mAlarmManager.cancel(pi);
	}
}
