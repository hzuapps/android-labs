package edu.hzuapps.androidworks.homeworks.net1314080903127;

import java.util.Calendar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class Net1314080903127_DbAdapter {

	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CREATED = "created";
	
	
	public static final String KEY_ROWID_COURSE = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_START = "start";
	public static final String KEY_END = "end";
	public static final String KEY_INDEX = "course_index";
	public static final String KEY_PLACE = "place";
	public static final String KEY_WEEK_INDEX = "week_index";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	//创建diary表
	private static final String DATABASE_CREATE = "create table diary " +
			"(_id integer primary key autoincrement, "
			+ "title text not null, body text not null, created text not null);";
	//创建course表
	private static final String DATABASE_CREATE_COURSE = "create table course " +
	"(_id integer not null primary key autoincrement, name text not null, " +
	"start integer not null, end integer not null, " + "course_index text not null," +
	" place text not null, week_index text not null);";

	//声明初始值
	private static final String DATABASE_NAME = "database.db";
	private static final String DATABASE_TABLE_DIARY = "diary";
	private static final String DATABASE_TABLE_COURSE = "course";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	//数据库类
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * 第一次创建数据库的时候执行 oncreate方法
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE_COURSE);
			Log.e("create", "111");
		}

		/**
		 * 更新数据库的操作
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS diary");
			db.execSQL("DROP TABLE IF EXISTS course");
			onCreate(db);
		}
	}

	public Net1314080903127_DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	//打开数据库
	public Net1314080903127_DbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		Log.e("open", "222");
		return this;
	}

	//关闭数据库
	public void closeclose() {
		Log.e("close", "asdf");
		mDbHelper.close();
	}

	//创建一个日记 
	//contenvalues只能存储基本类型的数据，像string，int之类的，不能存储对象这种东西
	public long createDiary(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_BODY, body);
		Calendar calendar = Calendar.getInstance();
		String created = calendar.get(Calendar.YEAR) + "年"
				+ calendar.get(Calendar.MONTH) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日"
				+ calendar.get(Calendar.HOUR_OF_DAY) + "时"
				+ calendar.get(Calendar.MINUTE) + "分";
		initialValues.put(KEY_CREATED, created);
		return mDb.insert(DATABASE_TABLE_DIARY, null, initialValues);
	}

	//删除选定日记
	public boolean deleteDiary(long rowId) {

		return mDb.delete(DATABASE_TABLE_DIARY, KEY_ROWID + "=" + rowId, null) > 0;
	}

	//得到所有日记
	public Cursor getAllNotes() {

		return mDb.query(DATABASE_TABLE_DIARY, new String[] { KEY_ROWID, KEY_TITLE,
				KEY_BODY, KEY_CREATED }, null, null, null, null, null);
	}

	//得到制定日记
	public Cursor getDiary(long rowId) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_DIARY, new String[] { KEY_ROWID, KEY_TITLE,
				KEY_BODY, KEY_CREATED }, KEY_ROWID + "=" + rowId, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//更新diary表
	public boolean updateDiary(long rowId, String title, String body) {
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_BODY, body);
		Calendar calendar = Calendar.getInstance();
		String created = calendar.get(Calendar.YEAR) + "年"
				+ calendar.get(Calendar.MONTH) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日"
				+ calendar.get(Calendar.HOUR_OF_DAY) + "时"
				+ calendar.get(Calendar.MINUTE) + "分";
		args.put(KEY_CREATED, created);

		return mDb.update(DATABASE_TABLE_DIARY, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//创建course
	public long createCourse(String name, int start, int end, String course_index, String place,String week_index_str) 
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_START, start);
		initialValues.put(KEY_END, end);
		initialValues.put(KEY_INDEX, course_index);
		initialValues.put(KEY_PLACE, place);
		initialValues.put(KEY_WEEK_INDEX, week_index_str);
		Log.e("createCourse", "333");
		return mDb.insert(DATABASE_TABLE_COURSE, null, initialValues);
	}

	//删除一个course
	public boolean deleteCourse(long rowId) {

		return mDb.delete(DATABASE_TABLE_COURSE, KEY_ROWID_COURSE + "=" + rowId, null) > 0;
	}

	//得到在指定日期内的课程
	public Cursor getAllCourses(int weeks) {
		Log.e("getAllCourses", "startgetAllCourses");

		return mDb.query(DATABASE_TABLE_COURSE, new String[] { KEY_ROWID_COURSE, KEY_NAME, KEY_PLACE, KEY_INDEX, KEY_WEEK_INDEX },
				KEY_START + "<=" + weeks +" and " +KEY_END + ">=" + weeks, null,null, null, KEY_WEEK_INDEX );
	}

	//得到所有的课程信息
	public Cursor getCourse(long rowId, String name) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_COURSE, new String[] { KEY_NAME, KEY_PLACE, KEY_INDEX }, 
				KEY_NAME + "=" + name, null, null,null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		Log.e("getCourse", "555");
		return mCursor;

	}
}
