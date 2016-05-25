package com.example.czg.com1314080901108;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CallAlarm extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		intent.setClass(context, AlarmAlert.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
