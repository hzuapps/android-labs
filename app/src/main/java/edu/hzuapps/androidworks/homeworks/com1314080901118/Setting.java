package edu.hzuapps.androidworks.homeworks.com1314080901118;

import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
//设置窗口
public class Setting extends AppCompatActivity {
    private AudioManager audioManager;
    private TextView volumeView;
    private SeekBar SoundseekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1314080901118);
        volumeView=(TextView)findViewById(R.id.tv);
        SoundseekBar=(SeekBar)findViewById(R.id.seekBar);
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);//获取音量服务
        int MaxSound=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取系统音量最大值
        SoundseekBar.setMax(MaxSound);//音量控制Bar的最大值设置为系统音量最大值
        int currentSount=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前音量
        final int excurrentSount=currentSount;
        SoundseekBar.setProgress(currentSount);//音量控制Bar的当前值设置为系统音量当前值
        SoundseekBar.setOnSeekBarChangeListener(new SeekBarListener());
        Button btn_ok = (Button) findViewById(R.id.ok_btn); //确定按钮设置
        btn_ok.setOnClickListener(new Button.OnClickListener() {
                                      public void onClick(View view)  //创建监听
                                      {
                                          Toast toast = Toast.makeText(Setting.this, "设置完成", Toast.LENGTH_LONG);
                                          toast.show();
                                          Setting.this.finish();
                                      }
                                  }
        );
        Button btn_cncel = (Button) findViewById(R.id.cncel_btn);     //取消按钮设置
        btn_cncel.setOnClickListener(new Button.OnClickListener() {
                                       public void onClick(View view) {
//                                           Intent in = new Intent();
//                                           in.setClassName(getApplicationContext(), "com.hzu.lxh.mencard.Com1314080901118Activity");
//                                           startActivity(in);
                                           SoundseekBar.setProgress(excurrentSount);
                                           SeekBar seekBar=SoundseekBar;
                                           int SeekPosition=seekBar.getProgress();
                                           audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,SeekPosition, 0);
//                                           Setting.this.finish();
                                       }
                                   }
        );
    }

    class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser) {
                int SeekPosition=seekBar.getProgress();
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, SeekPosition, 0);
            }
            volumeView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

    }
}
