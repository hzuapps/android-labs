package edu.hzuapps.androidworks.homeworks.net123456;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import edu.hzuapps.androidworks.R;
import edu.hzuapps.androidworks.homeworks.BackActivity;

public class Net123456Activity extends BackActivity {

    public static final String TAG = Net123456Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 填充活动|屏幕的内容
        setContentView(R.layout.activity_net123456);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop.");
    }
}
