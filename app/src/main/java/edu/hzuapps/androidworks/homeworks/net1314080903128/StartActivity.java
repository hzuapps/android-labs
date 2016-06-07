package net1314080903128.homeworks.androidworks.hzuapps.edu.net1314080903128;

/**
 * Created by LZL on 2016/4/28.
 */
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;



public class StartActivity extends Activity{

    static int minute = -1;
    static int second = -1;
    final static String tag = "tag";
    TextView timeView;
    Timer timer;
    TimerTask  timerTask;
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            System.out.println("handle!");
            if (minute == 0) {
                if (second == 0) {
                    timeView.setText("下课啦!");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                }else {
                    second--;
                    if (second >= 10) {
                        timeView.setText("0"+minute + ":" + second);
                    }else {
                        timeView.setText("0"+minute + ":0" + second);
                    }
                }
            }else {
                if (second == 0) {
                    second =59;
                    minute--;
                    if (minute >= 10) {
                        timeView.setText(minute + ":" + second);
                    }else {
                        timeView.setText("0"+minute + ":" + second);
                    }
                }else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            timeView.setText(minute + ":" + second);
                        }else {
                            timeView.setText("0"+minute + ":" + second);
                        }
                    }else {
                        if (minute >= 10) {
                            timeView.setText(minute + ":0" + second);
                        }else {
                            timeView.setText("0"+minute + ":0" + second);
                        }
                    }
                }
            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(tag, "log---------->onCreate!");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        timeView = (TextView)findViewById(R.id.myTime);

        if (minute == -1 && second == -1) {
            Intent intent = getIntent();
            ArrayList<Integer> times = intent.getIntegerArrayListExtra("times");
            minute = times.get(0);
            second = times.get(1);
        }

        timeView.setText(minute + ":" + second);

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask,0,1000);

    }

    @Override
    protected void onDestroy() {
        Log.v(tag, "log---------->onDestroy!");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.v(tag, "log---------->onStart!");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.v(tag, "log---------->onStop!");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.v(tag, "log---------->onResume!");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.v(tag, "log---------->onRestart!");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.v(tag, "log---------->onPause!");
        super.onPause();
    }

}
