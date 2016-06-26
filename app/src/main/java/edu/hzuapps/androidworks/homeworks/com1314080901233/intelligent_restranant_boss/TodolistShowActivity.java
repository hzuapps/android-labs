package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.receiver.AlarmReceiver;
import com.example.intelligent_restranant_boss.util.DBHelper;

import java.util.Calendar;

public class TodolistShowActivity extends Activity {

	private AlarmManager alarmManager=null;
	Calendar cal=Calendar.getInstance();
	final int DIALOG_TIME = 0; //设置对话框id
	
	private DBHelper dbhelper;
	private SQLiteDatabase db;  
	
	SimpleCursorAdapter adapter = null;
	ListView lv;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todolist);
		
		alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		dbhelper = new DBHelper(this, "db_bwl", null, 1);
		db = dbhelper.getReadableDatabase();
		
		Cursor cursor = db.query("tb_bwl", new String[]{"id as _id","title","content","noticeDate","noticeTime"}, null, null, null, null,null);
		
		lv = (ListView)findViewById(R.id.lv_bwlList);
		ImageView iv_empty_show= (ImageView)findViewById(R.id.iv_empty_show);

		lv.setEmptyView(iv_empty_show);


		adapter = new SimpleCursorAdapter(this, R.layout.activity_todolist_listview_item, cursor,
				new String[]{"title","noticeDate","noticeTime","content"}, 
				new int[]{R.id.title,R.id.noticeDate,R.id.noticeTime,R.id.content});
		
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getDatas());
		
		lv.setAdapter(adapter);


		this.registerForContextMenu(lv);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String title = ((TextView)view.findViewById(R.id.title)).getText().toString();
				String content = ((TextView)view.findViewById(R.id.content)).getText().toString();
				String noticeDate = ((TextView)view.findViewById(R.id.noticeDate)).getText().toString();
				String noticeTime = ((TextView)view.findViewById(R.id.noticeTime)).getText().toString();
				
				Intent intent = new Intent();
				intent.setClass(TodolistShowActivity.this,TodolistEditActivity.class);
				Bundle bundle = new Bundle();
				bundle.putLong("id", id);
				bundle.putString("title", title);
				bundle.putString("content", content);
				bundle.putString("noticeDate", noticeDate);
				bundle.putString("noticeTime", noticeTime);
				
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
			
		});
		
		
		//添加备忘录按钮
		Button btnAdd = (Button)findViewById(R.id.bt_edit);
		btnAdd.setOnClickListener(new View.OnClickListener() { 
			public void onClick(View view) {
				Intent intent = new Intent(TodolistShowActivity.this,TodolistEditActivity.class);
				startActivity(intent);
			}
		});
		
		
//		//创建两个Intent,一个是用于AlarmReceiver类处理的，一个是用于广播的
//		AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//		Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
//		intent.setAction("ALARM_ACTION");
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
//		
//		//设置真正能让alarm起作用的参数
//		//设置alarm是以何种方式发生的，这里用的是以UTC时间为基准，并在alarm发生时同时唤醒设备的模式。
//		am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
//		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10*1000, 60*60*1000, pendingIntent);
		
		
	}

	//长按的上下文菜单
	public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo menuInfo){
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.add(0,3,0,"修改");
		menu.add(0,4,0,"删除");
	}
	
	public boolean onContextItemSelected(MenuItem item){
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId()){
		case 3:
			String title = ((TextView)menuInfo.targetView.findViewById(R.id.title)).getText().toString();
			String content = ((TextView)menuInfo.targetView.findViewById(R.id.content)).getText().toString();
			String noticeDate = ((TextView)menuInfo.targetView.findViewById(R.id.noticeDate)).getText().toString();
			String noticeTime = ((TextView)menuInfo.targetView.findViewById(R.id.noticeTime)).getText().toString();
			
			Intent intent = new Intent();
			intent.setClass(this,TodolistEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putLong("id", menuInfo.id);
			bundle.putString("title", title);
			bundle.putString("content", content);
			bundle.putString("noticeDate", noticeDate);
			bundle.putString("noticeTime", noticeTime);
			
			intent.putExtras(bundle);
			
			startActivity(intent);
			break;
		case 4:
			dbhelper = new DBHelper(this, "db_bwl", null, 1);
			db = dbhelper.getWritableDatabase();
			int status = db.delete("tb_bwl", "id=?", new String[]{""+menuInfo.id});
			if(status!=-1){
				//删除后更新listview
				Cursor cursor = db.query("tb_bwl", new String[]{"id as _id","title","content","noticeDate","noticeTime"}, null, null, null, null,null);
				adapter.changeCursor(cursor);
//				adapter.notifyDataSetChanged();
				Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this, "删除失败", Toast.LENGTH_LONG).show();
			}
			
			break;
			
		}
		
		
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog=null;
		switch (id) {
		case DIALOG_TIME:
		dialog=new TimePickerDialog(
		this,
		new TimePickerDialog.OnTimeSetListener(){
		public void onTimeSet(TimePicker timePicker, int hourOfDay,int minute) {
		Calendar c=Calendar.getInstance();//获取日期对象 
		c.setTimeInMillis(System.currentTimeMillis()); //设置Calendar对象
		c.set(Calendar.HOUR, hourOfDay); //设置闹钟小时数
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+1); //设置闹钟的分钟数
		c.set(Calendar.SECOND, 0); //设置闹钟的秒数
		c.set(Calendar.MILLISECOND, 0); //设置闹钟的毫秒数
		
		Intent intent = new Intent(TodolistShowActivity.this, AlarmReceiver.class); //创建Intent对象
//		intent.setAction("ALARM_ACTION");
		PendingIntent pi = PendingIntent.getBroadcast(TodolistShowActivity.this, 0, intent, 0); //创建PendingIntent
		
		//参数说明：http://www.eoeandroid.com/blog-119358-2995.html
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+30000, pi); //设置闹钟，当前时间就唤醒
		Toast.makeText(TodolistShowActivity.this, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户
		}
		},
		cal.get(Calendar.HOUR_OF_DAY),
		cal.get(Calendar.MINUTE),
		true);

		break;
		}
		
		Log.e("AndroidBWL", "onCreateDialog end...");
		return dialog;
	}


	@Override
	protected void onResume() {
		super.onResume();
		Cursor cursor = db.query("tb_bwl", new String[]{"id as _id","title","content","noticeDate","noticeTime"}, null, null, null, null,null);
		adapter.changeCursor(cursor);
	}
	
	
	
	
}
