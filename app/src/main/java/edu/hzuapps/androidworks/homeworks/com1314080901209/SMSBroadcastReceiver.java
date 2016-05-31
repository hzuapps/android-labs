package edu.hzuapps.androidworks.homeworks.com1314080901209;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {
	private MediaPlayer music = null;// 播放器引用
	 private TextView tv;  
 
	 /**
	  * 短信接收
	  */
	 @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage msg = null;
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object p : pdusObj) {
                    msg= SmsMessage.createFromPdu((byte[]) p);
                    String msgTxt =msg.getMessageBody();//得到消息的内容
                    Date date = new Date(msg.getTimestampMillis());//时间
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String receiveTime = format.format(date);
                    String senderNumber = msg.getOriginatingAddress();
                if (msgTxt.trim().equals("baojing")) {
                    Toast.makeText(context, "正在报警......", Toast.LENGTH_LONG)
                            .show();
//                    tv = (TextView)(intent.findViewById(R.id.myTextView1));  
//                    View v= inflater.inflate(R.layout.activity_index, null);
                    PlayMusic(context,R.raw.baoj);
                    System.out.println("正在报警......");
                    return;
                } else {
                	String c="发送人："+senderNumber+"  短信内容："+msgTxt+"接受时间："+receiveTime;
                    Toast.makeText(context, c, Toast.LENGTH_LONG).show();
                    System.out.println(c);
                    return;
                }
            }
 
            return;
        }
            else {
            	Toast.makeText(context, "is null", Toast.LENGTH_LONG).show();
				
			}
    }
	 
	/**
 	* 播放音乐，参数是资源id
 	* 
 	* @param MusicId
 	*/ 
 	private void PlayMusic(Context context, int MusicId) {
		music = MediaPlayer.create(context, MusicId);
		music.start();

	}
}