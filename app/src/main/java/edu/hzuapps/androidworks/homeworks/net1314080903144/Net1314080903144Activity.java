package edu.hzuapps.androidworks.homeworks.net1314080903144;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Net1314080903144Activity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    //语音文件保存路径
    private String FileName = null;
    private ImageView imageView;

    //界面控件
    private Button startRecord_net1314080903144;
    private Button startPlay_net1314080903144;
    private Button stopRecord_net1314080903144;
    private Button stopPlay_net1314080903144;

    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903144);

        imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setKeepScreenOn(true);
        //开始录音
        startRecord_net1314080903144 = (Button)findViewById(R.id.startRecord);
        startRecord_net1314080903144.setText(R.string.startRecord_net1314080903144);
        //绑定监听器
        startRecord_net1314080903144.setOnClickListener(new startRecordListener());

        //结束录音
        stopRecord_net1314080903144 = (Button)findViewById(R.id.stopRecord);
        stopRecord_net1314080903144.setText(R.string.stopRecord_net1314080903144);
        stopRecord_net1314080903144.setOnClickListener(new stopRecordListener());

        //开始播放
        startPlay_net1314080903144 = (Button)findViewById(R.id.startPlay);
        startPlay_net1314080903144.setText(R.string.startPlay_net1314080903144);
        //绑定监听器
        startPlay_net1314080903144.setOnClickListener(new startPlayListener());

        //结束播放
        stopPlay_net1314080903144 = (Button)findViewById(R.id.stopPlay);
        stopPlay_net1314080903144.setText(R.string.stopPlay_net1314080903144);
        stopPlay_net1314080903144.setOnClickListener(new stopPlayListener());

        //设置sdcard的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/audiorecordtest.3gp";
    }
    //开始录音
    class startRecordListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(FileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
            mRecorder.start();
        }

    }
    //停止录音
    class stopRecordListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

    }
    //播放录音
    class startPlayListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPlayer = new MediaPlayer();
            try{
                mPlayer.setDataSource(FileName);
                mPlayer.prepare();
                mPlayer.start();
            }catch(IOException e){
                Log.e(LOG_TAG,"播放失败");
            }
        }

    }
    //停止播放录音
    class stopPlayListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPlayer.release();
            mPlayer = null;
        }

    }
}
