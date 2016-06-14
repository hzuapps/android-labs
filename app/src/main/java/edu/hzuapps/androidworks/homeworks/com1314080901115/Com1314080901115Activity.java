package edu_hzuapps_androidworks_homeworks_com1314080901115_activity;

import com.example.dianzicaidan.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class Com1314080901115Activity extends Activity

	{
		private  CheckBox cb1;
		private  CheckBox cb2;
		private  CheckBox cb3;
		private  CheckBox cb4;
		private  CheckBox cb5;
		private  CheckBox cb6;
		private  Button bt;
		private  TextView tv;
		private  String total = "0";
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO 自动生成的方法存根
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_diancai);
				bt= (Button) findViewById(R.id.button1);
				bt.setOnClickListener(new OnClickListener()
					{
						
						@Override
						public void onClick(View arg0)
							{
								// TODO 自动生成的方法存根
								Intent intent = new Intent(Com1314080901115Activity.this, Weiyemian.class);
								startActivity(intent);
							}
					});
				tv= (TextView) findViewById(R.id.totalTV);
				tv.setText(total +"元");
				cb1= (CheckBox) findViewById(R.id.checkBox1);
				cb2= (CheckBox) findViewById(R.id.checkBox2);
				cb3= (CheckBox) findViewById(R.id.checkBox3);
				cb4= (CheckBox) findViewById(R.id.checkBox4);
				cb5= (CheckBox) findViewById(R.id.checkBox5);
				cb6= (CheckBox) findViewById(R.id.checkBox6);
				
				
				cb1.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1)
							{
								// TODO 自动生成的方法存根
								if(cb1.isChecked())
									{
										int i = Integer.valueOf(total);
										i = i + 20 ;
										total = String.valueOf(i);
										tv.setText(total + "元");
									}
								else {
									int i = Integer.valueOf(total);
									i = i - 20 ;
									total = String.valueOf(i);
									tv.setText(total + "元");
								}
							}
					});
				cb2.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1)
							{
								// TODO 自动生成的方法存根
								if(cb2.isChecked())
									{
										int i = Integer.valueOf(total);
										i = i + 8 ;
										total = String.valueOf(i);
										tv.setText(total + "元");
									}
								else {
									int i = Integer.valueOf(total);
									i = i - 8 ;
									total = String.valueOf(i);
									tv.setText(total + "元");
								}
							}
					});
				cb3.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1)
							{
								// TODO 自动生成的方法存根
								if(cb3.isChecked())
									{
										int i = Integer.valueOf(total);
										i = i + 22 ;
										total = String.valueOf(i);
										tv.setText(total + "元");
									}
								else {
									int i = Integer.valueOf(total);
									i = i - 22 ;
									total = String.valueOf(i);
									tv.setText(total + "元");
								}
							}
					});
				cb4.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1)
							{
								// TODO 自动生成的方法存根
								if(cb4.isChecked())
									{
										int i = Integer.valueOf(total);
										i = i + 18 ;
										total = String.valueOf(i);
										tv.setText(total + "元");
									}
								else {
									int i = Integer.valueOf(total);
									i = i - 18 ;
									total = String.valueOf(i);
									tv.setText(total + "元");
								}
							}
					});
				cb5.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1)
							{
								// TODO 自动生成的方法存根
								if(cb5.isChecked())
									{
										int i = Integer.valueOf(total);
										i = i + 30 ;
										total = String.valueOf(i);
										tv.setText(total + "元");
									}
								else {
									int i = Integer.valueOf(total);
									i = i - 30 ;
									total = String.valueOf(i);
									tv.setText(total + "元");
								}
							}
					});
				cb6.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
						
						@Override
						public void onCheckedChanged(CompoundButton arg0, boolean arg1)
							{
								// TODO 自动生成的方法存根
								if(cb6.isChecked())
									{
										int i = Integer.valueOf(total);
										i = i + 25 ;
										total = String.valueOf(i);
										tv.setText(total + "元");
									}
								else {
									int i = Integer.valueOf(total);
									i = i - 25 ;
									total = String.valueOf(i);
									tv.setText(total + "元");
								}
							}
					});
			}
	}