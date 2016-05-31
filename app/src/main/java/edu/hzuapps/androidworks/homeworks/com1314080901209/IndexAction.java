package edu.hzuapps.androidworks.homeworks.com1314080901209;

import java.util.ArrayList;

import android.telephony.SmsManager;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IndexAction extends Activity {
	  private EditText num;
	  private  MediaPlayer music;
	    private EditText content;
	 /*
		 * 爬虫
		
	public void spider(){
		String data="无信息！";
		String url="https://github.com/chzayi/android-labs/blob/master/app/src/main/java" +
				"/edu/hzuapps/androidworks/homeworks/com1314080901209/data.json";//json数据url
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			int size = is.available();
			byte b[]=new byte[size];
			if(-1!=is.read(b)){
				 data=new String(b);
				//System.out.println(data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView t = new TextView(this);
		t.setText(data);
		setContentView(t);
	}
	     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * 1. 编写一个Activity； 2. 将标题设置为自己的学号+选题题目； 3. 运行后截图存入实验报告； 4.
		 * Activity内容根据自己选择的题目编写。
		 */
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_index);
		  num=(EditText) findViewById(R.id.Number);
	      content=(EditText) findViewById(R.id.Content);
	     
	}
	/**
	 * 发送短信
	 * @param view
	 */
	  public void send(View view ) {
	        String strNo=num.getText().toString();
	        String strContent=content.getText().toString();
	        
	        SmsManager smsManager = SmsManager.getDefault();
	        //如果字数超过5,需拆分成多条短信发送
	        if (strContent.length() > 5) {
	            ArrayList<String> msgs = smsManager.divideMessage(strContent);
	            for (String msg : msgs) {
	                smsManager.sendTextMessage(strNo, null, msg, null, null);                        
	            }
	        } else {
	           smsManager.sendTextMessage(strNo, null, strContent, null, null);
	        }
	        num.setText("");
	       // PlayMusic(this,R.raw.baoj);
	        content.setText("");
	        Toast.makeText(IndexAction.this, "短信发送完成", Toast.LENGTH_LONG).show();
	    }
	  /**
	   * 测试播放音乐
	   * @param context
	   * @param MusicId
	   */
	 	private void PlayMusic(Context context, int MusicId) {
			  music = MediaPlayer.create(context, MusicId);
			music.start();

		}
}
