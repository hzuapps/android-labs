package com.example.yaoyiyao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by Administrator on 2016/6/15.
 */
public class Shark extends Activity implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Vibrator vibrator = null;
    private LinearLayout topLayout, bottomLayout;
    private ImageView topLineIv, bottomLineIv;
    private boolean isShake = false;
    String date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shark_layout);
        topLayout = (LinearLayout)findViewById(R.id.shake_top_layout);
        topLineIv = (ImageView)findViewById(R.id.shake_top_line);
        bottomLayout = (LinearLayout)findViewById(R.id.shake_bottom_layout);
        bottomLineIv = (ImageView)findViewById(R.id.shake_bottom_line);

        topLineIv.setVisibility(View.GONE);
        bottomLineIv.setVisibility(View.GONE);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Shark.this.finish();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        int sensorType = event.sensor.getType();
        // values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17) && !isShake) {
                isShake = true;
                new Thread() {
                    public void run() {
                        try {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // 摇动手机后，再伴随震动提示~~
                                    vibrator.vibrate(300);
                                    topLineIv.setVisibility(View.VISIBLE);
                                    bottomLineIv.setVisibility(View.VISIBLE);
                                    startAnimation(false);
                                }
                            });
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // 摇动手机后，再伴随震动提示~~
                                    vibrator.vibrate(300);
                                }
                            });
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    isShake = false;
                                    startAnimation(true);
                                }
                            });
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    private void startAnimation(boolean isBack) {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        float topFromYValue;
        float topToYValue;
        float bottomFromYValue;
        float bottomToYValue;
        if (isBack) {
            topFromYValue = -0.5f;
            topToYValue = 0;
            bottomFromYValue = 0.5f;
            bottomToYValue = 0;
        } else {
            topFromYValue = 0;
            topToYValue = -0.5f;
            bottomFromYValue = 0;
            bottomToYValue = 0.5f;
        }
        TranslateAnimation topAnimation = new TranslateAnimation(type, 0, type,
                0, type, topFromYValue, type, topToYValue);
        topAnimation.setDuration(200);
        topAnimation.setFillAfter(true);
        TranslateAnimation bottomAnimation = new TranslateAnimation(type, 0,
                type, 0, type, bottomFromYValue, type, bottomToYValue);
        bottomAnimation.setDuration(200);
        bottomAnimation.setFillAfter(true);
        if (isBack) {
            bottomAnimation
                    .setAnimationListener(new TranslateAnimation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // TODO Auto-generated method stub
                            topLineIv.setVisibility(View.GONE);
                            bottomLineIv.setVisibility(View.GONE);
//							Toast.makeText(getActivity(),"摇一摇ok!!!",Toast.LENGTH_LONG).show();

                            Random random = new Random();
                            int n;
                            DBHelper dbHelper=new DBHelper(Shark.this);
                            SQLiteDatabase readdb = dbHelper.getReadableDatabase();
                            Cursor c = readdb.query("user", null,null,null,null,null,null);
                            int m=c.getCount();
                            do {
                                n = random.nextInt(m);
                            } while (n == 0);
                            getdbdate(n);

//                            DBHelper dbHelper=new DBHelper(Shark.this);
//                            SQLiteDatabase readdb = dbHelper.getReadableDatabase();
//                            Cursor c = readdb.query("user", null,"userID=?",new String[]{String.valueOf(2)} , null, null, null);
////                            Cursor c = readdb.query("user", null,null,null , null, null, null);
//                            while (c.moveToNext()){
//                                date=c.getString(c.getColumnIndex("date"));
//                                System.out.println("摇一摇："+String.format("date=%s",date));
//                            }
//                            readdb.close();

                            new AlertDialog.Builder(Shark.this).setItems(
                                    new String[]{date}, null).show();
                        }
                    });
        }
        bottomLayout.startAnimation(bottomAnimation);
        topLayout.startAnimation(topAnimation);
    }

    public String getdbdate(int n){
        String mname=null;
        DBHelper dbHelper=new DBHelper(Shark.this);
        SQLiteDatabase readdb = dbHelper.getReadableDatabase();
        Cursor c = readdb.query("user", null,"userID=?",new String[]{String.valueOf(n)} , null, null, null);
        while (c.moveToNext()){
           date=c.getString(c.getColumnIndex("date"));
            System.out.println("摇一摇："+String.format("date=%s",date));
        }
        readdb.close();
        return mname;

    }



}

