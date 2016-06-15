package edu.hzuapps.androidworks.homeworks.com1314080901122;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MyNoteEdit extends Activity {
	int mAppWidgetId;
	TextView mTextView;
	ImageButton mImBtn1, mImBtn2, mImBtn3, mImBtn4;

	final String mPerfName = "com.silenceburn.MyNoteConf";
	SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901122_my_note_conf);

		Intent t = getIntent();
		Log.i("myLog", t.getAction());
		mAppWidgetId = t.getExtras().getInt(
				AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		Log.i("myLog", "it's [" + mAppWidgetId + "] editing!");

		mPref = getSharedPreferences(mPerfName, 0);
		String noteContent = mPref.getString("DAT" + mAppWidgetId, "");

		mTextView = (TextView) findViewById(R.id.EditText02);
		mTextView.setText(noteContent);
		mImBtn1 = (ImageButton) findViewById(R.id.ImageButton01);
		mImBtn2 = (ImageButton) findViewById(R.id.ImageButton02);
		mImBtn3 = (ImageButton) findViewById(R.id.ImageButton03);
		mImBtn4 = (ImageButton) findViewById(R.id.ImageButton04);

		mImBtn1.setOnClickListener(mBtnClick);
		mImBtn2.setOnClickListener(mBtnClick);
		mImBtn3.setOnClickListener(mBtnClick);
		mImBtn4.setOnClickListener(mBtnClick);

	}

	OnClickListener mBtnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			SharedPreferences.Editor prefsEdit = mPref.edit();
			prefsEdit.putString("DAT" + mAppWidgetId, mTextView.getText()
					.toString());
			prefsEdit.commit();

			int srcId = R.drawable.com1314080901122_sketchy_paper_008;
			switch (v.getId()) {
			case R.id.ImageButton01:
				srcId = R.drawable.com1314080901122_sketchy_paper_003;
				break;
			case R.id.ImageButton02:
				srcId = R.drawable.com1314080901122_sketchy_paper_004;
				break;
			case R.id.ImageButton03:
				srcId = R.drawable.com1314080901122_sketchy_paper_007;
				break;
			case R.id.ImageButton04:
				srcId = R.drawable.com1314080901122_sketchy_paper_011;
				break;
			}

			RemoteViews views = new RemoteViews(MyNoteEdit.this
					.getPackageName(), R.layout.activity_com1314080901122_my_note_widget);
			views.setImageViewResource(R.id.my_widget_img, srcId);

			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(MyNoteEdit.this);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);

			MyNoteEdit.this.finish();
		}
	};
}
