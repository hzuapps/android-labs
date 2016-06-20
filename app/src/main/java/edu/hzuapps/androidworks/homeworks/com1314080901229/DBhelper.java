package com.example.administrator.fingergame;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/18.
 */
public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, null, null, 1);
    }

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table  IF NOT EXISTS guess(all_data text not null," +
                "win_data text not null,fail_data text not null,ping_data text not null)";
        String sql1 = "insert into user values ('33','11','22','44')";
        String sql2 = "insert into user values ('11','22','44','55')";

        db.execSQL(sql);
//        db.execSQL(sql1);
//        db.execSQL(sql2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
