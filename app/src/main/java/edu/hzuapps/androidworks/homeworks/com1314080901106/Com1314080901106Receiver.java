package edu.hzuapps.androidworks.homeworks.com1314080901106;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Com1314080901106Receiver extends BroadcastReceiver {
    public static final String ACTION ="edu.hzuapps.androidworks.homeworks.com1314080901106.intent.action.Com1314080901106Receiver";
    public Com1314080901106Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到了消息,消息的内容是："+intent.getStringExtra("data"));
    }
}
