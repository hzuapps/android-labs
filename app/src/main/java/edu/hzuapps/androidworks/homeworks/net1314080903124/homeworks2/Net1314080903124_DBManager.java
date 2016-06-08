package edu.hzuapps.androidworks.homeworks2;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Net1314080903124_DBManager {

	private static Net1314080903124_DBManager mInstance;
	private SQLiteDatabase mDB;

	public ArrayList<Net1314080903124_CauseInfo> query(String tableName) {
		Cursor cursor = mDB.query(tableName, null, null, null, null, null, null);
		boolean hasNext = cursor.moveToFirst();
		ArrayList<Net1314080903124_CauseInfo> mData = new ArrayList<Net1314080903124_CauseInfo>();
		while (hasNext) {
			String timu_title = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TYPES));
			int reply = cursor.getInt(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_REPLY));
			Net1314080903124_CauseInfo info = new Net1314080903124_CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow, daan_three,
					daan_four,detail, types, reply);
			mData.add(info);
			hasNext = cursor.moveToNext();
		}
		cursor.close();
		return mData;
	}

	public ArrayList<Net1314080903124_CauseInfoHasId> queryHasId(String tableName) {
		Cursor cursor = mDB.query(tableName, null, null, null, null, null, null);
		boolean hasNext = cursor.moveToFirst();
		ArrayList<Net1314080903124_CauseInfoHasId> mData = new ArrayList<Net1314080903124_CauseInfoHasId>();
		while (hasNext) {
			int id = cursor.getInt(cursor.getColumnIndex(Net1314080903124_BaseColumns.ID));
			String timu_title = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursor.getString(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TYPES));
			int reply = cursor.getInt(cursor.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_REPLY));
			Net1314080903124_CauseInfoHasId info = new Net1314080903124_CauseInfoHasId(id, timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
					daan_three, daan_four,detail, types, reply);
			mData.add(info);
			hasNext = cursor.moveToNext();
		}
		cursor.close();
		return mData;
	}

	public ArrayList<Net1314080903124_CauseInfo> queryExam(String tableName) { 
		Cursor cursorDan = mDB.query(tableName, null, Net1314080903124_BaseColumns.COLUMN_TYPES + "=?", new String[] { "单选" }, null, null,
				"RANDOM() limit 8");
		Cursor cursorDuo = mDB.query(tableName, null, Net1314080903124_BaseColumns.COLUMN_TYPES + "=?", new String[] { "多选" }, null, null,
				"RANDOM() limit 6");
		Cursor cursorPan = mDB.query(tableName, null, Net1314080903124_BaseColumns.COLUMN_TYPES + "=?", new String[] { "判断" }, null, null,
				"RANDOM() limit 6");
		boolean hasNextDan = cursorDan.moveToFirst();
		ArrayList<Net1314080903124_CauseInfo> mData = new ArrayList<Net1314080903124_CauseInfo>();
		while (hasNextDan) {
			String timu_title = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursorDan.getString(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TYPES));
			int reply = cursorDan.getInt(cursorDan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_REPLY));
			Net1314080903124_CauseInfo info = new Net1314080903124_CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow, daan_three,
					daan_four,detail, types, reply);
			mData.add(info);
			hasNextDan = cursorDan.moveToNext();
		}
		cursorDan.close();
		boolean hasNextDuo = cursorDuo.moveToFirst();
		while (hasNextDuo) {
			String timu_title = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursorDuo.getString(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TYPES));
			int reply = cursorDuo.getInt(cursorDuo.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_REPLY));
			Net1314080903124_CauseInfo info = new Net1314080903124_CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow, daan_three,
					daan_four,detail, types, reply);
			mData.add(info);
			hasNextDuo = cursorDuo.moveToNext();
		}
		cursorDuo.close();
		boolean hasNextPan = cursorPan.moveToFirst();
		while (hasNextPan) {
			String timu_title = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursorPan.getString(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_TYPES));
			int reply = cursorPan.getInt(cursorPan.getColumnIndex(Net1314080903124_BaseColumns.COLUMN_REPLY));
			Net1314080903124_CauseInfo info = new Net1314080903124_CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow, daan_three,
					daan_four,detail, types, reply);
			mData.add(info);
			hasNextPan = cursorPan.moveToNext();
		}
		cursorPan.close();
		return mData;
	}
	
	public ArrayList<Net1314080903124_HisResult> queryHisResult(String tableName) {
		Cursor cursor = mDB.query(tableName, null, null, null, null, null, null);
		boolean hasNext = cursor.moveToFirst();
		ArrayList<Net1314080903124_HisResult> mData = new ArrayList<Net1314080903124_HisResult>();
		while (hasNext) {
			String curTime = cursor.getString(cursor.getColumnIndex(Net1314080903124_HistoryResultColumns.COLUMN_CUR_TIME));
			String useTime = cursor.getString(cursor.getColumnIndex(Net1314080903124_HistoryResultColumns.COLUMN_USE_TIME));
			String hisResult = cursor.getString(cursor.getColumnIndex(Net1314080903124_HistoryResultColumns.COLUMN_HIS_RESULT));
			String userName = cursor.getString(cursor.getColumnIndex(Net1314080903124_HistoryResultColumns.COLUMN_USER_NAME));
			Net1314080903124_HisResult result = new Net1314080903124_HisResult(curTime, useTime, hisResult, userName);
			mData.add(result);
			hasNext = cursor.moveToNext();
		}
		cursor.close();
		return mData;
	}
	
	public void insert(String tableName, Net1314080903124_CauseInfo info) {
		// 判断数据库中是否已存在
		Cursor cursor = mDB.query(tableName, new String[] { Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE }, Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE + "=?",
				new String[] { info.getTimu_title() }, null, null, null);
		if (cursor.getCount() == 0) {
			ContentValues values = new ContentValues();
			values.put(Net1314080903124_AnswerColumns.COLUMN_TIMU_TITLE, info.getTimu_title());
			values.put(Net1314080903124_AnswerColumns.COLUMN_TIMU_ONE, info.getTimu_one());
			values.put(Net1314080903124_AnswerColumns.COLUMN_TIMU_TOW, info.getTimu_tow());
			values.put(Net1314080903124_AnswerColumns.COLUMN_TIMU_THREE, info.getTimu_three());
			values.put(Net1314080903124_AnswerColumns.COLUMN_TIMU_FOUR, info.getTimu_four());
			values.put(Net1314080903124_AnswerColumns.COLUMN_DAAN_ONE, info.getDaan_one());
			values.put(Net1314080903124_AnswerColumns.COLUMN_DAAN_TOW, info.getDaan_tow());
			values.put(Net1314080903124_AnswerColumns.COLUMN_DAAN_THREE, info.getDaan_three());
			values.put(Net1314080903124_AnswerColumns.COLUMN_DAAN_FOUR, info.getDaan_four());
			values.put(Net1314080903124_AnswerColumns.COLUMN_DAAN_DETAIL, info.getDetail());
			values.put(Net1314080903124_AnswerColumns.COLUMN_TYPES, info.getTypes());
			values.put(Net1314080903124_AnswerColumns.COLUMN_REPLY, info.reply);
			mDB.insert(tableName, null, values);
		}
	}

	public void insertHisResult(Net1314080903124_HisResult info) {
		// 判断数据库中是否已存在
			ContentValues values = new ContentValues();
			values.put(Net1314080903124_HistoryResultColumns.COLUMN_CUR_TIME, info.getCurTime());
			values.put(Net1314080903124_HistoryResultColumns.COLUMN_USE_TIME, info.getUseTime());
			values.put(Net1314080903124_HistoryResultColumns.COLUMN_HIS_RESULT, info.getHisResult());
			values.put(Net1314080903124_HistoryResultColumns.COLUMN_USER_NAME, info.getName());
			mDB.insert(Net1314080903124_HistoryResultColumns.TABLE_NAME, null, values);
	}
	
	public void update(String tableName, Net1314080903124_CauseInfo info) {
		ContentValues values = new ContentValues();
		values.put(Net1314080903124_BaseColumns.COLUMN_REPLY, info.reply);
		mDB.update(tableName, values, Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE + "=?", new String[] { info.getTimu_title() });
	}

	public void updateWhenDestroy(String tableName) {
		ContentValues values = new ContentValues();
		values.put(Net1314080903124_BaseColumns.COLUMN_REPLY, Net1314080903124_BaseColumns.NULL);
		mDB.update(tableName, values, null, null);
	}

	public void remove(String tableName, Net1314080903124_CauseInfo info) {
		mDB.delete(tableName, Net1314080903124_BaseColumns.COLUMN_TIMU_TITLE + "=?", new String[] { info.getTimu_title() });
	}

	public void removeAll(String tableName) {
		mDB.delete(tableName, null, null);
	}
	
	public static synchronized Net1314080903124_DBManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new Net1314080903124_DBManager(context);
		}
		return mInstance;
	}

	private Net1314080903124_DBManager(Context context) {
		Net1314080903124_MySqliteHelper helper = new Net1314080903124_MySqliteHelper(context);
		mDB = helper.getWritableDatabase();
	}

}
