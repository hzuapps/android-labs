package edu.hzuapps.androidworks.homeworks.Net134080903234;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ljl on 2016/4/18 0018.
 */
public class Net1314080903234Db extends SQLiteOpenHelper {

    public Net1314080903234Db(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table whereYou(" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "longitude text DEFAULT \"\"," +
                "latitude text DEFAULT \"\")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
