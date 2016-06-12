package edu_hzuapps_androidworks_homeworks_com1314080901115_activity;

import com.example.dianzicaidan.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Shouyemian extends Activity

	{
		private Button bt1;
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			// TODO 自动生成的方法存根
			super.onCreate(savedInstanceState);
			setContentView(R.layout.shouyemian);
			bt1=(Button) findViewById(R.id.button1_first);
			bt1.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
						{
							// TODO 自动生成的方法存根
							Intent intent = new Intent(Shouyemian.this, Com1314080901115Activity.class) ;
							startActivity(intent);
						}
				});
		}
		
	}
