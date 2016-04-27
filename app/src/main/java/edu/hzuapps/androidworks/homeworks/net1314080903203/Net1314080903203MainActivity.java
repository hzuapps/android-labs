package edu.hzuapps.androidworks.homeworks.net1314080903203;

import android.app.Activity;
import android.os.Bundle;

import com.example.imageswitchviewtest.Image3DSwitchView.OnImageSwitchListener;

/**
 * 项目主Activity。
 * 

 */
public class Net1314080903203MainActivity extends Activity {

	private Image3DSwitchView imageSwitchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageSwitchView = (Image3DSwitchView) findViewById(R.id.image_switch_view);
		imageSwitchView.setOnImageSwitchListener(new OnImageSwitchListener() {
			@Override
			public void onImageSwitch(int currentImage) {
				// Log.d("TAG", "current image is " + currentImage);
			}
		});
		imageSwitchView.setCurrentImage(1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageSwitchView.clear();
	}

}
