package single_thread_download.skyward.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skyward on 2016/6/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String SQL_CREATE = "create table thread_info(_id integer primary key," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    public static final String SQL_DROP = "drop table if exists thread_info";
    public static final String DB_NAME = "download.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
