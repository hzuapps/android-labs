package com.example.ljl.mygps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Net1314080903234Db extends SQLiteOpenHelper {

    public Db(Context context) {
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
