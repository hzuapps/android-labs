package com.example.recoder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

	private ToggleButton record,control;
	private Button stop;
	private MediaRecorder recorder;
	private MediaPlayer player;
	private ListView list;
	private ArrayList<String> mylist;
	private ArrayAdapter<String> adapter;
	private String songPath;
	private SeekBar bar;
	
	
    Handler handler=new Handler();
    Runnable updateThread=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			bar.setProgress(player.getCurrentPosition()/1000);
			handler.postDelayed(updateThread, 100);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        record=(ToggleButton)findViewById(R.id.toggleButton1);
        control=(ToggleButton)findViewById(R.id.toggleButton2);
        stop=(Button)findViewById(R.id.button1);
        list=(ListView)findViewById(R.id.listView1);
        bar=(SeekBar)findViewById(R.id.seekBar1);
        mylist=new ArrayList<String>();
        player=new MediaPlayer();
        
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_activated_1,mylist);
        list.setAdapter(adapter);
        
        refreshFiles();
        
        record.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (record.isChecked()) {
					startRecord();
					Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
				} else {
					stopRecord();
					refreshFiles();
					Toast.makeText(MainActivity.this, "结束录音", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        control.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (control.isChecked()) {
					player.start();
					bar.setMax(player.getDuration()/1000);
					handler.post(updateThread);
				} else {
					handler.removeCallbacks(updateThread);
					player.pause();
				}
			}
		});
        
        stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handler.removeCallbacks(updateThread);
				player.stop();
				bar.setProgress(0);
				control.setChecked(false);
				prepareSong();
			}
		});
        
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				songPath=(String) list.getItemAtPosition(position);
				songPath=Environment.getExternalStorageDirectory().getPath()+"/sound_recorder/"+songPath;
				prepareSong();
			}
		});
        
        list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				songPath=(String) list.getItemAtPosition(position);
				songPath=Environment.getExternalStorageDirectory().getPath()+"/sound_recorder/"+songPath;
				
				AlertDialog.Builder builder=new Builder(MainActivity.this);
				builder.setMessage("确定删除吗?");
				builder.setTitle("提示");
				builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						File f=new File(songPath);
						if (f.exists()) {
							f.delete();
							refreshFiles();
						}
							
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
				return false;
			}
		});
        
        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser==true) {
					player.seekTo(progress*1000);
				}
			}
		});

    }
    
    private void startRecord() {
    	recorder=new MediaRecorder();
    	recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    	recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    	recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    	recorder.setAudioChannels(1);
    	recorder.setOutputFile(Environment.getExternalStorageDirectory().getPath()+"/sound_recorder/"+getTime()+".amr");
    	
    	
    	try {
    		recorder.prepare();
    		recorder.start();
    	} catch (IllegalStateException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private void stopRecord() {
    	
    	
    	
        recorder.stop();
        recorder.reset();
        recorder.release();
    }
    
    private void startPlay() {
    	
    }
    
    private void refreshFiles() {
    	String dir=Environment.getExternalStorageDirectory().getPath()+"/sound_recorder";
        File []files=new File(dir).listFiles();
        String result;
        mylist.clear();
        
        for (File file:files) {
        	mylist.add(file.getName());
        }
        
    	adapter.notifyDataSetChanged();
    }
    
    private String getTime() {
    	SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    	Date curDate=new Date(System.currentTimeMillis());
    	String str=formatter.format(curDate);
    	return str;
    }
    
    private void prepareSong() {
		try {
			player.reset();
			player.setDataSource(songPath);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    
}
