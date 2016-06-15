package edu.hzuapps.androidworks.homeworks.com1314080901130;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;


public class MusicService extends Service {
	List<Object> musiclists = new ArrayList<Object>();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	MyReceiver serviceReceiver;
	MediaPlayer mPlayer;
	int status = 0x11;
	//歌曲下标
	int current = 0;
	int count = 0;
    int flog = 0;
    //String filename;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		flog=0;
		mPlayer.stop();
		mPlayer.release();
		super.onDestroy();
	}

	@Override
	public void onCreate() {
		flog=1;
		musicList();
		count = list.size();
		Log.e("Service", "onStart");
		serviceReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MainActivity.CTL_ACTION);
		registerReceiver(serviceReceiver, filter);
		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				current++;
				if (current >= count) {
					current = 0;
				}
				String filename = ((Music) musiclists.get(current)).getData();
				playMusic(filename);
			}
		});
		
		super.onCreate();
	}

	
	@Override
	public void onStart(Intent intent, int startId) {	
		if(flog==2){
			Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
			sendIntent.putExtra("update", status);
			//sendIntent.putExtra("current", current);
			sendBroadcast(sendIntent);
		}
		flog=2;
	}


	
	
	
	 private void playMusic(String path)   
	    {   
	        try   
	        {   
	            /* 重置MediaPlayer */
				mPlayer.reset();
	            /* 设置要播放的文件的路径 */
				mPlayer.setDataSource(path);
				/* 准备播放 */
				mPlayer.prepare();
	             /* 开始播放 */
				mPlayer.start();
	        	mPlayer.setOnCompletionListener(new OnCompletionListener()    
	            {   
	                public void onCompletion(MediaPlayer arg0)   
	                {
						//播放完成一首之后进行下一首
						current--;
	    				if (current < 0) {
	    					current = count;
	    				}
	    				playMusic( ((Music) musiclists.get(current)).getData());
	    				status = 0x12;
	                }   
	            });   
	        }catch (IOException e){}   
	    }

		/* 播放列表 */
		public void musicList() {
			// 取得指定位置的文件设置显示到播放列表
			String[] music = new String[] { Media._ID, Media.DISPLAY_NAME,
					Media.TITLE, Media.DURATION, Media.ARTIST, Media.DATA };
			Cursor cursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI,
					music, null, null, null);
			while (cursor.moveToNext()) {
				Music temp = new Music();
				temp.setFilename(cursor.getString(1));
				temp.setTitle(cursor.getString(2));
				temp.setDuration(cursor.getInt(3));
				temp.setArtist(cursor.getString(4));
				temp.setData(cursor.getString(5));
				musiclists.add(temp);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", cursor.getString(1));
				map.put("artist", cursor.getString(4));
				list.add(map);
			}
		}
	
	public class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int control = intent.getIntExtra("control", -1);
			switch (control) {
			case 1: {
				// 如果原来处于停止的状态
				if (status == 0x11) {
					playMusic( ((Music) musiclists.get(current)).getData());
					status = 0x12;
				}
				// 原来处于播放状态
				else if (status == 0x12) {
					mPlayer.pause();
					status = 0x13;
				}
				// 原来处于暂停状态
				else if (status == 0x13) {
					mPlayer.start();
					status = 0x12;
				}
				break;
			}
			case 2: {
				// 如果原来正在播放或暂停
				if (status == 0x12 || status == 0x13) {
					mPlayer.stop();
					status = 0x11;
				}
				break;
			}
			case 3: {
				current--;
				if (current < 0) {
					current = count;
				}
				playMusic( ((Music) musiclists.get(current)).getData());
				status = 0x12;
				break;
			}
			case 4: {
				current++;
				if (current > count) {
					current = 0;
				}
				playMusic( ((Music) musiclists.get(current)).getData());
				status = 0x12;
				break;
			}
			}

			Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
			sendIntent.putExtra("update", status);
			sendBroadcast(sendIntent);
		}

	}

	
}
