package edu.hzuapps.androidworks.homeworks.net1314080903103;

import com.gb.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Net1314080903103_GameBirdActivity extends Activity {
	public static Net1314080903103_GameBirdActivity instance;
    private LinearLayout gameView;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.net1314080903103_content_view);

		//game
		gameView = (LinearLayout)this.findViewById(R.id.game_view);
		
		gameView.addView(new Net1314080903103_GameBirdSurfaceView(this));
	}

	public void showMessage(int level){

		saveSettingData(level);
		
		Intent intent = new Intent(this, Net1314080903103_LoadingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(intent);

	}

	public static final String GameBirdSettingsFile = "GameBird_Settings";
	public static final String Settings_LevelLast = "LevelLast";
	public static final String Settings_LevelTop = "LevelTop";
	
	private void saveSettingData(int level) {
		
		SharedPreferences gb_settings = getSharedPreferences(
				GameBirdSettingsFile, 0);

		gb_settings.edit().putInt(Settings_LevelLast, level).commit();
		
		int top = gb_settings.getInt(Settings_LevelTop, 0);
		
		if(level>top){
			gb_settings.edit().putInt(Settings_LevelTop, level).commit();
		}

	}
}