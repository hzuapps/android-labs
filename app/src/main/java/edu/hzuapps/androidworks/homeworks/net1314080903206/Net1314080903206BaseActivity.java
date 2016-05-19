package edu.hzuapps.androidworks.homeworks.net134080903206;

import android.app.Activity;
import android.os.Bundle;

public class Net1314080903206BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Net1314080903206ActivityCollector.addActivity(this);		
	}
		
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Net1314080903206ActivityCollector.removeActivity(this);
	}
}
