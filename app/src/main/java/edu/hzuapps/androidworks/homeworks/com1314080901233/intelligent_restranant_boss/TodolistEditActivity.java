package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.receiver.AlarmReceiver;
import com.example.intelligent_restranant_boss.util.DBHelper;

import java.util.Calendar;
import java.util.Date;

public class TodolistEditActivity extends Activity {
	private EditText et_date = null, et_time = null, et_title = null,
			et_content = null;
	private ImageButton ib_save = null;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	private DBHelper dbhelper;

	Bundle bundle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todolist_add_note);

		dbhelper = new DBHelper(this, "db_bwl", null, 1);

		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);

		et_date = (EditText) findViewById(R.id.et_date);
		et_date.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(DATE_DIALOG_ID);
			}
		});

		et_time = (EditText) findViewById(R.id.et_time);
		et_time.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(TIME_DIALOG_ID);
			}
		});

		ib_save = (ImageButton) findViewById(R.id.ib_save);
		ib_save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ContentValues value = new ContentValues();
				String title = et_title.getText().toString();
				String content = et_content.getText().toString();
				String noticeDate = et_date.getText().toString();
				String noticeTime = et_time.getText().toString();

				value.put("title", title);
				value.put("content", content);
				value.put("noticeDate", noticeDate);
				value.put("noticeTime", noticeTime);

				SQLiteDatabase db = dbhelper.getWritableDatabase();

				long id = 0;

				long status = 0;
				if (bundle != null) {
					id = bundle.getLong("id");
					status = db.update("tb_bwl", value, "id=?",
							new String[] { bundle.getLong("id") + "" });
				} else {
					status = db.insert("tb_bwl", null, value);
					id = status;
				}

				if (status != -1) {
					setAlarm(id);
					Toast.makeText(TodolistEditActivity.this, "保存成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(TodolistEditActivity.this, "保存失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		// 获取上一个activity的传值
		bundle = this.getIntent().getExtras();
		if (bundle != null) {
			et_date.setText(bundle.getString("noticeDate"));
			et_time.setText(bundle.getString("noticeTime"));
			et_title.setText(bundle.getString("title"));
			et_content.setText(bundle.getString("content"));
		}

	}

	private OnDateSetListener dateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker datePicker, int year, int month,
				int day) {

			StringBuilder sb_date = new StringBuilder();
			sb_date.append(year).append("-").append(month + 1).append("-")
					.append(day);

			et_date.setText(sb_date.toString());
		}
	};

	private OnTimeSetListener timeSetListener = new OnTimeSetListener() {
		public void onTimeSet(TimePicker timePicker, int hour, int minute) {
			StringBuilder timeStr = new StringBuilder();
			timeStr.append(hour).append(":").append(minute);
			et_time.setText(timeStr.toString());
		}
	};

	// 当Activity调用showDialog函数时会触发该函数的调用
	protected Dialog onCreateDialog(int id) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		switch (id) {
		case DATE_DIALOG_ID:
			DatePickerDialog dpd = new DatePickerDialog(this, dateSetListener,
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			dpd.setCancelable(true);
			dpd.setTitle("选择日期");
			dpd.show();
			break;
		case TIME_DIALOG_ID:
			TimePickerDialog tpd = new TimePickerDialog(this, timeSetListener,
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
					true);
			tpd.setCancelable(true);
			tpd.setTitle("选择时间");
			tpd.show();
			break;
		default:
			break;
		}
		return null;
	}

	private AlarmManager alarmManager = null;

	public void setAlarm(long id) {

		Log.e("AndroidBWL", "setAlarm start...");

		String noticeDate = et_date.getText().toString();
		String noticeTime = et_time.getText().toString();

		//Calendar calendar = Calendar.getInstance();

	//	calendar.set(Integer.parseInt(noticeDate.split("-")[0]),
		//		Integer.parseInt(noticeDate.split("-")[1]) - 1,
		//		Integer.parseInt(noticeDate.split("-")[2]),
		//		Integer.parseInt(noticeTime.split(":")[0]),
		//		Integer.parseInt(noticeTime.split(":")[1]));

	//	Log.e("AndroidBWL",
	//			"" + (calendar.getTimeInMillis() - System.currentTimeMillis()));
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(TodolistEditActivity.this, AlarmReceiver.class); // 创建Intent对象
		Bundle bundle = new Bundle();
		bundle.putLong("id", id);
		bundle.putString("title", et_title.getText().toString());
		bundle.putString("content", et_content.getText().toString());
		bundle.putString("noticeDate", et_date.getText().toString());
		bundle.putString("noticeTime", et_time.getText().toString());

		intent.putExtras(bundle);

		// PendingIntent.getBroadcast intent 数据不更新。
		// 传不同的 action 来解决这个问题
		//intent.setAction("ALARM_ACTION" + calendar.getTimeInMillis());

		PendingIntent pi = PendingIntent.getBroadcast(TodolistEditActivity.this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT); // 创建PendingIntent

		// 参数说明：http://www.eoeandroid.com/blog-119358-2995.html
		//alarmManager.set(AlarmManager.RTC_WAKEUP,
		//		calendar.getTimeInMillis() + 5000, pi); // 设置闹钟，当前时间就唤醒

		Log.e("AndroidBWL", "setAlarm end...");

	}

}
