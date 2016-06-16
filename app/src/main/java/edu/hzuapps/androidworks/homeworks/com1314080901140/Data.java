package com.hzu.xu.planewar;

import android.app.Application;

public class Data extends Application{
    public int b;



    public int getNumber() {
        return b;
    }

    public void setNumber(int number) {
        this.b = number;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }
}