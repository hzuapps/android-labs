package edu.hzuapps.androidworks.homeworks.com1314080901209;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class IndexAction extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * 1. 编写一个Activity； 2. 将标题设置为自己的学号+选题题目； 3. 运行后截图存入实验报告； 4.
		 * Activity内容根据自己选择的题目编写。
		 */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
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

}
