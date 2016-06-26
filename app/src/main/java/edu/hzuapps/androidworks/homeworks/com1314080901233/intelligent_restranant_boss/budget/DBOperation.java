package com.example.intelligent_restranant_boss.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBOperation {

	private SQLiteDatabase db;
	private Context mContext;
	private final String DATEBASE_NAME = "myDatebase";
	public static final String TABLE_NAME = "budgetsDetail";

	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	public static final String MEAL = "meal";
	public static final String SNACKS = "snacks";
	public static final String CLOTHES = "clothes";
	public static final String LIFE = "life";
	public static final String OTHERS = "others";
	public static final String REMARKS = "remarks";
	public static final String TOTAL = "total";

	public DBOperation(Context context) {
		mContext = context;
	}

	/**
	 * 建立及打开数据库
	 */
	public void openOrCreateDatebase() {
		// 建立连接
		db = mContext.openOrCreateDatabase(DATEBASE_NAME,
				mContext.MODE_PRIVATE, null);
		// 建立表
		// db.execSQL("create TABLE " + TABLE_NAME
		// + "(userId INTEGER PRIMARY KEY ," + COLUMN_USERNAME
		// + " VARCHAR(50)  NOT NULL," + COLUMN_USERADDRESS
		// + " VARCHAR(50))");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
				+ "(id integer primary key autoincrement," + YEAR
				+ " varchar(10) not null" + "," + MONTH
				+ " varchar(10) not null," + DAY + " varchar(10) not null ,"
				+ MEAL + " varchar(10)," + SNACKS + " varchar(10)," + CLOTHES
				+ " varchar(10)," + LIFE + " varchar(10)," + OTHERS
				+ " varchar(10)," + REMARKS + " varchar(100)," + TOTAL
				+ " varchar(10))");

	}

	public void insert(String year, String month, String day, String meal,
			String snacks, String clothes, String life, String others,
			String remarks, String total) {
		// 插入一条记录
		ContentValues values = new ContentValues();
		values.put(YEAR, year);
		values.put(MONTH, month);
		values.put(DAY, day);
		values.put(MEAL, meal);
		values.put(SNACKS, snacks);
		values.put(CLOTHES, clothes);
		values.put(LIFE, life);
		values.put(OTHERS, others);
		values.put(REMARKS, remarks);
		values.put(TOTAL, total);
		db.insert(TABLE_NAME, "", values);
	}

	public void delete(String id) {
		String whereClause = "id=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		db.delete(TABLE_NAME, whereClause, whereArgs);
	}

	public void update(String id) {
		String[] columns = new String[] { "id", "username", "info" };
		String selection = "id=?";
		String[] selectionArgs = { String.valueOf(id) };
		String groupBy = null;
		String having = null;
		String orderBy = null;
		db.query(TABLE_NAME, columns, selection, selectionArgs,
				groupBy, having, orderBy);
	}

	public Cursor queryByYear(String year) {
		// 要返回的列
		String[] columns = new String[] { YEAR, MONTH, DAY, MEAL, SNACKS,
				CLOTHES, LIFE, OTHERS, REMARKS, TOTAL };

		// 查询条件语句
		String selection = YEAR + "=?";

		// 查询匹配条件，与selection要对应
		String[] selectionArgs = new String[] { year };

		// 调用SQLiteDatabase类的query函数查询记录

		return db.query(TABLE_NAME, columns, selection,
				selectionArgs, null, null, null);
	}

	public Cursor queryByMonth(String month) {
		String[] columns = new String[] { YEAR, MONTH, DAY, MEAL, SNACKS,
				CLOTHES, LIFE, OTHERS, REMARKS, TOTAL };
		String selection = MONTH + "=?";
		String[] selectionArgs = new String[] { month };
		return db.query(TABLE_NAME, columns, selection,
				selectionArgs, null, null, null);
	}

	public Cursor queryByDay(String day) {
		String[] columns = new String[] { YEAR, MONTH, DAY, MEAL, SNACKS,
				CLOTHES, LIFE, OTHERS, REMARKS, TOTAL };
		String selection = DAY + "=?";
		String[] selectionArgs = new String[] { day };
		return db.query(TABLE_NAME, columns, selection,
				selectionArgs, null, null, null);
	}

	public Cursor queryByDay(String year, String month, String day) {
		String[] columns = new String[] { YEAR, MONTH, DAY, MEAL, SNACKS,
				CLOTHES, LIFE, OTHERS, REMARKS, TOTAL };
		String selection = DAY + "=?";
		String selection1 = YEAR + "=?";
		String selection2 = MONTH + "=?";
		String[] selectionArgs = new String[] { year, month, day };

		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " where '" + YEAR + "'='" + year + "' and '" + MONTH + "'='"
				+ month + "' and '" + DAY + "'='" + day + "'", null);

		// Cursor c = db.query(TABLE_NAME, new String[] { year,
		// month, day }, YEAR + "=" + year + " and " + MONTH + "=" + month
		// + " and " + DAY + "=" + day, null, null, null, null);

		return cursor;
	}

	/**
	 * 查询指定表中的数据并封装到对象中
	 * 
	 * @param table
	 *            表名。相当于select语句from关键字后面的部分。例：query("user", new
	 *            String[]{"u_name,u_id"}, "u_name like ?", new
	 *            String[]{"%张三%"});
	 * @param columns
	 *            要查询出来的列名。相当于select语句select关键字后面的部分，为null则查询出所有列。
	 * @param whereClause
	 *            查询条件子句，相当于select语句where关键字后面的部分，多重条件用 AND 连接，在条件子句允许使用占位符“?”
	 * @param whereArgs
	 *            对应于whereClause语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致。
	 * @return 返回封装对象
	 */
	public Cursor query(String table, String[] columns, String whereClause,
			String[] whereArgs) {
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.query(table, columns, whereClause,
					whereArgs, null, null, null);
//			if (cursor != null) {
//				cursor.close();
//			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

		return cursor;
	}

	/**
	 * 更新表中的数据
	 * 
	 * @param table
	 *            表名，例：update("user", new String[]{"u_name"}, new
	 *            String[]{"tao"}, "u_id=?", new String[]{"2"});
	 * @param columns
	 *            更新的字段（列）
	 * @param values
	 *            更新的值
	 * @param whereClause
	 *            更新条件子句，为null则更新所有行，在条件子句允许使用占位符“?”，如：u_id=?
	 * @param whereArgs
	 *            对应于whereClause语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致。
	 * @return the number of rows affected
	 */
	public int update( String[] columns, String[] values,
			String whereClause, String[] whereArgs) {
		int rows = -1;
		db.beginTransaction();
		try {
			ContentValues cv = new ContentValues();
			for (int i = 0; i < columns.length; i++) {
				cv.put(columns[i], values[i]);
			}

			rows = db.update(TABLE_NAME, cv, whereClause, whereArgs);

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

		return rows;
	}
	public void selectAll() {

	}

	public void close() {
		// 关闭数据库连接
		db.close();
		db = null;
	}
}
