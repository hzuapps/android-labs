package edu.hzuapps.androidworks.homeworks;

import com.jikexueyuan.jkxyclock.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Net1314080903238_PlayAlarmAty extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903238alarm_player_aty);
		
		mp = MediaPlayer.create(this, R.raw.music);
		mp.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		mp.stop();
		mp.release();
	}
	
	private MediaPlayer mp;
}
