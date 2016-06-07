package edu.hzuapps.androidworks.homeworks.net1314080903214;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


public class Net1314080903214PlayAlarmAty extends Activity{

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_player_net1314080903214);

        mp = MediaPlayer.create(Net1314080903214PlayAlarmAty.this, R.raw.net1314080903214music);
        mp.start();

//        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mp.stop();
//                mp.release();
//                finish();
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mp.stop();
        mp.release();
    }

}
