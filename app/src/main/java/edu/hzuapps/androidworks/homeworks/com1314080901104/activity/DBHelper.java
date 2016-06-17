package com.baidu.android.voicedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by DUDU on 2016/5/25.
 */
public class DBHelper extends SQLiteOpenHelper {
    final static int DB_VERSION = 2;
    private static final String DB_Name="MyAccount";
    private SQLiteDatabase db;
    Context context;//表示应用程序的上下文，在构造函数中保存住

    public DBHelper(Context context){
        super(context,DB_Name,null,DB_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){// 创建数据库后，对数据库的操作
//         db=openOrCreateDatabase("myaccount.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS Account");//创建account表
        String sql="create table Account(_id integer primary key autoincrement,result TEXT,comsumedate timestamp not null default CURRENT_TIMESTAMP)";

        db.execSQL(sql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    //  更改数据库版本的操作
        String sql=" DROP TABLE IF EXISTS Account";
        db.execSQL(sql);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //  每次成功打开数据库后首先被执行
    }

}
