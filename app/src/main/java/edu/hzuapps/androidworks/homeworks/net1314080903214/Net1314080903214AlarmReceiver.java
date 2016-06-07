package edu.hzuapps.androidworks.homeworks.net1314080903214;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Net1314080903214AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("闹钟执行了");

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // 取消当前执行的闹钟
        am.cancel(PendingIntent.getBroadcast(context, getResultCode(), new Intent(context, Net1314080903214AlarmReceiver.class), 0));

        Intent i = new Intent(context, Net1314080903214PlayAlarmAty.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 启动activity
        context.startActivity(i);
    }
}
