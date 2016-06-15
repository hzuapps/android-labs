package com.perkin.weather.util;

/**
 * Created by perkin on 2016/6/13.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
