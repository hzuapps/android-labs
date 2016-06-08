package edu.hzuapps.androidworks.homeworks2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Net1314080903124_MySqliteHelper extends SQLiteOpenHelper{

	private final static int VERSION = 1;
	
	public Net1314080903124_MySqliteHelper(Context context) {
		super(context, "RailwaySystem.db", null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Net1314080903124_AnswerColumns.getCreateTableSql());
		db.execSQL(Net1314080903124_CollectColumns.getCreateTableSql());
		db.execSQL(Net1314080903124_ErrorColumns.getCreateTableSql());
		db.execSQL(Net1314080903124_ExamResultColumns.getCreateTableSql());
		db.execSQL(Net1314080903124_ExamErrorColumns.getCreateTableSql());
		db.execSQL(Net1314080903124_HistoryResultColumns.getCreateTableSql());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
