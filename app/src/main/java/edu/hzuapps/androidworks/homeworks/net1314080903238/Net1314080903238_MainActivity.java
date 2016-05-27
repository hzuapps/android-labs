package edu.hzuapps.androidworks.homeworks;

import com.jikexueyuan.jkxyclock.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class Net1314080903238_MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903238activity_main);
        
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tabTime));
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("倒计时").setContent(R.id.tabTimer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("秒表").setContent(R.id.tabStopWatch));
        
        net1314080903238StopWatchView = (Net1314080903238_StopWatchView) findViewById(R.id.tabStopWatch);
    }
    
    @Override
    protected void onDestroy() {
    	
    	net1314080903238StopWatchView.onDestory();
    	
    	super.onDestroy();
    }
    
    private Net1314080903238_StopWatchView net1314080903238StopWatchView;
    private TabHost tabHost;

}
