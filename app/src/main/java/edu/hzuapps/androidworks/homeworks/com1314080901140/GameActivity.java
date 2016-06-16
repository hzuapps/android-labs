package com.hzu.xu.planewar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏显示
        Init();
    }

    public void Init(){
        WindowManager wm = this.getWindowManager();
        Display display = wm.getDefaultDisplay();
        MainGame gameView = new MainGame(this, display);
        gameView.setBackgroundColor(Color.BLACK);
        this.setContentView(gameView);
    }
}
