package com.example.mp3encodedemo;

import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private Mp3EncodeClient mp3EncodeClient;
	private Button startRecordingBtn;
	private Button stopRecordingBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startRecordingBtn = (Button) this.findViewById(R.id.start_recording_btn); 
		startRecordingBtn = (Button) this.findViewById(R.id.pass_btn); 
		startRecordingBtn.setOnClickListener(this);     
        stopRecordingBtn = (Button) this.findViewById(R.id.stop_recording_btn); 
        stopRecordingBtn.setOnClickListener(this);
        
        mp3EncodeClient = new Mp3EncodeClient();
        
        // 检查sdcard状态
        String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			
			File yzsPath = new File(Settings.recordingPath);
	        if(!yzsPath.isDirectory()){
	        	yzsPath.mkdir();
	        }
	        
	    }else{
	    	Toast.makeText(this, "sdcard error!", 0).show();
	    }
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.start_recording_btn:
			
			startRecordingBtn.setVisibility(View.INVISIBLE);
			stopRecordingBtn.setVisibility(View.VISIBLE);
			
			mp3EncodeClient.start();
			break;

		case R.id.stop_recording_btn:
			
			startRecordingBtn.setVisibility(View.VISIBLE);
			stopRecordingBtn.setVisibility(View.INVISIBLE);
			
			mp3EncodeClient.stop();
			
			break;
		}
		
	}

	

}
