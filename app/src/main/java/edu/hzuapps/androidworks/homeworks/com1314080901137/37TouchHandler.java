package edu.hzuapps.androidworks.homeworks.com1314080901137;

import java.util.List;

import android.view.View.OnTouchListener;

import edu.hzuapps.androidworks.homeworks.com1314080901137.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    
    public int getTouchX(int pointer);
    
    public int getTouchY(int pointer);
    
    public List<TouchEvent> getTouchEvents();
}
