package edu_hzuapps_androidworks_homeworks_com1314080901115_activity;

import com.example.dianzicaidan.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Weiyemian extends Activity
{
	private Button bt2;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weiyemian);
		bt2=(Button) findViewById(R.id.button1_second);
		bt2.setOnClickListener(new OnClickListener()
		{
			@Override
		public void onClick(View v)
		{
		// 创建Intent对象
		Intent intent=new Intent();
		//为Intent设置Action,Category属性
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
		}
		}); 
	}
	
}
