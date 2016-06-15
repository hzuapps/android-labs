package com.example.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Com1314080901239GetJson extends Activity {

	private final int SUCCESS = 1;
	private final int FAILURE = 0;
	private final int ERRORCODE = 2;
	private TextView tv;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SUCCESS:
				String result=getJson(msg.obj.toString());
				//主线程更新UI
				tv.setText(result);
				Toast.makeText(Com1314080901239GetJson.this, "获取数据成功", Toast.LENGTH_SHORT)
				.show();
				break;
			case FAILURE:
				Toast.makeText(Com1314080901239GetJson.this, "获取数据失败", Toast.LENGTH_SHORT)
				.show();
			case ERRORCODE:
				Toast.makeText(Com1314080901239GetJson.this, "获取的CODE码不为200！",
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901239_get_json);
		tv=(TextView) findViewById(R.id.tv);
	}
	
	public String getJson(String string){
		JSONObject jObject= null;
		try {
			jObject =new JSONObject(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("创建JsonObject失败");
		}
		//解析JSON数据
		String city=jObject.optString("city");
		String city_en=jObject.optString("city_en");
		String date_y=jObject.optString("date_y");
		String week=jObject.optString("week");
		int cityid=jObject.optInt("cityid");
		String temp1=jObject.optString("temp1");
		System.out.println(temp1+""+date_y+""+city_en+""+cityid+""+week+""+city);
		String weatherResult="城市："+city+"\n城市英文名："+city_en+"\n日期："+date_y+"\n星期："+week+"\n城市编号："+cityid+"\n温度："+temp1;
		return weatherResult;
	}
	
	public void click(View v){
		//开子线程获取网络请求
		new Thread(){
			int code=0;
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					String path="https://raw.githubusercontent.com/hzuapps/android-labs/master/app/src/main/java/edu/hzuapps/androidworks/homeworks/com1314080901239/city.json";
					URL url=new URL(path);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					//设置GET请求
					conn.setRequestMethod("GET");
					//设置请求超时时间
					conn.setConnectTimeout(5000);
					code=conn.getResponseCode();
					System.out.println(code);
					//200表示获取请求成功
					if(code==200){
						System.out.println("获取数据成功");
						InputStream is=conn.getInputStream();
						String result = readMyInputStream(is);
						
						//handle Message
						Message msg=new Message();
						msg.obj= result;
						msg.what=SUCCESS;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = ERRORCODE;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("获取数据失败");
					e.printStackTrace();
					Message msg = new Message();
					msg.what = FAILURE;
					handler.sendMessage(msg);
				}
				
					
				super.run();
			}
		}.start();
	}
	
	//以流的形式读取数据
	public String readMyInputStream(InputStream is){
		byte[] result;
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int len;
			while((len = is.read(buffer))!= -1){
				baos.write(buffer,0,len);
			}
			is.close();
			baos.close();
			result =baos.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String errorString="获取数据失败";
			return errorString;
		}
		return new String(result);
	}
}
