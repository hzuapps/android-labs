package edu.hzuapps.androidworks.homeworks.com1314080901127;


import android.app.Activity;
import android.os.Bundle;

import android.view.Display;
import android.view.Window;

import android.view.WindowManager;


public class baoshi extends Activity {
    /** Called when the activity is first created. */
    GameView  game;
    public int width;
    public int height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏设置
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获得屏幕高宽 传给startview保存
        WindowManager win=getWindowManager();
        Display dis=win.getDefaultDisplay();
        width=dis.getWidth();
        height=dis.getHeight();

        game=new GameView(this,this);
        setContentView(game);
    }
}