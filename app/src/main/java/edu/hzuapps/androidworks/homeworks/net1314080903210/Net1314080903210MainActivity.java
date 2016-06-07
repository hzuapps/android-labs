package edu.hzuapps.androidworks.homeworks.net1314080903210;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Net1314080903210MainActivity extends Activity {
	
	private Button strattime;
	private Button endtime;
	private TextView showtime;
	private static int time=0;
	private static final int sendtime=1;
	private volatile Boolean isContinue = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_1314080903210_activity_main);
		
		strattime=(Button) findViewById(R.id.starttime);
		endtime=(Button) findViewById(R.id.endtime);
		showtime=(TextView) findViewById(R.id.showtime);
		
				
		strattime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				isContinue = true;
				TimeThread T = new TimeThread();
				T.start();
			
			}
		});
		
		endtime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				isContinue = false;
				showtime.setText("结束计时");
				
				
			}
		});
		
		
				
	}
	
	private Handler h=new Handler()
	{
		public void handleMessage(Message msg)
		{

			switch(msg.what)
			{
			case sendtime:
				showtime.setText(time+"s");	
				break;
			default:
				break;
			}
		}
	};
	private class TimeThread extends Thread{
		
			@Override
			public void run() {
			
				time=30;
				while(isContinue)
				{
					Message m=new Message();
					m.what=sendtime;
					h.sendMessage(m);
					try 
					{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
					time--;
					if(time <0)
						break;
				}
			}
			
	}
	

}
