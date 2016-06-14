package com.example.lzf.dianzi_clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import java.util.Calendar;



public class TimeChangeBroadcastReceiver_com1314080901219 extends BroadcastReceiver {
    private  int hour,minute;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        SharedPreferences pre=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        hour=pre.getInt("hour",-1);
        minute=pre.getInt("minute",-1);
        Calendar calendar=Calendar.getInstance();
        int systemhour=calendar.get(Calendar.HOUR_OF_DAY);
        int systemminute=calendar.get(Calendar.MINUTE);
        Log.e("时间","="+systemhour+":"+systemminute);
        if (systemhour==hour&&systemminute==minute) {
            Toast.makeText(context, "闹钟响啦", Toast.LENGTH_LONG).show();
        }
    }


}
