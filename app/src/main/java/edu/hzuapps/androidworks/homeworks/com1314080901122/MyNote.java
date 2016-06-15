package edu.hzuapps.androidworks.homeworks.com1314080901122;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class MyNote extends AppWidgetProvider {
	/** Called when the activity is first created. */
	
	final String mPerfName = "com.silenceburn.MyColorNoteConf";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Log.i("myLog", "this is [" + appWidgetId + "] onUpdate!");

		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Log.i("myLog", "this is [" + appWidgetId + "] onDelete!");
		}
	}

}