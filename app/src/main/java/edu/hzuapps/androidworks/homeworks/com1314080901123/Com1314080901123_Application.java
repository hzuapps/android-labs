package edu.hzuapps.androidworks.homeworks.com1314080901123;

import android.app.Application;
import android.content.Context;

/**
 * Created by LK on 2016/5/19.
 */
public class Com1314080901123_Application extends Application {
    private static Context context;

    public void onCreate(){

        context=getApplicationContext();

    }

    public static Context getContext(){

        return context;

    }
}
