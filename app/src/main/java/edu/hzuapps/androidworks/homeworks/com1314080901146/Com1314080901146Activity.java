package edu.hzuapps.androidworks.homeworks.com1314080901146;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class com1314080901146Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_com1314080901146);
    }
}

    public static final class drawable {
        public static final int icon=0x7f020000;
        public static final int myicon=0x7f020001;
    }