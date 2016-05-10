package edu.hzuapps.androidworks.homeworks.net1314080903138;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Net1314080903138MainActivity extends Activity {

    private ImageButton ibStart;
    private CheckBox cbSoundSwitch;
    private MediaPlayer mp;
    private ScaleAnimation anim1 = new ScaleAnimation(1, 0, 1, 1,
            Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f );
    private ScaleAnimation anim2 = new ScaleAnimation(0, 1, 1, 1,
            Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f );

    private ImageView[] ivs = new ImageView[12];
    private int[] ivIds = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4,
            R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8,
            R.id.iv9, R.id.iv10, R.id.iv11, R.id.iv12};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibStart = (ImageButton) findViewById(R.id.startButton);
        cbSoundSwitch = (CheckBox) findViewById(R.id.soundSwitch);
        mp = MediaPlayer.create(this,R.raw.dididi);

        ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Net1314080903138MainActivity.this, Game.class);
                startActivity(intent);
            }
        });

        cbSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mp.start();
                    System.out.println("!!!!!!Music!!!!!!!!");

                } else {
                    mp.pause();
                }
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer arg0) {
                mp.start();
                mp.setLooping(true);
            }
        });


        for(int i=0;i<12; i++){
            ivs[i] = (ImageView) findViewById(ivIds[i]);
        }


        anim1.setDuration(500);
        anim2.setDuration(500);


//        handler.postDelayed(task, 5000);//延迟调用
        handler.post(task1);//立即调用
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * double-click exit
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "lick again will exit", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    private Handler handler = new Handler();

    private boolean flag1=true;
    private int count = 0;

    private Runnable task1 =new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            count++;
            handler.postDelayed(this, 500);
            //需要执行的代码
            if(count<8){
                if(flag1){
                    start1(3);
                    start1(5);
                    start1(8);
                    start1(10);
                    flag1 = !flag1;
                }
                else {
                    start2(3);
                    start2(5);
                    start2(8);
                    start2(10);
                    flag1 = !flag1;
                }
            }
            else if(count ==8){
                flag1 =true;
            }
            else if(count<16) {
                if(flag1){
                    start1(1);
                    start1(4);
                    start1(6);
                    start1(11);
                    flag1 = !flag1;
                }
                else {
                    start2(1);
                    start2(4);
                    start2(6);
                    start2(11);
                    flag1 = !flag1;
                }
            }
            else if(count ==16){
                flag1 =true;
            }
            else {
                count=0;
            }

        }
    };


    private void start1(int n){
        ivs[n].startAnimation(anim1);
    }

    private void start2(int n){
        ivs[n].startAnimation(anim2);
    }


}

