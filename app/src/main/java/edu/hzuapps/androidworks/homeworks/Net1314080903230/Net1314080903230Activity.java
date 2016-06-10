package com.example.turngame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Net1314080903230Activity extends Activity
{
	Button btns[]=new Button[25];
	Button btn;
	Button overbtn;
	int i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		overbtn=(Button)findViewById(R.id.overbtn);
		overbtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		for(i=0;i<btns.length;i++){
			btns[i]=(Button)findViewById(R.id.btn01+i);//获取按钮id，每当i增加1，按钮id增加1，即btn01+i表示此刻获取id
			btns[i].setTag("0");
					
			btn=(Button)findViewById(R.id.btn);
			btn.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					for(i=0;i<btns.length;i++){
						btns[i].setBackgroundResource(R.drawable.cc);
						btns[i].setTag("0");
					}
				}
			});
			
			btns[i].setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{	
					//获取当前点击按钮的id与第一个按钮的id差值，作为数组索引
					int index=v.getId()-btns[0].getId();
					if(index==0){
						changeImage(btns[index],index);
						changeImage(btns[index+1],index+1);
						changeImage(btns[index+5],index+5);
					}else if(index==4){
						changeImage(btns[index],index);
						changeImage(btns[index-1],index-1);
						changeImage(btns[index+5],index+5);
					}else if(0<index&index<4){
						changeImage(btns[index],index);
						changeImage(btns[index-1],index-1);
						changeImage(btns[index+1],index+1);
						changeImage(btns[index+5],index+5);
					}else if(index==20){
						changeImage(btns[index],index);
						changeImage(btns[index+1],index+1);
						changeImage(btns[index-5],index-5);
					}else if(index==24){
						changeImage(btns[index],index);
						changeImage(btns[index-1],index-1);
						changeImage(btns[index-5],index-5);
					}else if(20<index&index<24){
						changeImage(btns[index],index);
						changeImage(btns[index-1],index-1);
						changeImage(btns[index+1],index+1);
						changeImage(btns[index-5],index-5);
					}else if(index==5|index==10|index==15){
						changeImage(btns[index],index);
						changeImage(btns[index-5],index-5);
						changeImage(btns[index+1],index+1);
						changeImage(btns[index+5],index+5);
					}else if(index==9|index==14|index==19){
						changeImage(btns[index],index);
						changeImage(btns[index-5],index-5);
						changeImage(btns[index-1],index-1);
						changeImage(btns[index+5],index+5);
					}else if(5<index&index<9|10<index&index<14|15<index&index<19){
						changeImage(btns[index],index);
						changeImage(btns[index-5],index-5);
						changeImage(btns[index-1],index-1);
						changeImage(btns[index+1],index+1);
						changeImage(btns[index+5],index+5);
					}
//					changeImage(btns[index]);
//					btns[index].setBackgroundResource(R.drawable.b);
				}
			});
		}

	}

	public void changeImage(Button btn,int index){
		String str=btn.getTag().toString();
		if(str.equals("0")){
			btn.setBackgroundResource(R.drawable.dd_01+index);
			btn.setTag("1");
		}else{
			btn.setBackgroundResource(R.drawable.cc);
			btn.setTag("0");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
