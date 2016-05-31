package edu.hzuapps.androidworks.homeworks.net1314080903138;

/**
 * Created by Administrator on 2016/4/26.
 */
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Net1314080903138Sound extends Service {
    private MediaPlayer mp;


    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.net1314080903138dididi);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //boolean playing = intent.getBooleanExtra("playing", false);
        //if (playing) {
        if(!mp.isPlaying()){
            mp.start();
        }
        return START_STICKY;
//        //} else {
//            mp.pause();
//        }
        //return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}