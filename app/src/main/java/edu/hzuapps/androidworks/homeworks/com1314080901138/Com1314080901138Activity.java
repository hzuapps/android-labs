package edu.hzuapps.androidworks.homeworks.com1314080901138;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.*;

public class Com1314080901138Activity extends Activity {
    private MediaPlayer mediaPlayer;//MediaPlayer对象
    private boolean isPause = false;//是否暂停
    private File file;//要播放的文件
    private TextView hint;//声明提示信息的文本框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901138);
        //获取各功能按钮
        final Button button = (Button) findViewById(R.id.button);//播放
        final Button button1 = (Button) findViewById(R.id.button2);//暂停
        final Button button2 = (Button) findViewById(R.id.button3);//停止
        hint = (TextView) findViewById(R.id.textView);
        if (!isFileExist()) {
            button.setEnabled(false);
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.my);
        //对MediaPlayer对象添加事件监听，当播放完成时重新开始音乐播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });
        //对播放按钮进行事件监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                if (isPause) {
                    button1.setText("暂停");
                    isPause = false;
                }
                button1.setEnabled(true);
                button2.setEnabled(true);
                button.setEnabled(false);
            }
        });
        //对暂停、继续按钮添加事件监听器
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying() && !isPause) {
                    mediaPlayer.pause();
                    isPause = true;
                    ((Button) v).setText("继续");
                    hint.setText("暂停播放音频....");
                    button.setEnabled(true);
                } else {
                    mediaPlayer.start();
                    ((Button) v).setText("暂停");
                    hint.setText("继续播放音频....");
                    button.setEnabled(false);
                }
            }
        });
        //对停止按钮添加事件监听器
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                hint.setText("停止播放音频...");
                button1.setEnabled(false);
                button2.setEnabled(false);
                button.setEnabled(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }

    //判断文件是否存在
    private boolean isFileExist() {
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "my.mp3");//获取外部存储中的音乐文件
        if (file.exists()) {
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.my);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "file exist", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "file don't exist", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //播放音乐的方法
    private void play() {
        try {
            mediaPlayer.reset();//重新设置要播放的音乐
            mediaPlayer = MediaPlayer.create(this, R.raw.my);
            mediaPlayer.start();//播放音乐
            hint.setText("Music is starting");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("err", e.getMessage());
        }
        //return ;
    }
}
