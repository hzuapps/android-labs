package com.example.ranine99.ranine;

import android.view.View;

/**
 * Created by ranine99 on 2016/4/28.
 */
//定义一个实体类，作为ListView适配器的适配类型
public class City {
    private String name;

    private String time;
    private int imageId;

    public City(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public City(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private View view;

    public City(String name, int imageId, View view) {
        this.name = name;
        this.imageId = imageId;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
