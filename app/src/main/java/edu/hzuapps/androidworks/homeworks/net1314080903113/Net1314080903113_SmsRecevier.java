package edu.hzuapps.androidworks.homeworks.net1314080903113;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class Net1314080903113_SmsRecevier extends BroadcastReceiver {
	private String num;

	public Net1314080903113_SmsRecevier(String number) {
		Log.v("TAG", "SmsRecevier create");
		num=number;
	}

	// 接受短信
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("TAG", "SmsRecevier onReceive");
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		if (pdus != null && pdus.length > 0) {
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				byte[] pdu = (byte[]) pdus[i];
				messages[i] = SmsMessage.createFromPdu(pdu);
			}
			for (SmsMessage message : messages) {
				String content = message.getMessageBody();
				String sender = message.getOriginatingAddress();
				if (sender.equals(num)) {
					abortBroadcast();
					Log.e("TAG", "此号码为黑名单号码，已拦截!");
				}else{
					Log.i("TAG", "发送成功");
				}
				
			}
		}
	}
}
