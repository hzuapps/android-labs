package app.src.main.java.edu.hzuapps.androidworks.homeworks.net1314080903238;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Net1314080903238AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("µ¹¼ÆÊ±");
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(context, getResultCode(), new Intent(context, Net1314080903238AlarmReceiver.class), 0));
		
		Intent i = new Intent(context, Net1314080903238PlayAlarmAty.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

}
