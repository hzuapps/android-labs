package edu.hzuapps.androidworks.homeworks.net1314080903120;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Net1314080903120dataBase extends SQLiteOpenHelper{
	/////////////////////////////////////////////////////////////////////////定义数据库字段名
	private static String DATABASE_NAME="scheme.db";
	private static String TABLE_NAME="project";
	private static String ID="project_id";
	private static String TOPIC="topic";
	private static String DETAIL="detail";
	private static String DATE="date";
	private static String TIME="time";
	private static String PRIORITY="priority";
	private static String PERIOD="period";
	private static String STATE="state";


	/////////////////////////////////////////////////////////////////////////构造方法
	public Net1314080903120dataBase(Context context) {
		super(context, DATABASE_NAME, null,1);
		// TODO Auto-generated constructor stub
	}
	/////////////////////////////////////////////////////////////////////////创建数据表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER primary key autoincrement, "+ PERIOD +" text," + TOPIC + " text, "+ DATE +" text,"
				+ TIME +" text,"+ DETAIL +" text,"+ PRIORITY +" text,"+ STATE +" text);";

		db.execSQL(sql);
		db.execSQL("INSERT INTO " + TABLE_NAME + " values(1 ,'短期计划','android作业','2016年4月29日','14:00','课程设计','紧急','未完成');");
		db.execSQL("INSERT INTO " + TABLE_NAME + " values(4 ,'中期计划','六级听力','2016年5月18日','14:30','submit the documents','重要','未完成');");

		Log.d("check",TABLE_NAME);



	}

	/////////////////////////////////////////////////////////////////////////插入数据的方法

	public long insert(String period,String topic,String date,String time,
					   String priority,String detail,String state)
	{


		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(PERIOD, period);
		cv.put(TOPIC, topic);
		cv.put(DATE, date);
		cv.put(TIME, time);
		cv.put(DETAIL, detail);
		cv.put(PRIORITY, priority);
		cv.put(STATE, state);



		long row= db.insert(TABLE_NAME, null, cv);
		return row;
	}


	//	public long insert(String topic,String date)
//	{
//		SQLiteDatabase db=this.getWritableDatabase();
//		ContentValues cv=new ContentValues();
//		cv.put(TOPIC, topic);
//		cv.put(DATE, date);
//		long row= db.insert(TABLE_NAME, null, cv);
//		return row;
//	}
	/////////////////////////////////////////////////////////////////////////删除
	public void delete(String id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + " = ?";
		String[] whereValue={id};
		db.delete(TABLE_NAME, where, whereValue);
	}
	////////////////////////////////////////////////////////////////////////修改数据方法
	public void update(String id,String period,String topic,String date,String time,
					   String priority,String detail)

	{

		SQLiteDatabase db = this.getWritableDatabase();

		String where = ID + " = ?";

		String[] whereValue = {id};

		ContentValues cv = new ContentValues();

		cv.put(PERIOD, period);
		cv.put(TOPIC, topic);
		cv.put(DATE, date);
		cv.put(TIME, time);
		cv.put(DETAIL, detail);
		cv.put(PRIORITY, priority);
		//cv.put(STATE, state);

		db.update(TABLE_NAME, cv, where, whereValue);

	}
	/////////////////////////////////////////////////////////////////////////插入数据中的状态的方法
	public void modifyState(String id,String state)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		String where = ID + " = ?";

		String[] whereValue = {id};

		ContentValues cv = new ContentValues();



		cv.put(STATE, state);


		db.update(TABLE_NAME, cv, where, whereValue);
	}

	/////////////////////////////////////////////////////////////////////////当表存在时则删除再创建

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//String initsql="INSERT INTO " + TABLE_NAME + " values(1 ,'zhangsan','2013/5/22');";
		//db.execSQL(initsql);
		Log.d("executing","insert initsql");
		String sql="DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);

	}
	/////////////////////////////////////////////////////////////////////////选择所有数据
	public Cursor select()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor=db.query(TABLE_NAME,  null, null, null, null, null, null);
		return cursor;
	}
	/////////////////////////////////////////////////////////////////////////插入特定id对应的数据
	public Cursor select(String id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String Selection= ID + " = ?";
		String[] selectionArgs={id};
		Cursor cursor=db.query(TABLE_NAME, null, Selection, selectionArgs, null, null, null);
		return cursor;
	}



}
