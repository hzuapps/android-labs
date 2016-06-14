package edu.hzuapps.androidworks.homeworks.com1314080901130;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnClickListener {
	List<Object> musiclists = new ArrayList<Object>();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	ImageButton play_pause,  onplay, downplay;
	Button exit;
	ActivityReceiver activityReceiver;
	public static final String CTL_ACTION = "org.crazyit.action.CTL_ACTION";
	public static final String UPDATE_ACTION = "org.crazyit.action.UPDATE_ACTION";
	Intent intentservice;
	// 定义音乐的播放状态 ，0X11 代表停止 ，0x12代表播放,0x13代表暂停
	int status = 0x11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UIinit();
		logic();
		musicList();
		activityReceiver = new ActivityReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_ACTION);
		registerReceiver(activityReceiver, filter);
		intentservice = new Intent(this, MusicService.class);
		startService(intentservice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void UIinit() {
		play_pause = (ImageButton) this.findViewById(R.id.play_pause);


		onplay = (ImageButton) this.findViewById(R.id.onplay);
		exit = (Button) this.findViewById(R.id.exit);
		downplay = (ImageButton) this.findViewById(R.id.downplay);
	}

	public void logic() {
		play_pause.setOnClickListener(this);

		onplay.setOnClickListener(this);
		downplay.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View source) {
		Intent intent = new Intent(CTL_ACTION);
		switch (source.getId()) {
		case R.id.play_pause: {
			intent.putExtra("control", 1);
			break;
		}

		case R.id.onplay: {
			intent.putExtra("control", 3);
			break;
		}
		case R.id.downplay: {
			intent.putExtra("control", 4);
			break;
		}

		case R.id.exit: {
			stopService(intentservice);
			this.finish();
			break;
		}
		}
		sendBroadcast(intent);

	}

	
	public class ActivityReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取Intent中的update消息，update代表播放状态,-1为默认值。

		 	int update = intent.getIntExtra("update", -1);
			switch (update) {
			case 0x11: {
				status = 0x11;
				break;
			}

			// 控制系统进入播放状态
			case 0x12: {

				// 设置当前状态
              status = 0x12;
				break;
			}
			// 控制系统进入暂停状态

				case 0x13: {
				status = 0x13;
				break;
			}
			}
		}

	}

	/* 播放列表 */
	public void musicList() {
		// 取得指定位置的文件设置显示到播放列表
		String[] music = new String[] { Media._ID, Media.DISPLAY_NAME,
				Media.TITLE, Media.DURATION, Media.ARTIST, Media.DATA };
		//获取数据实例。即歌曲
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

		ListView listview = (ListView) findViewById(R.id.musics);
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.musicsshow, new String[] { "name", "artist" },
				new int[] { R.id.name, R.id.artist });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int current, long id) {

				Intent intent=new Intent(CTL_ACTION);
				intent.putExtra("control", 5);
				intent.putExtra("current", current);
				sendBroadcast(intent);
			}
		});
	}
}