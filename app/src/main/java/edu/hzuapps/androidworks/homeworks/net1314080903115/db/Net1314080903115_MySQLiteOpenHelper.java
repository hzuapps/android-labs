package edu.hzuapps.androidworks.homeworks.net1314080903115.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Net1314080903115_MySQLiteOpenHelper extends SQLiteOpenHelper {
	/*
	 * 创建数据库
	 */
	public Net1314080903115_MySQLiteOpenHelper(Context context) {
		super(context, Net1314080903115_Constant.DATABASE_NAME, null, 1);
	}

	// 初始化表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建用户表
		db.execSQL("create table " + Net1314080903115_Constant.TABLE_Account + "("
				+ Net1314080903115_Constant.Account_ID + " integer primary key not null,"
				+ Net1314080903115_Constant.Account_Phone+ " text not null,"
				+ Net1314080903115_Constant.Account_PassWord+ " text not null,"
				+ Net1314080903115_Constant.Account_UserName + " text not null,"
				+ Net1314080903115_Constant.Account_HeadPortrait + " text, "
				+ Net1314080903115_Constant.Account_NoteTable + " text not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
