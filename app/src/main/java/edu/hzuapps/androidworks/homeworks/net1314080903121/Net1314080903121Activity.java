package com.xiaoyuantong.activity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyuantong.R;

public class Net1314080903121Activity extends Activity {
    private ImageView iv;
    private TextView mint;
    private TextView sec;
    private Button start;
    private Button reset;
    private long timeusedinsec;
    private boolean isstop = false;

    private Handler mHandler = new Handler() {
        /*
         * edit by ；linjunfeng 2016-04-27 9:10
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // 添加更新ui的代码
                    if (!isstop) {
                        updateView();
                        mHandler.sendEmptyMessageDelayed(1, 1000);//一秒重新运行一次
                    }
                    break;
                case 0:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903121);
        initViews();
    }

    private void initViews() {
        mint = (TextView) findViewById(R.id.mint);
        sec = (TextView) findViewById(R.id.sec);
        reset = (Button) findViewById(R.id.reset);
        start = (Button) findViewById(R.id.start);
        //reset点击事件
        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //把时间设为0
                mint.setText("00");
                sec.setText("00");
                start.setText("start");
                timeusedinsec=0;//计时置0
                isstop=true;//停止
            }
        });
        //start点击事件
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mHandler.removeMessages(1);
                String aaa=start.getText().toString();
                if(aaa.equals("start")){//如果是"start"开始计时
                    mHandler.sendEmptyMessage(1);
                    isstop = false;
                    start.setText("pause");
                }else {//如果是"pause"
                    mHandler.sendEmptyMessage(0);
                    isstop = true;
                    start.setText("start");
                }

            }
        });
    }
    //更新时间的显示
    private void updateView() {
        timeusedinsec += 1;
        int minute = (int) (timeusedinsec / 60)%60;
        int second = (int) (timeusedinsec % 60);
        if (minute < 10)
            mint.setText("0" + minute);
        else
            mint.setText("" + minute);
        if (second < 10)
            sec.setText("0" + second);
        else
            sec.setText("" + second);
    }
}


