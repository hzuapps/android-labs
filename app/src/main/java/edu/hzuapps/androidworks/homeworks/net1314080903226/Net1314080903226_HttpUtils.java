package edu.hzuapps.androidworks.homeworks.net1314080903226;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class Net1314080903226_HttpUtils {
	
	public static void getNewsJSON(final String url, final Handler handler){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn;
				InputStream is;
				try {
					conn = (HttpURLConnection) new URL(url).openConnection();
					conn.setRequestMethod("GET");
					is = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					String line = "";
					StringBuilder result = new StringBuilder();
					while ( (line = reader.readLine()) != null ){
						result.append(line);
					}
					Message msg = new Message();
					msg.obj = result.toString();
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}).start();
	}
	
	public static void setPicBitmap(final ImageView ivPic, final String pic_url){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(pic_url).openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					ivPic.setImageBitmap(bitmap);
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}).start();
	}
	
}












