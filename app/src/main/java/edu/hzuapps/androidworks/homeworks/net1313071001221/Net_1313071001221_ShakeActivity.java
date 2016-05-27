package edu.hzuapps.androidworks.homeworks.net1313071001221;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.Map;

public class Net_1313071001221_ShakeActivity extends Activity {
    Net_1313071001221_ShakeListener mShakeListener=null;
    Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    private SoundPool sndPool;
    private Map<Integer,Integer> loadSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_1313071001221__main);
        init();
        loadSound=Net_1313071001221_Utils.loadSound(sndPool,this);
    }

    private void init(){
        mVibrator=(Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
        mImgUp=(RelativeLayout)findViewById(R.id.shakeImgUp);
        mImgDn=(RelativeLayout)findViewById(R.id.shakeImgDown);
        sndPool=new SoundPool(2,AudioManager.STREAM_SYSTEM,5);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mShakeListener=new Net_1313071001221_ShakeListener(this);
        mShakeListener.setOnShakeListener(new Net_1313071001221_ShakeListener.OnShakeListener() {
            public void onShake() {
                final String hint = "没有数据库、网络编程\n无法找到同一时刻摇一摇的人";
                Net_1313071001221_Utils.startAnim(mImgUp, mImgDn);
                mShakeListener.stop();
                sndPool.play(loadSound.get(0), (float) 1, (float) 1, 0, 0, (float) 1.2);
                StartVibrato();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        sndPool.play(loadSound.get(1), (float) 1, (float) 1, 0, 0, (float) 1.0);
                        Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_SHORT).show();
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mShakeListener!=null){
            mShakeListener.stop();
        }
    }

    public void StartVibrato(){

        mVibrator.vibrate(new long[] { 500,200,500,200 },-1);
    }

    public void shake_activity_back(View v){
        this.finish();
    }
}