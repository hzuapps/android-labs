package com.example.intelligent_restranant_boss.weather;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MyWidgetProviderInfo extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context,WidgetService2.class));
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		context.startService(new Intent(context,WidgetService2.class));
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	
	@Override
	public void onDisabled(Context context) {
		context.stopService(new Intent(context,WidgetService2.class));
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		context.startService(new Intent(context,WidgetService2.class));
		super.onEnabled(context);
	}
	
}
