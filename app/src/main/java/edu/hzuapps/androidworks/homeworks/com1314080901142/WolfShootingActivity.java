package edu.hzuapps.androidworks.homework.com1314080901142;

import android.app.Activity;
import android.os.Bundle;

public class WolfShootingActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901142_main);//设置界面
        new Listener(this);//核心类对象
    }
}