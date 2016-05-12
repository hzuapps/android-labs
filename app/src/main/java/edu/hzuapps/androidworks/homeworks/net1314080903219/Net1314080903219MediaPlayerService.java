package edu.hzuapps.androidworks.homeworks.net1314080903219;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class Net1314080903219MediaPlayerService extends Service {
    MediaPlayer player = new MediaPlayer();

    public Net1314080903219MediaPlayerService() {

      /*  try {
            player.reset();
            player.setDataSource(path);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    //在这里我们需要实例化MediaPlayer对象
    public void onCreate(){

        super.onCreate();
        //我们从raw文件夹中获取一个应用自带的mp3文件

        System.out.println("sfsfsfsf  dsf fdfd fsdf sf ffsfs");


    }

    /**
     * 该方法在SDK2.0才开始有的，替代原来的onStart方法
     */
    public int onStartCommand(Intent intent, int flags, int startId){
        if(!player.isPlaying()){
        //    System.out.println(String.valueOf(intent.getCharSequenceArrayExtra("song")));
            try {
                player.reset();
                player.setDataSource(intent.getStringExtra("song"));
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;
    }

    public void onDestroy(){
        //super.onDestroy();
        if(player.isPlaying()){
            player.stop();
        }
        player.release();
    }




    //后退播放进度
    public void haveFun(){
        if(player.isPlaying() && player.getCurrentPosition()>2500){
            player.seekTo(player.getCurrentPosition()-2500);
        }
    }
}

