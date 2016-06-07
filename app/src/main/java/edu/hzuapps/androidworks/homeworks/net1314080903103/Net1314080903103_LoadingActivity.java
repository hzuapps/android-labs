package edu.hzuapps.androidworks.homeworks.net1314080903103;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gb.R;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;

public class Net1314080903103_LoadingActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.net1314080903103_loading);

		final TextView gameStart = (TextView)findViewById(R.id.game_start);
		
		AdView adView = (AdView)findViewById(R.id.adView);
		adView.setAdListener(new AdListener() {
			
			@Override
			public void onReceiveAd(Ad arg0) {
				gameStart.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPresentScreen(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLeaveApplication(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDismissScreen(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		View gameMessage = findViewById(R.id.GameMessage);
		
		int[] data = getSettingData();
		
		TextView levelMessage = (TextView)findViewById(R.id.level_Message);
		
		levelMessage.setText("SCORE: "+data[0]+"\nBEST: "+data[1]);
		
		gameMessage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(Net1314080903103_LoadingActivity.this, Net1314080903103_GameBirdActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				startActivity(intent);
				
				finish();
			}
		});

	}
	
	private int[] getSettingData() {

		SharedPreferences gb_settings = getSharedPreferences(Net1314080903103_GameBirdActivity.GameBirdSettingsFile, 0);

		int last = gb_settings.getInt(Net1314080903103_GameBirdActivity.Settings_LevelLast, 0);
		int top = gb_settings.getInt(Net1314080903103_GameBirdActivity.Settings_LevelTop, 0);
		
		return new int[]{last, top};
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

				try{
					Net1314080903103_GameBirdActivity.instance.finish();
				}catch(Exception e){}
				finish();
				System.exit(0);
				
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
