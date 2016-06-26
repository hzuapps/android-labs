package com.example.intelligent_restranant_boss.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DBHelper extends SQLiteOpenHelper {
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version); 
	}

	public void onCreate(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("create table if not exists tb_bwl(")
		.append("id integer primary key autoincrement,")
		.append("title varchar(50),")
		.append("content varchar(200),")
		.append("createDate varchar(10),")
		.append("noticeDate varchar(10),")
		.append("noticeTime varchar(5) )");
		db.execSQL(sql.toString());
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
