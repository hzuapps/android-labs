package edu.hzuapps.androidworks.homeworks.net1314080903104;

import android.app.Activity;
import android.os.Bundle;

public class Net1314080903104MainActivity extends Activity {
    private Net1314080903104MyTempView mTempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903104_main);
        mTempView = (Net1314080903104MyTempView) findViewById(R.id.mTemp);
        mTempView.setTemp("38.5", 39.5f,
                35.4f, this, 380);
    }


}