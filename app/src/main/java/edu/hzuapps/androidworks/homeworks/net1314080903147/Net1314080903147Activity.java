package edu.hzuapps.androidworks.homeworks.net1314080903147;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Net1314080903147Activity extends Activity {
    private String url = "http://c5.97you.net/download/mac%E7%89%88Adobe_11@22415.exe";

    byte[] imageData = null;
    Button b;
    Net1314080903147NetWorkSpeedInfo net1314080903147NetWorkSpeedInfo = null;
    private final int UPDATE_SPEED = 1;// 进行中
    private final int UPDATE_DNOE = 0;// 完成下载
    private ImageView imageView;
    private long begin = 0;
    private Button startButton;
    private TextView connectionType, nowSpeed, avageSpeed;
    long tem = 0;
    long falg = 0;
    long numberTotal = 0;
    List<Long> list = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903147_equipment);
        imageView = (ImageView) findViewById(R.id.iv_needle);
        startButton = (Button) findViewById(R.id.start_button);
        connectionType = (TextView) findViewById(R.id.connection_type);
        nowSpeed = (TextView) findViewById(R.id.now_speed);
        avageSpeed = (TextView) findViewById(R.id.average_speed);
        net1314080903147NetWorkSpeedInfo = new Net1314080903147NetWorkSpeedInfo();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                list.clear();
                tem = 0;
                falg = 0;
                numberTotal = 0;

                new Thread() {
                    @Override
                    public void run() {
                        Log.i("开始", "**********开始  Net1314080903147_ReadFile*******");
                        imageData = Net1314080903147_ReadFile.getFileFromUrl(url, net1314080903147NetWorkSpeedInfo);
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        Log.i("开始", "**********开始  netWorkSpeedInfo1*******");
                        while (net1314080903147NetWorkSpeedInfo.hadFinishedBytes < net1314080903147NetWorkSpeedInfo.totalBytes) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(UPDATE_SPEED);
                        }
                        if (net1314080903147NetWorkSpeedInfo.hadFinishedBytes == net1314080903147NetWorkSpeedInfo.totalBytes) {
                            handler.sendEmptyMessage(UPDATE_SPEED);
                            net1314080903147NetWorkSpeedInfo.hadFinishedBytes = 0;
                        }

                    }
                }.start();
            }
        });
    }

    protected void startAnimation(double d) {
        AnimationSet animationSet = new AnimationSet(true);
        /**
         * 前两个参数定义旋转的起始和结束的度数，后两个参数定义圆心的位置
         */
        // Random random = new Random();
        int end = getDuShu(d);

        Log.i("", "********************begin:" + begin + "***end:" + end);
        RotateAnimation rotateAnimation = new RotateAnimation(begin, end, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setDuration(1000);
        animationSet.addAnimation(rotateAnimation);
        imageView.startAnimation(animationSet);
        begin = end;
    }

    public int getDuShu(double number) {
        double a = 0;
        if (number >= 0 && number <= 512) {
            a = number / 128 * 15;
        } else if (number > 512 && number <= 1024) {
            a = number / 256 * 15 + 30;
        } else if (number > 1024 && number <= 10 * 1024) {
            a = number / 512 * 5 + 80;
        } else {
            a = 180;
        }
        return (int) a;
    }

    private Handler handler = new Handler() {
        long tem = 0;
        long falg = 0;
        long numberTotal = 0;
        List<Long> list = new ArrayList<Long>();

        @Override
        public void handleMessage(Message msg) {
            int value = msg.what;
            switch (value) {
                case UPDATE_SPEED:
                    tem = net1314080903147NetWorkSpeedInfo.speed / 1024;
                    list.add(tem);
                    Log.i("a", "tem****" + tem);
                    for (Long numberLong : list) {
                        numberTotal += numberLong;
                    }
                    falg = numberTotal / list.size();
                    numberTotal = 0;
                    nowSpeed.setText(tem + "kb/s");
                    avageSpeed.setText(falg + "kb/s");
                    startAnimation(Double.parseDouble(tem+""));
                    break;
                default:
                    break;
            }
        }
    };

}
