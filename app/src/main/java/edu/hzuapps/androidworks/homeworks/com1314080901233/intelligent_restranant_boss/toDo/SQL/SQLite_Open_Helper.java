package com.example.intelligent_restranant_boss.toDo.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Linco_325 on 2016/4/11.
 */
//用来建立一个数据库,构造对象就是对该数据库封装操作的实例, 以及建表
public class SQLite_Open_Helper extends SQLiteOpenHelper {
    //构造类时建立名为R.string.DataBase_name的数据库
    public SQLite_Open_Helper(Context context){
        //通过其他地方传过来的Context,如果没有现成的上下文,直接用getApplicationContext 用全局的context或者用application都是可以的
        super(context,Notes_DataBase_OP.DB_NAME,null,1);
    }
    //建表 编号,标题,时间
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_str="create table "+ Notes_DataBase_OP.TABLE_NAME+"( "
                +Notes_DataBase_OP.TABLE_ITEM_1+" integer PRIMARY KEY autoincrement,"
                +Notes_DataBase_OP.TABLE_ITEM_2+" text,"+Notes_DataBase_OP.TABLE_ITEM_3
                +" text,"+Notes_DataBase_OP.TABLE_ITEM_4+" text,"+Notes_DataBase_OP.TABLE_ITEM_5+" integer)";
        db.execSQL(sql_str);
        Log.d("create table",sql_str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //没有需要升级数据库,方法为空
    }
}
