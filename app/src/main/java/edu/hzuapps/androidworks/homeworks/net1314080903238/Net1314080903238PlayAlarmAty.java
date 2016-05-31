package app.src.main.java.edu.hzuapps.androidworks.homeworks.net1314080903238;

import com.jikexueyuan.jkxyclock.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Net1314080903238PlayAlarmAty extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_player_aty_net1314080903238);
		
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
