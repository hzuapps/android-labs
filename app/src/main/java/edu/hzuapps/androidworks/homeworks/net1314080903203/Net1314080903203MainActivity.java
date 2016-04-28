package edu.hzuapps.androidworks.homeworks.net1314080903203;

import android.app.Activity;
import android.os.Bundle;

import edu.hzuapps.androidworks.homeworks.net1314080903203.Net1314080903203Image3DSwitchView.OnImageSwitchListener;
/**
 * Created by XIAOCONG_HOME on 2016/4/27 0027.
 */
public class Net1314080903203MainActivity extends Activity {

    private Net1314080903203Image3DSwitchView imageSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSwitchView = (Net1314080903203Image3DSwitchView) findViewById(R.id.image_switch_view);
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
        super.onDestroy();}}