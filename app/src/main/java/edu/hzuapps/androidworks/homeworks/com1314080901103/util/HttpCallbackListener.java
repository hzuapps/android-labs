package edu.hzuapps.androidworks.homeworks.com1314080901103.util;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
