package com.example.mense.net1314080903237;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Net1314080903237_DBHelper extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "mycontacts.db";//数据库名
	public static final int DATABASE_VERSION = 2;				 //版本
	public static final String CONTACTS_TABLE = "contacts";	 //表名
	//创建表
	private static final String DATABASE_CREATE = 
		"CREATE TABLE " + CONTACTS_TABLE +" ("					
		+ Net1314080903237_ContactColumn._ID+" integer primary key autoincrement,"
		+ Net1314080903237_ContactColumn.NAME+" text,"
		+ Net1314080903237_ContactColumn.MOBILENUM+" text,"
		+ Net1314080903237_ContactColumn.HOMENUM+" text,"
		+ Net1314080903237_ContactColumn.ADDRESS+" text,"
		+ Net1314080903237_ContactColumn.EMAIL+" text,"
		+ Net1314080903237_ContactColumn.BLOG+" text);";
	public Net1314080903237_DBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
		onCreate(db);
	}
}

