package com.example.com1314080901210.Kechengbiao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="lesson.db";
	private static final int DATABASE_VERSION=1;
	
	public DBHelper(Context ctx){
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL("create table event (day_no integer not null,class_no integer not null,class_name text not null," +
				"class_address text not null,class_teacher text not null,primary key (day_no,class_no,class_name))");
		Log.i("database", "create event");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	public Cursor query(String sql, String[] args)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);		
		return cursor;
	}
}
