package edu.hzuapps.androidworks.homeworks.Com1314080901129;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Com1314080901129Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_com1314080901103);
    }
}
