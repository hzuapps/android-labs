package com.example.ranine99.ranine;

/**
 * Created by ranine99 on 2016/5/2.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.AttributeSet;

public class DigitalClockView extends android.widget.DigitalClock{
    Calendar mCalendar;

    private final static String mFormat = "EEEE,MMMM-dd-yyyy hh:mm aa";//h:mm:ss aa
    private FormatChangeObserver mFormatChangeObserver;

    private Runnable mTicker;
    private Handler mHandler;

    private boolean mTickerStopped = false;

    public DigitalClockView(Context context) {
        super(context);
        initClock(context);
    }

    public DigitalClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock(context);
    }

    private void initClock(Context context){
        Resources r = context.getResources();
        if(mCalendar == null){
            mCalendar = Calendar.getInstance();
        }
        mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, mFormatChangeObserver);

    }

    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();

        mHandler = new Handler();

        mTicker = new Runnable(){
            @Override
            public void run() {
                if(mTickerStopped){
                    return ;
                }
                Time mCalendar=new Time("GMT+1");

                mCalendar.setToNow();
                /*DateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                mCalendar.setTimeInMillis(System.currentTimeMillis());*/
                /*setText(DateFormat.format(mFormat, mCalendar));
                invalidate();

                long now = SystemClock.uptimeMillis();*/
                /*long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);*/
            }
        };
        mTicker.run();
    }



    private class FormatChangeObserver extends ContentObserver{

        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {

        }
    }
}
