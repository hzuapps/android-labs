package com.example.czg.com1314080901108;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by czg on 2016/5/6.
 */
public class Alarm_clockActivity extends AppCompatActivity{
    Button button2 = null;
    private Button mSet;
    Calendar mCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmclock);
        ObjectPool.mAlarmHelper = new AlarmHelper(this);
        mSet = (Button) findViewById(R.id.mSet);
        setListener();
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Alarm_clockActivity.this,Com1314080901108Activity.class);
                startActivity(intent);
            }
        });

    }
    public void setListener() {
        mSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCalendar.setTimeInMillis(System.currentTimeMillis());
                int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = mCalendar.get(Calendar.MINUTE);
                new TimePickerDialog(Alarm_clockActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                mCalendar.setTimeInMillis(System
                                        .currentTimeMillis());
                                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                mCalendar.set(Calendar.MINUTE, minute);
                                mCalendar.set(Calendar.SECOND, 0);
                                mCalendar.set(Calendar.MILLISECOND, 0);
                                ObjectPool.mAlarmHelper.openAlarm(32, "ddd",
                                        "ffff", mCalendar.getTimeInMillis());
                            }
                        }, mHour, mMinute, true).show();
            }
        });
    }


}
