package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//显示监控
public class VideoActivity extends Activity {
	ImageView iv_camera;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		init();
	}
	private void init(){
		iv_camera=(ImageView)findViewById(R.id.iv_open_camera);

		iv_camera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(VideoActivity.this, "未找到摄像头", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
